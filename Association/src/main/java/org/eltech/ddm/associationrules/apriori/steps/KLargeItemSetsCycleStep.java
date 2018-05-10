package org.eltech.ddm.associationrules.apriori.steps;

import org.eltech.ddm.associationrules.dhp.DHPModel;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.algorithms.MiningLoop;
import org.eltech.ddm.miningcore.algorithms.MiningSequence;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import static org.eltech.ddm.miningcore.miningmodel.EMiningModel.index;

/**
 * Cycle for all itemsets from the attributes of large k-itemsets (those with minimum support).
 * @author Ivan
 *
 */
public class KLargeItemSetsCycleStep extends MiningLoop {

    private final int[] indexSet;

    private final int startPosition;

    private final int countElement;

    public KLargeItemSetsCycleStep(EMiningFunctionSettings settings, int[] indexSet, MiningBlock... blocks) throws MiningException {
        super(settings);
        this.indexSet = indexSet;
        startPosition = 0;
        countElement = -1;
        iteration = new MiningSequence(settings, blocks);
    }

    public KLargeItemSetsCycleStep(EMiningFunctionSettings settings, int[] indexSet, int startPos, int countElement, MiningSequence block) throws MiningException {
        super(settings);
        this.indexSet = indexSet;
        startPosition = startPos;
        this.countElement = countElement;
        iteration = block;
    }

    @Override
    protected EMiningModel initLoop(EMiningModel model) throws MiningException {
        model.setCurrentElement(indexSet, startPosition);
        return model;
    }

    @Override
    protected boolean conditionLoop(EMiningModel model) throws MiningException {
        DHPModel modelA = (DHPModel) model;
        if(countElement < 0)
            return !model.currIsLastElement(indexSet) && modelA.getCurrentLargeItemsetIndex() <
                    (modelA.getElement(index(DHPModel.HASH_TABLE_SET, modelA.getCurrentHashTableIndex()))).size();
        else {
            return ((model.getCurrentElementIndex(indexSet) - startPosition) < countElement) &&
                    modelA.getCurrentLargeItemsetIndex() < (modelA.getElement(index(DHPModel.HASH_TABLE_SET, modelA.getCurrentHashTableIndex()))).size();
        }
    }

    @Override
    protected EMiningModel beforeIteration(EMiningModel model) throws MiningException {
        return model;
    }

    @Override
    protected EMiningModel afterIteration(EMiningModel model) throws MiningException {
        model.nextCurrElement(indexSet);
        return model;
    }
}

