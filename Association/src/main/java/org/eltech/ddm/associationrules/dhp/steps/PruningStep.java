package org.eltech.ddm.associationrules.dhp.steps;

import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.HashMapMiningModelElement;
import org.eltech.ddm.associationrules.ItemSet;
import org.eltech.ddm.associationrules.dhp.DHPModel;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.List;
import java.util.stream.Collectors;

import static org.eltech.ddm.miningcore.miningmodel.EMiningModel.index;

public class PruningStep extends MiningBlock {
    private final double minSupport;

    public PruningStep(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
        minSupport = ((AssociationRulesFunctionSettings) settings).getMinSupport();
    }

    @Override
    protected EMiningModel execute(EMiningModel model) throws MiningException {
        DHPModel modelA = (DHPModel) model;
        HashMapMiningModelElement hashTable =
                (HashMapMiningModelElement) modelA.getElement(index(DHPModel.HASH_TABLE_SET, modelA.getCurrentHashTableIndex() + 1));
        if (hashTable != null) {
            double transCount = modelA.getTransactionCount();
            List<MiningModelElement> largeItemSets = hashTable.getSet().stream()
                    .filter(e -> (((ItemSet) hashTable.getElement(e.getID())).getSupportCount() / transCount) >= minSupport)
                    .collect(Collectors.toList());
            hashTable.setSet(largeItemSets);
        }
        return modelA;
    }
}
