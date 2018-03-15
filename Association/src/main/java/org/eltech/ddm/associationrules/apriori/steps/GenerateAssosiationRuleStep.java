package org.eltech.ddm.associationrules.apriori.steps;

import java.util.HashSet;
import java.util.List;

import org.eltech.ddm.associationrules.AssociationRule;
import org.eltech.ddm.associationrules.AssociationRuleSet;
import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.Item;
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

public class GenerateAssosiationRuleStep extends Step {
	private static final long serialVersionUID = 1L;

	protected final double minConfidence;

	public GenerateAssosiationRuleStep(EMiningFunctionSettings settings) throws MiningException {
		super(settings);
		minConfidence = ((AssociationRulesFunctionSettings)settings).getMinConfidence();
	}

	@Override
	protected EMiningModel execute(MiningInputStream inputData, EMiningModel model) throws MiningException {
		AprioriMiningModel modelA = (AprioriMiningModel) model;
		//if((Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_LARGE_ITEM_SETS) == 0) // It doesn't built rules for 1-items attributes
		if(modelA.getCurrentLargeItemSets() == 0) // It doesn't built rules for 1-items attributes
			return modelA;

		ItemSets currentItemSetList = modelA.getLargeItemSetsList().get(modelA.getCurrentLargeItemSets());
				//(Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_LARGE_ITEM_SETS));
		ItemSet itemSet = currentItemSetList.get(modelA.getCurrentItemSet());
				//(Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_ITEM_SET));
		String itemId = itemSet.getItemIDList().get(modelA.getCurrentItem());
				//(Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_ITEM));

		ItemSet a = new ItemSet(itemSet.getItemIDList());
		a.getItemIDList().remove(itemId);

		double confidence = ((double)itemSet.getSupportCount()) / ((double)a.getSupportCount()); // TODO: div on 0!!!
		if (confidence >= minConfidence) {
			ItemSet c = new ItemSet(itemSet.getItemIDList());
			c.getItemIDList().removeAll(a.getItemIDList());
			AssociationRule rule = new AssociationRule(a, c, (double)itemSet.getSupportCount() / (double)modelA.getTransactionCount(), confidence);
			List<AssociationRule> associationRuleSet = modelA.getAssociationRuleSet();
			if(!associationRuleSet.contains(rule)) {
				associationRuleSet.add(rule);
			}
		}

		return modelA;
	}

}
