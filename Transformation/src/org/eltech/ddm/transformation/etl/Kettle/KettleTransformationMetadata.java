/**
 * Kettle-specific implementation of MiningTransformationMetadata.
 * 
 * @author Evgeniy Krapivin
 * */

package org.eltech.ddm.transformation.etl.Kettle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eltech.ddm.transformation.MiningTransformationMetadata;
import org.pentaho.di.core.row.ValueMetaInterface;

@SuppressWarnings("serial")
public class KettleTransformationMetadata extends MiningTransformationMetadata {
	private List<String> fieldsNames = new ArrayList<String>();
	private List<Integer> fieldsValues = new ArrayList<Integer>();
	
	private String fieldDelimiter = ",";
	private String recordDelimiter = "\n";
	private int skipRowsAmount = 0;
	
	public KettleTransformationMetadata() {
		// no implementation
	}
	
	@Override
	public void addFieldDescription(String aName, int aType) {
        if(aType == MiningTransformationMetadata.FIELD_TYPE_INTEGER) {
        	fieldsNames.add(aName);
        	fieldsValues.add(ValueMetaInterface.TYPE_INTEGER);
        }
        else if(aType == MiningTransformationMetadata.FIELD_TYPE_STRING) {
        	fieldsNames.add(aName);
        	fieldsValues.add(ValueMetaInterface.TYPE_STRING);
        }
        else if(aType == MiningTransformationMetadata.FIELD_TYPE_LOGIC) {
        	fieldsNames.add(aName);
        	fieldsValues.add(ValueMetaInterface.TYPE_BOOLEAN);
        }
        else if(aType == MiningTransformationMetadata.FIELD_TYPE_DOUBLE) {
        	fieldsNames.add(aName);
        	fieldsValues.add(ValueMetaInterface.TYPE_NUMBER);
        }
	}

	@Override
	public void setParameter(int aId, String aValue) {
        if(aId == MiningTransformationMetadata.METADATA_PROP_FIELD_DELIMETER)
        	fieldDelimiter = aValue;
        else if(aId == MiningTransformationMetadata.METADATA_PROP_RECORD_DELIMETER)
            recordDelimiter = aValue;
	}

	@Override
	public void setParameter(int aId, int aValue) {
        if(aId == MiningTransformationMetadata.METADATA_PROP_SKIP_SOURCE_ROW)
        	skipRowsAmount = aValue;
	}

	@Override
	public Object getObject() {
		return null;
	}
	
	public List<String> getNames() {
		return fieldsNames;
	}
	
	public List<Integer> getValues() {
		return fieldsValues;
	}
	
	public String getFieldDelimiter() {
		return fieldDelimiter;
	}
	
	public String getRecordDelimiter() {
		return recordDelimiter;
	}
	
	public int getSkipRowsAmount() {
		return skipRowsAmount;
	}
}
