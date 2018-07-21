package jtops;

import jtops.Face.NullFace;

public class Edge {
	
	/**
	 * @return the first half-edge making this edge
	 */
	public HalfEdge getHE0() { return he[0]; }
	
	/**
	 * @return the second half-edge making this edge
	 */
	public HalfEdge getHE1() { return he[1]; }
	
	/**
	 * @return true if this edge is on a boundary
	 */
	public boolean isBoundary() { return he[0].face.isNull() || he[1].face.isNull(); }
	
	/**
	 * @return the NullFace that is the boundary of this edge or null if this edge is not on a boundary
	 */
	public NullFace getBoundary() { 
		if (he[0].face.isNull()) return (NullFace) he[0].face; 
		if (he[1].face.isNull()) return (NullFace) he[1].face;
		return null;
	}
	
	//
	//		IMPLEMENTATION
	//
	
	HalfEdge[] he = {null, null};
	
	Edge(HalfEdge he0, HalfEdge he1) {
		he[0] = he0;
		he[1] = he1;
		he0.edge = this;
		he1.edge = this;
	}
	
}
