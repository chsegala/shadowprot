package br.unip.shadowprot.util;

public class CombinerArgs {
	private double begin;
	private double end;
	private double step;
	
	public CombinerArgs(double begin, double end, double step){
		this.begin = begin;
		this.end = end;
		this.step = step;
	}
	
	public double getBegin() {
		return begin;
	}
	
	public double getEnd() {
		return end;
	}
	
	public double getStep() {
		return step;
	}
}
