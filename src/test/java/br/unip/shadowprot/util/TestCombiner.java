package br.unip.shadowprot.util;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class TestCombiner {

	@Test
	public void testCombiner() {
		Combiner c = new Combiner(new CombinerArgs(0, 10, 1));

		double i = 0;

		for (double[] values : c) {
			Assert.assertEquals(i, values[0]);
			i++;
		}
	}

	@Test
	public void testCombiner2Values() {
		Combiner c = new Combiner(new CombinerArgs(0, 10, 1), new CombinerArgs(0, 10, 1));

		List<double[]> values = new ArrayList<>();
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				values.add(new double[] { j, i });
			}
		}

		int index = 0;
		for (double[] v : c) {
			for(int i = 0; i < v.length; i++){
				Assert.assertEquals(values.get(index)[i], v[i]);
			}
			index++;
		}
	}
	
	@Test
	public void testCombiner2ValuesBegin5() {
		Combiner c = new Combiner(new CombinerArgs(5, 10, 1), new CombinerArgs(5, 10, 1));

		List<double[]> values = new ArrayList<>();
		for (int i = 5; i < 11; i++) {
			for (int j = 5; j < 11; j++) {
				values.add(new double[] { j, i });
			}
		}

		int index = 0;
		for (double[] v : c) {
			for(int i = 0; i < v.length; i++){
				Assert.assertEquals(values.get(index)[i], v[i]);
			}
			index++;
		}
	}
	
	@Test
	public void testCombiner3ValuesUntil90Step5() {
		Combiner c = new Combiner(new CombinerArgs(0, 91, 5), new CombinerArgs(0, 91, 5), new CombinerArgs(0, 91, 5));

		List<double[]> values = new ArrayList<>();
		for (int i = 0; i < 91; i+=5) {
			for (int j = 0; j < 91; j+=5) {
				for(int k = 0; k < 91; k+=5){
					values.add(new double[] { k, j, i });
				}
			}
		}

		int index = 0;
		for (double[] v : c) {
			for(int i = 0; i < v.length; i++){
				Assert.assertEquals(values.get(index)[i], v[i]);
			}
			index++;
		}
	}
}
