//package org.eltech.ddm.associationrules.apriori.steps;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
//import org.eltech.ddm.associationrules.Item;
//import org.eltech.ddm.associationrules.ItemSet;
//import org.eltech.ddm.associationrules.ItemSets;
//import org.eltech.ddm.associationrules.apriori.AprioriMiningModel;
////import org.eltech.ddm.association.apriori.PartitionMiningModel;
//import org.eltech.ddm.inputdata.MiningInputStream;
//import org.eltech.ddm.miningcore.MiningException;
//import org.eltech.ddm.miningcore.algorithms.Step;
//import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
//import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
//import org.omg.java.cwm.analysis.datamining.miningcore.miningfunctionsettings.MiningFunctionSettings;
//import org.omg.java.cwm.analysis.datamining.miningcore.miningmodel.MiningModel;
//
//public class CreateKItemSetCandidateStep extends Step {
//
//	public CreateKItemSetCandidateStep(EMiningFunctionSettings  settings) throws MiningException {
//		super(settings);
//	}
//
//	@Override
//	protected EMiningModel execute(MiningInputStream inputData, EMiningModel model) throws MiningException {
//		AprioriMiningModel modelA = (AprioriMiningModel) model;
//
//		ItemSets prevItemsetList = modelA.getLargeItemSetsList().get(modelA.getCurrentLargeItemSets() - 1);
//				//(Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_LARGE_ITEM_SETS) - 1);
//		ItemSets currItemsetList = null;
//		if(modelA.getLargeItemSetsList().size() <= (modelA.getCurrentLargeItemSets())){
//				//(Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_LARGE_ITEM_SETS))){
//			currItemsetList = new ItemSets();
//			modelA.getLargeItemSetsList().add(currItemsetList);
//		} else
//			currItemsetList = modelA.getLargeItemSetsList().get(modelA.getCurrentLargeItemSets());
//					//(Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_LARGE_ITEM_SETS));
//
//		ItemSet itemSet = prevItemsetList.get(modelA.getCurrentItemSet());
//				//(Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_ITEM_SET));
//		ItemSet itemSet2 = prevItemsetList.get(modelA.getCurrentItemSet2());
//				//(Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_ITEM_SET_2));
//
//		//System.out.println("Thread: " + Thread.currentThread().getName() + " itemSet=" + itemSet + " itemSet2=" + itemSet2);
//
//		ItemSet newItemSet = union(itemSet, itemSet2);
//		modelA.setCurrentCandidate(-1);
//		//setStateParameter(model, AprioriMiningModel.NAME_CURRENT_CANDIDATE, -1);
//		if(newItemSet.getItemIDList().size() == itemSet.getItemIDList().size() + 1) {
//			if(!currItemsetList.contains(newItemSet))
//				currItemsetList.add(newItemSet);
//				int currentCandidate = currItemsetList.indexOf(newItemSet);
//				modelA.setCurrentCandidate(currentCandidate);
//				//setStateParameter(model, AprioriMiningModel.NAME_CURRENT_CANDIDATE, currentCandidate);
//				//addNewItemSet(modelA, newItemSet);
//		}
//
//		return modelA;
//	}
//
//	protected ItemSet union(ItemSet itemSet, ItemSet itemSet2) {
//		Set<String> set = new HashSet<String>();
//        set.addAll(itemSet.getItemIDList());
//        set.addAll(itemSet2.getItemIDList());
//        List<String> itemIds =  new ArrayList<String>(set);
//		ItemSet newItemSet = new ItemSet(itemIds);
//
//		return  newItemSet;
//	 }
//
//}
