package org.eltech.ddm.associationrules.dhp;

import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.AssociationRulesMiningModel;
import org.eltech.ddm.miningcore.MiningException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DHPMiningModel extends AssociationRulesMiningModel {

    protected Map<Integer, Map<List<String>, Integer>> itemSetsHashTable = new HashMap<>();

    private boolean isTransactionPruned = false;

    public boolean isTransactionPruned() {
        return isTransactionPruned;
    }

    public void setTransactionPruned(boolean isTransactionPruned) {
        this.isTransactionPruned = isTransactionPruned;
    }

    private int transactionCount = 0;

    public int getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }

    public DHPMiningModel(AssociationRulesFunctionSettings settings) throws MiningException {
        super(settings);
    }

    public Map<Integer, Map<List<String>, Integer>> getItemSetsHashTable() {
        return itemSetsHashTable;
    }

    public void setItemSetsHashTree(Map<Integer, Map<List<String>, Integer>> itemSetsHashTree) {
        this.itemSetsHashTable = itemSetsHashTree;
    }

//    public Object clone() {
//        org.eltech.ddm.associationrules.apriori.dhp.DHPMiningModel o = null;
//        o = (org.eltech.ddm.associationrules.apriori.dhp.DHPMiningModel)super.clone();
//
//        if(itemSetsHashTable != null){
//            o.itemSetsHashTable = new HashMap<Integer, Map<List<String>, Integer>>();
//
//            for (Integer i : itemSetsHashTable.keySet()) {
//                Map<List<String>, Integer> map = itemSetsHashTable.get(i);
//                Map<List<String>, Integer> copiedMap = new HashMap<List<String>, Integer>();
//                for (List<String> key : map.keySet()) {
//                    copiedMap.put(new ArrayList<String>(key), map.get(key));
//                }
//                o.itemSetsHashTable.put(i, copiedMap);
//            }
//        }
//
//        return o;
//    }
}
