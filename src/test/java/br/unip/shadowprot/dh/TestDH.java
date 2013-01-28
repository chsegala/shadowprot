package br.unip.shadowprot.dh;

import javax.vecmath.Point3d;

import junit.framework.Assert;

import org.junit.Test;

public class TestDH {	
	@Test
	//arm wide open to the side
	//braço aberto para o lado
	public void DHCalculator_0_0_0_Test(){
		DHCalculator calc = new DHCalculator();
		Point3d endEffector = calc.calculate(0, 0, 0);
		
		System.out.println(endEffector);
		
		Assert.assertEquals(2, endEffector.x, 0.05);
		Assert.assertEquals(0, endEffector.y, 0.05);
		Assert.assertEquals(0, endEffector.z, 0.05);
	}
	
	@Test
	//full arm up
	//braço esticado para cima
	public void DHCalculator_90_0_0_Test(){
		DHCalculator calc = new DHCalculator();
		Point3d endEffector = calc.calculate(90, 0, 0);
		
		System.out.println(endEffector);
		
		Assert.assertEquals(0, endEffector.x, 0.05);
		Assert.assertEquals(2, endEffector.y, 0.05);
		Assert.assertEquals(0, endEffector.z, 0.05);
	}
	
	@Test
	//full arm ahead
	//braço apontando para frente
	public void DHCalculator_0_90_0_Test(){
		DHCalculator calc = new DHCalculator();
		Point3d endEffector = calc.calculate(0, 90, 0);
		
		System.out.println(endEffector);
		
		Assert.assertEquals(0, endEffector.x, 0.05);
		Assert.assertEquals(0, endEffector.y, 0.05);
		Assert.assertEquals(2, endEffector.z, 0.05);
	}
	
	@Test
	public void DHCalculator_45_90_0(){
		DHCalculator calc = new DHCalculator();
		Point3d endEffector = calc.calculate(45, 90, 0);
		
		System.out.println(endEffector);
		
		Assert.assertEquals(0, endEffector.x, 0.05);
		Assert.assertEquals(0, endEffector.y, 0.05);
		Assert.assertEquals(2, endEffector.z, 0.05);
	}
	
	@Test
	//full arm to the side, elbow pointing hand up
	//braço para o lado, mão apontando para cima
	public void DHCalculator_0_0_90_Test(){
		DHCalculator calc = new DHCalculator();
		Point3d endEffector = calc.calculate(0, 0, 90);
		
		System.out.println(endEffector);
		
		Assert.assertEquals(1, endEffector.x, 0.05);
		Assert.assertEquals(1, endEffector.y, 0.05);
		Assert.assertEquals(0, endEffector.z, 0.05);
	}
	
	@Test
	//arm full ahead
	//braço apontando para frente
	public void DHCalculator_90_90_0_Test(){
		DHCalculator calc = new DHCalculator();
		Point3d endEffector = calc.calculate(90, 90, 0);
		
		System.out.println(endEffector);
		
		Assert.assertEquals(0, endEffector.x, 0.05);
		Assert.assertEquals(0, endEffector.y, 0.05);
		Assert.assertEquals(2, endEffector.z, 0.05);
	}
	
	@Test
	//arm ahead with hand pointing up
	//braço para a frente com mão apontando para cima
	public void DHCalculator_0_90_90_Test(){
		DHCalculator calc = new DHCalculator();
		Point3d endEffector = calc.calculate(0, 90, 90);
		
		System.out.println(endEffector);
		
		Assert.assertEquals(0, endEffector.x, 0.05);
		Assert.assertEquals(1, endEffector.y, 0.05);
		Assert.assertEquals(1, endEffector.z, 0.05);
	}
	
	@Test
	//arm over the head
	//braço acima da cabeça
	public void DHCalculator_90_0_90_Test(){
		DHCalculator calc = new DHCalculator();
		Point3d endEffector = calc.calculate(90, 0, 90);
		
		System.out.println(endEffector);
		
		Assert.assertEquals(-1, endEffector.x, 0.05);
		Assert.assertEquals(1, endEffector.y, 0.05);
		Assert.assertEquals(0, endEffector.z, 0.05);
	}
}
