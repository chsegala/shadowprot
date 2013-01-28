package br.unip.shadowprot.skeleton.endeffector;

import javax.vecmath.Point3d;

import lombok.Data;

@Data
class ZList implements ListValueable{
	private double z;
	private double alpha;
	private double beta;
	private double gamma;
	
	@Override
	public double getValue() {
		return z;
	}

	public ZList(Point3d endEffector, double alpha, double beta, double gamma) {
		z = endEffector.z;
		this.alpha = alpha;
		this.beta = beta;
		this.gamma = gamma;
	}

	public void put(Point3d endEffector, double alpha, double beta, double gamma) {
		z = endEffector.z;
		this.alpha = alpha;
		this.beta = beta;
		this.gamma = gamma;
	}
}
