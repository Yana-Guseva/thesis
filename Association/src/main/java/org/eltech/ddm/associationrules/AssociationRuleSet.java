package org.eltech.ddm.associationrules;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.List;

public class AssociationRuleSet extends MiningModelElement {
	private static final long serialVersionUID = 1L;

	public AssociationRuleSet(String id) {
		super(id);
	}
	
    public boolean contains(Object o) {
    	boolean contain = false;
    	for(MiningModelElement rule : super.set) {
    		if(rule.equals(o)) {
    			contain = true;
    			break;
    		} else {
    			contain = false;
    		}
    	}
    	return contain;
    }

	@Override
	protected String propertiesToString() {
		return "";
	}

	public String toString() {
		StringBuilder b = new StringBuilder();
		for (MiningModelElement rule : super.set) {
	        b.append(rule).append("\n");
		}
		return b.toString();
	}

	@Override
	public void merge(List<MiningModelElement> elements) throws MiningException {

	}
}
