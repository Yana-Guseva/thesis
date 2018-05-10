package org.eltech.ddm.associationrules.dhp.steps;

import org.eltech.ddm.associationrules.dhp.DHPModel;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningDecision;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

public class IsThereCurrentTransaction extends MiningDecision {

    public IsThereCurrentTransaction(EMiningFunctionSettings settings, CreateHashTable createHashTable) throws MiningException {
        super(settings, createHashTable);
    }

    @Override
    protected boolean condition(EMiningModel model) throws MiningException {
        return (!((DHPModel)model).isTransactionPruned()
                && ((DHPModel) model).getHashTable(((DHPModel) model).getCurrentHashTableIndex() + 1).size() > 0);
    }
}
