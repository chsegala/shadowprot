package br.unip.shadowprot.skeleton;

import javax.vecmath.Point3d;

import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;

import br.unip.shadowprot.dh.DHCalculator;
import br.unip.shadowprot.skeleton.endeffector.EndEffector;
import br.unip.shadowprot.util.Combiner;
import br.unip.shadowprot.util.CombinerArgs;
import br.unip.shadowprot.util.Point3dUtils;

/**
 * Tabela que contém as informações de todos os pontos possíveis ao robô
 * 
 * Table to hold all the robot's reachable points
 * 
 * @author chsegala
 *
 */
@Data
public class LookupTable {
	public static enum CreationStatus{
		IDLE, CREATING, CREATED
	}
	
	private EndEffector points = new EndEffector();
	private int angleStep = 10;
	private CreationStatus status = CreationStatus.IDLE;
	
	@Autowired
	private DHCalculator calculator;
	
	public LookupTable create(){
		status = CreationStatus.CREATING;
		CombinerArgs[] combiners = new CombinerArgs[]{
				new CombinerArgs(0, 90, angleStep),
				new CombinerArgs(0, 90, angleStep),
				new CombinerArgs(0, 90, angleStep),
		};
		Combiner combiner = new Combiner(combiners);
		
		for(double[] angles : combiner){
			Point3d position = calculator.calculate(angles[0], angles[1], angles[2]);
			position = Point3dUtils.round(position);
			points.put(position, angles[0], angles[1], angles[2]);
		}
		
		status = CreationStatus.CREATED;
		return this;
	}

	public double[] get(Point3d endEffector){
		if(status != CreationStatus.CREATED)
			return new double[]{0,0,0};
		return points.get(endEffector);
	}
	
	public void printTable(){
		if(status != CreationStatus.CREATED) return;
		points.printTable();
	}
}
