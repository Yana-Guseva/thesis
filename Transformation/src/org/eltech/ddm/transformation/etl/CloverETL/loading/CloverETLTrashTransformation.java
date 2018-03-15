package org.eltech.ddm.transformation.etl.CloverETL.loading;

import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformation;
import org.eltech.ddm.transformation.*;
import org.jetel.component.Trash;


/**
 * Transformation for Trash
 * 
 * @author SemenchenkoA
 *
 */
public class CloverETLTrashTransformation extends CloverETLTransformation {

		public CloverETLTrashTransformation() {
			if(inputPortCount == MiningTransformation.NO_SOURCE_AVAILABLE_FOR_TRANSFORMATION)
				inputPortCount = 0;
			if(outputPortCount == MiningTransformation.NO_TARGET_AVAILABLE_FOR_TRANSFORMATION)
				outputPortCount = 0;
							
			this.id = String.format("node_trash_%d", instanceCount++);
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

	        this.nodeObject = new Trash(this.id);
	        //this.nodeObject.setEnabled("enabled");
		}
	}


