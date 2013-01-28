package br.unip.shadowprot;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.vecmath.Point3d;

import org.OpenNI.Context;
import org.OpenNI.SkeletonCapability;
import org.OpenNI.StatusException;
import org.OpenNI.UserGenerator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import br.unip.shadowprot.serial.SerialConnector;
import br.unip.shadowprot.serial.filter.SerialFilter;
import br.unip.shadowprot.skeleton.LookupTable;
import br.unip.shadowprot.skeleton.SkeletonJoints;
import br.unip.shadowprot.ui.PreviewFrame;
import br.unip.shadowprot.ui.StatusFrame;

/**
 * Init point of application, creates the frame and the preview, then starts
 * tracking user's skeleton. <br/>
 * Ponto inicial da aplicação, cria o frame e o previsualizador, então começa a
 * capturar o esqueleto do usuário
 * 
 * @author chsegala
 * 
 */
public class App {
	private PreviewFrame previewFrame;
	private StatusFrame statusFrame;
	private SkeletonJoints skeletonJoints;
	private LookupTable lookupTable;

	private boolean shouldRun = true;
	private SerialConnector serialConnector;
	private boolean isConnected = false;
	private SkeletonCapability skeletonCapability;
	private UserGenerator userGenerator;
	private SerialFilter serialFilter;

	private App() throws BeansException, StatusException {
		ApplicationContext appContext = new AnnotationConfigApplicationContext(
				"br.unip.shadowprot.config.**");
		previewFrame = appContext.getBean(PreviewFrame.class);

		previewFrame.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent arg0) {
			}

			public void keyReleased(KeyEvent arg0) {
			}

			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
					shouldRun = false;
				}
			}
		});

		statusFrame = appContext.getBean(StatusFrame.class);
		skeletonJoints = appContext.getBean(SkeletonJoints.class);
		appContext.getBean(Context.class).startGeneratingAll();
		lookupTable = appContext.getBean(LookupTable.class);

		serialConnector = appContext.getBean(SerialConnector.class);
		skeletonCapability = appContext.getBean(SkeletonCapability.class);
		userGenerator = appContext.getBean(UserGenerator.class);
		serialFilter = appContext.getBean(SerialFilter.class);
		statusFrame.setVisible(true);
		previewFrame.setVisible(true);
		connectSerial();

		run();
	}

	private void connectSerial() {
		try {
			serialConnector.Connect("/dev/ttyACM0");
			isConnected = true;
		} catch (Exception e) {
			System.out.println("No Serial Found, no data will be sent.");
		}
	}

	void run() {
		while (shouldRun) {
			int user;
			
			previewFrame.getViewer().updateDepth();
			previewFrame.getViewer().repaint();
			
			try {
				if(userGenerator.getUsers().length < 1)
					continue;
				
				user = userGenerator.getUsers()[0];
			} catch (StatusException e) {
				continue;
			}
			
			if (skeletonCapability.isSkeletonTracking(user)) {
				Point3d handPosition = skeletonJoints.getHandPosition();
				if(handPosition == null) continue;
				double[] statusData = lookupTable.get(handPosition);
				statusData = serialFilter.filter(statusData);
				statusFrame.setValues(statusData);
				statusFrame.setHandValue(handPosition);
				
				if (isConnected) {
					byte[] sendData = new byte[] { (byte) statusData[0],
							(byte) statusData[1], (byte) statusData[2] };
					serialConnector.send(sendData);
				}
			}

			Thread.yield();
		}
		previewFrame.dispose();
	}

	public static void main(String s[]) throws BeansException, StatusException {
		new App();
	}
}
