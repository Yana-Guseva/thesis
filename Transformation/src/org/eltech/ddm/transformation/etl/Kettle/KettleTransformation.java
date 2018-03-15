/**
 * Kettle-specific core implementation of MiningTransformation.
 * Describes common properties of all Kettle transformations.
 * 
 * @author Evgeniy Krapivin
 * */

package org.eltech.ddm.transformation.etl.Kettle;

import org.eltech.ddm.transformation.MiningTransformation;
import org.pentaho.di.trans.step.StepMeta;

@SuppressWarnings("serial")
public abstract class KettleTransformation extends MiningTransformation {

	protected static int instanceCount = 0;
	
	protected String id = "";
	protected StepMeta nodeObject = null;

	@Override
	public Object getObject() {
		return nodeObject;
	}
}
