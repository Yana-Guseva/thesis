/**
 * CloverETL clean node implementation of MiningTransformation.
 * Transforms each data-record in input by applying a custom transformation
 * method to it.
 * 
 * @author Evgeniy Krapivin
 * */

package org.eltech.ddm.transformation.etl.CloverETL.transformations;

import org.jetel.component.Reformat;
import org.eltech.ddm.transformation.*;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformation;

@SuppressWarnings("serial")
public class CloverETLReformatTransformation extends CloverETLTransformation {

    private String methodName = "";
    
	public CloverETLReformatTransformation() {
		if(inputPortCount == MiningTransformation.NO_SOURCE_AVAILABLE_FOR_TRANSFORMATION)
			inputPortCount = 0;
		outputPortCount = 0; // always only one output port available
		
		this.id = String.format("node_reformat_%d", instanceCount++);
	}
	
	public void setParentActivity(MiningTransformationActivity aActivity){
		this.parentActivity = aActivity;
	}
	
	@Override
	public void setParameter(int aId, String aValue) throws Exception {
        if(aId == MiningTransformation.TRANSFORMATION_PARAM_METHOD_NAME)
            this.methodName = aValue;
	}

	@Override
	public void setParameter(int aId, int aValue) throws Exception {
		// no implementation
	}

	@Override
	public void init() throws Exception {
        if(this.methodName.isEmpty())
            throw new IllegalAccessException("Field methodName: input filename cannot be empty.");
        
        this.nodeObject = new Reformat(this.id, null, this.methodName, null);
	}
}
