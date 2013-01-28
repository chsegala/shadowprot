package br.unip.shadowprot.skeleton.endeffector;

import java.util.HashSet;
import java.util.Set;

import javax.vecmath.Point3d;

import lombok.Data;

@Data
class XList implements ListValueable{
	private double x;
	private Set<YList> list = new HashSet<>();
	
	public XList(Point3d endEffector, double alpha, double beta, double gamma) {
		x = endEffector.x;
		list.add(new YList(endEffector, alpha, beta, gamma));
	}
	
	@Override
	public double getValue() {
		return x;
	}

	public void put(Point3d endEffector, double alpha, double beta, double gamma) {
		YList yList = ListUtils.getList(list, endEffector.y);
		if(yList == null)
			list.add(new YList(endEffector, alpha, beta, gamma));
		else
			yList.put(endEffector, alpha, beta, gamma);
	}
}
