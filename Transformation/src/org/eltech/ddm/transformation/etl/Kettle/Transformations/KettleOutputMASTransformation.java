/**
 * Kettle MiningArrayStream writer node implementation of MiningTransformation.
 * Incapsulates MASWriter logic into a MiningTransformationActivity chain.
 * 
 * @author Evgeniy Krapivin
 * */

package org.eltech.ddm.transformation.etl.Kettle.Transformations;

import org.eltech.ddm.transformation.MiningETLArrayStreamInformer;
import org.eltech.ddm.transformation.MiningTransformation;
import org.eltech.ddm.transformation.etl.Kettle.KettleTransformation;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.csvinput.CsvInputMeta;

@SuppressWarnings("serial")
public class KettleOutputMASTransformation extends KettleTransformation {
	private MiningETLArrayStreamInformer miningInformer = null;
	private int dataBatchSize = 0;
	private OutputMASTransMeta cim = null;
	
	public KettleOutputMASTransformation() {
		id = String.format("node_output_mas_%d", instanceCount++);
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
        cim = new OutputMASTransMeta();
		cim.setMiningPerformer(miningInformer);
		cim.setBatchSize(dataBatchSize);
        
        nodeObject = new StepMeta("OutputMASTransMeta", id, cim);
	}
	
	public void setInformer(MiningETLArrayStreamInformer aInformer) {
		miningInformer = aInformer;
	}
}
