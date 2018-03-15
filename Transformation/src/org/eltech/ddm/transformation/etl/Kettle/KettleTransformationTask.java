/**
 * Kettle-specific implementation of MiningTransformationTask.
 * 
 * @author Evgeniy Krapivin
 * */

package org.eltech.ddm.transformation.etl.Kettle;

import java.util.List;

import org.eltech.ddm.transformation.MiningTransformation;
import org.eltech.ddm.transformation.MiningTransformationMetadata;
import org.eltech.ddm.transformation.MiningTransformationTask;
import org.eltech.ddm.transformation.etl.Kettle.Transformations.KettleInputFileTransformation;
import org.pentaho.di.trans.TransHopMeta;
import org.pentaho.di.trans.step.StepMeta;

public class KettleTransformationTask extends MiningTransformationTask {
	private KettleTransformationMetadata metadata = null;
	private StepMeta hopFrom = null;
	private StepMeta hopTo = null;
	
	public KettleTransformationTask(MiningTransformationMetadata aMetadata) {
		metadata = (KettleTransformationMetadata) aMetadata;
	}
	
	@Override
	public void setSource(MiningTransformation aTransformation) throws Exception {
		if(aTransformation instanceof KettleInputFileTransformation)
			((KettleInputFileTransformation) aTransformation).setMetadata(metadata);
		
		hopFrom = (StepMeta) aTransformation.getObject();
	}

	@Override
	public void setTarget(MiningTransformation aTransformation) throws Exception {
		hopTo = (StepMeta) aTransformation.getObject();
	}

	@Override
	public Object getObject() {
		return new TransHopMeta(hopFrom, hopTo);
	}

	@Override
	public List<MiningTransformation> getTransformationList() {
		return null;
	}
}
