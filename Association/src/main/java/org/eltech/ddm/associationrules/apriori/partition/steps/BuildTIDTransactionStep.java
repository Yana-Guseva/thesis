//package org.eltech.ddm.associationrules.apriori.partition.steps;
//
//import org.eltech.ddm.associationrules.AssociationRulesMiningModel;
//import org.eltech.ddm.associationrules.Item;
//import org.eltech.ddm.associationrules.Transaction;
//import org.eltech.ddm.associationrules.apriori.steps.BuildTransactionStep;
//import org.eltech.ddm.miningcore.MiningException;
//import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
//
//public class BuildTIDTransactionStep extends BuildTransactionStep {
//
//	public BuildTIDTransactionStep(EMiningFunctionSettings settings)
//			throws MiningException {
//		super(settings);
//		// TODO Auto-generated constructor stub
//	}
//
//	protected Transaction createTransaction(String transId, Item item, AssociationRulesMiningModel modelA){
//		Transaction transaction = super.createTransaction(transId, item, modelA);
//		item.getTidList().add(transaction.getTID());
//
//		return transaction;
//	}
//
//}
