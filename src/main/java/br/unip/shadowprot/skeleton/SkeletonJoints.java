package br.unip.shadowprot.skeleton;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.vecmath.Point3d;

import org.OpenNI.DepthGenerator;
import org.OpenNI.Point3D;
import org.OpenNI.SkeletonCapability;
import org.OpenNI.SkeletonJoint;
import org.OpenNI.SkeletonJointPosition;
import org.OpenNI.StatusException;
import org.OpenNI.UserGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import br.unip.shadowprot.dh.DHCalculator;
import br.unip.shadowprot.skeleton.filter.Point3DFilter;
import br.unip.shadowprot.util.Point3dUtils;

/**
 * Bean que encapsula as ações e as informações junções do esqueleto.
 * Bean to hold actions and skeleton's joints' informations.<br/>
 * 
 * @author chsegala
 * 
 */
public class SkeletonJoints {
	@Autowired
	private SkeletonCapability skeletonCapability;
	@Autowired
	private DepthGenerator depthGenerator;
	@Autowired
	private UserGenerator userGenerator;
	@Autowired
	private DHCalculator calculator;
	@Autowired
	private Point3DFilter filter;

	private Map<SkeletonJoint, SkeletonJointPosition> joints = 
		Collections.synchronizedMap(new HashMap<SkeletonJoint, SkeletonJointPosition>());
	
	private boolean shouldRun = false;

	/**
	 * Amostrador das juntas do esqueleto, busca as informações do Kinect em um intervalo de tempo
	 * Samples from Kinect all the joint positions every time interval
	 * 
	 * @author chsegala
	 *
	 */
	private class Sampler implements Runnable {

		public void run() {
			while (shouldRun) {
				getJoints();
				try{Thread.sleep(100);}catch(InterruptedException e){}
			}
		}

		public void getJoint(int user, SkeletonJoint joint)
				throws StatusException {
			SkeletonJointPosition pos = skeletonCapability.getSkeletonJointPosition(user, joint);

			if (pos.getPosition().getZ() != 0) {
				Point3D point = depthGenerator.convertRealWorldToProjective(pos.getPosition());
				point = filter.filterPoint(point, joints, joint);
				SkeletonJointPosition sjp = new SkeletonJointPosition(point, pos.getConfidence());

				joints.put(joint, sjp);
			} else {
				joints.put(joint, new SkeletonJointPosition(new Point3D(), 0));
			}
		}

		public Map<SkeletonJoint, SkeletonJointPosition> getJoints() {
			try {
				if (userGenerator.getUsers().length < 1)
					return joints;
				int user = userGenerator.getUsers()[0];

				getJoint(user, SkeletonJoint.HEAD);
				getJoint(user, SkeletonJoint.NECK);

				getJoint(user, SkeletonJoint.RIGHT_SHOULDER);
				getJoint(user, SkeletonJoint.RIGHT_ELBOW);
				getJoint(user, SkeletonJoint.RIGHT_HAND);

			} catch (StatusException ex) {
				ex.printStackTrace();
			}

			return joints;
		}

	}
	
	public Map<SkeletonJoint, SkeletonJointPosition> getJoints(){
		return joints;
	}

	public void startSampling() {
		shouldRun = true;
		Thread t = new Thread(new Sampler());
		t.setName("sampler");
		t.setPriority(1);
		t.start();
	}

	public void stopSampling() {
		shouldRun = false;
	}

	public Point3d getHandPosition() {
		try {
			SkeletonJointPosition hand = getJoints().get(
					SkeletonJoint.RIGHT_HAND);
			SkeletonJointPosition shoulder = getJoints().get(
					SkeletonJoint.RIGHT_SHOULDER);

			if (hand.getConfidence() == 0 || shoulder.getConfidence() == 0)
				return null;

			Point3d point = new Point3d(
					hand.getPosition().getX() - shoulder.getPosition().getX(),
					hand.getPosition().getY() - shoulder.getPosition().getY(),
					hand.getPosition().getZ() - shoulder.getPosition().getZ());

			point = Point3dUtils.round(point);
			
			if(point.y > 0)
				point.y = 0;
			
			if(point.z > 0)
				point.z = 0;
			
			point.x = Math.abs(point.x);
			point.y = Math.abs(point.y);
			point.z = Math.abs(point.z);
			
			return point;
		} catch (Exception e) {
			return new Point3d(calculator.getArmLength()
					+ calculator.getForearmLength(), 0, 0);
		}
	}
}
