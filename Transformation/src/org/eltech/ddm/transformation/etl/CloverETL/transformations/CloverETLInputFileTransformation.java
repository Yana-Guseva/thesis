/**
 * CloverETL file reader node implementation of MiningTransformation.
 * Reads data-records from *.csv file.
 * 
 * @author Evgeniy Krapivin
 * */

package org.eltech.ddm.transformation.etl.CloverETL.transformations;

import org.jetel.component.DataReader;
import org.eltech.ddm.transformation.*;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformation;

@SuppressWarnings("serial")
public class CloverETLInputFileTransformation extends CloverETLTransformation {

    private String fileName = "";
    
    public CloverETLInputFileTransformation(MiningTransformationActivity aActivity) {
    	if(outputPortCount == MiningTransformation.NO_TARGET_AVAILABLE_FOR_TRANSFORMATION)
			outputPortCount = 0;
    	
    	this.parentActivity = aActivity;
    	
    	this.id = String.format("node_input_file_%d", instanceCount++);
    }
    
	@Override
	public void setParameter(int aId, String aValue) throws Exception {
        if(aId == MiningTransformation.TRANSFORMATION_PARAM_FILENAME)
            this.fileName = aValue;
	}

	@Override
	public void setParameter(int aId, int aValue) throws Exception {
		// no implementation
	}

	@Override
	public void init() throws Exception {
        if(this.fileName.isEmpty())
            throw new IllegalAccessException("Field fileName: input filename cannot be empty.");

        this.nodeObject = new DataReader(this.id, this.fileName);
	}
}
