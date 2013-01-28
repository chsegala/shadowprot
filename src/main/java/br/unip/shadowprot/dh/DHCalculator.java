package br.unip.shadowprot.dh;

import javax.vecmath.Point3d;

import lombok.Data;

import org.springframework.stereotype.Component;

/**
 * calcula a posição final do atuador
 * calculates the end point effector's position
 * 
 * @author chsegala
 *
 */
@Component("dhCalculator")
@Data
public class DHCalculator {
	private double armLength = 1;
	private double forearmLength = 1;
	
	public Point3d calculate(double alpha, double beta, double gamma){
		DHParameters[] params = new DHParameters[]{
				new DHParameters(0, 0, 90, alpha),
				new DHParameters(0, 1 * armLength, 0, beta),
				new DHParameters(0, 0, -90, 0),
				new DHParameters(0, 1 * forearmLength, 0, gamma)
		};
		
		DHMatrix dhMatrix = new DHMatrix();
		return dhMatrix.calculate(params, new Point3d(0,0,0));
	}
}
