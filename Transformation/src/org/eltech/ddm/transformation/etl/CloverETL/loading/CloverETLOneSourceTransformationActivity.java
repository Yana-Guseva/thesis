package org.eltech.ddm.transformation.etl.CloverETL.loading;

import org.eltech.ddm.transformation.MiningETLArrayStreamInformer;
import org.eltech.ddm.transformation.MiningTransformation;
import org.eltech.ddm.transformation.MiningTransformationMetadata;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationActivity;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationMetadata;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationStep;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationTask;
import org.eltech.ddm.transformation.etl.CloverETL.transformations.CloverETLInputXLSDataTransformation;
import org.eltech.ddm.transformation.etl.CloverETL.transformations.CloverETLOutputMASTransformation;

/**
 * Type 1. (only for ETL readers) This activity present: source in one file
 * local or remote. Deprecated use
 * CloverETLOneSourceDynamicTransformationActivity with actually support for set
 * of files formats. Not change this class
 * CloverETLOneSourceDynamicTransformationActivity use it.
 * 
 * @author SemenchenkoA
 *
 */

public class CloverETLOneSourceTransformationActivity extends
		CloverETLTransformationActivity {
	private static final long serialVersionUID = 1L;

	protected CloverETLTransformationMetadata meta = null;
	
	public CloverETLTransformationMetadata getMeta() {
		return meta;
	}

	public void setMeta(CloverETLTransformationMetadata meta) {
		this.meta = meta;
	}

	protected int batchSize = 1;

	/**
	 * @param metaDataName
	 *            Name for meta data
	 * @param fieldDelimeter
	 *            Field dilimeter
	 * @param recordDelimeter
	 *            Record delimeter
	 * @throws Exception
	 */
	public void setMetaData(String metaDataName, String fieldDelimeter,
			String recordDelimeter) throws Exception {
		meta = (CloverETLTransformationMetadata) this.createMetadata();
		meta.setName(metaDataName);
		meta.setParameter(
				MiningTransformationMetadata.METADATA_PROP_FIELD_DELIMETER,
				fieldDelimeter);
		meta.setParameter(
				MiningTransformationMetadata.METADATA_PROP_RECORD_DELIMETER,
				recordDelimeter);
	}

	/**
	 * @throws Exception
	 */
	CloverETLOneSourceTransformationActivity() throws Exception {
		super();
	}

	/**
	 * @param fileURL
	 *            can be local or remote
	 * @param informer
	 *            where realize MiningETLArrayStreamInformer
	 * @throws Exception
	 */
	public void runActivity(String fileURL,
			MiningETLArrayStreamInformer informer) throws Exception {
		// CloverETLInputFileTransformation input =
		// (CloverETLInputFileTransformation)
		// this.createTransformation(MiningTransformation.TRANSFORMATION_TYPE_INPUT_FILE);
		CloverETLInputXLSDataTransformation input = (CloverETLInputXLSDataTransformation) this
				.createTransformation(MiningTransformation.TRANSFORMATION_TYPE_INPUTXLS_FILE);
		input.setParameter(MiningTransformation.TRANSFORMATION_PARAM_FILENAME,
				fileURL);
		input.init();

		CloverETLOutputMASTransformation output = (CloverETLOutputMASTransformation) this
				.createTransformation(MiningTransformation.TRANSFORMATION_TYPE_OUTPUT_MAS);
		output.setParameter(
				MiningTransformation.TRANSFORMATION_PARAM_DATABATCHSIZE,
				batchSize);

		output.setInformer(informer);
		output.init();

		CloverETLTransformationTask task = (CloverETLTransformationTask) this
				.createTask(meta);
		task.setSource(input);
		task.setTarget(output);

		CloverETLTransformationStep step = (CloverETLTransformationStep) this
				.createStep();
		step.addTask(task);

		this.transform();
	}

	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	/**
	 * @param dataFieldsName
	 *            Field name
	 * @param dataFieldsType
	 *            Field type
	 */
	public void addMetaDataField(String dataFieldsName, int dataFieldsType) {
		if (meta != null)
			meta.addFieldDescription(dataFieldsName, dataFieldsType);
	}
}
