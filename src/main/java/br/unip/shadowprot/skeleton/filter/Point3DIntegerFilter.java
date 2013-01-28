package br.unip.shadowprot.skeleton.filter;

import java.util.Map;

import org.OpenNI.Point3D;
import org.OpenNI.SkeletonJoint;
import org.OpenNI.SkeletonJointPosition;

/**
 * Filtro para aproveitar somente a parte inteira da amostra
 * Filter to obtain only integer part from sample
 * 
 * @author chsegala
 *
 */
public class Point3DIntegerFilter implements Point3DFilter {

	@Override
	public Point3D filterPoint(Point3D point,
			Map<SkeletonJoint, SkeletonJointPosition> joints,
			SkeletonJoint joint) {
		float x = (float) Math.floor(point.getX());
		float y = (float) Math.floor(point.getY());
		float z = (float) Math.floor(point.getZ());
		
		return new Point3D(x,y,z);
	}

}
