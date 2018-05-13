package org.eltech.ddm.associationrules.dhp;

import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.HashMapMiningModelElement;
import org.eltech.ddm.associationrules.HashTable;
import org.eltech.ddm.associationrules.HashTableList;
import org.eltech.ddm.associationrules.apriori.AprioriMiningModel;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.Arrays;
import java.util.List;

public class DHPModel extends AprioriMiningModel implements Cloneable {

    public final static int HASH_TABLE_SET = 4;
    public final static int[] INDEX_HASH_TABLE_SET = {HASH_TABLE_SET};
    public static final int[] INDEX_CURRENT_LARGE_ITEM_SET = {HASH_TABLE_SET, CURRENT_ITEM};
    public static final int[] INDEX_CURRENT_ITEM_LARGE_ITEM_SET = {HASH_TABLE_SET, CURRENT_ITEM, CURRENT_ITEM};

    private int transactionCount;

    private boolean isTransactionPruned = false;

    public DHPModel(AssociationRulesFunctionSettings settings) throws MiningException {
        super(settings);
        sets.add(HASH_TABLE_SET, new HashTableList("hashTableSet"));
    }

    @Override
    public void initModel() throws MiningException {

    }

//    public HashMapMiningModelElement getHashTable(int index) throws MiningException {
//        return (HashMapMiningModelElement) getElement(index(DHPModel.HASH_TABLE_SET, index));
//    }

    @Override
    public MiningModelElement nextCurrElement(int[] indexSet) throws MiningException {
        int pos = currents.get(Arrays.toString(indexSet));
        currents.put(Arrays.toString(indexSet), pos+1);
        return null;
    }

    public int getCurrentHashTableIndex() {
        Integer index = currents.get(Arrays.toString(INDEX_HASH_TABLE_SET));
        if(index == null)
            return -1;
        return index;
    }

    public int getCurrentLargeItemsetIndex() {
        Integer index = currents.get(Arrays.toString(INDEX_CURRENT_LARGE_ITEM_SET));
        if(index == null)
            return -1;
        return index;
    }

    public int getCurrentItemLargeItemsetIndex() {
        Integer index = currents.get(Arrays.toString(INDEX_CURRENT_ITEM_LARGE_ITEM_SET));
        if(index == null)
            return -1;
        return index;
    }

    public MiningModelElement getLargeItemSets() throws MiningException {
        return getElement(index(DHPModel.HASH_TABLE_SET));
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }

    public boolean isTransactionPruned() {
        return isTransactionPruned;
    }

    public void setTransactionPruned(boolean isTransactionPruned) {
        this.isTransactionPruned = isTransactionPruned;
    }

    public HashMapMiningModelElement getHashTable(int index) throws MiningException {
        return (HashMapMiningModelElement) getElement(index(DHPModel.HASH_TABLE_SET, index));
    }
}
