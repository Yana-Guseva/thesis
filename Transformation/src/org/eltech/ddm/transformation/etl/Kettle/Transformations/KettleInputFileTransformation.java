/**
 * Kettle file reader node implementation of MiningTransformation.
 * Reads data-records from *.csv file.
 * 
 * @author Evgeniy Krapivin
 * */

package org.eltech.ddm.transformation.etl.Kettle.Transformations;

import java.util.List;
import java.util.Map;

import org.eltech.ddm.transformation.MiningTransformation;
import org.eltech.ddm.transformation.etl.Kettle.KettleTransformation;
import org.eltech.ddm.transformation.etl.Kettle.KettleTransformationMetadata;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.csvinput.CsvInputMeta;
import org.pentaho.di.trans.steps.textfileinput.TextFileInputField;

@SuppressWarnings("serial")
public class KettleInputFileTransformation extends KettleTransformation {
    private String fileName = "";
    private CsvInputMeta cim = null;
    
    public KettleInputFileTransformation() {
    	id = String.format("node_input_file_%d", instanceCount++);
    }
    
	@Override
	public void setParameter(int aId, String aValue) throws Exception {
        if(aId == MiningTransformation.TRANSFORMATION_PARAM_FILENAME)
            fileName = aValue;
	}

	@Override
	public void setParameter(int aId, int aValue) throws Exception {
		// no implementation
	}

	@Override
	public void init() throws Exception {
        if(fileName.isEmpty())
            throw new IllegalAccessException("Field fileName: input filename cannot be empty.");

        PluginRegistry kettlePluginRegistry = PluginRegistry.getInstance();
        
        cim = new CsvInputMeta();
		String csvInputPid = kettlePluginRegistry.getPluginId(StepPluginType.class, cim);
		nodeObject = new StepMeta(csvInputPid, id, cim);
	}
	
	public void setMetadata(KettleTransformationMetadata aMetadata) {
		List<String> fieldsNames = aMetadata.getNames();
		List<Integer> fieldsValues = aMetadata.getValues();

		TextFileInputField[] fields = new TextFileInputField[fieldsNames.size()];
		for (int idx = 0; idx < fields.length; idx++) {
			fields[idx] = new TextFileInputField();
			
			fields[idx].setName(fieldsNames.get(idx));
			fields[idx].setType(fieldsValues.get(idx));
			fields[idx].setFormat("");
			fields[idx].setLength(-1);
			fields[idx].setPrecision(-1);
			fields[idx].setCurrencySymbol("");
			fields[idx].setDecimalSymbol(".");
			fields[idx].setGroupSymbol("");
			fields[idx].setTrimType(ValueMetaInterface.TRIM_TYPE_NONE);
		}

		cim.setIncludingFilename(false);
		cim.setFilename(fileName);
		cim.setDelimiter(aMetadata.getFieldDelimiter());
		cim.setEnclosure("\"");
		cim.setBufferSize("50000");
		cim.setLazyConversionActive(false);
		cim.setHeaderPresent(aMetadata.getSkipRowsAmount() != 0);
		cim.setAddResultFile(false);
		cim.setRunningInParallel(false);
		cim.setInputFields(fields);
	}
}
