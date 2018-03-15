/**
 * Kettle-specific implementation of MiningTransformationActivity.
 * Incapsulates everything that concerns TransMeta manipulation.
 * 
 * @author Evgeniy Krapivin
 * */

package org.eltech.ddm.transformation.etl.Kettle;

import java.util.ArrayList;
import java.util.List;

import org.eltech.ddm.transformation.MiningTransformation;
import org.eltech.ddm.transformation.MiningTransformationActivity;
import org.eltech.ddm.transformation.MiningTransformationMetadata;
import org.eltech.ddm.transformation.etl.Kettle.Transformations.KettleInputFileTransformation;
import org.eltech.ddm.transformation.etl.Kettle.Transformations.KettleOutputMASTransformation;
import org.jetel.connection.jdbc.DBConnection;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransHopMeta;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;

@SuppressWarnings("serial")
public class KettleTransformationActivity extends MiningTransformationActivity {
	private TransMeta kettleTransformationMetadata = null;
	private List<KettleTransformationTask> tasks = new ArrayList<KettleTransformationTask>();
	private List<KettleTransformation> usedNodes = new ArrayList<KettleTransformation>();
			
	public KettleTransformationActivity() {
		// init
		
		try {
			KettleEnvironment.init();
		} catch (KettleException e) {
			e.printStackTrace();
		}
		
		// create a new transformation
		
		kettleTransformationMetadata = new TransMeta();
		kettleTransformationMetadata.setName("Kettle_Transformation");
	}
	
	@Override
	public KettleTransformation createTransformation(int aId) throws Exception {
		KettleTransformation transformation = null;
		
        if(aId == MiningTransformation.TRANSFORMATION_TYPE_INPUT_FILE)
        	transformation = new KettleInputFileTransformation();
        else if(aId == MiningTransformation.TRANSFORMATION_TYPE_OUTPUT_MAS)
        	transformation = new KettleOutputMASTransformation();
        else
            throw new IllegalArgumentException("Argument aId: unknown transformation type.");

        usedNodes.add(transformation);
        
        return transformation;
	}

	@Override
	public KettleTransformationMetadata createMetadata() throws Exception {
		return new KettleTransformationMetadata();
	}

	@Override
	public KettleTransformationTask createTask(MiningTransformationMetadata aMetadata) throws Exception {
		KettleTransformationTask task;
        
        if(aMetadata != null)
        {
        	task = new KettleTransformationTask(aMetadata);
            tasks.add(task);
        }
        else
            throw new IllegalArgumentException("Argument aMetadata: cannot be null.");

        return task;
	}

	@Override
	public KettleTransformationStep createStep() throws Exception {
		KettleTransformationStep step = new KettleTransformationStep(this);
        addStep(step);

        return step;
	}

	@Override
	public void transform() throws Exception {
        if(kettleTransformationMetadata == null)
            throw new IllegalAccessException("Field kettleTransformationMetadata: Kettle transformation activity was not initialized properly.");
        
        // add transformations to KTR
        
        for (KettleTransformation transformation : usedNodes)
        	kettleTransformationMetadata.addStep((StepMeta) transformation.getObject());
        
        // add tasks (edges) to activity (KTR)

        for (KettleTransformationTask task : tasks)
            kettleTransformationMetadata.addTransHop((TransHopMeta)task.getObject());
        
		// execute transformation
		
        Trans trans = new Trans(kettleTransformationMetadata);
        try {
    		trans.prepareExecution(null);
    		trans.startThreads();
    		trans.waitUntilFinished();
        } catch (Exception e) {
		    //System.out.println("KettleTransformationMetadata execution failed. Reason: " + e.getMessage());
        	e.printStackTrace();
	    }
	}

	@Override
	public void transformationsSupplied() throws Exception {
		// TODO Автоматически созданная заглушка метода
		
	}

	@Override
	public void addDBConnection(DBConnection aConnection) {
		// TODO Автоматически созданная заглушка метода
		
	}
}
