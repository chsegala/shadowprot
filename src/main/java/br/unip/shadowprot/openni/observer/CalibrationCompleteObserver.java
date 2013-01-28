package br.unip.shadowprot.openni.observer;

import javax.vecmath.Point3d;

import lombok.Data;

import org.OpenNI.CalibrationProgressEventArgs;
import org.OpenNI.CalibrationProgressStatus;
import org.OpenNI.IObservable;
import org.OpenNI.IObserver;
import org.OpenNI.Point3D;
import org.OpenNI.PoseDetectionCapability;
import org.OpenNI.SkeletonCapability;
import org.OpenNI.SkeletonJoint;
import org.OpenNI.StatusException;

import br.unip.shadowprot.dh.DHCalculator;
import br.unip.shadowprot.skeleton.LookupTable;
import br.unip.shadowprot.skeleton.SkeletonJoints;

/**
 * Observador que executa ações quando o esqueleto é calibrado
 * Observer to provide actions when the skeleton is calibrated<br/>
 * 
 * @author chsegala
 *
 */
@Data
public class CalibrationCompleteObserver implements
		IObserver<CalibrationProgressEventArgs> {
	
	private SkeletonCapability skeletonCap;
	private PoseDetectionCapability poseDetectionCap;
	private SkeletonJoints skeletonJoints;
	private DHCalculator calculator;
	private LookupTable lookupTable;

	public CalibrationCompleteObserver(SkeletonCapability skeletonCapability,
			PoseDetectionCapability poseCap, SkeletonJoints skeletonJoints, LookupTable lookupTable) {
		this.skeletonCap = skeletonCapability;
		this.poseDetectionCap = poseCap;
		this.skeletonJoints = skeletonJoints;
		this.lookupTable = lookupTable;
		this.calculator = lookupTable.getCalculator();
	}

	public void update(IObservable<CalibrationProgressEventArgs> observable,
			CalibrationProgressEventArgs args) {
		System.out.println("Calibraion complete: " + args.getStatus());

		try {
			String calibPose = skeletonCap.getSkeletonCalibrationPose();
			if (args.getStatus() == CalibrationProgressStatus.OK) {
				System.out.println("starting tracking " + args.getUser());
				skeletonCap.startTracking(args.getUser());
				skeletonJoints.startSampling();
				
				System.out.println("creating lookup table");
				populateCalculatorLengths();
				lookupTable.create();

			} else if (args.getStatus() != CalibrationProgressStatus.MANUAL_ABORT) {
				if (skeletonCap.needPoseForCalibration()) {
					poseDetectionCap.startPoseDetection(calibPose,
							args.getUser());
				} else {
					skeletonCap
							.requestSkeletonCalibration(args.getUser(), true);
				}
			}
		} catch (StatusException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Adiciona as informações de comprimento do braço para o calculo da tabela de pontos
	 * Adds arm's lengths to calculate the point lookup table
	 */
	private void populateCalculatorLengths() {
		while(!skeletonJoints.getJoints().containsKey(SkeletonJoint.RIGHT_HAND))
			Thread.yield();
		
		Point3D shoulder0 = skeletonJoints.getJoints().get(SkeletonJoint.RIGHT_SHOULDER).getPosition();
		Point3D elbow0 = skeletonJoints.getJoints().get(SkeletonJoint.RIGHT_ELBOW).getPosition();
		Point3D hand0 = skeletonJoints.getJoints().get(SkeletonJoint.RIGHT_HAND).getPosition();
		
		Point3d shoulder = convertToPoint(shoulder0);
		Point3d elbow = convertToPoint(elbow0);
		Point3d hand = convertToPoint(hand0);
		
		double arm = shoulder.distance(elbow);
		double forearm = elbow.distance(hand);
		
		calculator.setArmLength(arm);
		calculator.setForearmLength(forearm);
	}

	private Point3d convertToPoint(Point3D point) {
		double[] values = new double[]{
				point.getX(),
				point.getY(),
				point.getZ()
		};
		
		return new Point3d(values);
	}
}
