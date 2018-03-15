package org.eltech.ddm.associationrules.apriori.steps;

import org.eltech.ddm.associationrules.apriori.AprioriMiningModel;
//import org.eltech.ddm.association.apriori.PartitionMiningModel;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.CycleStep;
import org.eltech.ddm.miningcore.algorithms.Step;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.omg.java.cwm.analysis.datamining.miningcore.miningfunctionsettings.MiningFunctionSettings;
import org.omg.java.cwm.analysis.datamining.miningcore.miningmodel.MiningModel;

public class LargeItemSetListsCycleStep extends CycleStep {

	public LargeItemSetListsCycleStep(MiningInputStream inputData, EMiningFunctionSettings settings, MiningModel model) throws MiningException {
		super(settings);
	}

    public LargeItemSetListsCycleStep(EMiningFunctionSettings settings, Step ...steps) throws MiningException {
		super(settings, steps);
	}

	@Override
	protected EMiningModel initLoop(MiningInputStream inputData, EMiningModel model) throws MiningException {

		//setStateParameter(model, AprioriMiningModel.NAME_CURRENT_LARGE_ITEM_SETS, 1);
		((AprioriMiningModel) model).setCurrentLargeItemSets(1);
		return model;
	}

	@Override
	protected boolean conditionLoop(MiningInputStream inputData, EMiningModel model) throws MiningException {
//		return  (((AprioriMiningModel) model).getLargeItemSetsList().size() >=
//					(Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_LARGE_ITEM_SETS)) &&
//				(((AprioriMiningModel) model).getLargeItemSetsList().get(
//						(Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_LARGE_ITEM_SETS) - 1).size() > 0);

		return  (((AprioriMiningModel) model).getLargeItemSetsList().size() >=
				((AprioriMiningModel) model).getCurrentLargeItemSets()) &&
				(((AprioriMiningModel) model).getLargeItemSetsList().get(
						((AprioriMiningModel) model).getCurrentLargeItemSets() - 1).size() > 0);
	}

	@Override
	protected EMiningModel beforeIteration(MiningInputStream inputData, EMiningModel model) throws MiningException {
		//((AprioriMiningModel) model).setCurrentLargeItemSetList(((AprioriMiningModel) model).getCurrentLargeItemSetList());
		return model;
	}

	@Override
	protected EMiningModel afterIteration(MiningInputStream inputData, EMiningModel model) throws MiningException {
//		setStateParameter(model, AprioriMiningModel.NAME_CURRENT_LARGE_ITEM_SETS,
//				(Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_LARGE_ITEM_SETS) + 1);
		((AprioriMiningModel) model).setCurrentLargeItemSets(((AprioriMiningModel) model).getCurrentLargeItemSets() + 1);

		return model;
	}

}
