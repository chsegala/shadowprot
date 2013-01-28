package br.unip.shadowprot.skeleton.endeffector;

import java.util.HashSet;
import java.util.Set;

import javax.vecmath.Point3d;

public class EndEffector {
	private Set<XList> xList = new HashSet<>();

	public void put(Point3d endEffector, double alpha, double beta, double gamma) {
		if(ListUtils.hasAngles(xList, alpha, beta, gamma))
			return;
		
		XList xList = ListUtils.getList(this.xList, endEffector.x);
		if(xList == null)
			this.xList.add(new XList(endEffector, alpha, beta, gamma));
		else
			xList.put(endEffector, alpha, beta, gamma);
	}

	public double[] get(Point3d endEffector) {
		XList xList = ListUtils.getClosestValueList(this.xList, endEffector.x);
		YList yList = ListUtils.getClosestValueList(xList.getList(), endEffector.y);
		ZList zList = ListUtils.getClosestValueList(yList.getList(), endEffector.z);
		
		return new double[]{zList.getAlpha(), zList.getBeta(), zList.getGamma()};
	}

	public void printTable() {
		for(XList xList : this.xList){
			System.out.print(xList.getX());
			for(YList yList : xList.getList()){
				System.out.print("\t" + yList.getY());
				for(ZList zList : yList.getList()){
					System.out.print("\t" + zList.getValue());
					System.out.print(String.format("\t%.0f-%.0f-%.0f\n", zList.getAlpha(), zList.getBeta(), zList.getGamma()));
				}
			}
		}
	}
}
