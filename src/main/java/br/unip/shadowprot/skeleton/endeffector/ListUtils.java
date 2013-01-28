package br.unip.shadowprot.skeleton.endeffector;

import java.util.Set;

public class ListUtils {
	public static <T extends ListValueable> T getList(Set<T> list, double desiredValue){	
		for(T valueItem : list){
			if(equals(valueItem.getValue(), desiredValue, .05))
				return valueItem;
		}
		return null;
	}
	
	public static <T extends ListValueable> T getClosestValueList(Set<T> list, double desiredValue) {
		T closest = list.iterator().next();
		for(T itemValue : list){
			double bestSoFar = Math.abs(desiredValue - closest.getValue());
			double thisIteration = Math.abs(desiredValue - itemValue.getValue());
			
			if(bestSoFar > thisIteration)
				closest = itemValue;
		}
		
		return closest;
	}

	public static <T extends ListValueable> boolean hasAngles(Set<XList> list, double alpha,
			double beta, double gamma) {
		for(XList xList : list){
			for(YList yList : xList.getList()){
				for(ZList zList : yList.getList())
				{
					if(zList.getAlpha() == alpha)
						if(zList.getBeta() == beta)
							if(zList.getGamma() == gamma)
								return true;
				}
			}
		}
		
		return false;
	}
	
	public static boolean equals(double v1, double v2, double precision){
		if(v2 >= v1 - precision && v2 <= v1 + precision)
			return true;
		return false;
	}
}
