package jtops;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Point {
	/**
	 * @param p a point
	 * @return a list of edges linking this point and point p
	 */
	public List<Edge> findEdges(Point p) {
		ArrayList<Edge> result = new ArrayList<Edge>();
		
		for(Vertex v: _vertices) {
			List<HalfEdge> hes = v.getAllIncidentHalfEdges();
			for(HalfEdge he : hes) { 
				if(p._vertices.contains(he.origin)) result.add(he.edge);
			}
		}
		return Collections.unmodifiableList(result);
	}
	
	
	//
	// 	IMPLEMENTATION
	//
	
	Vector3D location;
	
	// All vertices belonging to this point 
	private HashSet<Vertex> _vertices = new HashSet<Vertex>();
	
	/**
	 * @return the number of vertices associated with this point
	 */
	int getVerticesCount() { return _vertices.size(); }
	
	/**
	 * Add the vertex v to the point. Vertex v.Location is changed
	 * @param v the vertex to add to this point
	 */
	void addVertex(Vertex v) {
		v.location = this;
		_vertices.add(v);
	}
	
	/**
	 * Unlink the vertex v from this point
	 * The vertex v is not anymore associated with a point 
	 * @param v the vertex to remove from this point
	 */
	void removeVertex(Vertex v) {
		if(_vertices.contains(v)) {
			_vertices.remove(v);
			v.location = null;
		}
	}
	
	/**
	 * Create a new vertex linked to this point
	 * @return
	 */
	Vertex makeVertex() {
		Vertex v = new Vertex(this);
		_vertices.add(v);
		return v;
	}
	
	/**
	 * Create a new point
	 * @param location is the location of the point in 3D Space
	 */
	Point(Vector3D loc) {
		location = loc;
	}
}
