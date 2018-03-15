/**
 * Kettle-specific implementation of MiningTransformationStep.
 * Kettle does not support step concept.
 * 
 * @author Evgeniy Krapivin
 * */

package org.eltech.ddm.transformation.etl.Kettle;

import org.eltech.ddm.transformation.MiningTransformationStep;
import org.eltech.ddm.transformation.MiningTransformationTask;

@SuppressWarnings("serial")
public class KettleTransformationStep extends MiningTransformationStep {
	public KettleTransformationStep(KettleTransformationActivity aActivity) {
		// no implementation
	}
	
	@Override
	public void addTask(MiningTransformationTask aTask) throws Exception {
		// no implementation
	}

	@Override
	public void init() throws Exception {
		// no implementation
	}

	@Override
	public Object getObject() {
		return null;
	}
}
