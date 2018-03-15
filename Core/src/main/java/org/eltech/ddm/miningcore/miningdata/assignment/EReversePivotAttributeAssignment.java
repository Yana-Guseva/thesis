package org.eltech.ddm.miningcore.miningdata.assignment;

import java.util.ArrayList;

import org.omg.java.cwm.objectmodel.core.Attribute;

public class EReversePivotAttributeAssignment extends ReversePivotAttributeAssignment_e{
	public EReversePivotAttributeAssignment() {
		selectorAttribute = new ArrayList<Attribute>();
	};
	
	public AttributeAssignmentType getAttributeAssignmentType(){
		return AttributeAssignmentType.ReversePivotAttributeAssignment;
	}
}