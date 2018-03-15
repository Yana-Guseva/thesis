/**
 * CloverETL-specific implementation of MiningTransformationMetadata.
 * Incapsulates work with DataRecordMetadata objects.
 * 
 * @author Evgeniy Krapivin
 * */

package org.eltech.ddm.transformation.etl.CloverETL;

import org.eltech.ddm.transformation.MiningTransformationMetadata;
import org.jetel.metadata.DataFieldMetadata;
import org.jetel.metadata.DataRecordMetadata;

@SuppressWarnings("serial")
public class CloverETLTransformationMetadata extends MiningTransformationMetadata {

    private static int instanceCount = 0;

    private String id;
    private DataRecordMetadata metadataObject;
    
	public CloverETLTransformationMetadata() {
        this.id = String.format("metadata_%d", instanceCount++);
        this.metadataObject = new DataRecordMetadata(this.id, DataRecordMetadata.DELIMITED_RECORD);
	}
	
	@Override
	public void addFieldDescription(String aName, int aType) {
        if(this.metadataObject.getField(aName) == null)
        {
            DataFieldMetadata field = null;

            if(aType == MiningTransformationMetadata.FIELD_TYPE_INTEGER)
                field = new DataFieldMetadata(aName, DataFieldMetadata.INTEGER_FIELD, null);
            else if(aType == MiningTransformationMetadata.FIELD_TYPE_STRING)
                field = new DataFieldMetadata(aName, DataFieldMetadata.STRING_FIELD, null);
            else if(aType == MiningTransformationMetadata.FIELD_TYPE_LOGIC)
                field = new DataFieldMetadata(aName, DataFieldMetadata.BOOLEAN_FIELD, null);
            else if(aType == MiningTransformationMetadata.FIELD_TYPE_DOUBLE)
                field = new DataFieldMetadata(aName, DataFieldMetadata.NUMERIC_FIELD, null);
            
            if(field != null)
                this.metadataObject.addField(field);
        }
	}

	@Override
	public void setParameter(int aId, String aValue) {
        if(aId == MiningTransformationMetadata.METADATA_PROP_FIELD_DELIMETER)
            this.metadataObject.setFieldDelimiter(aValue);
        else if(aId == MiningTransformationMetadata.METADATA_PROP_RECORD_DELIMETER)
            this.metadataObject.setRecordDelimiter(aValue);
	}

	@Override
	public void setParameter(int aId, int aValue) {
        if(aId == MiningTransformationMetadata.METADATA_PROP_SKIP_SOURCE_ROW)
            this.metadataObject.setSkipSourceRows(aValue);
	}

	@Override
	public Object getObject() {
		return this.metadataObject;
	}
}
