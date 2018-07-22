package jtops;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import jtops.Face.NullFace;
import jtops.Face.TriangleFace;

public class Mesh {

	public Set<Vertex> getVertices() { return Collections.unmodifiableSet(_vertices); }
	public Set<Edge> getEdges() { return Collections.unmodifiableSet(_edges); }
	public Set<Face> getFaces() { return Collections.unmodifiableSet(_faces); }
	public Set<NullFace> getBoundaries() { return Collections.unmodifiableSet(_boundaries); }
	
	/**
	 * @param position the position of the new point
	 * @return the new point
	 */
	public Point makePoint(Vector3D position) {
		return new Point(position);
	}
	
	/**
	 * 
	 * @param p0 first point
	 * @param p1 second point
	 * @param p2 third point
	 * @return the new triangular face
	 */
	public TriangleFace makeFace(Point p0, Point p1, Point p2) {
		TriangleFace f = new TriangleFace(this);
		_faces.add(f);
		
		NullFace nf = new NullFace(this);
		_boundaries.add(nf);
		
		Vertex v0 = p0.makeVertex();
		Vertex v1 = p1.makeVertex();
		Vertex v2 = p2.makeVertex();
		_vertices.add(v0);
		_vertices.add(v1);
		_vertices.add(v2);
		
		HalfEdge hea0 = new HalfEdge();
		HalfEdge hea1 = new HalfEdge();
		HalfEdge heb0 = new HalfEdge();
		HalfEdge heb1 = new HalfEdge();
		HalfEdge hec0 = new HalfEdge();
		HalfEdge hec1 = new HalfEdge();
		
		hea0 = new HalfEdge(); 
		hea1 = new HalfEdge(); 
		heb0 = new HalfEdge(); 
		heb1 = new HalfEdge(); 
		hec0 = new HalfEdge(); 
		hec1 = new HalfEdge(); 
		hea0.setONF(v0, heb0, f);
		hea1.setONF(v1, hec1, nf);
		heb0.setONF(v1, hec0, f);
		heb1.setONF(v2, hea1, nf);
		hec0.setONF(v2, hea0, f);
		hec1.setONF(v0, heb1, nf);
		
		Edge ea = new Edge(hea0, hea1);
		Edge eb = new Edge(heb0, heb1);
		Edge ec = new Edge(hec0, hec1);
		_edges.add(ea);
		_edges.add(eb);
		_edges.add(ec);
		
		f.halfEdge = hea0;
		nf.halfEdge = hea1;

		v0.star = hea1;
		v1.star = heb1;
		v2.star = hec1;
		
		return f;
	}
	
	
	/**
	 * fully unglue the face and then remove it from the mesh
	 * A null face cannot be unglued
	 * @param f the face to kill
	 * @return true if the operation was performed successfully, false otherwise
	 */
	public boolean killFace(Face f) {
		return false;
	}
	
	
	/**
	 * An edge can be flipped only if has a face on both sides
	 * edge flip can create invalid geometry so... take care
	 * @param e the edge to flip
	 * @return true if the operation was successful and false otherwise
	 */
	public boolean flipEdge(Edge e) {
		if(e.he[0].face.isNull() || e.he[1].face.isNull())
			return false;
		if(!e.he[0].face.isTriangle() || !e.he[1].face.isTriangle())
			return false;
		
		HalfEdge he0 = e.he[0];
		HalfEdge he1 = e.he[1];
		HalfEdge he0p = he0.getPrev();
		HalfEdge he0n = he0.next;
		HalfEdge he1p = he1.getPrev();
		HalfEdge he1n = he1.next;
		Face f0 = he0.face;
		Face f1 = he1.face;
		Vertex v0 = he0.origin;
		Vertex v2 = he1.origin;
		Vertex v1 = he1p.origin;
		Vertex v3 = he0p.origin;
		
		he1.next = he1p;
		he0n.next = he1;
		he0.next = he0p;
		he0p.next = he1n;
		he1.origin = v3;
		he0.origin = v1;
		he1n.face = f0;
		he0n.face = f1;
		
		if(!v0.star.face.isNull()) v0.star = he0p;
		if(!v2.star.face.isNull()) v2.star = he1p;
		
		return true;
	}
	

