package org.eltech.ddm.associationrules.apriori.steps;

import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.AssociationRulesMiningModel;
import org.eltech.ddm.associationrules.Item;
import org.eltech.ddm.associationrules.ItemSet;
import org.eltech.ddm.associationrules.ItemSets;
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

public class CreateLarge1ItemSetStep extends Step {

	final protected double minSupport;

	public CreateLarge1ItemSetStep(EMiningFunctionSettings settings) throws MiningException {
		super(settings);
		minSupport = ((AssociationRulesFunctionSettings)settings).getMinSupport();
	}

	@Override
	protected EMiningModel execute(MiningInputStream inputData, EMiningModel model) throws MiningException {
		TransactionList transactionList = ((AprioriMiningModel) model).getTransactionList();

		AprioriMiningModel modelA = (AprioriMiningModel) model;

		ItemSets oneItemsets = null;
		if(modelA.getLargeItemSetsList().size() == 0){
			oneItemsets = new ItemSets();
			modelA.getLargeItemSetsList().add(oneItemsets);
		} else
			oneItemsets = modelA.getLargeItemSetsList().get(0);

		Transaction transaction = transactionList.get(modelA.getCurrentTransaction());
			//	(Integer)getStateParameter(model, AssociationRulesMiningModel.NAME_CURRENT_TRANSACTION));
		String itemID = transaction.getItemIDList().get(modelA.getCurrentItem());
				//(Integer)getStateParameter(model, AssociationRulesMiningModel.NAME_CURRENT_ITEM));
		Item item = modelA.getItem(itemID);

		double supp = calcSupport(item, modelA);
		if(supp >= minSupport){
			ItemSet oneItemSet = new ItemSet(item);
			oneItemSet.setSupportCount(item.getSupportCount());
			if(oneItemsets.contains(oneItemSet))
				oneItemsets.remove(oneItemSet);
			oneItemsets.add(oneItemSet);
		}

 		return modelA;
	}


	protected double calcSupport(Item item, AprioriMiningModel modelA) {
		// TODO Auto-generated method stub
		return ((double)item.getSupportCount())/((double)modelA.getTransactionCount());
	}

}