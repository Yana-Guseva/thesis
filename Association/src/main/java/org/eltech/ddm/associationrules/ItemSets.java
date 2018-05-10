package org.eltech.ddm.associationrules;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.Collection;
import java.util.List;

public class ItemSets extends MiningModelElement {
	private static final long serialVersionUID = 1L;

	public ItemSets(String id) {
        super(id);
    }

//    public ItemSets(Collection<ItemSet> itemsets) {
//        super(itemsets);
//    }
//
//    public ItemSets(ItemSet... itemsets) {
//        super();
//        for(ItemSet itemset : itemsets) {
//        	add(itemset);
//        }
//    }
//
//    public ItemSets(ItemSet itemset) {
//        super();
//        add(itemset);
//    }

    @Override
    protected String propertiesToString() {
        return null;
    }

//    @Override
//    public String toString() {
//        StringBuilder b = new StringBuilder();
//        for (ItemSet itemset : this) {
//            b.append(itemset.getItemIDList());
//        }
//        return b.toString();
//    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {

    }

    public boolean contains(MiningModelElement element) {
    	return getElement(element.getID()) != null;
    }
}
