package org.eltech.ddm.transformation.etl.CloverETL.loading;

import static org.junit.Assert.assertEquals;

import org.eltech.ddm.transformation.MiningTransformation;
import org.eltech.ddm.transformation.MiningTransformationFactory;
import org.eltech.ddm.transformation.MiningTransformationMetadata;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationActivity;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationMetadata;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationStep;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationTask;
import org.eltech.ddm.transformation.etl.CloverETL.loading.CloverETLEmptyTransformation;
import org.eltech.ddm.transformation.etl.CloverETL.transformations.CloverETLInputFileTransformation;
import org.eltech.ddm.transformation.etl.CloverETL.transformations.CloverETLOutputFileTransformation;
import org.jetel.graph.Result;
import org.junit.Test;

/**
 * Test for From file to file, for additional test added empty component
 * 
 * @author SemenchenkoA
 *
 */
public class TestCloverETLFromInputFileToOutputFile {
	@Test
	public void test() throws Exception {
		CloverETLTransformationActivity cloverActivity=(CloverETLTransformationActivity) MiningTransformationFactory.createActivity(MiningTransformationFactory.ACTIVITY_TYPE_CLOVERETL);
			
		CloverETLInputFileTransformation input=(CloverETLInputFileTransformation) cloverActivity.createTransformation(MiningTransformation.TRANSFORMATION_TYPE_INPUT_FILE);

		input.setParameter(MiningTransformation.TRANSFORMATION_PARAM_FILENAME, "book.csv");
		input.init();
		
		CloverETLTransformationMetadata meta = (CloverETLTransformationMetadata) cloverActivity.createMetadata();
		meta.addFieldDescription("id", MiningTransformationMetadata.FIELD_TYPE_STRING);
		meta.addFieldDescription("text", MiningTransformationMetadata.FIELD_TYPE_STRING);
		meta.addFieldDescription("idx", MiningTransformationMetadata.FIELD_TYPE_STRING);
		meta.setParameter(MiningTransformationMetadata.METADATA_PROP_FIELD_DELIMETER, ";");
		meta.setParameter(MiningTransformationMetadata.METADATA_PROP_RECORD_DELIMETER, "\n");
		
		CloverETLTransformationTask task = (CloverETLTransformationTask) cloverActivity.createTask(meta);
		task.setSource(input);
		
		//CloverETLReformatTransformation reformat=(CloverETLReformatTransformation) cloverActivity.createTransformation(MiningTransformation.TRANSFORMATION_TYPE_REFORMAT);
		//reformat.setParameter(MiningTransformation.TRANSFORMATION_PARAM_METHOD_NAME, "RecordTransform");
		//reformat.init();
		//task.setTarget(reformat);
		CloverETLTransformationTask task2 = (CloverETLTransformationTask) cloverActivity.createTask(meta);
		
		CloverETLEmptyTransformation empty = (CloverETLEmptyTransformation)cloverActivity.createTransformation(MiningTransformation.TRANSFORMATION_TYPE_EMPTY);
		empty.init();
		task.setTarget(empty);
		task2.setSource(empty);
		
		CloverETLOutputFileTransformation output=(CloverETLOutputFileTransformation) cloverActivity.createTransformation(MiningTransformation.TRANSFORMATION_TYPE_OUTPUT_FILE);
		output.setParameter(MiningTransformation.TRANSFORMATION_PARAM_FILENAME, "out.txt");
		output.init();
		//task.setTarget(output);
		task2.setTarget(output);

		CloverETLTransformationStep step=(CloverETLTransformationStep) cloverActivity.createStep();
		CloverETLTransformationStep step2=(CloverETLTransformationStep) cloverActivity.createStep();
		step.addTask(task);
		step2.addTask(task2);
		
		cloverActivity.transform();
		
		assertEquals(cloverActivity.getResult().get(), Result.FINISHED_OK);
	}
}
