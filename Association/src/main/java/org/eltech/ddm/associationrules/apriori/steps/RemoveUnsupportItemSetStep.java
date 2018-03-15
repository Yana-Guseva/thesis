package org.eltech.ddm.associationrules.apriori.steps;

import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.ItemSet;
import org.eltech.ddm.associationrules.ItemSets;
import org.eltech.ddm.associationrules.apriori.AprioriMiningModel;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.Step;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.omg.java.cwm.analysis.datamining.miningcore.miningfunctionsettings.MiningFunctionSettings;
import org.omg.java.cwm.analysis.datamining.miningcore.miningmodel.MiningModel;

public class RemoveUnsupportItemSetStep extends Step {
	final protected double minSupport;

	public RemoveUnsupportItemSetStep(EMiningFunctionSettings settings)
			throws MiningException {
		super(settings);
		minSupport = ((AssociationRulesFunctionSettings)settings).getMinSupport();
	}

	@Override
	protected EMiningModel execute(MiningInputStream inputData, EMiningModel model)
			throws MiningException {
		AprioriMiningModel modelA = (AprioriMiningModel) model;

		ItemSets nextItemsetList = modelA.getLargeItemSetsList().get(
				((AprioriMiningModel) model).getCurrentLargeItemSets());
				//(Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_LARGE_ITEM_SETS));

		//if((Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_CANDIDATE) >= 0){ // if new candidate was added
		if(((AprioriMiningModel) model).getCurrentCandidate() >= 0){ // if new candidate was added
			ItemSet itemSet = nextItemsetList.get(
					((AprioriMiningModel) model).getCurrentCandidate());
					//(Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_CANDIDATE));

			if(((double)itemSet.getSupportCount()/(double)modelA.getTransactionCount())<minSupport){
				nextItemsetList.remove(itemSet);
				//modelA.setCurrentItemSet(modelA.getCurrentItemSet()-1);
			}
		}

		return modelA;
	}

}

