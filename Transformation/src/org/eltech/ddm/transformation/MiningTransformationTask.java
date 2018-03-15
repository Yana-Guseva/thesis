/**
 * Core CWM TransformationTask class implementation.
 * 
 * @author Evgeniy Krapivin
 * */

package org.eltech.ddm.transformation;

import java.util.*;
import org.omg.java.cwm.analysis.transformation.*;

public abstract class MiningTransformationTask extends TransformationTask {

	/**
	 * Sets source transformation (writer) for the task.
	 * */
    public abstract void setSource(MiningTransformation aTransformation) throws Exception;
    /**
     * Sets target transformation (reader) for the task.
     * */
    public abstract void setTarget(MiningTransformation aTransformation) throws Exception;
    
    /**
     * Returns internal object that represents task in specific tool.
     * */
    public abstract Object getObject();
    
    /**
     * Returns collection of transformations attached to the task.
     * */
    public abstract List<MiningTransformation> getTransformationList();
}