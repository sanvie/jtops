import java.util.List;

import jtops.Edge;
import jtops.Face;
import jtops.Mesh;
import jtops.Point;
import jtops.Vector3D;
import jtops.Vertex;

public class jtopsMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		testMakeFace();
		testSplitFace();
		testWeldVertex1();
		testWeldVertex2();
		testSplitEdge1();
		testSplitEdge2();
		testSwapEdge();
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
	
	private static void testWeldVertex1() {
		System.out.println("testWeldVertex 1:");
		
		Mesh mesh = new Mesh();
		Point p0 = mesh.makePoint(new Vector3D(0,0,0));
		Point p1 = mesh.makePoint(new Vector3D(1,0,0));
		Point p2 = mesh.makePoint(new Vector3D(0,1,0));
		Face f = mesh.makeFace(p0, p1, p2);
		
		Vertex v = mesh.splitFace(f, new Vector3D(0.5, 0.2, 0));
		mesh.weldVertex(v);
		
		mesh.check();
		mesh.toOBJ("testWeldVertex1.obj");
		
		System.out.println();
		System.out.println();
	}
	
	private static void testWeldVertex2() {
		System.out.println("testWeldVertex 2:");
		
		Mesh mesh = new Mesh();
		Point p0 = mesh.makePoint(new Vector3D(0,0,0));
		Point p1 = mesh.makePoint(new Vector3D(1,0,0));
		Point p2 = mesh.makePoint(new Vector3D(0,1,0));
		mesh.makeFace(p0, p1, p2);
		
		List<Edge> e = p0.findEdges(p1);
		Vertex va = mesh.splitEdge(e.get(0), new Vector3D(0.5, 0, 0));
		
		e = va.getPoint().findEdges(p2);
		Vertex vb = mesh.splitEdge(e.get(0), new Vector3D(0.25, 0.5, 0));
		
		mesh.weldVertex(va);
		mesh.weldVertex(vb);
		
		mesh.check();
		mesh.toOBJ("testWeldVertex2.obj");
		
		System.out.println();
		System.out.println();
	}
	
	private static void testSplitEdge1() {
		System.out.println("testSplitEdge 1:");
		
		Mesh mesh = new Mesh();
		Point p0 = mesh.makePoint(new Vector3D(0,0,0));
		Point p1 = mesh.makePoint(new Vector3D(1,0,0));
		Point p2 = mesh.makePoint(new Vector3D(0,1,0));
		mesh.makeFace(p0, p1, p2);
		
		List<Edge> e = p0.findEdges(p1);
		mesh.splitEdge(e.get(0), new Vector3D(0.5, 0, 0));
		
		e = p0.findEdges(p2);
		mesh.splitEdge(e.get(0), new Vector3D(0, 0.5, 0));
		
		e = p1.findEdges(p2);
		mesh.splitEdge(e.get(0), new Vector3D(0.5, 0.5, 0));
		
		mesh.check();
		mesh.toOBJ("testSplitEdge1.obj");
		
		System.out.println();
		System.out.println();
	}
	
	private static void testSplitEdge2() {
		System.out.println("testSplitEdge 2:");
		
		Mesh mesh = new Mesh();
		Point p0 = mesh.makePoint(new Vector3D(0,0,0));
		Point p1 = mesh.makePoint(new Vector3D(1,0,0));
		Point p2 = mesh.makePoint(new Vector3D(0,1,0));
		mesh.makeFace(p0, p1, p2);
		
		List<Edge> e = p0.findEdges(p1);
		Vertex v = mesh.splitEdge(e.get(0), new Vector3D(0.5, 0, 0));
		
		e = v.getPoint().findEdges(p2);
		v = mesh.splitEdge(e.get(0), new Vector3D(0.25, 0.5, 0));
		
		mesh.check();
		mesh.toOBJ("testSplitEdge2.obj");
		
		System.out.println();
		System.out.println();
	}
	
	private static void testSwapEdge() {
		System.out.println("better to have glue before ...");
		
		System.out.println("testSwapEdge:");
		
		Mesh mesh = new Mesh();
		
		mesh.check();
		mesh.toOBJ("testSwapEdge.obj");
		
		System.out.println();
		System.out.println();
	}
}
