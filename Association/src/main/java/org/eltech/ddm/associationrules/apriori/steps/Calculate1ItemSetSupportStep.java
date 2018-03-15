package org.eltech.ddm.associationrules.apriori.steps;

import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.AssociationRulesMiningModel;
import org.eltech.ddm.associationrules.Item;
import org.eltech.ddm.associationrules.Transaction;
import org.eltech.ddm.associationrules.TransactionList;
import org.eltech.ddm.associationrules.apriori.AprioriMiningModel;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.Step;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.omg.java.cwm.analysis.datamining.miningcore.miningfunctionsettings.MiningFunctionSettings;
import org.omg.java.cwm.analysis.datamining.miningcore.miningmodel.MiningModel;

public class Calculate1ItemSetSupportStep extends Step {
	private static final long serialVersionUID = 1L;

	final protected double minSupport;

	public Calculate1ItemSetSupportStep( EMiningFunctionSettings settings) throws MiningException {
		super(settings);
		minSupport = ((AssociationRulesFunctionSettings)settings).getMinSupport();
	}

	@Override
	protected EMiningModel execute(MiningInputStream inputData, EMiningModel model) throws MiningException {
		AprioriMiningModel modelA = (AprioriMiningModel) model;
		TransactionList transactionList = modelA.getTransactionList();

		Transaction transaction = transactionList.get(modelA.getCurrentTransaction());
				//(Integer)getStateParameter(model, AssociationRulesMiningModel.NAME_CURRENT_TRANSACTION));
		String itemID = transaction.getItemIDList().get(modelA.getCurrentItem());
				//(Integer)getStateParameter(model, AssociationRulesMiningModel.NAME_CURRENT_ITEM));
		Item item = modelA.getItem(itemID);

		item.setSupportCount(item.getSupportCount()+1);

		return modelA;
	}
}
