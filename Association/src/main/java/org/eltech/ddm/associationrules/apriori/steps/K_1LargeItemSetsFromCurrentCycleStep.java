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

public class K_1LargeItemSetsFromCurrentCycleStep extends CycleStep {
	private static final long serialVersionUID = 1L;

	public K_1LargeItemSetsFromCurrentCycleStep(EMiningFunctionSettings settings)
			throws MiningException {
		super(settings);
	}

    public K_1LargeItemSetsFromCurrentCycleStep(EMiningFunctionSettings settings, Step ...steps) throws MiningException {
		super(settings, steps);
	}

	@Override
	protected EMiningModel initLoop(MiningInputStream inputData, EMiningModel model) throws MiningException {
		((AprioriMiningModel)model).setCurrentItemSet2(
				((AprioriMiningModel)model).getCurrentItemSet() + 1);
//		setStateParameter(model, AprioriMiningModel.NAME_CURRENT_ITEM_SET_2,
//				(Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_ITEM_SET)+1);
		return model;
	}

	@Override
	protected boolean conditionLoop(MiningInputStream inputData, EMiningModel model) throws MiningException {
		return (((AprioriMiningModel)model).getCurrentItemSet2() <
				((AprioriMiningModel) model).getLargeItemSetsList().get(
						((AprioriMiningModel)model).getCurrentLargeItemSets() - 1).size() );

//		return ((Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_ITEM_SET_2) <
//				((AprioriMiningModel) model).getLargeItemSetsList().get(
//						(Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_LARGE_ITEM_SETS) - 1).size() );
	}

	@Override
	protected EMiningModel beforeIteration(MiningInputStream inputData, EMiningModel model) throws MiningException {

		return model;
	}

	@Override
	protected EMiningModel afterIteration(MiningInputStream inputData, EMiningModel model) throws MiningException {
		((AprioriMiningModel)model).setCurrentItemSet2(
				((AprioriMiningModel)model).getCurrentItemSet2() + 1);

//		setStateParameter(model, AprioriMiningModel.NAME_CURRENT_ITEM_SET_2,
//				(Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_ITEM_SET_2)+1);
		return model;
	}


}
