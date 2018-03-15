package org.eltech.ddm.associationrules;

import java.util.ArrayList;
import java.util.Collection;

public class ItemSets extends ArrayList<ItemSet> {
	private static final long serialVersionUID = 1L;

	public ItemSets() {
        super();
    }

    public ItemSets(Collection<ItemSet> itemsets) {
        super(itemsets);
    }

    public ItemSets(ItemSet... itemsets) {
        super();
        for(ItemSet itemset : itemsets) {
        	add(itemset);
        }
    }

    public ItemSets(ItemSet itemset) {
        super();
        add(itemset);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (ItemSet itemset : this) {
            b.append(itemset.getItemIDList());
        }
        return b.toString();
    }

    @Override
    public boolean contains(Object o) {
    	boolean contain = false;
    	for(ItemSet itemSet : this) {
    		if(itemSet.equals(o)) {
    			contain = true;
    			break;
    		} else {
    			contain = false;
    		}
    	}
    	return contain;
    }

}
