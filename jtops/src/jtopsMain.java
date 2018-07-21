import java.util.List;

import jtops.Edge;
import jtops.Face;
import jtops.Mesh;
import jtops.Point;
import jtops.Vector3D;

public class jtopsMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		testMakeFace();
		testSplitFace();
		testSplitEdge();
	}

	private static void testMakeFace() {
		System.out.println("testMakeFace:");
		
		Mesh mesh = new Mesh();
		Point p0 = mesh.makePoint(new Vector3D(0,0,0));
		Point p1 = mesh.makePoint(new Vector3D(1,0,0));
		Point p2 = mesh.makePoint(new Vector3D(0,1,0));
		mesh.makeFace(p0, p1, p2);
		
		mesh.check();
		
		System.out.println();
		System.out.println();
	}
	
	private static void testSplitFace() {
		System.out.println("testSplitFace:");
		
		Mesh mesh = new Mesh();
		Point p0 = mesh.makePoint(new Vector3D(0,0,0));
		Point p1 = mesh.makePoint(new Vector3D(1,0,0));
		Point p2 = mesh.makePoint(new Vector3D(0,1,0));
		Face f = mesh.makeFace(p0, p1, p2);
		
		mesh.splitFace(f, new Vector3D(0.5, 0.2, 0));
		mesh.check();
		mesh.toOBJ("testSplitFace.obj");
		
		System.out.println();
		System.out.println();
	}
	
	private static void testSplitEdge() {
		System.out.println("testSplitEdge:");
		
		Mesh mesh = new Mesh();
		Point p0 = mesh.makePoint(new Vector3D(0,0,0));
		Point p1 = mesh.makePoint(new Vector3D(1,0,0));
		Point p2 = mesh.makePoint(new Vector3D(0,1,0));
		mesh.makeFace(p0, p1, p2);
		
		List<Edge> e = p0.findEdges(p1);
		
		mesh.splitEdge(e.get(0), new Vector3D(0.5, 0, 0));
		mesh.check();
		mesh.toOBJ("testSplitEdge.obj");
		
		System.out.println();
		System.out.println();
	}
}
