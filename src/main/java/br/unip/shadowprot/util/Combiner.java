package br.unip.shadowprot.util;

import java.util.Arrays;
import java.util.Iterator;

public class Combiner implements Iterable<double[]>, Iterator<double[]> {
	private CombinerArgs[] args;
	private double[] actual;
	private double[] next;
	
	public Combiner(CombinerArgs... args){
		this.args = args;
		
		actual = new double[args.length];
		for(int i = 0; i < args.length; i++){
			actual[i] = args[i].getBegin();
		}
		
		next = increment(0, actual);
	}

	private double[] increment(int index, double[] values){
		if(index > values.length - 1)
			return null;
		
		values = Arrays.copyOf(values, values.length);
		values[index] += args[index].getStep();
		
		double end = args[index].getEnd();

		if(end >= 0){
			if(values[index] > end){
				values[index] = args[index].getBegin();
				return increment(index + 1, values);
			}
		}else{
			if(values[index] < end){
				values[index] = args[index].getBegin();
				return increment(index + 1, values);
			}
		}
		
		return values;
	}

	@Override
	public Iterator<double[]> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		return next != null;
	}

	@Override
	public double[] next() {
		double[] ret = actual;
		actual = next;
		next = increment(0, next);
		return ret;
	}

	@Override
	public void remove() { }

}
