package org.eltech.ddm.transformation.etl.CloverETL.loading;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningdata.EPhysicalData;
import org.eltech.ddm.miningcore.miningdata.assignment.EPhysicalAttribute;
import org.eltech.ddm.transformation.MiningTransformationMetadata;
import org.jetel.data.DataRecord;
import org.jetel.data.Defaults;
import org.jetel.data.IntegerDataField;
import org.jetel.data.NumericDataField;
import org.jetel.data.StringDataField;
import org.jetel.exception.ComponentNotReadyException;
import org.jetel.exception.ConfigurationProblem;
import org.jetel.exception.ConfigurationStatus;
import org.jetel.graph.InputPortDirect;
import org.jetel.graph.Node;
import org.jetel.graph.Result;
import org.jetel.metadata.DataFieldMetadata;
import org.jetel.metadata.DataRecordMetadata;
import org.jetel.util.SynchronizeUtils;
import org.jetel.util.string.StringUtils;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeType;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.CategoryProperty;

public class PhysicalDataComponent extends Node {

	public final static String COMPONENT_TYPE = "NEW_COMPONENT";

	private final static int READ_FROM_PORT = 0;

	private final static int WRITE_TO_PORT = 0;

	private ByteBuffer recordBuffer;
	
	private List< ArrayList<String>> listsCategorical = null; 
	private EPhysicalData physicalData = null;
	private ELogicalData logicalData = null;
	
	public PhysicalDataComponent(String id) {
		super(id);
	}

	public String getType() {
		return COMPONENT_TYPE;
	}

