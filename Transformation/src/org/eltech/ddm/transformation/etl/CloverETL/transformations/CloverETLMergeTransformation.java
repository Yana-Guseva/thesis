/**
 * CloverETL merge transformation node implementation of MiningTransformation.
 * Merges data-records in all input ports using a set of merge field-names.
 * 
 * @author Evgeniy Krapivin
 * */

package org.eltech.ddm.transformation.etl.CloverETL.transformations;

import java.util.*;
import org.jetel.component.Merge;
import org.eltech.ddm.transformation.*;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformation;

@SuppressWarnings("serial")
public class CloverETLMergeTransformation extends CloverETLTransformation {

    private List<String> mergeKeys = new ArrayList<String>();
    
	public CloverETLMergeTransformation() {
		if(inputPortCount == MiningTransformation.NO_SOURCE_AVAILABLE_FOR_TRANSFORMATION)
			inputPortCount = 0;
		if(outputPortCount == MiningTransformation.NO_TARGET_AVAILABLE_FOR_TRANSFORMATION)
			outputPortCount = 0;
		
		this.id = String.format("node_merge_%d", instanceCount++);
	}
	
	public void setParentActivity(MiningTransformationActivity aActivity){
		this.parentActivity = aActivity;
	}
	
	@Override
	public void setParameter(int aId, String aValue) throws Exception {
        if(aId == MiningTransformation.TRANSFORMATION_PARAM_MERGE_KEY)
        {
            if(!aValue.isEmpty() && this.mergeKeys.indexOf(aValue) == -1)
                this.mergeKeys.add(aValue);
        }
	}

	@Override
	public void setParameter(int aId, int aValue) throws Exception {
		// no implementation
	}

	@Override
	public void init() throws Exception {
        String[] arrayMergeKeys = new String[this.mergeKeys.size()];
        int arrayIndex = 0;
        for (String key : this.mergeKeys)
            arrayMergeKeys[arrayIndex++] = key;

        this.nodeObject = new Merge(this.id, arrayMergeKeys);
        //this.nodeObject.setEnabled("enabled");
	}
}
