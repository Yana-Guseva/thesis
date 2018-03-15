package org.eltech.ddm.associationrules;

import java.util.ArrayList;

public class AssociationRuleSet extends ArrayList<AssociationRule> {
	private static final long serialVersionUID = 1L;

	public AssociationRuleSet() {
		super();
	}
	
	@Override
    public boolean contains(Object o) {
    	boolean contain = false;
    	for(AssociationRule rule : this) {
    		if(rule.equals(o)) {
    			contain = true;
    			break;
    		} else {
    			contain = false;
    		}
    	}
    	return contain;
    }
	
	public String toString() {
		StringBuilder b = new StringBuilder();
		for (AssociationRule rule : this) {
	        b.append(rule).append("\n");
		}
		return b.toString();
	}
}
