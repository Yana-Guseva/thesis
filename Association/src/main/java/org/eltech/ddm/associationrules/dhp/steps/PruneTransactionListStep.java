package org.eltech.ddm.associationrules.dhp.steps;

import org.eltech.ddm.associationrules.AssociationRulesMiningModel;
import org.eltech.ddm.associationrules.HashMapMiningModelElement;
import org.eltech.ddm.associationrules.Transaction;
import org.eltech.ddm.associationrules.dhp.DHPModel;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import java.util.ArrayList;
import java.util.List;

import static org.eltech.ddm.miningcore.miningmodel.EMiningModel.index;

public class PruneTransactionListStep extends MiningBlock {
    /**
     * Constructor of algorithm's calculation step (not using data set)
     *
     * @param settings - settings for build model
     */
    public PruneTransactionListStep(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
    }

    @Override
    protected EMiningModel execute(EMiningModel model) throws MiningException {
        DHPModel modelA = (DHPModel) model;
        Transaction transaction = modelA.getTransaction(modelA.getCurrentTransactionIndex());
        if (modelA.getCurrentHashTableIndex() > 0) {
            HashMapMiningModelElement map =
                    (HashMapMiningModelElement) modelA.getElement(index(DHPModel.HASH_TABLE_SET, modelA.getCurrentHashTableIndex()));
            if (map != null) {
                if (isNeedPrune(map, transaction, modelA.getCurrentHashTableIndex() + 1)) {
                    modelA.removeElement(index(AssociationRulesMiningModel.TRANSACTION_LIST_SET, modelA.getCurrentTransactionIndex()));
                    modelA.setCurrentElement(AssociationRulesMiningModel.INDEX_TRANSACTION_LIST_SET, modelA.getCurrentTransactionIndex() - 1);
                    modelA.setTransactionPruned(true);
                } else {
                    modelA.setTransactionPruned(false);
                }
            }
        }
        return modelA;
    }

    public boolean isNeedPrune(HashMapMiningModelElement map, Transaction transaction, int k) {
        List<String> transactionItemIDList = transaction.getItemIDList();
        if (transactionItemIDList.size() < k) {
            return true;
        }

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
            if (map.containsKey(key)) {
                return false;
            }

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
        return true;
    }
}
