/**
 * CloverETL-specific implementation of MiningTransformationTask.
 * Incapsulates work with Edge objects and input and output ports.
 * 
 * @author Evgeniy Krapivin
 * */

package org.eltech.ddm.transformation.etl.CloverETL;

import java.util.*;

import org.jetel.graph.Edge;
import org.jetel.graph.Node;
import org.jetel.metadata.DataRecordMetadata;
import org.eltech.ddm.transformation.*;

public class CloverETLTransformationTask extends MiningTransformationTask {

    public static int instanceCount = 0;

    private String id = "";
    private Edge edgeObject = null;
    private List<MiningTransformation> transformations = new ArrayList<MiningTransformation>();
    
	public CloverETLTransformationTask(MiningTransformationMetadata aMetadata) {
        this.id = String.format("edge_%d", instanceCount++);
        this.edgeObject = new Edge(this.id, (DataRecordMetadata) aMetadata.getObject());
	}
	
	@Override
	public void setSource(MiningTransformation aTransformation) throws Exception {
        if(aTransformation != null && (aTransformation instanceof CloverETLTransformation)) {
        	CloverETLTransformation cloverTransformation = (CloverETLTransformation) aTransformation;
            int outputPort = cloverTransformation.getNextOutputPort();
            if(outputPort != MiningTransformation.NO_TARGET_AVAILABLE_FOR_TRANSFORMATION) {
                ((Node)cloverTransformation.getObject()).addOutputPort(outputPort, edgeObject);

                if(!transformations.contains(aTransformation))
                	transformations.add(aTransformation);
            }
        }
	}
	
	public void setSourceXML(MiningTransformation aTransformation) throws Exception {
        if(aTransformation != null && (aTransformation instanceof CloverETLTransformation)) {
        	CloverETLTransformation cloverTransformation = (CloverETLTransformation) aTransformation;
            int outputPort = cloverTransformation.getNextOutputPort();
            if(outputPort != MiningTransformation.NO_TARGET_AVAILABLE_FOR_TRANSFORMATION) {
                ((Node)cloverTransformation.getObject()).addOutputPort(outputPort, edgeObject);

                if(!transformations.contains(aTransformation))
                	transformations.add(aTransformation);
            }
        }
	}

	@Override
	public void setTarget(MiningTransformation aTransformation) throws Exception {
        if(aTransformation != null && (aTransformation instanceof CloverETLTransformation)) {
        	CloverETLTransformation cloverTransformation = (CloverETLTransformation) aTransformation;
            int inputPort = cloverTransformation.getNextInputPort();
            if(inputPort != MiningTransformation.NO_SOURCE_AVAILABLE_FOR_TRANSFORMATION) {
                ((Node)cloverTransformation.getObject()).addInputPort(inputPort, edgeObject);

                if(!transformations.contains(aTransformation))
                	transformations.add(aTransformation);
            }
        }
	}
	
	@Override
	public Object getObject() {
		return this.edgeObject;
	}

	@Override
	public List<MiningTransformation> getTransformationList() {
		return this.transformations;
	}
}
