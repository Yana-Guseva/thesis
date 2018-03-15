/**
 * Core CWM TransformationStep class implementation.
 * 
 * @author Evgeniy Krapivin
 * */

package org.eltech.ddm.transformation;

import org.omg.java.cwm.analysis.transformation.*;

@SuppressWarnings("serial")
public abstract class MiningTransformationStep extends TransformationStep {

    /**
     * Adds mining transformation task to the task collection of the step.
     * */
    public abstract void addTask(MiningTransformationTask aTask) throws Exception;

    /**
     * Initializes mining transformation step.
     * */
    public abstract void init() throws Exception;

    /**
     * Returns internal object that represents step in specific tool.
     * */
    public abstract Object getObject();
}