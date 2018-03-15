package org.eltech.ddm.transformation.etl.CloverETL.loading;

import static org.junit.Assert.*;

import org.eltech.ddm.transformation.MiningTransformation;
import org.eltech.ddm.transformation.MiningTransformationFactory;
import org.eltech.ddm.transformation.MiningTransformationMetadata;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationActivity;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationMetadata;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationStep;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationTask;
import org.eltech.ddm.transformation.etl.CloverETL.transformations.CloverETLInputXLSDataTransformation;
import org.jetel.graph.Result;
import org.junit.Test;

public class TestCloverETLTrashAndPhysicalDataTransformation {
	
	@Test
	public void testTrash() throws Exception{
		CloverETLTransformationActivity cloverActivity=(CloverETLTransformationActivity) MiningTransformationFactory.createActivity(MiningTransformationFactory.ACTIVITY_TYPE_CLOVERETL);
		
		CloverETLInputXLSDataTransformation input=(CloverETLInputXLSDataTransformation) cloverActivity.createTransformation(MiningTransformation.TRANSFORMATION_TYPE_INPUTXLS_FILE);

		input.setParameter(MiningTransformation.TRANSFORMATION_PARAM_FILENAME, "https://dl-doc.dropbox.com/s/ybcg5ohd51ytl8c/apriori1.xlsx"+";"+
				"https://dl-doc.dropbox.com/s/rzvkyfoppstu593/apriori2.xlsx");
		input.init();
		
		CloverETLTransformationMetadata meta = (CloverETLTransformationMetadata) cloverActivity.createMetadata();
		meta.addFieldDescription("transactId", MiningTransformationMetadata.FIELD_TYPE_INTEGER);
		meta.addFieldDescription("itemId", MiningTransformationMetadata.FIELD_TYPE_STRING);
		meta.setParameter(MiningTransformationMetadata.METADATA_PROP_FIELD_DELIMETER, ";");
		meta.setParameter(MiningTransformationMetadata.METADATA_PROP_RECORD_DELIMETER, "\n");
		
		CloverETLTransformationTask task = (CloverETLTransformationTask) cloverActivity.createTask(meta);
		task.setSource(input);
		
		CloverETLTransformationTask task2 = (CloverETLTransformationTask) cloverActivity.createTask(meta);
		
		CloverETLPhysicalDataTransformation physicalData = (CloverETLPhysicalDataTransformation) cloverActivity.createTransformation(MiningTransformation.TRANSFORMATION_TYPE_PHYSICALDATA);
		physicalData.init();
		task.setTarget(physicalData);
		task2.setSource(physicalData);
		
		CloverETLTrashTransformation output=(CloverETLTrashTransformation) cloverActivity.createTransformation(MiningTransformation.TRANSFORMATION_TYPE_TRASH);
		output.init();

		task2.setTarget(output);

		CloverETLTransformationStep step=(CloverETLTransformationStep) cloverActivity.createStep();
		CloverETLTransformationStep step2=(CloverETLTransformationStep) cloverActivity.createStep();
		step.addTask(task);
		step2.addTask(task2);
		
		cloverActivity.transform(); 
		
		assertEquals(cloverActivity.getResult().get(), Result.FINISHED_OK);
	}
}
