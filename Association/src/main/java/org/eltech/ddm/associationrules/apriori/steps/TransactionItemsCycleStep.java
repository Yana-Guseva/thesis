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

public class TransactionItemsCycleStep extends CycleStep {


	public TransactionItemsCycleStep(EMiningFunctionSettings settings)
			throws MiningException {
		super(settings);
	}

    public TransactionItemsCycleStep(EMiningFunctionSettings settings, Step ...steps) throws MiningException {
		super(settings, steps);
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected EMiningModel initLoop(MiningInputStream inputData, EMiningModel model) throws MiningException {
		//setStateParameter(model, AprioriMiningModel.NAME_CURRENT_ITEM, 0);
		((AprioriMiningModel) model).setCurrentItem(0);
		return model;
	}

	@Override
	protected boolean conditionLoop(MiningInputStream inputData, EMiningModel model) throws MiningException {
//		return ((Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_ITEM) <
//				((AprioriMiningModel) model).getTransactionList().get(
//						(Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_TRANSACTION)).getItemIDList().size());

		return (((AprioriMiningModel) model).getCurrentItem() <
				((AprioriMiningModel) model).getTransactionList().get(
						((AprioriMiningModel) model).getCurrentTransaction()).getItemIDList().size());

	}

	@Override
	protected EMiningModel beforeIteration(MiningInputStream inputData, EMiningModel model) throws MiningException {

		return model;
	}

	@Override
	protected EMiningModel afterIteration(MiningInputStream inputData, EMiningModel model) throws MiningException {
		((AprioriMiningModel) model).setCurrentItem(((AprioriMiningModel) model).getCurrentItem() + 1 );
//		setStateParameter(model, AprioriMiningModel.NAME_CURRENT_ITEM,
//				(Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_ITEM) + 1);
		return model;
	}

}
