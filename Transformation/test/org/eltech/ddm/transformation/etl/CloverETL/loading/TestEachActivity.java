package org.eltech.ddm.transformation.etl.CloverETL.loading;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Test each test class activity for find what activity broken in activity inheritance.
 * 
 * @author SemenchenkoA
 *
 */
public class TestEachActivity {
	Result result = null;
	
	@Test
	public void testSimpleCloverActivityFactory(){
		result = JUnitCore.runClasses(TestSimpleCloverActivityFactory.class);
		//assertTrue("TestSimpleCloverActivityFactory test unsuccess", result.getFailureCount() == 0);
	}
	
	@Test
	public void testCloverETLMultiSourceDynamicTransformation(){
		result = JUnitCore.runClasses(TestCloverETLMultiSourceDynamicTransformation.class);
	}
	
	@Test
	public void testCloverETLOneSourceDynamicTransformationActivity(){
		result = JUnitCore.runClasses(TestCloverETLOneSourceDynamicTransformationActivity.class);
	}
	
	@Test
	public void testCloverETLOneSourceTransformationActivity(){
		result = JUnitCore.runClasses(TestCloverETLOneSourceTransformationActivity.class);
	}
	
	@Test
	public void testCloverETLSchemeActivity(){
		result = JUnitCore.runClasses(TestCloverETLSchemeActivity.class);
	}
	
	@Test
	public void testCloverETLDisplacedTransformation(){
		result = JUnitCore.runClasses(TestCloverETLDisplacedTransformation.class);
	}
	
	@After
	public void traceFailture(){
		for(Failure fail: result.getFailures())
			fail(fail.toString());
	}
}
