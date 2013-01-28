package br.unip.shadowprot.skeleton.filter;

import java.util.Map;

import org.OpenNI.Point3D;
import org.OpenNI.SkeletonJoint;
import org.OpenNI.SkeletonJointPosition;

public interface Point3DFilter {
	Point3D filterPoint(Point3D point,
			Map<SkeletonJoint, SkeletonJointPosition> joints,
			SkeletonJoint joint);
}
