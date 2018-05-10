//package org.eltech.ddm.associationrules.apriori.steps;
//
//import org.eltech.ddm.associationrules.AssociationRulesMiningModel;
//import org.eltech.ddm.associationrules.ItemSet;
//import org.eltech.ddm.associationrules.Transaction;
//import org.eltech.ddm.associationrules.apriori.AprioriMiningModel;
//import org.eltech.ddm.inputdata.MiningInputStream;
//import org.eltech.ddm.miningcore.MiningException;
//import org.eltech.ddm.miningcore.algorithms.Step;
//import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
//import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
//import org.omg.java.cwm.analysis.datamining.miningcore.miningfunctionsettings.MiningFunctionSettings;
//import org.omg.java.cwm.analysis.datamining.miningcore.miningmodel.MiningModel;
//
//public class CalculateKItemSetSupportStep extends Step {
//	private static final long serialVersionUID = 1L;
//
//	public CalculateKItemSetSupportStep(EMiningFunctionSettings settings)
//			throws MiningException {
//		super(settings);
//	}
//
//	@Override
//	protected EMiningModel execute(MiningInputStream inputData, EMiningModel model)
//			throws MiningException {
//		AprioriMiningModel modelA = (AprioriMiningModel) model;
//		/*
//		ItemSetList nextItemsetList = null;
//
//		nextItemsetList=modelA.getLargeItemSetLists().get(modelA.getCurrentLargeItemSetList());
//
//		ItemSet itemSet = nextItemsetList.get(modelA.getCurrentItemSet());
//
//		if(nextItemsetList.contains(itemSet)){
//			itemSet.setSupportCount((itemSet.getSupportCount()+1));
//		}*/
//
//
//		Transaction transact = modelA.getTransactionList().getTransaction(modelA.getCurrentTransaction());
//				//(Integer)getStateParameter(model, AssociationRulesMiningModel.NAME_CURRENT_TRANSACTION));
//		ItemSet itemSet = modelA.getLargeItemSetsList().get(modelA.getCurrentLargeItemSets()).get(modelA.getCurrentCandidate());
////				(Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_LARGE_ITEM_SETS)).get(
////						(Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_CANDIDATE));
//
//		if(transact!=null && itemSet!=null)
//		//if(itemSet.getItems().size()!=0 && transact.getItemList().size()!=0)
//		if(transact.getItemIDList().containsAll(itemSet.getItemIDList())){
//			itemSet.setSupportCount((itemSet.getSupportCount()+1));
//		}
//
//		return modelA;
//	}
//}
