package br.unip.shadowprot.skeleton.filter;

import java.util.Map;

import org.OpenNI.Point3D;
import org.OpenNI.SkeletonJoint;
import org.OpenNI.SkeletonJointPosition;
import org.springframework.stereotype.Component;

@Component
public class Point3DFilterChain implements Point3DFilter {

	private Point3DFilter filter2 = new Point3DDecimationFilter();
	private Point3DFilter filter1 = new Point3DIntegerFilter();
	
	@Override
	public Point3D filterPoint(Point3D point,
			Map<SkeletonJoint, SkeletonJointPosition> joints,
			SkeletonJoint joint) {
		
		point = filter1.filterPoint(point, joints, joint);
		point = filter2.filterPoint(point, joints, joint);
		
		return point;
	}

}
