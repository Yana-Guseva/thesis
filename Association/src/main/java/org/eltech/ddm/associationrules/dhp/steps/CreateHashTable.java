package org.eltech.ddm.associationrules.dhp.steps;

import org.eltech.ddm.associationrules.*;
import org.eltech.ddm.associationrules.dhp.DHPModel;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.ArrayList;
import java.util.List;

import static org.eltech.ddm.miningcore.miningmodel.EMiningModel.index;

public class CreateHashTable extends MiningBlock {
    /**
     * Constructor of algorithm's calculation step (not using data set)
     *
     * @param settings - settings for build model
     */
    public CreateHashTable(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
    }

    @Override
    protected EMiningModel execute(EMiningModel model) throws MiningException {
        DHPModel modelA = (DHPModel) model;
        Transaction transaction = modelA.getTransaction(modelA.getCurrentTransactionIndex());
        int curLargeItemsetIdx = modelA.getCurrentHashTableIndex() + 2;
        HashMapMiningModelElement largeItemSets =
                (HashMapMiningModelElement) modelA.getElement(index(DHPModel.HASH_TABLE_SET, curLargeItemsetIdx - 1));
        HashTableList hashTableList = (HashTableList) modelA.getElement(index(DHPModel.HASH_TABLE_SET));
        HashTable hashTable = (HashTable) hashTableList.createOrGetElement(curLargeItemsetIdx);
        List<String> transactionItemIDList = getTransactionItemIdList(transaction, largeItemSets);
        getTransactionSubsets(hashTable, transactionItemIDList, curLargeItemsetIdx + 1);
        return modelA;
    }

    private void getTransactionSubsets(HashTable hashTable, List<String> transactionItemIDList, int k) {
        if (transactionItemIDList.size() < k) {
            return;
        }
//        System.out.println(k+ " " + "generate from " + transaction.getID());

        int indexes[] = new int[k];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = i;
        }

        boolean flag = true;
        while (flag) {
            List<String> elements = new ArrayList<String>();
            for (int i : indexes) {
                elements.add(transactionItemIDList.get(i));
            }
            StringBuilder sb = new StringBuilder(elements.size());
            elements.stream()
                    .sorted()
                    .forEach(e -> {
                        sb.append(e);
                        sb.append(";");
                    });
            addItemSet(hashTable, sb.toString(), elements);
            int n = 1;
            while (flag) {
                indexes[indexes.length - n]++;
                if (indexes[indexes.length - n] < transactionItemIDList.size() - n + 1) {
                    for (int h = indexes.length - n + 1; h < indexes.length; h++) {
                        indexes[h] = indexes[h - 1] + 1;
                    }
                    n = 1;
                    break;
                } else {
                    n++;
                    if (n == k + 1) {
                        flag = false;
                    }
                }
            }
        }
    }

    private void addItemSet(HashTable hashTable, String key, List<String> elements) {
        ItemSet itemSet = (ItemSet) hashTable.createOrGetElement(key, elements);
        itemSet.incSupportCount();
    }

    private List<String> getTransactionItemIdList(Transaction transaction, HashMapMiningModelElement largeItemSets) {
        List<String> transactionItemIDList = transaction.getItemIDList();
        transactionItemIDList.retainAll(largeItemSets.getAllKeyElements());
        return transactionItemIDList;
    }
}
