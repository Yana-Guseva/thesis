package org.eltech.ddm.transformation.etl.CloverETL.loading;


import org.eltech.ddm.transformation.MiningTransformation;
import org.eltech.ddm.transformation.MiningTransformationFactory;
import org.eltech.ddm.transformation.MiningTransformationMetadata;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationActivity;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationMetadata;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationStep;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationTask;
import org.eltech.ddm.transformation.etl.CloverETL.loading.CloverETLSaveResultInMIS;
import org.eltech.ddm.transformation.etl.CloverETL.transformations.CloverETLInputFileTransformation;

import org.eltech.ddm.transformation.etl.CloverETL.transformations.CloverETLOutputMASTransformation;

import org.junit.Test;

/**
 * Test for From file to MiningInputStream batch=5
 * 
 * @author SemenchenkoA
 *
 */
public class TestCloverETLFromInputFileToMIS {
	@Test
	public void test() throws Exception {
		CloverETLTransformationActivity cloverActivity=(CloverETLTransformationActivity) MiningTransformationFactory.createActivity(MiningTransformationFactory.ACTIVITY_TYPE_CLOVERETL);
			
		CloverETLInputFileTransformation input=(CloverETLInputFileTransformation) cloverActivity.createTransformation(MiningTransformation.TRANSFORMATION_TYPE_INPUT_FILE);

		input.setParameter(MiningTransformation.TRANSFORMATION_PARAM_FILENAME, "..//data//excel//apriori.csv");
		input.init();
		
		CloverETLTransformationMetadata meta = (CloverETLTransformationMetadata) cloverActivity.createMetadata();
		//meta.addFieldDescription("id", MiningTransformationMetadata.FIELD_TYPE_INTEGER);
		meta.addFieldDescription("transactId", MiningTransformationMetadata.FIELD_TYPE_STRING);
		meta.addFieldDescription("itemId", MiningTransformationMetadata.FIELD_TYPE_STRING);
		meta.setParameter(MiningTransformationMetadata.METADATA_PROP_FIELD_DELIMETER, ";");
		meta.setParameter(MiningTransformationMetadata.METADATA_PROP_RECORD_DELIMETER, "\n");
	
		CloverETLTransformationTask task = (CloverETLTransformationTask) cloverActivity.createTask(meta);
		task.setSource(input);
		
		CloverETLOutputMASTransformation output=(CloverETLOutputMASTransformation) cloverActivity.createTransformation(MiningTransformation.TRANSFORMATION_TYPE_OUTPUT_MAS);
		output.setParameter(MiningTransformation.TRANSFORMATION_PARAM_DATABATCHSIZE, 15);
		
		CloverETLSaveResultInMIS in = new CloverETLSaveResultInMIS();
		output.setInformer(in);
		output.init();
		task.setTarget(output);
				
		CloverETLTransformationStep step=(CloverETLTransformationStep) cloverActivity.createStep();
		step.addTask(task);

		cloverActivity.transform();		
	}
}
