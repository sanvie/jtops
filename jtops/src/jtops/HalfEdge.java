package jtops;

public class HalfEdge {

	/**
	 * @return the edge this half-edge belongs to
	 */
	public Edge getEdge() { return edge; }
	
	/**
	 * @return the face incident to this half-edge
	 */
	public Face getFace() { return face; }
	
	/**
	 * @return the origin vertex of this half-edge
	 */
	public Vertex getOrigin() { return origin; }
	
	/**
	 * @return the destination vertex
	 */
	public Vertex getDestination() { return getMate().origin; }
	
	/**
	 * @return the next half-edge in the face loop this half-edge belongs to
	 */
	public HalfEdge getNext() { return next; }
	
	
	/**
	 * @return the opposite half-edge
	 */
	public HalfEdge getMate() { return edge.he[0] == this ? edge.he[1] : edge.he[0]; }
	
	/**
	 * @return the previous half-edge in the face loop.
	 * If the face is a NullFace this will loop the entire boundary !
	 */
	public HalfEdge getPrev() {
		if (face.isTriangle()) return next.next;
		else {
			HalfEdge he = this;
			do { he = he.next; }while(he.next != this);
			return he;
		}
	}
	
	public Vector3D toVector3D() {
		return Vector3D.substract(getDestination().getLocation(), origin.getLocation());
	}
	
	
	//
	//		IMPLEMENTATION
	//
	Face face = null;
	HalfEdge next = null;
	Edge edge = null;
	Vertex origin = null;
	
	
	HalfEdge() {
		
	}
	
	void setONF(Vertex o, HalfEdge n, Face f) {
		origin = o;
		next = n;
		face = f;
	}
	
	public String toString() {
		return "[HE] "+getOrigin()+" -> "+getDestination();
	}
	
}
