package br.unip.shadowprot.util;

import javax.vecmath.Point3d;

import junit.framework.Assert;

import org.junit.Test;

import br.unip.shadowprot.util.AngleUtils.RotationAxis;

public class TestAngleUtils {
	@Test
	public void testUnitVector45ZRotation(){
		Point3d reference = new Point3d(0, 0, 0);
		Point3d expected = new Point3d(0.7, 0.7, 0);
		Point3d rotated = AngleUtils.getEndEffector(reference, 1, 45, RotationAxis.Z);
		
		Assert.assertEquals(expected.x, rotated.x, 0.1);
		Assert.assertEquals(expected.y, rotated.y, 0.1);
		Assert.assertEquals(expected.z, rotated.z, 0.1);
	}
	
	@Test
	public void testUnitVector30ZRotation(){
		Point3d reference = new Point3d(0, 0, 0);
		Point3d expected = new Point3d(0.866, 0.5, 0);
		Point3d rotated = AngleUtils.getEndEffector(reference, 1, 30, RotationAxis.Z);
		
		Assert.assertEquals(expected.x, rotated.x, 0.1);
		Assert.assertEquals(expected.y, rotated.y, 0.1);
		Assert.assertEquals(expected.z, rotated.z, 0.1);
	}
	
	@Test
	public void testUnitVector60ZRotation(){
		Point3d reference = new Point3d(0, 0, 0);
		Point3d expected = new Point3d(0.5, 0.866, 0);
		Point3d rotated = AngleUtils.getEndEffector(reference, 1, 60, RotationAxis.Z);
		
		Assert.assertEquals(expected.x, rotated.x, 0.1);
		Assert.assertEquals(expected.y, rotated.y, 0.1);
		Assert.assertEquals(expected.z, rotated.z, 0.1);
	}
}
