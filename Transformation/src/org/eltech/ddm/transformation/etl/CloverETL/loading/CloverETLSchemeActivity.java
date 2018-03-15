package org.eltech.ddm.transformation.etl.CloverETL.loading;

import java.util.HashMap;
import java.util.Map;

import org.eltech.ddm.transformation.MiningETLArrayStreamInformer;
import org.eltech.ddm.transformation.MiningTransformation;
import org.eltech.ddm.transformation.MiningTransformationMetadata;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationActivity;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationMetadata;
import org.eltech.ddm.transformation.etl.CloverETL.transformations.CloverETLOutputMASTransformation;

/**
 * Type 2.
 * This activity present: source in clover scheme where we have one output port (can be after merge), 
 * all transformation in clover scheme.
 * 
 * @author SemenchenkoA
 *
 ** 
 */

public class CloverETLSchemeActivity extends CloverETLTransformationActivity{
	private static final long serialVersionUID = 1L;
	
	private CloverETLDisplacedTransformationActivity cloverActivityDis = null;
	private CloverETLTransformationMetadata meta = null ;

	private int batchSize = 1; 
	private String graphPath = "";
	private String lastComponentID = "";
	
	/**
	 * @return clover activity
	 */
	public CloverETLTransformationActivity getCloverActivity() {
		return this;
	}

	/**
	 * Constructor
	 */
	public CloverETLSchemeActivity(){
		super();
	}
	
	/**
	 * @param informer Set where MiningETLArrayStreamInformer implements
	 * @throws Exception
	 */
	public void runActivity(MiningETLArrayStreamInformer informer) throws Exception{	
		cloverActivityDis = new CloverETLDisplacedTransformationActivity(
				graphPath, getLastComponentID(), meta);

		CloverETLOutputMASTransformation output = (CloverETLOutputMASTransformation) cloverActivityDis
				.createTransformation(MiningTransformation.TRANSFORMATION_TYPE_OUTPUT_MAS);
		output.setParameter(
				MiningTransformation.TRANSFORMATION_PARAM_DATABATCHSIZE, batchSize);
		
		output.setInformer(informer);
		output.init();
		
		cloverActivityDis.setWelcomeComponent(output);

		cloverActivityDis.transform();		
	}
	
	/**
	 * @param dataFieldsName Field name
	 * @param dataFieldsType Field type
	 */
	public void addMetaDataField(String dataFieldsName, int dataFieldsType){
		if(meta != null) meta.addFieldDescription(dataFieldsName, dataFieldsType);
	}
	
	/**
	 * @return Data fields types
	 */
	public Map<Integer, String> getDataFieldsTypes(){
		Map<Integer, String> dataFieldsTypes = new HashMap<Integer, String>();
		dataFieldsTypes.put(0, "Integer");
		dataFieldsTypes.put(1, "String");
		dataFieldsTypes.put(2, "Boolean");
		dataFieldsTypes.put(3, "Double");

		return dataFieldsTypes;
	}
	
	/**
	 * @param metaDataName Name for meta data
	 * @param fieldDelimeter Field dilimeter
	 * @param recordDelimeter Record delimeter
	 * @throws Exception
	 */
	public void setMetaData(String metaDataName, String fieldDelimeter, String recordDelimeter) throws Exception{
		meta = (CloverETLTransformationMetadata)this.getCloverActivity().createMetadata();
		meta.setName(metaDataName);
		meta.setParameter(
				MiningTransformationMetadata.METADATA_PROP_FIELD_DELIMETER,
				fieldDelimeter);
		meta.setParameter(
				MiningTransformationMetadata.METADATA_PROP_RECORD_DELIMETER,
				recordDelimeter);
	}
	
	public String getLastComponentID() {
		/*
		String res = "";
		
		for(int i = 0; i<this.graph.getNodes().size(); i++){		
			if(this.graph.getNodes().get(i).getOutPorts().size()==0){
				res = this.graph.getNodes().get(i).getId();
			}
		}*/

		return lastComponentID;
	}

	public void setLastComponentID(String lastComponentID) {
		this.lastComponentID = lastComponentID;
	}

	public String getGraphPath() {
		return graphPath;
	}

	public void setGraphPath(String graphPath) {
		this.graphPath = graphPath;
	}

	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}
	
	public CloverETLDisplacedTransformationActivity getCloverActivityDis() {
		return cloverActivityDis;
	}
	
	public CloverETLTransformationMetadata getMeta() {
		return meta;
	}

	public void setMeta(CloverETLTransformationMetadata meta) {
		this.meta = meta;
	}
}
