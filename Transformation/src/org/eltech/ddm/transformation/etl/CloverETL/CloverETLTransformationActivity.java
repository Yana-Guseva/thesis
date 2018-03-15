/**
 * CloverETL-specific implementation of MiningTransformationActivity.
 * Incapsulates everything that concerns TranformationGraph manipulation.
 * 
 * @author Evgeniy Krapivin
 * */

package org.eltech.ddm.transformation.etl.CloverETL;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.jetel.connection.jdbc.DBConnection;
import org.jetel.graph.Edge;
import org.jetel.graph.Phase;
import org.jetel.graph.Result;
import org.jetel.graph.TransformationGraph;
import org.jetel.graph.runtime.EngineInitializer;
import org.jetel.graph.runtime.GraphRuntimeContext;
import org.jetel.main.runGraph;
import org.eltech.ddm.transformation.*;
import org.eltech.ddm.transformation.etl.CloverETL.loading.CloverETLEmptyTransformation;
import org.eltech.ddm.transformation.etl.CloverETL.loading.CloverETLPhysicalDataTransformation;
import org.eltech.ddm.transformation.etl.CloverETL.loading.CloverETLTrashTransformation;
import org.eltech.ddm.transformation.etl.CloverETL.transformations.*;

@SuppressWarnings("serial")
public class CloverETLTransformationActivity extends MiningTransformationActivity {

    protected TransformationGraph graph = null;
    protected List<MiningTransformation> transformations = new ArrayList<MiningTransformation>();
    protected List<MiningTransformationTask> tasks = new ArrayList<MiningTransformationTask>();
//    private List<ETLPhase> phaseList = new ArrayList<ETLPhase>();
    protected Future<Result> result = null;
    
	public CloverETLTransformationActivity() {
        // init

		EngineInitializer.initEngine("libs/CloverETL/plugins", null, null);
        EngineInitializer.forceActivateAllPlugins();

        // create graph

        this.graph = new TransformationGraph();	
	}
	
	@Override
	public MiningTransformation createTransformation(int aId) throws Exception {
        MiningTransformation transformation;

        if(aId == MiningTransformation.TRANSFORMATION_TYPE_INPUT_DB)
        	transformation = new CloverETLInputDBTransformation(this);
        else if(aId == MiningTransformation.TRANSFORMATION_TYPE_INPUT_FILE)
        	transformation = new CloverETLInputFileTransformation(this);
        else if(aId == MiningTransformation.TRANSFORMATION_TYPE_OUTPUT_FILE)
        	transformation = new CloverETLOutputFileTransformation(this);
        else if(aId == MiningTransformation.TRANSFORMATION_TYPE_OUTPUT_MAS)
        	transformation = new CloverETLOutputMASTransformation(/*this*/);
        else if(aId == MiningTransformation.TRANSFORMATION_TYPE_MERGE){
        	transformation = new CloverETLMergeTransformation();
        	((CloverETLMergeTransformation)transformation).setParentActivity(this);
        }
        else if(aId == MiningTransformation.TRANSFORMATION_TYPE_REFORMAT){
        	transformation = new CloverETLReformatTransformation();
        	((CloverETLReformatTransformation)transformation).setParentActivity(this);        	
        }
        else if(aId == MiningTransformation.TRANSFORMATION_TYPE_EMPTY || aId == MiningTransformation.TRANSFORMATION_TYPE_XML_GRAPH_IN){
        	transformation = new CloverETLEmptyTransformation();
        	((CloverETLEmptyTransformation)transformation).setParentActivity(this);
        }
        else if (aId == MiningTransformation.TRANSFORMATION_TYPE_INPUTXLS_FILE)
        	transformation = new CloverETLInputXLSDataTransformation(this);
        else if (aId == MiningTransformation.TRANSFORMATION_TYPE_TRASH){
        	transformation = new CloverETLTrashTransformation();
        	((CloverETLTrashTransformation)transformation).setParentActivity(this);
        }
        else if(aId == MiningTransformation.TRANSFORMATION_TYPE_PHYSICALDATA){
        	transformation = new CloverETLPhysicalDataTransformation();
        	((CloverETLPhysicalDataTransformation)transformation).setParentActivity(this);
        }else
            throw new IllegalArgumentException("Argument aId: unknown transformation type.");

        this.transformations.add(transformation);
        
        return transformation;
	}

	@Override
	public MiningTransformationMetadata createMetadata() throws Exception {
		return new CloverETLTransformationMetadata();
	}

	@Override
	public MiningTransformationTask createTask(MiningTransformationMetadata aMetadata) throws Exception {
		MiningTransformationTask task;
        
        if(aMetadata != null)
        {
        	task = new CloverETLTransformationTask(aMetadata);
            this.tasks.add(task);
        }
        else
            throw new IllegalArgumentException("Argument aMetadata: cannot be null.");

        return task;
	}

	@Override
	public MiningTransformationStep createStep() throws Exception {
        MiningTransformationStep step = new CloverETLTransformationStep(this);
        this.addStep(step);

        return step;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void transform() throws Exception {
        if(this.graph == null)
            throw new IllegalAccessException("Field graph: CloverETL transformation activity was not initialized properly.");
        
        // add transformations (nodes) to steps (phases)

        List<MiningTransformationStep> steps = (ArrayList<MiningTransformationStep>) this.getStep();
        for (MiningTransformationStep step : steps) {
        	step.init();
        	
        	// add step (phase) to activity (graph)
        	
        	this.graph.addPhase((Phase)step.getObject());
        }

        // add tasks (edges) to activity (graph)

        for (MiningTransformationTask task : this.tasks)
            this.graph.addEdge((Edge)task.getObject());
        
        // prepare runtime parameters - JMX is turned of

        GraphRuntimeContext runtimeContext = new GraphRuntimeContext();
        runtimeContext.setUseJMX(false);

        // execute graph

        try { 
            result = runGraph.executeGraph(this.graph, runtimeContext);
            if (!result.get().equals(Result.FINISHED_OK))
                throw new Exception("CloverETL engine transformation job failed.");
        } catch (Exception e) {
		    //System.out.println("Failed graph execution!\n" + e.getMessage());
        	e.printStackTrace();
	    }
	}

	@Override
	public void transformationsSupplied() throws Exception {
        for(MiningTransformation transformation : this.transformations)
        	transformation.init();
	}

    public void addDBConnection(DBConnection aConnection) {
        if(aConnection != null) {
            this.graph.addConnection(aConnection);

            this.DBConnectionsCount++;
        }
    }
    
	public Future<Result> getResult() {
		return result;
	}
}
