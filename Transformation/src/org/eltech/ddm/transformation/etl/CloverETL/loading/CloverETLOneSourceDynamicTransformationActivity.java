package org.eltech.ddm.transformation.etl.CloverETL.loading;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.eltech.ddm.transformation.MiningETLArrayStreamInformer;
import org.eltech.ddm.transformation.MiningTransformation;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformation;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationStep;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationTask;
import org.eltech.ddm.transformation.etl.CloverETL.transformations.CloverETLInputXLSDataTransformation;
import org.eltech.ddm.transformation.etl.CloverETL.transformations.CloverETLOutputMASTransformation;
import org.eltech.ddm.transformation.etl.CloverETL.transformations.CloverETLInputFileTransformation;
import org.eltech.ddm.transformation.etl.CloverETL.transformations.CloverETLInputDBTransformation;

/**
 * Type 3, 5 and type 7 with the same type source and contains type 1.
 * 
 * @author SemenchenkoA
 *
 */
public class CloverETLOneSourceDynamicTransformationActivity extends
		CloverETLOneSourceTransformationActivity {
	private static final long serialVersionUID = 1L;
	private List<String> cloverTransformations = null;
	private String fileURLs = "";
	
	public String getFileURLs() {
		return fileURLs;
	}

	private void setFileURLs(String fileURLs) {
		this.fileURLs = fileURLs;
	}

	CloverETLOneSourceDynamicTransformationActivity() throws Exception {
		super();
		
		cloverTransformations = new ArrayList<String>();
	}
	
	@Override
	public void runActivity(String fileURL, MiningETLArrayStreamInformer informer) throws Exception{
		CloverETLTransformation input = null;
		setFileURLs(fileURL);
		
		String fileType = FilenameUtils.getExtension(fileURL).toLowerCase();
		
		if(fileType.equals("xls")||fileType.equals("xlsx")){
			input = (CloverETLInputXLSDataTransformation) this.createTransformation(MiningTransformation.TRANSFORMATION_TYPE_INPUTXLS_FILE);
		}else if(fileType.equals("csv")||fileType.equals("txt")){
			input = (CloverETLInputFileTransformation) this.createTransformation(MiningTransformation.TRANSFORMATION_TYPE_INPUT_FILE);
		}else{ //database table TODO add database types
			input = (CloverETLInputDBTransformation) this.createTransformation(MiningTransformation.TRANSFORMATION_TYPE_INPUT_DB);
			/*
			input.setParameter(CloverETLTransformation.TRANSFORMATION_PARAM_DB_URL, "https://dl-doc.dropbox.com/s/huuhjzaohw0oss1/ListVPro.mdb");
			input.setParameter(CloverETLTransformation.TRANSFORMATION_PARAM_DB_TYPE, "Microsoft Access");
			input.setParameter(CloverETLTransformation.TRANSFORMATION_PARAM_JDBC_DRIVER, "");
			input.setParameter(CloverETLTransformation.TRANSFORMATION_PARAM_USERNAME, "admin");
			input.setParameter(CloverETLTransformation.TRANSFORMATION_PARAM_PASSWORD, "");
			input.setParameter(CloverETLTransformation.TRANSFORMATION_PARAM_SQL_STRING, "select * ");*/
		}
		
		input.setParameter(MiningTransformation.TRANSFORMATION_PARAM_FILENAME, fileURL);
		input.init();
		
		CloverETLTransformationExtends extds = new CloverETLTransformationExtends();
		extds.getTransformationExtends();		
		
		for(int i = 0; i < cloverTransformations.size(); i++){
			Class<? extends CloverETLTransformation> classes = extds.getClassByName(cloverTransformations.get(i));
			CloverETLTransformation trans = classes.newInstance();
			
			//TODO set parameters from each transformation
			
			trans.init();			
			
			this.transformations.add(trans);
			
			CloverETLTransformationTask task = (CloverETLTransformationTask)this.createTask(meta);
			// i=0 input
			if(i == 0) task.setSource(input); else task.setSource(this.transformations.get(i)); 

			task.setTarget(trans);
			
			CloverETLTransformationStep step = (CloverETLTransformationStep)this.createStep();
			step.addTask(task);
		}		
		/*
		CloverETLOutputMASTransformation output = (CloverETLOutputMASTransformation) this
				.createTransformation(MiningTransformation.TRANSFORMATION_TYPE_OUTPUT_MAS);
		output.setParameter(
				MiningTransformation.TRANSFORMATION_PARAM_DATABATCHSIZE, batchSize);
		
		output.setInformer(informer);
		output.init();
		
		CloverETLTransformationTask task = (CloverETLTransformationTask)this.createTask(meta);
		// -2 because without output and -1 we start from 0
		task.setSource(this.transformations.get(this.transformations.size()-2));
		task.setTarget(output);

		CloverETLTransformationStep step = (CloverETLTransformationStep)this.createStep();
		step.addTask(task);
		
		this.transform();*/
		formEnds(informer);
	}
	
	public void formEnds(MiningETLArrayStreamInformer informer) throws Exception{
		CloverETLOutputMASTransformation output = (CloverETLOutputMASTransformation) this
				.createTransformation(MiningTransformation.TRANSFORMATION_TYPE_OUTPUT_MAS);
		output.setParameter(
				MiningTransformation.TRANSFORMATION_PARAM_DATABATCHSIZE, batchSize);
		
		output.setInformer(informer);
		output.init();
		
		CloverETLTransformationTask task = (CloverETLTransformationTask)this.createTask(meta);
		// -2 because without output and -1 we start from 0
		task.setSource(this.transformations.get(this.transformations.size()-2));
		task.setTarget(output);

		CloverETLTransformationStep step = (CloverETLTransformationStep)this.createStep();
		step.addTask(task);
		
		this.transform();
	}
	
	/**
	 * Add transformation from String class name
	 * 
	 * @param transformation
	 */
	public void addTransformation(String transformation){
		cloverTransformations.add(transformation);
	}
}
