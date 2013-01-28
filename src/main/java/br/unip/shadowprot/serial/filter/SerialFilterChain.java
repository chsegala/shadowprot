package br.unip.shadowprot.serial.filter;

import org.springframework.stereotype.Component;

@Component
public class SerialFilterChain implements SerialFilter {
	private SerialFilter filter1 = new SerialDecimationFilter();
	private SerialFilter filter2 = new SerialStabilizerFilter();

	@Override
	public double[] filter(double[] values) {
		double[] newValues = filter2.filter(values);
		//newValues = filter2.filter(values);
		return newValues;
	}

}
