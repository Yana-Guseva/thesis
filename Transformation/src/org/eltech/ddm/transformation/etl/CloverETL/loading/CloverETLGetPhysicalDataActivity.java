package org.eltech.ddm.transformation.etl.CloverETL.loading;

import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningdata.EPhysicalData;
import org.eltech.ddm.transformation.MiningETLArrayStreamInformer;
import org.eltech.ddm.transformation.MiningTransformation;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationStep;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationTask;

/**
 * Activity for get physical data
 * 
 * @author SemenchenkoA
 */
public class CloverETLGetPhysicalDataActivity extends CloverETLOneSourceDynamicTransformationActivity{
	private static final long serialVersionUID = 1L;
	private CloverETLPhysicalDataTransformation physicalTransformation = null;

	CloverETLGetPhysicalDataActivity() throws Exception {
		super();
	}
	
	public EPhysicalData getPhysicalData(){
		return physicalTransformation.getPhysicalData();
	}
	public ELogicalData getLogicalData(){
		return physicalTransformation.getLogicalData();
	}
	
	public void formEnds(MiningETLArrayStreamInformer informer) throws Exception{
		physicalTransformation = (CloverETLPhysicalDataTransformation)
				this
				.createTransformation(MiningTransformation.TRANSFORMATION_TYPE_PHYSICALDATA);
		physicalTransformation.init();
		
		CloverETLTransformationTask task0 = (CloverETLTransformationTask)this.createTask(meta);
		task0.setSource(this.transformations.get(this.transformations.size()-2));
		task0.setTarget(physicalTransformation);
		
		CloverETLTrashTransformation output = (CloverETLTrashTransformation) this
				.createTransformation(MiningTransformation.TRANSFORMATION_TYPE_TRASH);
		output.init();	
		
		CloverETLTransformationTask task = (CloverETLTransformationTask)this.createTask(meta);
		task.setSource(physicalTransformation);
		task.setTarget(output);

		CloverETLTransformationStep step = (CloverETLTransformationStep)this.createStep();
		step.addTask(task0);
		CloverETLTransformationStep step2 = (CloverETLTransformationStep)this.createStep();
		step2.addTask(task);
		
		this.transform();		
		
		/*
		CloverETLTrashTransformation output = (CloverETLTrashTransformation) this
				.createTransformation(MiningTransformation.TRANSFORMATION_TYPE_TRASH);
		
		output.init();
		
		CloverETLTransformationTask task = (CloverETLTransformationTask)this.createTask(meta);
		// -2 because without output and -1 we start from 0
		task.setSource(this.transformations.get(this.transformations.size()-2));
		task.setTarget(output);

		CloverETLTransformationStep step = (CloverETLTransformationStep)this.createStep();
		step.addTask(task);
		
		this.transform();*/
	}
}
