package org.eltech.ddm.associationrules.apriori.steps;

import org.eltech.ddm.associationrules.dhp.DHPModel;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.algorithms.MiningLoop;
import org.eltech.ddm.miningcore.algorithms.MiningSequence;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import static org.eltech.ddm.associationrules.dhp.DHPModel.HASH_TABLE_SET;
import static org.eltech.ddm.miningcore.miningmodel.EMiningModel.index;

public class LargeItemSetListsCycleStep extends MiningLoop {

    private int[] indexSet = {HASH_TABLE_SET};

    private final int startPosition;

    private final int countElement;

    public LargeItemSetListsCycleStep(EMiningFunctionSettings settings, MiningBlock... blocks) throws MiningException {
        super(settings);
        startPosition = 0;
        countElement = -1;
        iteration = new MiningSequence(settings, blocks);
    }

    public LargeItemSetListsCycleStep(EMiningFunctionSettings settings, int startPos, MiningBlock... blocks) throws MiningException {
        super(settings);
        startPosition = startPos;
        countElement = -1;
        iteration = new MiningSequence(settings, blocks);
    }

    public LargeItemSetListsCycleStep(EMiningFunctionSettings settings, int startPos, int countElement, MiningSequence block) throws MiningException {
        super(settings);
        startPosition = startPos;
        this.countElement = countElement;
        iteration = block;
    }

    public LargeItemSetListsCycleStep(EMiningFunctionSettings settings, int[] index, int startPos, int countElement, MiningSequence block) throws MiningException {
        super(settings);
        this.indexSet = index;
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
        if(countElement < 0) {
            return !model.currIsLastElement(indexSet)
                    && !isCurrentLargeItemSetEmpty((DHPModel) model);
        }
        else {
            return ((model.getCurrentElementIndex(indexSet) - startPosition) < countElement)
                    && !isCurrentLargeItemSetEmpty((DHPModel) model);
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

    private boolean isCurrentLargeItemSetEmpty(DHPModel model) throws MiningException {
        MiningModelElement hashTable = model.getElement(index(DHPModel.HASH_TABLE_SET))
                .getElement(model.getCurrentHashTableIndex() + 1);
        return  hashTable == null ||  hashTable.size() == 0;
    }


    public int[] getIndexSet() {
        return indexSet;
    }

//    private boolean isCurrentLargeItemSetEmpty(AprioriMiningModel model) throws MiningException {
//        return model.getElement(index(AprioriMiningModel.LARGE_ITEM_SETS_SET))
//                .getElement(model.getCurrentLargeItemSetsIndex()).size() == 0;
//    }
}
