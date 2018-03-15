package org.eltech.ddm.transformation.etl.CloverETL.loading;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.transformation.MiningETLArrayStreamInformer;
import org.eltech.ddm.transformation.MiningTransformationMetadata;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationActivity;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationMetadata;

/**
 * Factory for type 1 and 2 transformation
 * 
 * @author SemenchenkoA
 *
 */
public class CloverETLSimpleActivityFactory {	
	private CloverETLTransformationMetadata meta = null;
	private CloverETLTransformationActivity activity = null;
	private MiningInputStream miningInputStream = null;

	private String fName = "";
	private int batchSize = 1;
	private int activityFormat = 0;

	public static int ACTIVITY_FORMAT_SCHEME = 1;
	public static int ACTIVITY_FORMAT_SIMPLE_ONE_SOURCE = 2;

	private static List<String> fileTypes = null;
	
	static{
		fileTypes = new ArrayList<String>();
		fileTypes.add("grf");
		fileTypes.add("xls");
		fileTypes.add("xlsx");
		fileTypes.add("csv");
		fileTypes.add("txt");		
	}

	public CloverETLSimpleActivityFactory(String fileURL) throws Exception {
		String fileType = FilenameUtils.getExtension(fileURL).toLowerCase();
		fName = fileURL;

		if (fileType.equals("grf")) {
			activity = new CloverETLSchemeActivity();
			((CloverETLSchemeActivity) activity).setGraphPath(fileURL);

			activityFormat = ACTIVITY_FORMAT_SCHEME;

		} else { // TODO another formats
			activity = new CloverETLOneSourceDynamicTransformationActivity();

			activityFormat = ACTIVITY_FORMAT_SIMPLE_ONE_SOURCE;
		}
	}
	
	public void setSettingForActivity(int batchSize, String lastCompId){
		if(getActivityFormat() == CloverETLSimpleActivityFactory.ACTIVITY_FORMAT_SCHEME){
			((CloverETLSchemeActivity) activity).setBatchSize(batchSize);
			((CloverETLSchemeActivity)activity).setLastComponentID(lastCompId);
			
		}
		else if(getActivityFormat()==CloverETLSimpleActivityFactory.ACTIVITY_FORMAT_SIMPLE_ONE_SOURCE){
			((CloverETLOneSourceDynamicTransformationActivity) activity).setBatchSize(batchSize);
		}
		
		setBatchSize(batchSize);
	}
	
	public void setMetaDataInActivity(CloverETLTransformationMetadata meta){
		if(getActivityFormat() == CloverETLSimpleActivityFactory.ACTIVITY_FORMAT_SCHEME){
			((CloverETLSchemeActivity) activity).setMeta(meta);
		}
		else if(getActivityFormat()==CloverETLSimpleActivityFactory.ACTIVITY_FORMAT_SIMPLE_ONE_SOURCE){
			((CloverETLOneSourceDynamicTransformationActivity) activity).setMeta(meta);
		}
	}

	public void runActivityFactory(MiningETLArrayStreamInformer informer) throws Exception {
		setMetaDataInActivity(meta);		
		if(getActivityFormat() == CloverETLSimpleActivityFactory.ACTIVITY_FORMAT_SCHEME){
			((CloverETLSchemeActivity) activity).runActivity(informer);
		}
		else if(getActivityFormat()==CloverETLSimpleActivityFactory.ACTIVITY_FORMAT_SIMPLE_ONE_SOURCE){
			((CloverETLOneSourceDynamicTransformationActivity) activity).runActivity(fName, informer);
		}
	}
	
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
		meta = (CloverETLTransformationMetadata) activity.createMetadata();
		meta.setName(metaDataName);
		meta.setParameter(
				MiningTransformationMetadata.METADATA_PROP_FIELD_DELIMETER,
				fieldDelimeter);
		meta.setParameter(
				MiningTransformationMetadata.METADATA_PROP_RECORD_DELIMETER,
				recordDelimeter);
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
	
	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	public CloverETLTransformationMetadata getMeta() {
		return meta;
	}

	public void setMeta(CloverETLTransformationMetadata meta) {
		this.meta = meta;
	}

	public int getActivityFormat() {
		return activityFormat;
	}
	
	public CloverETLTransformationActivity getActivity() {
		return activity;
	}
	
	public static List<String> getSupportedFileTypes(){
		return fileTypes;
	}	
	
	public void setMiningInputStream(MiningInputStream miningInputStream) {
		this.miningInputStream = miningInputStream;
	}

	public MiningInputStream getMiningInputStream() {
		return miningInputStream;
	}
}
