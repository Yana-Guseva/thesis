package org.eltech.ddm.associationrules;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.List;

public class HashTableList extends MiningModelElement {
    public HashTableList(String id) {
        super(id);
    }

    @Override
    protected String propertiesToString() {
        return null;
    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {

    }

    public synchronized MiningModelElement createOrGetElement(int index) {
        MiningModelElement element = getElement(index);
        if (element == null) {
            element = new HashTable(String.valueOf(index));
            add(element);
        }
        return element;
    }
}
