package br.unip.shadowprot.dh;

import static java.lang.Math.PI;

import javax.vecmath.Point3d;

import org.ejml.simple.SimpleMatrix;

import br.unip.shadowprot.util.Point3dUtils;

/**
 * calcula a matrix de Denavit-Hartenberg
 * calculates the Denavit-Hartenberg matrix
 * 
 * @author chsegala
 *
 */
public class DHMatrix {
	public Point3d calculate(DHParameters[] params, Point3d reference){
		double[] ref = new double[]{ reference.x, reference.y, reference.z };
		SimpleMatrix matrix = getDHMatrix(params);
		SimpleMatrix position = getReferenceMatrix(ref);
		
		SimpleMatrix result = matrix.mult(position);
		return convertToPoint(result);
	}
	
	public Point3d calculate(DHParameters params, Point3d reference, double theta){
		double[] ref = new double[]{ reference.x, reference.y, reference.z };
		return calculate(params, ref, theta);
	}
	
	public Point3d calculate(DHParameters params, double[] reference, double theta){
		SimpleMatrix dhMatrix = new SimpleMatrix(getDHMatrix(params));
		SimpleMatrix referencePoint = getReferenceMatrix(reference);
		
		SimpleMatrix endEffectorMatrix = dhMatrix.mult(referencePoint);
		return convertToPoint(endEffectorMatrix);
	}
	
	private SimpleMatrix getReferenceMatrix(double[] reference) {
		SimpleMatrix base = new SimpleMatrix(new double[][] { reference });
		SimpleMatrix ref = new SimpleMatrix(1, 4);
		ref.set(1);
		ref.insertIntoThis(0, 0, base);
		
		ref.reshape(4, 1);
		return ref;
	}

	public double[][] getDHMatrix(DHParameters params){
		double alpha = params.getTwist();
		double offset = params.getOffset();
		double length = params.getLength();
		double theta = params.getTheta();
		
		//denavit hartenberg
		double[][] protMatrix = new double[][]
		{
			{ cos(theta), -sin(theta) * cos(alpha), sin(theta) * sin(alpha) , length * cos(theta) },
			{ sin(theta), cos(theta) * cos(alpha) , -cos(theta) * sin(alpha), length * sin(theta) },
			{ 0         , sin(alpha)              , cos(alpha)              , offset              },
			{ 0         , 0                       , 0                       , 1                   }
		};
		
		return protMatrix;
	}
	
	public SimpleMatrix getDHMatrix(DHParameters... params){
		SimpleMatrix matrix = new SimpleMatrix(getDHMatrix(params[0]));
		for(int i = 1; i < params.length; i++){
			SimpleMatrix otherMatrix = new SimpleMatrix(getDHMatrix(params[i]));
			matrix = matrix.mult(otherMatrix);
		}
		
		return matrix;
	}
	
	public Point3d convertToPoint(SimpleMatrix matrix){
		double[] endEffector = new double[]{
				matrix.get(0, 0),
				matrix.get(1, 0),
				matrix.get(2, 0)
			};
			
		return Point3dUtils.round(new Point3d(endEffector), 2);
	}
	
	public double cos(double value){
		return Math.cos(toRad(value));
	}
	
	public double sin(double value){
		return Math.sin(toRad(value));
	}
	
	public double toRad(double value){
		return value * PI / 180;
	}
}
