package org.eltech.ddm.transformation.etl.CloverETL.loading;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.List;

import org.eltech.ddm.transformation.MiningTransformation;
import org.eltech.ddm.transformation.MiningTransformationTask;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationActivity;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationMetadata;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationStep;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationTask;
import org.jetel.graph.Edge;
import org.jetel.graph.Node;
import org.jetel.graph.TransformationGraph;
import org.jetel.graph.TransformationGraphXMLReaderWriter;
import org.jetel.graph.runtime.EngineInitializer;
import org.jetel.graph.runtime.GraphRuntimeContext;
import org.jetel.metadata.DataRecordMetadata;
import org.omg.java.cwm.objectmodel.core.ModelElement;

/**
 * This activity using for merging graph file from CloverETL designer 
 * with graph created Transformation project.
 * Based on CloverETLTransformationActivity.
 * 
 * @author SemenchenkoA
 *
 */
public class CloverETLDisplacedTransformationActivity extends CloverETLTransformationActivity {

	private static final long serialVersionUID = 1L;

	public List<MiningTransformationTask> getTasks() {
		return tasks;
	}
	
	public ModelElement[] getSteps() {
		return step;
	}
	
	/**
	 * Set first component in graph created Transformation project.
	 * @param component
	 * @throws Exception
	 */
	public void setWelcomeComponent(MiningTransformation component) throws Exception{
		CloverETLTransformationTask task = (CloverETLTransformationTask)this.getTasks().get(this.getTasks().size()-1);
		task.setTarget(component);

		CloverETLTransformationStep step = (CloverETLTransformationStep)this.getSteps()[this.getSteps().length-1];
		step.addTask(task);
	}
	
	public CloverETLDisplacedTransformationActivity(String graphPath, String lastComponentID, CloverETLTransformationMetadata meta) throws Exception {
		EngineInitializer.initEngine("libs/CloverETL/plugins", null, null);
		EngineInitializer.forceActivateAllPlugins();

		java.io.InputStream is = new BufferedInputStream(new FileInputStream(
				graphPath));

		GraphRuntimeContext runtimeContext = new GraphRuntimeContext();

		TransformationGraph graph = null;

		graph = TransformationGraphXMLReaderWriter
				.loadGraph(is, runtimeContext);

		super.graph = graph;
				
		Edge outEdge=new Edge("OutEdgeOriginal", (DataRecordMetadata)meta.getObject());
		
		for(int i = 0; i < graph.getNodes().size(); i++){
			CloverETLTransformationTask.instanceCount++;
			CloverETLTransformationStep.instanceCount++;

			if(i == super.graph.getNodes().size()-1){
				Node node = super.graph.getNodes().get(lastComponentID);
				
				node.addOutputPort(0, outEdge);

				super.graph.getPhase(super.graph.getPhases().length-1).addEdge(outEdge);
				super.graph.addEdge(outEdge);
			}
		}	
		
		CloverETLEmptyTransformation emptyA = (CloverETLEmptyTransformation)this.createTransformation(MiningTransformation.TRANSFORMATION_TYPE_EMPTY);
		emptyA.init();
		Node node = (Node) emptyA.getObject();
		node.addInputPort(0, outEdge);
				
		CloverETLTransformationTask task = (CloverETLTransformationTask)this.createTask(meta);

		task.setSource(emptyA);
		
		this.createStep();
	}
}
