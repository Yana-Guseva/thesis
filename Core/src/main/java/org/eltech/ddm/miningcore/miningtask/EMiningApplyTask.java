package org.eltech.ddm.miningcore.miningtask;

import java.util.Map;

import javax.datamining.JDMException;
import javax.datamining.task.apply.ApplyTask;

import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeAssignment;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeAssignmentSet;
import org.omg.java.cwm.analysis.datamining.miningcore.miningtask.MiningApplyTask;

/**
 * CWM Class
 *
 * This describes a task that computes the result of an application of a data mining model
 * to (new) data.
 *
 * @author Ivan Holod
 *
 */
public class EMiningApplyTask extends MiningApplyTask  //implements ApplyTask
{
	/**
	 * Returns the attribute map for apply input data. Returns null if no map has been provided.
	 * @return
	 */
	public Map getApplyDataMap() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns the name of the apply settings specification to be used.
	 * @return
	 */
	public String getApplySettingsName() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns the name of the model to apply.
	 * @return name of the model to apply
	 */
	public String getModelName() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Specifies a mapping between physical attribute names and signature attribute names for input apply data.
	 * The key for the mapping is the physical attribute name.
	 * In case of explicit mapping, the user would need to provide a java.util.Map object that contains
	 * mapping of physical attribute name to logical attribute name.
	 *
	 * Throws an exception if applyDataMap is null.
	 *
	 * If the physical data is transactional and no mapping is provided, the data is still mapped as
	 * transactional as long as the data contains at least two PhysicalAttribute with caseId and atttributeName roles.
	 * Such data can be used for AssociationRules directly without explicit mapping.
	 *
	 * @param applyDataMap - A map that contains the mapping between physical attribute names and signature attribute names for apply data.
	 * @throws JDMException
	 */
	public void setApplyDataMap(Map applyDataMap) throws JDMException {
		// TODO Auto-generated method stub

	}

	/**
	 * Sets the name of the apply settings specification to be used.
	 * Any previously attributes apply settings is replaced.
	 * @param applySettingsName
	 * @throws JDMException
	 */
	public void setApplySettingsName(String applySettingsName ) throws JDMException {
		// TODO Auto-generated method stub

	}

	/**
	 * Sets the name of the model to apply
	 * @param modelName - A new model name to be used for apply.
	 * @throws JDMException
	 */
	public void setModelName(String modelName) throws JDMException {
		// TODO Auto-generated method stub

	}

}