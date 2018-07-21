package jtops;

public class Face {

	public static class NullFace extends Face {
		NullFace(Mesh mesh) { super(mesh); }
		
		@Override
		public boolean isNull() { return true; }
	}
	
	public static class TriangleFace extends Face {
		TriangleFace(Mesh mesh) { super(mesh); }
		
		@Override
		public boolean isTriangle() { return true; }
	}
	
	/**
	 * @return true if this face is a NullFace
	 */
	public boolean isNull() { return false; }
	
	/**
	 * @return true if this face is a triangle
	 */
	public boolean isTriangle() { return false; }
	
	/**
	 * @return the mesh this face is part of
	 */
	public Mesh getMesh() { return mesh; }
	
	/**
	 * @return a half-edge that is part of the internal loop of this face
	 */
	public HalfEdge getHalfEdge() { return halfEdge; }
	
	//
	//		IMPLEMENTATION
	//
	
	/**
	 * The mesh this face is part of
	 */
	Mesh mesh = null;
	
	/**
	 * A half edge part of the internal loop of this face
	 */
	HalfEdge halfEdge = null;
	
	Face(Mesh m) {
		mesh = m;
	}
	
	
}
