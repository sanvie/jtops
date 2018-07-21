package jtops;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Vertex {
	/**
	 * @return the point associated with this vertex
	 */
	public Point getPoint() { return location; }
	
	/**
	 * @return the location of this vertex in 3D space
	 */
	public Vector3D getLocation() { return location.location; }
	
	/**
	 * The star half-edge is an incoming to this vertex half-edge
	 * If the vertex is on the boundary, the star half-edge is (by convention) part of the boundary loop
	 * (meaning its associated face is a NullFace)
	 * @return the star half-edge for this vertex
	 */
	public HalfEdge getStar() { return star; }
	
	/**
	 * @return true id the vertex is on a boundary
	 */
	public boolean isBoundary() { return star.face.isNull(); }
	
	
	/**
	 * @return all incident faces to this vertex
	 */
	public List<Face> getAllIncidentFaces() {
		ArrayList<Face> result = new ArrayList<Face>();
		
		HalfEdge he = star;
		do {
			if(!he.face.isNull()) { result.add(he.face); }
			he = he.next.getMate();
		} while(he != star);
		
		return Collections.unmodifiableList(result);
	}
	
	public List<HalfEdge> getAllIncidentHalfEdges() {
		ArrayList<HalfEdge> result = new ArrayList<HalfEdge>();
		
		HalfEdge he = star;
		do { result.add(he); he = he.next.getMate(); } while(he != star); 
		
		return Collections.unmodifiableList(result);
	}
	
	//
	//	IMPLEMENTATION
	//
	int Index = -1;
	
	/**
	 * Location of this vertex
	 */
	Point location = null;
	
	/**
	 * an incident half-edge to this vertex
	 * If the vertex is a boundary vertex, the Star half-edge is on the boundary 
	 * (meaning its associated face is a NullFace
	 */
	HalfEdge star = null;
	
	Vertex(Point p) {
		// add vertex change this.Location
		p.addVertex(this);
	}
	
	void setPoint(Point p) {
		location.removeVertex(this);
		
		// add vertex change this.Location
		p.addVertex(this);
	}
	
}
