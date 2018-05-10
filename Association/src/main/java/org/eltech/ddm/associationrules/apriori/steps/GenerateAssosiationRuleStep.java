package org.eltech.ddm.associationrules.apriori.steps;

import org.eltech.ddm.associationrules.AssociationRule;
import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.ItemSet;
import org.eltech.ddm.associationrules.dhp.DHPModel;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import static org.eltech.ddm.miningcore.miningmodel.EMiningModel.index;

public class GenerateAssosiationRuleStep extends MiningBlock {
	private static final long serialVersionUID = 1L;

	protected final double minConfidence;

	public GenerateAssosiationRuleStep(EMiningFunctionSettings settings) throws MiningException {
		super(settings);
		minConfidence = ((AssociationRulesFunctionSettings)settings).getMinConfidence();
	}

	@Override
	protected EMiningModel execute(EMiningModel model) throws MiningException {
		DHPModel modelA = (DHPModel) model;
		if(modelA.getHashTable(modelA.getCurrentHashTableIndex()).size() == 0) // It doesn't built rules for 1-items attributes
			return modelA;

		ItemSet itemSet = (ItemSet) modelA.getElement(index(DHPModel.HASH_TABLE_SET, modelA.getCurrentHashTableIndex(), modelA.getCurrentLargeItemsetIndex()));
        String itemId = itemSet.getItemIDList().get(modelA.getCurrentItemLargeItemsetIndex());

		ItemSet a = new ItemSet(itemSet.getID(), itemSet.getItemIDList());
		a.getItemIDList().remove(itemId);

		double confidence = ((double)itemSet.getSupportCount()) / ((double)a.getSupportCount()); // TODO: div on 0!!!
		if (confidence >= minConfidence) {
			ItemSet c = new ItemSet(itemSet.getID(), itemSet.getItemIDList());
			c.getItemIDList().removeAll(a.getItemIDList());
			AssociationRule rule = new AssociationRule(a, c, (double)itemSet.getSupportCount() / (double)modelA.getTransactionCount(), confidence);
			if (modelA.getAssociationRule(rule.getID()) == null) {
				modelA.addElement(index(DHPModel.ASSOCIATION_RULE_SET), rule);
			}
		}

		return modelA;
	}

}
