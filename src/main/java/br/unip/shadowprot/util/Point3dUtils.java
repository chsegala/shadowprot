package br.unip.shadowprot.util;

import javax.vecmath.Point3d;

import org.OpenNI.Point3D;

public class Point3dUtils {
	public static Point3d round(Point3d endEffector) {
		return round(endEffector, 5);
	}
	
	public static Point3d round(Point3d endEffector, int decimalPlaces) {
		endEffector.x = round(endEffector.x, decimalPlaces);
		endEffector.y = round(endEffector.y, decimalPlaces);
		endEffector.z = round(endEffector.z, decimalPlaces);
		
		return endEffector;
	}
	
	public static Point3D round(Point3D point) {
		float x = (float) round(point.getX(), 2);
		float y = (float) round(point.getY(), 2);
		float z = (float) round(point.getZ(), 2);
		
		return new Point3D(x, y, z);
	}

	public static double round(double value, int decimalPlaces) {
		int multiplier = (int) (1 * Math.pow(10, decimalPlaces));
		return Math.floor(value * multiplier) / multiplier;
	}
}
