/**
 * CloverETL file writer node implementation of MiningTransformation.
 * Writes all data-records into file.
 * 
 * @author Evgeniy Krapivin
 * */

package org.eltech.ddm.transformation.etl.CloverETL.transformations;

import org.jetel.component.DataWriter;
import org.eltech.ddm.transformation.*;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformation;

@SuppressWarnings("serial")
public class CloverETLOutputFileTransformation extends CloverETLTransformation {

    private String fileName = "";
    private String encoding = "";
    
	public CloverETLOutputFileTransformation(MiningTransformationActivity aActivity) {
		if(inputPortCount == MiningTransformation.NO_SOURCE_AVAILABLE_FOR_TRANSFORMATION)
			inputPortCount = 0;
		
		this.parentActivity = aActivity;
		
		this.id = String.format("node_output_file_%d", instanceCount++);   
	}
	
	@Override
	public void setParameter(int aId, String aValue) throws Exception {
        if(aId == MiningTransformation.TRANSFORMATION_PARAM_FILENAME)
            this.fileName = aValue;
        else if(aId == MiningTransformation.TRANSFORMATION_PARAM_CHARSET)
            this.encoding = aValue; 
	}

	@Override
	public void setParameter(int aId, int aValue) throws Exception {
		// no implementation
	}

	@Override
	public void init() throws Exception {
        if(this.fileName.isEmpty())
            throw new IllegalAccessException("Field fileName: output filename cannot be empty.");

        if(this.encoding.isEmpty())
            this.encoding = "UTF-8";

        this.nodeObject = new DataWriter(this.id, this.fileName, this.encoding, false);
	}
}
