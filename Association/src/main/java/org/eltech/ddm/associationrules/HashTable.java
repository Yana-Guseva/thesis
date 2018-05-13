package org.eltech.ddm.associationrules;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.ArrayList;
import java.util.List;

public class HashTable extends HashMapMiningModelElement {
    public HashTable(String id) {
        super(id);
    }

    @Override
    public MiningModelElement createNewCopyElement() {
        return new HashTable(getID());
    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {
        if (this.getSet().size() == 0) {
            for (MiningModelElement hashTable : elements) {
                List<MiningModelElement> set = ((HashMapMiningModelElement) hashTable).getSet();
                if (set != null) {
                    List<MiningModelElement> newElems = new ArrayList<>();
                    for (MiningModelElement elem : set) {
                        ItemSet itemSet = (ItemSet) this.getElement(elem.getID());
                        if (itemSet != null) {
                            itemSet.setSupportCount(itemSet.getSupportCount() + ((ItemSet) elem).getSupportCount());
                        } else {
                            newElems.add(elem);
                        }
                    }
                    if (newElems.size() != 0) {
                        List<MiningModelElement> mergedSet = this.getSet();
                        if (mergedSet == null) {
                            mergedSet = new ArrayList<>();
                        }
                        mergedSet.addAll(newElems);
                        this.setSet(mergedSet);
                    }
                }
            }
//            if (this.getSet() != null) {
//                for (MiningModelElement hashTable : elements) {
//                    hashTable = null;
////                    ((HashTable) hashTable).setSet(this.getSet());
//                }
//            }
        }
    }

    @Override
    public Object clone() {
        HashTable o =  new HashTable(getID());
        List<String> allKeyElements = getAllKeyElements();
        List<String> clonedAllKeyElements = new ArrayList<>();
        for (String key : allKeyElements) {
            clonedAllKeyElements.add(key);
        }
        o.setAllKeyElements(clonedAllKeyElements);
        return o;
    }

    public synchronized MiningModelElement createOrGetElement(String key, List<String> elements) {
        ItemSet itemSet = (ItemSet) getElement(key);
        if (itemSet == null) {
            itemSet = new ItemSet(key, elements);
            put(key, itemSet);
        }
        return itemSet;
    }

    public synchronized MiningModelElement createOrGetElement(Item item) {
        ItemSet itemSet = (ItemSet) getElement(item.getID());
        if (itemSet == null) {
            itemSet = new ItemSet(item);
            put(item.getID(), itemSet);
        }
        return itemSet;
    }
}
