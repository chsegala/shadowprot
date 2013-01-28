package br.unip.shadowprot.openni.observer;

import org.OpenNI.IObservable;
import org.OpenNI.IObserver;
import org.OpenNI.UserEventArgs;

import br.unip.shadowprot.skeleton.SkeletonJoints;

/**
 * Observer to provide actions when the user leaves the scene<br/>
 * Observador que executa ações quando o usuário deixa a cena
 * 
 * @author chsegala
 *
 */
public class LostUserObserver implements IObserver<UserEventArgs> {
	private SkeletonJoints skeletonJoints;

	public LostUserObserver(SkeletonJoints skeletonJoints) {
		this.skeletonJoints = skeletonJoints;
	}

	@Override
	public void update(IObservable<UserEventArgs> arg0, UserEventArgs arg1) {
		skeletonJoints.stopSampling();
		skeletonJoints.getJoints().clear();
	}
}
