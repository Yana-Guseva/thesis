package org.eltech.ddm.transformation.etl.CloverETL.loading;

import static org.junit.Assert.assertEquals;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningdata.EPhysicalData;
import org.eltech.ddm.transformation.MiningTransformationMetadata;
import org.jetel.graph.Result;
import org.junit.After;
import org.junit.Test;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeType;

/**
 * Test for CloverETLGetPhysicalDataActivity
 * 
 * @author SemenchenkoA
 *
 */
public class TestCloverETLGetPhysicalDataActivity {
	private CloverETLGetPhysicalDataActivity cloverOneSourceActivity = null;

	@Test
	public void testActivity() throws Exception {
		cloverOneSourceActivity = new CloverETLGetPhysicalDataActivity();
		cloverOneSourceActivity.setMetaData("relation", ";", "\n");
		cloverOneSourceActivity.addMetaDataField("transactId",
				MiningTransformationMetadata.FIELD_TYPE_INTEGER);
		cloverOneSourceActivity.addMetaDataField("itemId",
				MiningTransformationMetadata.FIELD_TYPE_STRING);
		
		//cloverOneSourceActivity.addTransformation("CloverETLPhysicalDataTransformation");

		cloverOneSourceActivity.runActivity(
				"https://dl-doc.dropbox.com/s/ybcg5ohd51ytl8c/apriori1.xlsx"+";"+
				"https://dl-doc.dropbox.com/s/rzvkyfoppstu593/apriori2.xlsx",
				null);					
				
		assertEquals("testActivity unsuccess", cloverOneSourceActivity.getResult().get(), Result.FINISHED_OK);
	}
		
	@After
	public void afterTest() throws MiningException{
		// show results
		EPhysicalData physicalData = cloverOneSourceActivity.getPhysicalData();
		
		for(int i=0; i<physicalData.getAttributeCount(); i++)
			System.out.println(physicalData.getAttribute(i).getName());
				
		ELogicalData logicalData = cloverOneSourceActivity.getLogicalData();
		
		for(int i = 0; i<logicalData.getAttributesNumber(); i++){
			System.out.println(logicalData.getAttribute(i).toString());
			
			if(logicalData.getAttribute(i).getAttributeType()==AttributeType.numerical){
				System.out.println("min, max: "+logicalData.getAttribute(i).getNumericalProperties().getLowerBound()+" "+
						logicalData.getAttribute(i).getNumericalProperties().getUpperBound());
			}
		}	
	}
}
