package org.eltech.ddm.transformation.etl.CloverETL.transformations;

import org.eltech.ddm.transformation.MiningTransformation;
import org.eltech.ddm.transformation.MiningTransformationActivity;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformation;
import org.jetel.component.XLSReader;

/**
 * CloverETL file reader for excel.
 * 
 * @author SemenchenkoA
 *
 */
public class CloverETLInputXLSDataTransformation extends
		CloverETLTransformation {

	private String fileName = "";

	public CloverETLInputXLSDataTransformation(
			MiningTransformationActivity aActivity) {
		if (outputPortCount == MiningTransformation.NO_TARGET_AVAILABLE_FOR_TRANSFORMATION)
			outputPortCount = 0;

		this.parentActivity = aActivity;

		this.id = String.format("node_input_file_%d", instanceCount++);
	}

	@Override
	public void setParameter(int aId, String aValue) throws Exception {
		if (aId == MiningTransformation.TRANSFORMATION_PARAM_FILENAME)
			this.fileName = aValue;
	}

	@Override
	public void setParameter(int aId, int aValue) throws Exception {
		// no implementation
	}

	@Override
	public void init() throws Exception {
		if (this.fileName.isEmpty())
			throw new IllegalAccessException(
					"Field fileName: input filename cannot be empty.");

		this.nodeObject = new XLSReader(this.id, this.fileName, null);
	}
}
