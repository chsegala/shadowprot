package br.unip.shadowprot.util;

import javax.vecmath.Point3d;

public class AngleUtils {
	public static enum RotationAxis{
		X, Y, Z
	}
	
	public static Point3d getEndEffector(Point3d reference, double length, double alpha, double betha, double gamma){
		return rotateOnZ(rotateOnY(rotateOnX(reference, length, alpha), length, betha), length, gamma);
	}
	
	public static Point3d getEndEffector(Point3d reference, double length, double theta, RotationAxis rotationAxis){
		switch(rotationAxis){
		case Z:
			return rotateOnZ(reference, length, theta);
		case X:
			return rotateOnX(reference, length, theta);
		default:
			return rotateOnY(reference, length, theta);
		}
	}

	private static Point3d rotateOnY(Point3d reference, double length,
			double theta) {
		double x = reference.x + length * Math.cos(toRad(theta));
		double y = reference.y;
		double z = reference.z + length * Math.sin(toRad(theta));
		
		return new Point3d(x, y, z);
	}

	private static Point3d rotateOnZ(Point3d reference, double length,
			double theta) {
		double x = reference.x + length * Math.cos(toRad(theta));
		double y = reference.y + length * Math.sin(toRad(theta));
		double z = reference.z;
		
		return new Point3d(x, y, z);
	}
	
	private static Point3d rotateOnX(Point3d reference, double length,
			double theta) {
		double x = reference.x;
		double y = reference.y + length * Math.sin(toRad(theta));
		double z = reference.z + length * Math.cos(toRad(theta));
		
		return new Point3d(x, y, z);
	}
	
	private static double toRad(double deg){
		return deg * Math.PI / 180;
	}
}
