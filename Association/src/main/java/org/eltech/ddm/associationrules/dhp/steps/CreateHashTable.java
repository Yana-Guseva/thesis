package org.eltech.ddm.associationrules.dhp.steps;

import org.eltech.ddm.associationrules.HashMapMiningModelElement;
import org.eltech.ddm.associationrules.HashTable;
import org.eltech.ddm.associationrules.ItemSet;
import org.eltech.ddm.associationrules.Transaction;
import org.eltech.ddm.associationrules.dhp.DHPModel;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        HashMapMiningModelElement hashTable = modelA.getHashTable(curLargeItemsetIdx);
        HashMapMiningModelElement largeItemSets = modelA.getHashTable(curLargeItemsetIdx - 1);
        if (hashTable == null) {
            hashTable = new HashTable(String.valueOf(curLargeItemsetIdx));
            model.addElement(index(DHPModel.HASH_TABLE_SET), hashTable);
        }
        getTransactionSubsets(hashTable, largeItemSets, transaction, curLargeItemsetIdx + 1);
        return modelA;
    }

    public void getTransactionSubsets(HashMapMiningModelElement hashTable,
                                      HashMapMiningModelElement largeItemSets, Transaction transaction, int k) {
        List<String> transactionItemIDList = transaction.getItemIDList();
        transactionItemIDList.retainAll(largeItemSets.getAllKeyElements());
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
            String key = sb.toString();
            ItemSet itemSet = (ItemSet) hashTable.getElement(key);
            if (itemSet == null) {
                itemSet = new ItemSet(key, elements);
                hashTable.put(key, itemSet);
            }
            itemSet.setSupportCount(itemSet.getSupportCount() + 1);
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
}
