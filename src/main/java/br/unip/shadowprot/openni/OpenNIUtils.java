package br.unip.shadowprot.openni;

import java.nio.ShortBuffer;

/**
 * utility class to calc the histogram used to show the image from the NUI.<br/>
 * classe utilitária para calcular o histograma para exibir a imagem da NUI.
 * 
 * @author chsegala
 * 
 */
public class OpenNIUtils {
	private static int histogramSize = 10000;
	// creates a histogram to the size of the depth field
	private static float[] histogram = new float[histogramSize];

	/**
	 * Calculates the histogram for the current depth field
	 * 
	 * @param depth
	 *            the depth field image buffer
	 * @return
	 */
	public static float[] calcHistogram(ShortBuffer depth) {
		//reset
		for (int i = 0; i < histogram.length; ++i)
            histogram[i] = 0;
		
		// sends the depth buffer's cursor to the beginning
		depth.rewind();

		int points = 0;
		while (depth.remaining() > 0) {
			short depthVal = depth.get();
			if (depthVal != 0) {
				histogram[depthVal]++;
				points++;
			}
		}

		for (int i = 1; i < histogram.length; i++) {
			histogram[i] += histogram[i - 1];
		}

		if (points > 0) {
			for (int i = 1; i < histogram.length; i++) {
				histogram[i] = 1.0f - (histogram[i] / (float) points);
			}
		}

		return histogram;
	}

	public static void setHistogramSize(int histogramSize) {
		OpenNIUtils.histogramSize = histogramSize;
		histogram = new float[histogramSize];
	}

	private OpenNIUtils() {
	}
}
