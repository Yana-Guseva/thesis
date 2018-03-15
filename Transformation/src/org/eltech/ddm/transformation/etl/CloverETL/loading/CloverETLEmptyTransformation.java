package org.eltech.ddm.transformation.etl.CloverETL.loading;

import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformation;


import org.eltech.ddm.transformation.*;


/**
 * Transformation for EmptyComponent
 * 
 * @author SemenchenkoA
 *
 */
public class CloverETLEmptyTransformation extends CloverETLTransformation {

		public CloverETLEmptyTransformation() {
			if(inputPortCount == MiningTransformation.NO_SOURCE_AVAILABLE_FOR_TRANSFORMATION)
				inputPortCount = 0;
			if(outputPortCount == MiningTransformation.NO_TARGET_AVAILABLE_FOR_TRANSFORMATION)
				outputPortCount = 0;
							
			this.id = String.format("node_empty_%d", instanceCount++);
		}
		
		public void setParentActivity(MiningTransformationActivity aActivity){
			this.parentActivity = aActivity;
		}
		
		@Override
		public void setParameter(int aId, String aValue) throws Exception {
	       
		}

		@Override
		public void setParameter(int aId, int aValue) throws Exception {

		}

		@Override
		public void init() throws Exception {

	        this.nodeObject = new EmptyComponent(this.id);
	        //this.nodeObject.setEnabled("enabled");
		}
	}