	/**
	 * Split and edge adding a vertex (and faces)
	 * This split is only allowed between two triangle face or on a boundary triangle face
	 * @param e the edge to split
	 * @param position the position of the new vertex/point
	 * @return the created vertex or null if the operation failed
	 */
	public Vertex splitEdge(Edge e, Vector3D position) {
		
		if(e.isBoundary()) {
			return _splitBoundaryEdge(e, position);
		} else {
			return _splitInternalEdge(e, position);
		}
	}
	
	
	/**
	 *  A vertex can be welded (removed) if its valence is 3
	 * @param v the vertex to weld
	 * @return true if the operation was successful and false otherwise
	 */
	public boolean weldVertex(Vertex v) {
		
		List<HalfEdge> halfEdges = v.getAllIncidentHalfEdges();
		if(halfEdges.size() != 3) return false;
		
		if(v.isBoundary()) return _weldBoundaryVertex(v, halfEdges);
		else return _weldInternalVertex(v, halfEdges);
	}
	
	
	/**
	 * Add a vertex inside a face and split it into new faces
	 * A null face cannot be split
	 * a triangle face is split into 3 faces
	 * a ngone is split into n faces
	 * @param f the face to split 
	 * @param position position of the new vertex
	 * @return the vertex created within the face 
	 */
	public Vertex splitFace(Face f, Vector3D position) {
		if(f.isNull()) return null;
		
		Point p = makePoint(position);
		Vertex v = p.makeVertex();
		_vertices.add(v);
		
		ArrayList<Edge> edges = new ArrayList<Edge>();
		ArrayList<Face> faces = new ArrayList<Face>();
		ArrayList<HalfEdge> hes = new ArrayList<HalfEdge>();
		
		HalfEdge he = f.halfEdge;
		do {
			HalfEdge he0 = new HalfEdge();
			HalfEdge he1 = new HalfEdge();
			Edge e = new Edge(he0, he1);
			_edges.add(e);
			edges.add(e);
			Face nf = new TriangleFace(this);
			_faces.add(nf);
			faces.add(nf);
			hes.add(he);
			
			he0.setONF(v, he, nf);
			he1.origin = he.origin;
			he = he.next;
		} while( he != f.halfEdge);
		
		for(int i=0; i<hes.size(); i++) {
			HalfEdge ihe = hes.get(i);
			Face iface = faces.get(i);
			Edge iedge = edges.get((i+1)%edges.size()); 
			iface.halfEdge = ihe;
			ihe.next = iedge.getHE1();
			iedge.getHE1().next = edges.get(i).getHE0();
			iedge.getHE1().face = iface;
		}
		
		v.star = edges.get(0).getHE1();
		
		return v;
	}
	
	/**
	 * Glue two edge together
	 * @param e1 first edge
	 * @param e2 second edge
	 * @return the newly formed edge or null if the operation failed
	 */
	public Edge glue(Edge e1, Edge e2) {
		return null;
	}
	
	
	/**
	 * 
	 * Unglue both half-edge of and edge, creating two new edges
	 * @param e the edge to unglue or null if the operation failed
	 * @return true if the operation was successful and false otherwise
	 */
	public boolean unglue(Edge e) {
		return false;
	}

	/**
	 * Separate an mesh into its unconnected sub meshes
	 * @return the list of unconnected meshes
	 */
	public List<Mesh> explode() {
		return new ArrayList<Mesh>();
	}
	
	
	/**
	 * @return true if no error was detected and false otherwise
	 */
	public boolean check() {
		boolean result = true;
		System.out.println("\tVertices count "+_vertices.size());
		System.out.println("\tEdges count "+_edges.size());
		System.out.println("\tFaces count "+_faces.size());
		System.out.println("\tBoundaries count "+_boundaries.size());
		
		// all stars properly set
		for(Vertex v : _vertices) {
			if(v.star == null) { System.out.println("ERROR: a vertex has no star "+v); } 
			if(v.star.getDestination() != v) { System.out.println("ERROR: vertex (v) star is not pointing toward (v) "+v); } 
		}
		
		// all faces properly closed
		for(Face f: _faces) {
			if(f.halfEdge == null) { System.out.println("ERROR: a face has no half-edge"); }
			if(f.isTriangle()) { 
				if(f.halfEdge.next.next.next != f.halfEdge) { System.out.println("ERROR: a face is not properly closed"); }  
			}
		}
		
		// boundaries closed and well connected
		for(NullFace nf : _boundaries) {
			HalfEdge he = nf.halfEdge;
			int count = 0;
			do {
				he = he.next;
				count++;
			} while( he != nf.halfEdge && count < _edges.size() +1 );
			if(he != nf.halfEdge || count> _edges.size()) { System.out.println("Boundary not closed"); }
		}
		
		return result;
	}
	
	
	public void toOBJ(String filename) {
		
		StringBuilder sb = new StringBuilder();
		int index = 1;
		for(Vertex v : _vertices) {
			v.Index = index++;
			Vector3D position = v.location.location;
			sb = sb.append("v "+position.X+" "+position.Y+" "+position.Z).append("\r\n");
		}
		for(Face f : _faces) {
			HalfEdge he = f.halfEdge;
			sb = sb.append("f ");
			do {
				sb = sb.append(he.origin.Index).append(" ");
				he = he.next;
			} while(he != f.halfEdge);
			sb = sb.append("\r\n");
		}
		
		try { FileUtils.writeStringToFile(new File(filename), sb.toString(), Charset.defaultCharset()); } catch(Exception e) { e.printStackTrace(); }
		
	}
	
	
	//		IMPLEMENTATION
	
