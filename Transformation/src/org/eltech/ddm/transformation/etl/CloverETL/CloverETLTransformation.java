/**
 * CloverETL-specific core implementation of MiningTransformation.
 * Describes common properties of all CloverETL transformations.
 * 
 * @author Evgeniy Krapivin
 * */

package org.eltech.ddm.transformation.etl.CloverETL;

import org.eltech.ddm.transformation.MiningTransformation;
import org.eltech.ddm.transformation.MiningTransformationActivity;
import org.jetel.graph.Node;

@SuppressWarnings("serial")
public abstract class CloverETLTransformation extends MiningTransformation {

	protected static int instanceCount = 0;
	
	protected int inputPortCount = MiningTransformation.NO_SOURCE_AVAILABLE_FOR_TRANSFORMATION;
	protected int outputPortCount = MiningTransformation.NO_TARGET_AVAILABLE_FOR_TRANSFORMATION;
	
	protected MiningTransformationActivity parentActivity = null;
	
	protected String id = "";
	protected Node nodeObject = null;

	@Override
	public Object getObject() {
		return this.nodeObject;
	}

    public int getNextInputPort() {
    	if(inputPortCount != MiningTransformation.NO_SOURCE_AVAILABLE_FOR_TRANSFORMATION)
    		return inputPortCount++;
    	else
    		return MiningTransformation.NO_SOURCE_AVAILABLE_FOR_TRANSFORMATION;
    }

    public int getNextOutputPort() {
    	if(outputPortCount != MiningTransformation.NO_TARGET_AVAILABLE_FOR_TRANSFORMATION)
    		return outputPortCount++;
    	else
    		return MiningTransformation.NO_TARGET_AVAILABLE_FOR_TRANSFORMATION;
    }
}
