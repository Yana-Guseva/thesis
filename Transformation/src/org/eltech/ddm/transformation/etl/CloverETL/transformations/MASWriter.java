/**
 * Custom transformation writer node.
 * Transforms all input data into MiningArrayStream object.
 * 
 * @author Evgeniy Krapivin
 * */

package org.eltech.ddm.transformation.etl.CloverETL.transformations;

import java.util.ArrayList;
import java.util.List;

import org.eltech.ddm.inputdata.MiningArrayStream;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.transformation.MiningETLArrayStreamDataProvider;
import org.eltech.ddm.transformation.MiningETLArrayStreamInformer;
import org.jetel.data.DataField;
import org.jetel.data.DataRecord;
import org.jetel.exception.ComponentNotReadyException;
import org.jetel.exception.ConfigurationProblem;
import org.jetel.exception.ConfigurationStatus;
import org.jetel.graph.InputPortDirect;
import org.jetel.graph.Node;
import org.jetel.graph.OutputPortDirect;
import org.jetel.graph.Result;
import org.jetel.metadata.DataFieldMetadata;
import org.jetel.metadata.DataRecordMetadata;
import org.jetel.util.string.CloverString;
import org.jetel.util.string.StringUtils;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeType;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.CategoryProperty;

public class MASWriter extends Node implements MiningETLArrayStreamDataProvider {

	public final static String COMPONENT_TYPE = "MAS_WRITER";
	
	/**
	 * All data should be passed to 0 port.
	 */
	private final static int READ_FROM_PORT = 0;
	
	//
	public MiningETLArrayStreamInformer miningInformer = null;
	public int dataBatchSize = 0;
	private ELogicalData logicalData = null;

	//private MiningArrayStream miningArrayStream;
	
	/** 
	 *  Simple constructor - everything is handled by super class
	 *  In case we need some additional initialization, it is done in init() method 
	 */
	public MASWriter(String id) {
		super(id);
	}

	/**
	 * Following method is mostly used by ComponentFactory when creating instance of this class 
	 */ 
	public String getType() {
		return COMPONENT_TYPE;
	}

	/** 
	 *  This method is called prior to starting component. Any allocation and checking should be
	 *  done here. If anything goes wrong, it should throw ComponentNotReadyException.
	 */
	public void init() throws ComponentNotReadyException {
        if(isInitialized())
			return;
			
		super.init();
	}

