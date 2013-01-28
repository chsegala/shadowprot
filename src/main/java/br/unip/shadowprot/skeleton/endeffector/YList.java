package br.unip.shadowprot.skeleton.endeffector;

import java.util.HashSet;
import java.util.Set;

import javax.vecmath.Point3d;

import lombok.Data;

@Data
class YList implements ListValueable {
	private double y;
	private Set<ZList> list = new HashSet<>();
	
	public YList(Point3d endEffector, double alpha, double beta, double gamma) {
		y = endEffector.y;
		list.add(new ZList(endEffector, alpha, beta, gamma));
	}
	
	@Override
	public double getValue() {
		return y;
	}

	public void put(Point3d endEffector, double alpha, double beta, double gamma) {
		ZList zList = ListUtils.getList(list, endEffector.z);
		if(zList == null)
			list.add(new ZList(endEffector, alpha, beta, gamma));
		else
			zList.put(endEffector, alpha, beta, gamma);
	}
}
