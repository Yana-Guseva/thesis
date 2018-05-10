package org.eltech.ddm.associationrules.apriori.steps;

import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.AssociationRulesMiningModel;
import org.eltech.ddm.associationrules.Item;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

public class Calculate1ItemSetSupportStep extends MiningBlock {
	private static final long serialVersionUID = 1L;

	final protected double minSupport;

	public Calculate1ItemSetSupportStep( EMiningFunctionSettings settings) throws MiningException {
		super(settings);
		minSupport = ((AssociationRulesFunctionSettings)settings).getMinSupport();
	}

	@Override
	protected EMiningModel execute(EMiningModel model) throws MiningException {
        AssociationRulesMiningModel modelA = (AssociationRulesMiningModel) model;
		int transIndex = modelA.getCurrentTransactionIndex();
		int itemIndex = modelA.getCurrentItemIndex();
        Item item = modelA.getItem(transIndex, itemIndex);
        item.setSupportCount(item.getSupportCount() + 1);

		return modelA;
	}
}
