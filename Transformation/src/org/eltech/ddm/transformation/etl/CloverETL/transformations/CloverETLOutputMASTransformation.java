/**
 * CloverETL MiningArrayStream writer node implementation of MiningTransformation.
 * Incapsulates MASWriter logic into a MiningTransformationActivity chain.
 * 
 * @author Evgeniy Krapivin
 * */

package org.eltech.ddm.transformation.etl.CloverETL.transformations;

import org.eltech.ddm.transformation.MiningETLArrayStreamInformer;
import org.eltech.ddm.transformation.MiningTransformation;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformation;

@SuppressWarnings("serial")
public class CloverETLOutputMASTransformation extends CloverETLTransformation {

	private MiningETLArrayStreamInformer miningInformer = null;
	private int dataBatchSize = 0;
	
	public CloverETLOutputMASTransformation() {
		if(inputPortCount == MiningTransformation.NO_SOURCE_AVAILABLE_FOR_TRANSFORMATION)
			inputPortCount = 0;
		
		this.id = String.format("node_output_mas_%d", instanceCount++);
	}
	
	@Override
	public void setParameter(int aId, String aValue) throws Exception {
		// no implementation
	}

	@Override
	public void setParameter(int aId, int aValue) throws Exception {
		if(aId == MiningTransformation.TRANSFORMATION_PARAM_DATABATCHSIZE)
			dataBatchSize = aValue;
	}

	@Override
	public void init() throws Exception {
        this.nodeObject = new MASWriter(this.id);
        
		((MASWriter) this.nodeObject).miningInformer = this.miningInformer;
		((MASWriter) this.nodeObject).dataBatchSize = this.dataBatchSize;
	}
	
	public void setInformer(MiningETLArrayStreamInformer aInformer) {
		this.miningInformer = aInformer;
	}
}
