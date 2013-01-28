package br.unip.shadowprot.util;

import javax.vecmath.Point3d;

import org.junit.Assert;
import org.junit.Test;

import br.unip.shadowprot.dh.DHCalculator;
import br.unip.shadowprot.skeleton.LookupTable;

public class LookupTableTest {
	@Test
	//fully extended arm
	public void test_0_0_0(){
		LookupTable lt = new LookupTable();
		DHCalculator calculator = new DHCalculator();
		
		lt.setCalculator(calculator);
		lt.create();
		
		double[] actual = lt.get(new Point3d(2, 0, 0));
		double[] expected = new double[]{ 0, 0, 0};
		
		Assert.assertArrayEquals(expected, actual, 0.05);
	}
	
	@Test
	//fully extended arm to the front
	public void test_90_90_0(){
		LookupTable lt = new LookupTable();
		DHCalculator calculator = new DHCalculator();
		
		lt.setCalculator(calculator);
		lt.create();
		
		double[] actual = lt.get(new Point3d(0, 0, 2));
		double[] expected = new double[]{ 90, 90, 0};
		
		Assert.assertArrayEquals(expected, actual, 0.05);
	}
	
	@Test
	//arm to the front, hand up
	public void test_0_90_90(){
		LookupTable lt = new LookupTable();
		DHCalculator calculator = new DHCalculator();
		
		lt.setCalculator(calculator);
		lt.create();
		
		double[] actual = lt.get(new Point3d(0, 1, 1));
		double[] expected = new double[]{ 0, 90, 90};
		
		Assert.assertArrayEquals(expected, actual, 0.05);
	}
	
	@Test
	//arm over the head
	public void test_90_0_90(){
		LookupTable lt = new LookupTable();
		DHCalculator calculator = new DHCalculator();
		
		lt.setCalculator(calculator);
		lt.create();
		
		double[] actual = lt.get(new Point3d(-1, 1, 0));
		double[] expected = new double[]{ 90, 0, 90};
		
		Assert.assertArrayEquals(expected, actual, 0.05);
	}
	
/*	@Test
	// test all points
	public void test(){
		LookupTable lt = new LookupTable();
		DHCalculator calculator = new DHCalculator();
		
		lt.setCalculator(calculator);
		lt.create();
		
		double alpha;
		double beta;
		double gamma;
		
		for(int i = 0; i < 90; i += 15){
			alpha = i;
			for(int j = 0; j < 90; j += 15){
				beta = j;
				for(int k = 0; k < 90; k += 15){
					gamma = k;
					assertValues(lt, calculator, alpha, beta, gamma);
				}
			}
		}
	}
	
	private void assertValues(LookupTable lt, DHCalculator calculator, double alpha, double beta, double gamma){		
		Point3d position = calculator.calculate(alpha, beta, gamma);
		position = Point3dUtils.round(position);
		
		double[] actual = lt.get(position);
		double[] expected = new double[]{ alpha, beta, gamma};
		
		Assert.assertArrayEquals(String.format("angles: %f, %f, %f - %s", alpha, beta, gamma, position), expected, actual, 0.05);
	}
	
	*/
}
