/**
 * CloverETL-specific implementation of MiningTransformationStep.
 * Incapsulates work with Phase objects and adding dependent nodes to them.
 * 
 * @author Evgeniy Krapivin
 * */

package org.eltech.ddm.transformation.etl.CloverETL;

import java.util.*;

import org.jetel.graph.Node;
import org.jetel.graph.Phase;
import org.eltech.ddm.transformation.*;

@SuppressWarnings("serial")
public class CloverETLTransformationStep extends MiningTransformationStep {

    public static int instanceCount = 0;

    private Phase phaseObject = null;
    private List<Node> usedNodes = new ArrayList<Node>();
    
    public CloverETLTransformationStep(MiningTransformationActivity aActivity) {
    	this.activity = aActivity;
    	
    	this.phaseObject = new Phase(instanceCount++);
    }
    
	@Override
	public void addTask(MiningTransformationTask aTask) throws Exception {
        if(aTask == null)
            throw new IllegalArgumentException("Argument aTask: cannot be null.");

      	List<MiningTransformation> transformations = aTask.getTransformationList();
        for (MiningTransformation transformation : transformations) {
            if(((MiningTransformationActivity) this.activity).useTransformation(transformation)) {
                this.usedNodes.add((Node)transformation.getObject());
            }
        }
	}

	@Override
	public void init() throws Exception {
		this.phaseObject.addAllNodes(this.usedNodes);
	}

	@Override
	public Object getObject() {
        return this.phaseObject;
	}
}
