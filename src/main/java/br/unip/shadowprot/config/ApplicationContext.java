package br.unip.shadowprot.config;

import org.OpenNI.Context;
import org.OpenNI.DepthGenerator;
import org.OpenNI.GeneralException;
import org.OpenNI.OutArg;
import org.OpenNI.PoseDetectionCapability;
import org.OpenNI.ScriptNode;
import org.OpenNI.SkeletonCapability;
import org.OpenNI.SkeletonProfile;
import org.OpenNI.StatusException;
import org.OpenNI.UserGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import br.unip.shadowprot.dh.DHCalculator;
import br.unip.shadowprot.openni.observer.CalibrationCompleteObserver;
import br.unip.shadowprot.openni.observer.LostUserObserver;
import br.unip.shadowprot.openni.observer.NewUserObserver;
import br.unip.shadowprot.skeleton.LookupTable;
import br.unip.shadowprot.skeleton.SkeletonJoints;
import br.unip.shadowprot.ui.UserTrackerWindowComponent;

/**
 * Configuration for Spring framework, all beans used bellow <br/>
 * Configuração do Spring, todos os beans usados estão abaixo.
 * 
 * @author chsegala
 *
 */
@Configuration
@ComponentScan(basePackages="br.unip.shadowprot.**")
public class ApplicationContext {
	private final static String SAMPLE_XML_FILE = "SamplesConfig.xml";

	@Bean
	@Scope(value = "singleton", proxyMode = ScopedProxyMode.NO)
	public Context context() throws GeneralException {
		OutArg<ScriptNode> scriptNode = new OutArg<ScriptNode>();
		return Context.createFromXmlFile(SAMPLE_XML_FILE, scriptNode);
	}

	@Bean
	@Scope(value = "singleton", proxyMode = ScopedProxyMode.NO)
	public DepthGenerator depthGenerator() throws GeneralException {
		return DepthGenerator.create(context());
	}

	@Bean
	@Scope(value = "singleton", proxyMode = ScopedProxyMode.NO)
	public UserGenerator userGenerator() throws GeneralException {
		UserGenerator userGenerator = UserGenerator.create(context());
		SkeletonCapability skelCap = userGenerator.getSkeletonCapability();
		PoseDetectionCapability poseCap = userGenerator
				.getPoseDetectionCapability();
		userGenerator.getNewUserEvent().addObserver(
				new NewUserObserver(skelCap, poseCap));
		userGenerator.getLostUserEvent().addObserver(
				new LostUserObserver(skeletonJoints()));

		return userGenerator;
	}

	@Bean
	public SkeletonJoints skeletonJoints() {
		return new SkeletonJoints();
	}

	@Bean
	public SkeletonCapability skeletonCapability() throws StatusException,
			GeneralException {
		SkeletonCapability skeletonCapability = userGenerator()
				.getSkeletonCapability();
		
		PoseDetectionCapability poseCap = userGenerator()
				.getPoseDetectionCapability();
		
		skeletonCapability.getCalibrationCompleteEvent().addObserver(
				new CalibrationCompleteObserver(skeletonCapability, poseCap, skeletonJoints(), lookupTable()));

		skeletonCapability.setSkeletonProfile(SkeletonProfile.ALL);
		skeletonCapability.setSmoothing(.1f);

		return skeletonCapability;
	}
	
	@Bean
	public DHCalculator calculator(){
		return new DHCalculator();
	}
	
	@Bean
	public LookupTable lookupTable(){
		return new LookupTable();
	}

	@Bean
	public PoseDetectionCapability poseDetectionCapability()
			throws StatusException, GeneralException {
		return userGenerator().getPoseDetectionCapability();
	}

	@Bean
	@Scope(value = "singleton", proxyMode = ScopedProxyMode.NO)
	public UserTrackerWindowComponent userTrackerWindowComponent()
			throws GeneralException {
		return new UserTrackerWindowComponent(depthGenerator());
	}
}
