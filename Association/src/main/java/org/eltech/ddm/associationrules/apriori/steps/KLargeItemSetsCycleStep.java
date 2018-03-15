package org.eltech.ddm.associationrules.apriori.steps;

import org.eltech.ddm.associationrules.apriori.AprioriMiningModel;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.CycleStep;
import org.eltech.ddm.miningcore.algorithms.Step;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.omg.java.cwm.analysis.datamining.miningcore.miningfunctionsettings.MiningFunctionSettings;
import org.omg.java.cwm.analysis.datamining.miningcore.miningmodel.MiningModel;

/**
 * Cycle for all itemsets from the attributes of large k-itemsets (those with minimum support).
 * @author Ivan
 *
 */
public class KLargeItemSetsCycleStep extends CycleStep{

	public KLargeItemSetsCycleStep(EMiningFunctionSettings settings)
			throws MiningException {
		super(settings);
	}

    public KLargeItemSetsCycleStep(EMiningFunctionSettings settings, Step ...steps) throws MiningException {
		super(settings, steps);
	}

	@Override
	protected EMiningModel initLoop(MiningInputStream inputData, EMiningModel model) throws MiningException {
		//setStateParameter(model, AprioriMiningModel.NAME_CURRENT_ITEM_SET, 0);
		((AprioriMiningModel)model).setCurrentItemSet(0);
		return model;
	}

	@Override
	protected boolean conditionLoop(MiningInputStream inputData, EMiningModel model) throws MiningException {
//		return ((((AprioriMiningModel) model).getLargeItemSetsList().size() >
//			(Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_LARGE_ITEM_SETS))&&
//				(Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_ITEM_SET) <
//				((AprioriMiningModel) model).getLargeItemSetsList().get(
//						(Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_LARGE_ITEM_SETS)).size());// attributes of large k-itemsets
		return ((((AprioriMiningModel) model).getLargeItemSetsList().size() >
				((AprioriMiningModel) model).getCurrentLargeItemSets())&&
				((AprioriMiningModel) model).getCurrentItemSet() <
						((AprioriMiningModel) model).getLargeItemSetsList().get(
								((AprioriMiningModel) model).getCurrentLargeItemSets()).size());// attributes of large k-itemsets

	}

	@Override
	protected EMiningModel beforeIteration(MiningInputStream inputData, EMiningModel model) throws MiningException {

		return model;
	}

	@Override
	protected EMiningModel afterIteration(MiningInputStream inputData, EMiningModel model) throws MiningException {
//		setStateParameter(model, AprioriMiningModel.NAME_CURRENT_ITEM_SET,
//				(Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_ITEM_SET) + 1);
		((AprioriMiningModel) model).setCurrentItemSet(((AprioriMiningModel) model).getCurrentItemSet() + 1);
		return model;
	}

}

