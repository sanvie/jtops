package jtops;

public class Vector3D {
	
	public double X = 0;
	public double Y = 0;
	public double Z = 0;
	
	/**
	 * Build a new null vector
	 */
	public Vector3D() {	X = 0; Y = 0; Z = 0; }
	
	/**
	 * Build a new vector copying another one
	 * @param v vector to copy
	 */
	public Vector3D(Vector3D v) { X = v.X; Y = v.Y; Z = v.Z; }
	
	/**
	 * Build a new vector with 3 values
	 * @param x the X component
	 * @param y the Y component
	 * @param z the Z component
	 */
	public Vector3D(double x, double y, double z) { X = x; Y = y; Z = z; }

	
	/**
	 * @return the squared length of the vector
	 */
	public double length2() { return (X * X + Y * Y + Z * Z); }
	
	/**
	 * @return the length of the vector
	 */
	public double length() { return Math.sqrt(length2()); }
	
	/**
	 * Compute the dot product of two vector
	 * @param a first vector
	 * @param b second vector
	 * @return the dot product of a with b
	 */
	public static double dot(Vector3D a, Vector3D b) { return a.X * b.X + a.Y * b.Y + a.Z * b.Z; }
	
	/**
	 * Compute the cross product of two vectors
	 * @param a first vector
	 * @param b second vector
	 * @return the cross product of a with b
	 */
	public static Vector3D cross(Vector3D a, Vector3D b) { 
		return new Vector3D(
				a.Y * b.Z - a.Z * b.Y,
				-a.X * b.Z + a.Z * b.X,
				a.X * b.Y - a.Y * b.X);
	}
	
	
	/**
	 * Divide a vector by a scalar value
	 * @param v the vector
	 * @param a the scalar value
	 * @return the result of dividing all components of v with a 
	 */
	public static Vector3D divide(Vector3D v, double a) {
		assert a != 0: "Vector3D:divide(Vector3D a, double divisisor), division by zero is not allowed !";
		
		Vector3D ret = new Vector3D(v);
		ret.X /= a;
		ret.Y /= a;
		ret.Z /= a;
		
		return ret;
	}
	
	/**
	 * Multiply a vector by a scalar
	 * @param v a vector
	 * @param a a scalar
	 * @return the result of multiplying all components of v by a
	 */
	public static Vector3D multiply(Vector3D v, double a) {
		Vector3D ret = new Vector3D(v);
		ret.X *= a;
		ret.Y *= a;
		ret.Z *= a;
		return ret;
	}
	
	/**
	 * Substract two vectors
	 * @param a first vector
	 * @param b second vector
	 * @return the result of substracting b to a
	 */
	public static Vector3D substract(Vector3D a, Vector3D b) {
		Vector3D ret = new Vector3D(a);
		ret.X -= b.X;
		ret.Y -= b.Y;
		ret.Z -= b.Z;
		return ret;
	}
	
	/**
	 * Normalize a vector
	 * @param v vector to normalize
	 * @return the normalized vector or null if ||v|| = 0
	 */
	public static Vector3D normalize(Vector3D v) {
		double l = v.length();
		if (l != 0) return new Vector3D(v.X/l, v.Y/l, v.Z/l);
		else return null;
	}
	
	public String toString() { return "("+X + "," + Y + "," + Z+")"; }
}
