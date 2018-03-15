package org.eltech.ddm.transformation.etl.CloverETL.loading;

import java.util.ArrayList;
import java.util.List;

import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningdata.EPhysicalData;
import org.eltech.ddm.transformation.MiningTransformation;
import org.eltech.ddm.transformation.MiningTransformationActivity;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformation;

public class CloverETLPhysicalDataTransformation extends
		CloverETLTransformation {

	public CloverETLPhysicalDataTransformation() {
		if(inputPortCount == MiningTransformation.NO_SOURCE_AVAILABLE_FOR_TRANSFORMATION)
			inputPortCount = 0;
		if(outputPortCount == MiningTransformation.NO_TARGET_AVAILABLE_FOR_TRANSFORMATION)
			outputPortCount = 0;
						
		this.id = String.format("node_physicaldata_%d", instanceCount++);
	}
	
	public EPhysicalData getPhysicalData(){
		return ((PhysicalDataComponent)this.nodeObject).getPhysicalData();
	}
	
	public ELogicalData getLogicalData(){
		return ((PhysicalDataComponent)this.nodeObject).getLogicalData();
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
        this.nodeObject = new PhysicalDataComponent(this.id);
        //this.nodeObject.setEnabled("enabled");
	}
	
	public List<ArrayList<String>> getListsCategory(){
		return ((PhysicalDataComponent)nodeObject).getListsCategorical();
	}
}
