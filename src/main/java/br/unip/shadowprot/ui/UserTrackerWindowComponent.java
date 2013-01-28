package br.unip.shadowprot.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.nio.ShortBuffer;
import java.util.Map;

import org.OpenNI.Context;
import org.OpenNI.DepthGenerator;
import org.OpenNI.DepthMetaData;
import org.OpenNI.GeneralException;
import org.OpenNI.Point3D;
import org.OpenNI.SkeletonCapability;
import org.OpenNI.SkeletonJoint;
import org.OpenNI.SkeletonJointPosition;
import org.OpenNI.StatusException;
import org.OpenNI.UserGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import br.unip.shadowprot.openni.OpenNIUtils;
import br.unip.shadowprot.skeleton.SkeletonJoints;

/**
 * Component to preview what's happening on the NUI, shows user and skeleton<br/>
 * Componente para previsualizar o que acontece na NUI, exibe usuário e
 * esqueleto
 * 
 * @author chsegala
 * 
 */
public class UserTrackerWindowComponent extends Component {

	private static final long serialVersionUID = -325673387268310473L;

	@Autowired
	private UserGenerator userGen;
	@Autowired
	private Context context;
	@Autowired
	private SkeletonJoints skeletonJoints;
	@Autowired
	private SkeletonCapability skeletonCapability;
	private DepthGenerator depthGen;

	private boolean drawPixels = true;
	private boolean drawSkeleton = true;
	private boolean drawBackground = true;
	private boolean printId = true;
	private boolean printState = true;

	private byte[] imgbytes;
	private int width;
	private int height;

	public UserTrackerWindowComponent(DepthGenerator depthGen) {
		this.depthGen = depthGen;
		DepthMetaData depthMD = depthGen.getMetaData();

		width = depthMD.getXRes();
		height = depthMD.getYRes();
		imgbytes = new byte[width * height * 3];
	}

	private void drawLine(Graphics g, SkeletonJoint joint1, SkeletonJoint joint2) {
		Map<SkeletonJoint, SkeletonJointPosition> jointHash = skeletonJoints
				.getJoints();

		Point3D p1 = jointHash.get(joint1).getPosition();
		Point3D p2 = jointHash.get(joint2).getPosition();

		if (jointHash.get(joint1).getConfidence() == 0
				|| jointHash.get(joint2).getConfidence() == 0)
			return;

		g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(),
				(int) p2.getY());
	}

	private void drawSkeleton(Graphics g, int user) {
		drawLine(g, SkeletonJoint.HEAD, SkeletonJoint.NECK);
		drawLine(g, SkeletonJoint.NECK, SkeletonJoint.RIGHT_SHOULDER);
		drawLine(g, SkeletonJoint.RIGHT_SHOULDER, SkeletonJoint.RIGHT_ELBOW);
		drawLine(g, SkeletonJoint.RIGHT_ELBOW, SkeletonJoint.RIGHT_HAND);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	@Override
	public void paint(Graphics g) {

		if (drawPixels) {
			drawPixels(g);
		}
		try {
			if (userGen.getUsers().length < 1)
				return;

			int user = userGen.getUsers()[0];
			Color c = Color.blue;
			c = new Color(255 - c.getRed(), 255 - c.getGreen(),
					255 - c.getBlue());

			g.setColor(c);

			if (drawSkeleton && skeletonCapability.isSkeletonTracking(user)) {
				drawSkeleton(g, user);
			}

			if (printId) {
				printId(user, g);
			}
		} catch (StatusException e) {
			e.printStackTrace();
		}
	}

	private void drawPixels(Graphics g) {
		DataBufferByte dataBuffer = new DataBufferByte(imgbytes, width * height
				* 3);

		WritableRaster raster = Raster.createInterleavedRaster(dataBuffer,
				width, height, width * 3, 3, new int[] { 0, 1, 2 }, null);

		ColorModel colorModel = new ComponentColorModel(
				ColorSpace.getInstance(ColorSpace.CS_sRGB),
				new int[] { 8, 8, 8 }, false, false,
				ComponentColorModel.OPAQUE, DataBuffer.TYPE_BYTE);

		BufferedImage bimg = new BufferedImage(colorModel, raster, false, null);
		g.drawImage(bimg, 0, 0, null);
	}

	private void printId(int user, Graphics g) throws StatusException {
		Point3D com = depthGen.convertRealWorldToProjective(userGen
				.getUserCoM(user));
		String label = "";
		if (!printState) {
			label = new String("" + 0);
		} else if (skeletonCapability.isSkeletonTracking(user)) {
			// Tracking
			label = new String(user + " - Tracking");
		} else if (skeletonCapability.isSkeletonCalibrating(user)) {
			// Calibrating
			label = new String(user + " - Calibrating");
		}

		g.drawString(label, (int) com.getX(), (int) com.getY());
	}

	public void updateDepth() {
		try {
			context.waitAnyUpdateAll();

			DepthMetaData depthMD = depthGen.getMetaData();
			ShortBuffer depth = depthMD.getData().createShortBuffer();

			float[] histogram = OpenNIUtils.calcHistogram(depth);
			depth.rewind();

			while (depth.remaining() > 0) {
				int pos = depth.position();
				short pixel = depth.get();

				imgbytes[3 * pos] = 0;
				imgbytes[3 * pos + 1] = 0;
				imgbytes[3 * pos + 2] = 0;

				if (drawBackground || pixel != 0) {
					if (pixel != 0) {
						float histValue = histogram[pixel];
						imgbytes[3 * pos] = (byte) (histValue * Color.gray
								.getRed());
						imgbytes[3 * pos + 1] = (byte) (histValue * Color.gray
								.getGreen());
						imgbytes[3 * pos + 2] = (byte) (histValue * Color.gray
								.getBlue());
					}
				}
			}
		} catch (GeneralException e) {
			e.printStackTrace();
		}
	}

}
