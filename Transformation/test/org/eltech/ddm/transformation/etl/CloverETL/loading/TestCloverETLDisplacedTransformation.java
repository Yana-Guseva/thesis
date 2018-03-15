package org.eltech.ddm.transformation.etl.CloverETL.loading;

import static org.junit.Assert.assertEquals;

import org.eltech.ddm.transformation.MiningTransformation;
import org.eltech.ddm.transformation.MiningTransformationMetadata;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationActivity;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationMetadata;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationStep;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationTask;
import org.eltech.ddm.transformation.etl.CloverETL.loading.CloverETLEmptyTransformation;
import org.eltech.ddm.transformation.etl.CloverETL.loading.CloverETLSaveResultInMIS;
import org.eltech.ddm.transformation.etl.CloverETL.transformations.CloverETLOutputFileTransformation;
import org.eltech.ddm.transformation.etl.CloverETL.transformations.CloverETLOutputMASTransformation;
import org.jetel.graph.Result;
import org.junit.Test;

/**
 * Test for CloverETLDisplacedTransformation activity
 * 
 * @author SemenchenkoA
 *
 */
public class TestCloverETLDisplacedTransformation {
	@Test
	public void testCloverETLXMLtoFile() throws Exception{	
		//From CloverETL XML to file
		String graphPath="..\\data\\grf\\new-graph3.grf";
		String lastComponentID="MERGE";

		CloverETLTransformationActivity cloverActivity = new CloverETLTransformationActivity();
		
		CloverETLTransformationMetadata meta = (CloverETLTransformationMetadata) cloverActivity.createMetadata();
	
		meta.addFieldDescription("text", MiningTransformationMetadata.FIELD_TYPE_STRING);
		meta.addFieldDescription("id", MiningTransformationMetadata.FIELD_TYPE_INTEGER);
		meta.setParameter(MiningTransformationMetadata.METADATA_PROP_FIELD_DELIMETER, "\t");
		meta.setParameter(MiningTransformationMetadata.METADATA_PROP_RECORD_DELIMETER, "\n");
		
		CloverETLDisplacedTransformationActivity cloverActivityDis = new CloverETLDisplacedTransformationActivity(graphPath,lastComponentID, meta);
		
		CloverETLOutputFileTransformation output=(CloverETLOutputFileTransformation) cloverActivityDis.createTransformation(MiningTransformation.TRANSFORMATION_TYPE_OUTPUT_FILE);
		output.setParameter(MiningTransformation.TRANSFORMATION_PARAM_FILENAME, "out1.xls");
		output.init();

		cloverActivityDis.setWelcomeComponent(output);
		
		cloverActivityDis.transform();
		
		assertEquals("CloverETLXMLtoFile unsuccess", cloverActivityDis.getResult().get(), Result.FINISHED_OK);
	}
	@Test
	public void testCloverETLXMLtoMiningInputStream() throws Exception{
		//From CloverETL XML to MiningInputStream
		String graphPath="..\\data\\grf\\new-graph3.grf";
		String lastComponentID="MERGE";

		CloverETLTransformationActivity cloverActivity = new CloverETLTransformationActivity();
		
		CloverETLTransformationMetadata meta = (CloverETLTransformationMetadata) cloverActivity.createMetadata();
	
		meta.addFieldDescription("text", MiningTransformationMetadata.FIELD_TYPE_STRING);
		meta.addFieldDescription("id", MiningTransformationMetadata.FIELD_TYPE_INTEGER);
		meta.setParameter(MiningTransformationMetadata.METADATA_PROP_FIELD_DELIMETER, "\t");
		meta.setParameter(MiningTransformationMetadata.METADATA_PROP_RECORD_DELIMETER, "\n");
		
		CloverETLDisplacedTransformationActivity cloverActivityDis = new CloverETLDisplacedTransformationActivity(graphPath,lastComponentID, meta);

		CloverETLOutputMASTransformation output=(CloverETLOutputMASTransformation) cloverActivityDis.createTransformation(MiningTransformation.TRANSFORMATION_TYPE_OUTPUT_MAS);
		output.setParameter(MiningTransformation.TRANSFORMATION_PARAM_DATABATCHSIZE, 5);
		CloverETLSaveResultInMIS in = new CloverETLSaveResultInMIS();
		output.setInformer(in);
		output.init();
		
		cloverActivityDis.setWelcomeComponent(output);
		
		cloverActivityDis.transform();		
		
		assertEquals("CloverETLXMLtoMiningInputStream unsuccess", cloverActivityDis.getResult().get(), Result.FINISHED_OK);
	}
	
	@Test
	public void testCloverETLXMLtoMiningInputStreamTestAdditionalComponent() throws Exception{
		//From CloverETL XML to MiningInputStream with example adding extra components (in future transformation component)
		String graphPath="..\\data\\grf\\new-graph3.grf";
		String lastComponentID="MERGE";

		CloverETLTransformationActivity cloverActivity = new CloverETLTransformationActivity();
		
		CloverETLTransformationMetadata meta = (CloverETLTransformationMetadata) cloverActivity.createMetadata();
	
		meta.addFieldDescription("text", MiningTransformationMetadata.FIELD_TYPE_STRING);
		meta.addFieldDescription("id", MiningTransformationMetadata.FIELD_TYPE_INTEGER);
		meta.setParameter(MiningTransformationMetadata.METADATA_PROP_FIELD_DELIMETER, "\t");
		meta.setParameter(MiningTransformationMetadata.METADATA_PROP_RECORD_DELIMETER, "\n");
		
		CloverETLDisplacedTransformationActivity cloverActivityDis = new CloverETLDisplacedTransformationActivity(graphPath,lastComponentID, meta);
		
		CloverETLEmptyTransformation empty = (CloverETLEmptyTransformation)cloverActivityDis.createTransformation(MiningTransformation.TRANSFORMATION_TYPE_EMPTY);
		empty.init();
		cloverActivityDis.setWelcomeComponent(empty);

		CloverETLTransformationTask task = (CloverETLTransformationTask) cloverActivityDis.createTask(meta);
		task.setSource(empty);
		
		CloverETLOutputMASTransformation output=(CloverETLOutputMASTransformation) cloverActivityDis.createTransformation(MiningTransformation.TRANSFORMATION_TYPE_OUTPUT_MAS);
		output.setParameter(MiningTransformation.TRANSFORMATION_PARAM_DATABATCHSIZE, 5);
		CloverETLSaveResultInMIS in = new CloverETLSaveResultInMIS();
		output.setInformer(in);
		output.init();
		task.setTarget(output);
		
		CloverETLTransformationStep step=(CloverETLTransformationStep) cloverActivityDis.createStep();
		step.addTask(task);		
		
		cloverActivityDis.transform();		
		assertEquals("CloverETLXMLtoMiningInputStreamTestAdditionalComponent unsuccess", cloverActivityDis.getResult().get(), Result.FINISHED_OK);
	}

}