	private HashSet<Vertex> _vertices = new HashSet<Vertex>();
	private HashSet<Edge> _edges = new HashSet<Edge>();
	private HashSet<Face> _faces = new HashSet<Face>();
	private HashSet<NullFace> _boundaries = new HashSet<NullFace>();
	
	private Vertex _splitBoundaryEdge(Edge e, Vector3D position) {
		HalfEdge he0 = e.getHE0().face.isNull() ? e.getHE1() : e.getHE0();
		if(!he0.face.isTriangle()) return null;
		
		NullFace boundary = e.getBoundary();
		
		Point p = makePoint(position);
		Vertex v = p.makeVertex();
		_vertices.add(v);
		
		HalfEdge he1 = he0.getMate();
		Vertex v1 = he0.getDestination();
		Face f = he0.face;
		HalfEdge he0n = he0.next;
		HalfEdge he0p = he0.getPrev();
		HalfEdge he1p = he1.getPrev();
		Vertex v2 = he0p.origin;
		
		Face nf = new TriangleFace(this);
		_faces.add(nf);
		
		HalfEdge hea0 = new HalfEdge();
		HalfEdge hea1 = new HalfEdge();
		Edge ea = new Edge(hea0, hea1);	
		HalfEdge heb0 = new HalfEdge();
		HalfEdge heb1 = new HalfEdge();
		Edge eb = new Edge(heb0, heb1);
		_edges.add(ea);
		_edges.add(eb);
		
		hea0.setONF(v, he0p, nf);
		hea1.setONF(v2, heb0, f);
		heb0.setONF(v, he0n, f);
		heb1.setONF(v1, he1, boundary);
		he0.next = hea0;
		he0n.next = hea1;
		he1p.next = heb1;
		v.star = heb1;
		nf.halfEdge = he0p;
		he0p.face = nf;
		he0.face = nf;
		f.halfEdge = he0n;
		
		return v;
	}
	
	private Vertex _splitInternalEdge(Edge e, Vector3D position) {
		
		_edges.remove(e);
		HalfEdge he0 = e.he[0];
		HalfEdge he1 = e.he[1];
		HalfEdge he0p = he0.getPrev();
		HalfEdge he0n = he0.next;
		HalfEdge he1p = he1.getPrev();
		HalfEdge he1n = he1.next;
		Vertex v0 = he0.origin;
		Vertex v1 = he1.origin;
		Face f0 = he0.face;
		Face f1 = he1.face;
		
		_faces.remove(f1);
		if(!v0.star.face.isNull()) v0.star = he0p.getMate();
		if(!v1.star.face.isNull()) v1.star = he1p;
		he1p.next = he0n;
		he0p.next = he1n;
		he1n.face = f0;
		he0n.face = f0;
		
		return splitFace(f0, position);
	}
	
	private boolean _weldBoundaryVertex(Vertex v, List<HalfEdge> halfEdges) {
		
		HalfEdge he0 = halfEdges.get(0);
		HalfEdge he1 = halfEdges.get(1);
		HalfEdge he2 = halfEdges.get(2);
		
		HalfEdge a = he0.getMate().next;
		HalfEdge b = he1.getPrev();
		
		_vertices.remove(v);
		_edges.remove(he2.edge);
		_faces.remove(he2.getMate().face);		
		he0.edge.he[0] = he1;
		
		a.next = b;
		he1.next = a;
		b.face = a.face;
		he1.face = a.face;
		
		return true;
	}
	
	private boolean _weldInternalVertex(Vertex v, List<HalfEdge> halfEdges) {
		
		// Half-edges are clockwise
		
		ArrayList<Edge> edges = new ArrayList<Edge>();
		ArrayList<Face> faces = new ArrayList<Face>();
		for(HalfEdge he : halfEdges) {
			edges.add(he.edge);
			faces.add(he.face);
		}
		
		_vertices.remove(v);
		_edges.removeAll(edges);
		_faces.removeAll(faces);
		
		HalfEdge he0 = halfEdges.get(0).getPrev();
		HalfEdge he1 = halfEdges.get(1).getPrev();
		HalfEdge he2 = halfEdges.get(2).getPrev();
		
		Vertex v0 = he0.origin;
		Vertex v1 = he1.origin;
		Vertex v2 = he2.origin;
		
		he0.next = he2;
		he1.next = he0;
		he2.next = he1;
		
		Face f = new TriangleFace(this);
		f.halfEdge = he0;
		he0.face = f;
		he1.face = f;
		he2.face = f;
		
		if(!v0.isBoundary()) v0.star = he1;
		if(!v1.isBoundary()) v1.star = he2;
		if(!v2.isBoundary()) v2.star = he0;
		
		
		return true;
	}
}
