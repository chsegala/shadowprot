package br.unip.shadowprot.openni.observer;

import org.OpenNI.IObservable;
import org.OpenNI.IObserver;
import org.OpenNI.PoseDetectionCapability;
import org.OpenNI.SkeletonCapability;
import org.OpenNI.StatusException;
import org.OpenNI.UserEventArgs;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Observador que executa ações quando o usuário entra na cena
 * Observer to provide actions when the user enters the scene<br/>
 * 
 * @author chsegala
 *
 */
public class NewUserObserver implements IObserver<UserEventArgs> {
	
	@Autowired
	private SkeletonCapability skelCap;
	@Autowired
	private PoseDetectionCapability poseCap;
	
	public NewUserObserver(SkeletonCapability skelCap, PoseDetectionCapability poseCap) {
		this.skelCap = skelCap;
		this.poseCap = poseCap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.OpenNI.IObserver#update(org.OpenNI.IObservable,
	 * java.lang.Object)
	 */
	public void update(IObservable<UserEventArgs> observable, UserEventArgs args) {
		try {
			if (skelCap.needPoseForCalibration()) {
				String calibPose = skelCap.getSkeletonCalibrationPose();
				poseCap.startPoseDetection(calibPose, args.getId());
			} else {
				skelCap.requestSkeletonCalibration(args.getId(), true);
			}
		} catch (StatusException e) {
			e.printStackTrace();
		}
	}
}
