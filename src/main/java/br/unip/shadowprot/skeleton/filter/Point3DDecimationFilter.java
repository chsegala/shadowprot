package br.unip.shadowprot.skeleton.filter;

import java.util.List;
import java.util.Map;

import org.OpenNI.Point3D;
import org.OpenNI.SkeletonJoint;
import org.OpenNI.SkeletonJointPosition;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

/**
 * Filtro da média dos ultimos pontos recebidos do Kinect para estabilizar as amostras.
 * Last samples' mean filter to stabilize the samples
 * 
 * @author chsegala
 *
 */
public class Point3DDecimationFilter implements Point3DFilter {
	private static final int MAX_SIZE = 10;
	private Multimap<SkeletonJoint, Point3D> joints = LinkedListMultimap.create();

	@Override
	public Point3D filterPoint(Point3D point,
			Map<SkeletonJoint, SkeletonJointPosition> joints,
			SkeletonJoint joint) {
		
		put(joint, point);
		return getAverageOfPositions(joint);
	}

	private Point3D getAverageOfPositions(SkeletonJoint joint) {
		int size = joints.get(joint).size();
		List<Point3D> points = (List<Point3D>) joints.get(joint);
		double[] x = new double[size];
		double[] y = new double[size];
		double[] z = new double[size];
		
		for(int i = 0; i < points.size(); i++){
			try{
				Point3D point = points.get(i);
				x[i] = point.getX();
				y[i] = point.getY();
				z[i] = point.getZ();
			}catch (IndexOutOfBoundsException e) {
				break;
			}
		}
		
		float xMean = (float) getMean(x);
		float yMean = (float) getMean(y);
		float zMean = (float) getMean(z);
		
		return new Point3D(xMean, yMean, zMean);
	}

	private double getMean(double[] x) {
		double sum = 0;
		for(int i = 0; i < x.length; i++)
			sum += x[i];
		
		return sum / x.length;
	}

	private void put(SkeletonJoint joint, Point3D point) {
		if(joints.get(joint) == null){
			joints.put(joint, point);
		}else{
			while(joints.get(joint).size() > MAX_SIZE)
				((List<?>)joints.get(joint)).remove(0);
			
			joints.get(joint).add(point);
		}
	}
}