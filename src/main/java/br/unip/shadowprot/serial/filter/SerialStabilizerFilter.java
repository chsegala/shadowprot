package br.unip.shadowprot.serial.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Filtro que aguarda o conjunto de ângulos se estabilizar 
 * antes de enviar um novo conjunto de ângulos ao arduíno
 * 
 * Filter which awaits the angle set to stabilize before
 * sending the new set to arduino
 * 
 * @author chsegala
 *
 */
public class SerialStabilizerFilter implements SerialFilter {
	private static final int MAX_SIZE = 10;
	private List<double[]> lastValues = new ArrayList<>();
	private double[] lastValidValue = new double[3];
	
	@Override
	public double[] filter(double[] values) {
		put(values);
		if(allValuesAreEqual()){
			lastValidValue = lastValues.get(0);
		}
		
		return lastValidValue;
	}

	private boolean allValuesAreEqual() {
		double[] firstValue = lastValues.get(0);
		for(int i = 1; i < lastValues.size(); i++){
			double[] currentValue = lastValues.get(i);
			for(int j = 0; j < currentValue.length; j++){
				int fvalue = (int) (firstValue[j] * 100);
				int cvalue = (int) (currentValue[j] * 100);
				
				if(fvalue != cvalue) return false;
			}
		}
		return true;
	}

	private void put(double[] values) {
		while(lastValues.size() > MAX_SIZE)
			lastValues.remove(0);
		lastValues.add(values);
	}

}
