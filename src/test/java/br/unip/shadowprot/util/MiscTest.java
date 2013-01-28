package br.unip.shadowprot.util;

import org.ejml.simple.SimpleMatrix;
import org.junit.Test;

public class MiscTest {
	@Test
	public void ejmlTest1() {
		double[][] a = new double[][] { { 1 },
										{ 2 } };
		double[][] b = new double[][] { {1 , 2 } };
	
		SimpleMatrix matrixA = new SimpleMatrix(a);
		SimpleMatrix matrixB = new SimpleMatrix(b);
		
		matrixA.reshape(1, 2);
		matrixA.reshape(2, 1);
		matrixA.print();
	}
	
	@Test
	public void ejmlTest2() {
		double[][] a = new double[][] { { 1, 2 }, { 3, 4 } };
		double[][] b = new double[][] { { 1 }, { 2 } };
	
		SimpleMatrix matrixA = new SimpleMatrix(a);
		SimpleMatrix matrixB = new SimpleMatrix(b);
		
		SimpleMatrix matrixR = matrixA.mult(matrixB);
		matrixR.print();
	}
	
	@Test
	public void ejmlTest3(){
		SimpleMatrix ref = new SimpleMatrix(new double[][] { { 1, 2, 3} });
		SimpleMatrix ref1 = new SimpleMatrix(1, 4);
		ref1.set(1);
		ref1.insertIntoThis(0, 0, ref);
		ref1.reshape(4, 1);
		ref1.print();
	}
}
