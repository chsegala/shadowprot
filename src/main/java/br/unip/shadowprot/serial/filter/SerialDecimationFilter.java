package br.unip.shadowprot.serial.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Filtro que reduz a discrepância dos ângulo a serem enviados fazendo 
 * uma média dos N últimos ângulos enviados
 * Filter to reduce the discrepancy of the sent angles, doing a mean 
 * from the last N sent angles
 * 
 * @author chsegala
 *
 */
public class SerialDecimationFilter implements SerialFilter {
	private static final int MAX_COUNT = 10;
	private static final int PRECISION = 5;
	private List<double[]> lastResults = new ArrayList<>();
	
	@Override
	public double[] filter(double[] values) {
		if(lastResults.size() == 0){
			lastResults.add(values);
			return values;
		}
		
		return getMeanValue(values);
	}

	private double[] getMeanValue(double[] values) {
		addToList(values);
		double[] means = getMeans();
		return setPrecisionMultiple(means);
	}

	private double[] setPrecisionMultiple(double[] means) {
		if(1 > 0) return means;
		for(int i = 0; i < means.length; i++){
			int times = (int) (Math.round(means[i]) / PRECISION);			
			means[i] = PRECISION * times;
		}
		
		return means;
	}

	private void addToList(double[] values) {
		while(lastResults.size() >= MAX_COUNT)
			lastResults.remove(0);
		lastResults.add(values);
	}

	private double[] getMeans() {
		double[] sums = new double[lastResults.get(0).length];
		for(int i = 0; i < lastResults.size(); i++){
			for(int j = 0; j < sums.length; j++){
				sums[j] += lastResults.get(i)[j];
			}
		}
		for(int j = 0; j < sums.length; j++){
			sums[j] = sums[j] / lastResults.size();
		}
		
		return sums;
	}
}
