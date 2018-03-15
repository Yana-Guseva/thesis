package org.eltech.ddm.transformation.etl.CloverETL.loading;

import static org.junit.Assert.*;

import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformation;
import org.junit.Test;

/**
 * Test for CloverETLTransformationExtends
 * 
 * @author SemenchenkoA
 *
 */
public class TestCloverETLTransformationExtends {
	@Test
	public void getClassExt(){
		assertFalse("Transformation extends not find", new CloverETLTransformationExtends().getTransformationExtends().isEmpty());
	}
	
	@Test
	public void testGetByName(){
		CloverETLTransformationExtends extds = new CloverETLTransformationExtends();
		extds.getTransformationExtends();
		
		Class<? extends CloverETLTransformation> classes = extds.getClassByName("CloverETLEmptyTransformation");
		System.out.println("Get by name: " + classes.toString());
		
		assertNotNull("Transformation by name not find", classes);
	}
}