	public void init() throws ComponentNotReadyException {
		if (isInitialized())
			return;
		super.init();

		recordBuffer = ByteBuffer
				.allocateDirect(Defaults.Record.FIELD_LIMIT_SIZE);
		if (recordBuffer == null) {
			throw new ComponentNotReadyException(
					"Can NOT allocate internal record buffer ! Required size:"
							+ Defaults.Record.FIELD_LIMIT_SIZE);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public Result execute() throws Exception {		
		InputPortDirect inPort = (InputPortDirect) getInputPort(READ_FROM_PORT);

		DataRecord inRecord1 = new DataRecord(inPort.getMetadata());
		inRecord1.init();
		
		DataRecordMetadata metaData = inPort.getMetadata();		
		
		listsCategorical = new ArrayList<ArrayList <String> >();
		physicalData = new EPhysicalData();
		logicalData = new ELogicalData();		
		
		for(int k = 0; k < metaData.getNumFields(); k++){
			String attributeName = metaData.getField(k).getName();

			switch (metaData.getField(k).getType()) {
			case DataFieldMetadata.INTEGER_FIELD:
				physicalData.addAttribute(new EPhysicalAttribute(attributeName,
						MiningTransformationMetadata.FIELD_TYPE_INTEGER));
				logicalData.addAttribute(new ELogicalAttribute(attributeName,
						AttributeType.numerical));
				break;
			case DataFieldMetadata.BOOLEAN_FIELD:
				physicalData.addAttribute(new EPhysicalAttribute(attributeName,
						MiningTransformationMetadata.FIELD_TYPE_LOGIC));
				logicalData.addAttribute(new ELogicalAttribute(attributeName,
						AttributeType.categorical));
				break;
			case DataFieldMetadata.NUMERIC_FIELD:
				physicalData.addAttribute(new EPhysicalAttribute(attributeName,
						MiningTransformationMetadata.FIELD_TYPE_DOUBLE));
				logicalData.addAttribute(new ELogicalAttribute(attributeName,
						AttributeType.numerical));
				break;
			case DataFieldMetadata.STRING_FIELD:
			default:
				listsCategorical.add(new ArrayList<String>());

				physicalData.addAttribute(new EPhysicalAttribute(attributeName,
						MiningTransformationMetadata.FIELD_TYPE_STRING));
				logicalData.addAttribute(new ELogicalAttribute(attributeName,
						AttributeType.categorical));
				break;
			}
		}
		
		while (inPort.readRecord(inRecord1) != null && runIt) {						
			for(int k = 0, findField = 0; k < metaData.getNumFields(); k++){				
				switch(metaData.getField(k).getType()){
				case DataFieldMetadata.INTEGER_FIELD:		
				case DataFieldMetadata.NUMERIC_FIELD:
					char type = metaData.getField(k).getType();
					double currentValue = 0.;
					
					if (type == DataFieldMetadata.INTEGER_FIELD)
						currentValue = ((IntegerDataField) inRecord1
								.getField(k)).getDouble();
					else
						currentValue = ((NumericDataField) inRecord1
								.getField(k)).getDouble();					
					
					if(currentValue<logicalData.getAttribute(k).getNumericalProperties().getLowerBound()){
						logicalData.getAttribute(k).getNumericalProperties().setLowerBound(currentValue);
					}
					if(currentValue>logicalData.getAttribute(k).getNumericalProperties().getUpperBound()){
						logicalData.getAttribute(k).getNumericalProperties().setUpperBound(currentValue);;
					}
					break;		
				case DataFieldMetadata.BOOLEAN_FIELD:
					String tempStr1 = inRecord1.getField(k).toString();
					
					if(!listsCategorical.get(findField).contains(tempStr1)){
						listsCategorical.get(findField).add(tempStr1);
						logicalData.getAttribute(k).getCategoricalProperties().addCategory(tempStr1, CategoryProperty.valid);
					}
					findField++;		
					break;
				case DataFieldMetadata.STRING_FIELD:
				default:
					String tempStr = inRecord1.getField(k).toString();
					
					if(!listsCategorical.get(findField).contains(tempStr)){
						listsCategorical.get(findField).add(tempStr);
						logicalData.getAttribute(k).getCategoricalProperties().addCategory(tempStr, CategoryProperty.valid);					
					}
					findField++;
					break;
				}
			}			

			writeRecordBroadcast(inRecord1);
			SynchronizeUtils.cloverYield();
		}
		
		//System.out.println("PhysicalCategories: " + listsCategorical.toString());
		System.out.println("ELogicalData: ");
		for(int i = 0; i<logicalData.getAttributesNumber(); i++){
			System.out.println(logicalData.getAttribute(i).toString());
			
			if(logicalData.getAttribute(i).getAttributeType()==AttributeType.numerical){
				System.out.println("min, max: "+logicalData.getAttribute(i).getNumericalProperties().getLowerBound()+" "+
						logicalData.getAttribute(i).getNumericalProperties().getUpperBound());
			}
		}

		return runIt ? Result.FINISHED_OK : Result.ABORTED;
	}

	@Override
	public synchronized void reset() throws ComponentNotReadyException {
		super.reset();
	}

	@Override
	public ConfigurationStatus checkConfig(ConfigurationStatus status) {
		super.checkConfig(status);

		if (!checkInputPorts(status, 1, 1)
				|| !checkOutputPorts(status, 1, Integer.MAX_VALUE)) {
			return status;
		}
		checkMetadata(status, getInMetadata(), getOutMetadata(), false);

		try {
			init();
		} catch (ComponentNotReadyException e) {
			ConfigurationProblem problem = new ConfigurationProblem(
					e.getMessage(), ConfigurationStatus.Severity.ERROR, this,
					ConfigurationStatus.Priority.NORMAL);
			if (!StringUtils.isEmpty(e.getAttributeName())) {
				problem.setAttributeName(e.getAttributeName());
			}
			status.add(problem);
		} finally {
			free();
		}

		return status;
	}
	
	public ELogicalData getLogicalData() {
		return logicalData;
	}

	public EPhysicalData getPhysicalData() {
		return physicalData;
	}

	public List<ArrayList<String>> getListsCategorical() {
		return listsCategorical;
	}
}
