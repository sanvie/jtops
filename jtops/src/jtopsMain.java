import jtops.Mesh;
import jtops.Point;
import jtops.Vector3D;

public class jtopsMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		testMakeFace();
		
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
}