	/** 
	 *  This is the main processing method of a component. Node and subsequently this component is 
	 *  inherited from Thread class. By implementing run() method, we define what is the thread  
	 *  going to do. After the graph is initialized (by calling init() methods of all registered  
	 *  components), for every component in graph, there is a thread started and it executes run() 
	 *  method.
	 */
	@Override
	public Result execute() throws Exception {
		InputPortDirect inPort = (InputPortDirect) getInputPort(READ_FROM_PORT);
		
		// generate logical data
		this.logicalData = new ELogicalData();
		DataRecordMetadata metadata = inPort.getMetadata();
		String[] fieldNames = metadata.getFieldNamesArray();
		for (String fieldName : fieldNames) {
//			String attributeName = String.format("attr_%s", fieldName);
			String attributeName = fieldName;
			char fieldMetaType = metadata.getField(fieldName).getType();
			
			ELogicalAttribute attribute;
			if(fieldMetaType == DataFieldMetadata.INTEGER_FIELD)
				attribute = new ELogicalAttribute(attributeName, AttributeType.numerical);
			else if(fieldMetaType == DataFieldMetadata.BOOLEAN_FIELD) // treat as categorial field
				attribute = new ELogicalAttribute(attributeName, AttributeType.categorical); 
			else if(fieldMetaType == DataFieldMetadata.STRING_FIELD)
				attribute = new ELogicalAttribute(attributeName, AttributeType.categorical);
			else if(fieldMetaType == DataFieldMetadata.NUMERIC_FIELD)
				attribute = new ELogicalAttribute(attributeName, AttributeType.numerical);
			else
				throw new Exception("Unknown metadata field type: " + fieldMetaType);
			
			this.logicalData.addAttribute(attribute);
		}
		
		miningInformer.MiningETLArrayStreamPrepared(this.logicalData, this);
  		
        return runIt ? Result.FINISHED_OK : Result.ABORTED;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public synchronized void reset() throws ComponentNotReadyException {
		super.reset();		
		//DO NOTHING
	}

	/**
	 *  Following method is used by TransformationGraph class when the graph is initialized.
	 *  This method is responsible for checking input output ports.      
     */
    @Override
	public ConfigurationStatus checkConfig(ConfigurationStatus status) {
    	super.checkConfig(status);
   		 
    	if(!checkInputPorts(status, 1, 1) || !checkOutputPorts(status, 0, 0))
    		   	return status;

        checkMetadata(status, getInMetadata(), getOutMetadata(), false);

        try {
            init();
		} 
		catch (ComponentNotReadyException e) {
            ConfigurationProblem problem = new ConfigurationProblem(e.getMessage(), ConfigurationStatus.Severity.ERROR, this, ConfigurationStatus.Priority.NORMAL);
            if(!StringUtils.isEmpty(e.getAttributeName()))
                problem.setAttributeName(e.getAttributeName());
            status.add(problem);
		} 
		finally {
            free();
        }
            
        return status;
    }
    
	@Override
	public double[][] getArrayStreamData() throws Exception {
		InputPortDirect inPort = (InputPortDirect) getInputPort(READ_FROM_PORT);
		
		DataRecordMetadata metadata = inPort.getMetadata();
		int attributeCount = metadata.getNumFields();
		
		String[] fieldNames = metadata.getFieldNamesArray();
		
		List<List<Double>> physicalDataTemp = new ArrayList<List<Double>>(attributeCount);
		for(int i = 0; i < attributeCount; i++)
			physicalDataTemp.add(new ArrayList<Double>());
		
		DataRecord record = new DataRecord(inPort.getMetadata());
		record.init();
		
		// generate physical data by dataBatchSize
		
		int k = 0;

		while (inPort.readRecord(record) != null && runIt) {
			for (String fieldName : fieldNames) {
				int attributeIndex = metadata.getFieldPosition(fieldName);
				char fieldMetaType = metadata.getField(fieldName).getType();
				DataField dataField = record.getField(attributeIndex);
				if(fieldMetaType == DataFieldMetadata.INTEGER_FIELD) {
					// integer attr
					
					Integer integerAttrValue = (Integer) dataField.getValue();
					
					physicalDataTemp.get(attributeIndex).add(new Double(integerAttrValue));
				}
				else if(fieldMetaType == DataFieldMetadata.NUMERIC_FIELD) {
					Double doubleAttrValue = (Double) dataField.getValue();
					
					physicalDataTemp.get(attributeIndex).add(doubleAttrValue);
				}
				else if(fieldMetaType == DataFieldMetadata.STRING_FIELD ||
						fieldMetaType == DataFieldMetadata.BOOLEAN_FIELD) {
					// logical attr treated as categorial
					// categorial attr
					
					String categorialAttrValue;
					if(fieldMetaType == DataFieldMetadata.STRING_FIELD)
						categorialAttrValue = ((CloverString) dataField.getValue()).toString();
					else
						categorialAttrValue = ((Boolean) dataField.getValue()).toString();
					
					// find corresponding categorial attr
					
					ELogicalAttribute categorialAttribute = logicalData.getAttribute(fieldName);
					if(categorialAttribute.getAttributeType() != AttributeType.categorical)
						throw new Exception("Incompatible attribute type with name: " + fieldName);
					
					// get cetegory collection
					
					Integer categoryIndex = categorialAttribute.getCategoricalProperties().getIndex(categorialAttrValue);
					if(categoryIndex == null) // add to category collection
						categoryIndex = categorialAttribute.getCategoricalProperties().addCategory(categorialAttrValue, CategoryProperty.valid);
					
					physicalDataTemp.get(attributeIndex).add(new Double(categoryIndex));
				}
			}
			
			k++;
			if(dataBatchSize > 0) // otherwise take all
				if(k == dataBatchSize)
					break;
		}
		
		System.out.println("Logical data: ");//
		System.out.println(logicalData.toString());//
		
		double[][] miningSourceArray = null;
		if(physicalDataTemp.get(0).size() != 0) {
			miningSourceArray = new double[physicalDataTemp.get(0).size()][attributeCount];
			for(int attributeIndex = 0; attributeIndex < attributeCount; attributeIndex++) {
				for(int cursorPosition = 0; cursorPosition < physicalDataTemp.get(attributeIndex).size(); cursorPosition++)
					miningSourceArray[cursorPosition][attributeIndex] = physicalDataTemp.get(attributeIndex).get(cursorPosition);
			}
		}
		
	   // this.miningArrayStream = new MiningArrayStream(miningSourceArray, logicalData);//
		return miningSourceArray;

//	    System.out.println("Physical data: ");
//	    while (miningArrayStream.next())
	//        System.out.println("Next mining vector: " + this.miningArrayStream.readVector().toString());

//		System.out.println("MASWriter done.");
	}
}
