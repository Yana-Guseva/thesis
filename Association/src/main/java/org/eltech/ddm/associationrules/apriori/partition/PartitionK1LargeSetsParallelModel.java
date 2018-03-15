package org.eltech.ddm.associationrules.apriori.partition;

import java.util.ArrayList;
import java.util.List;

import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.AssociationRulesMiningModel;
import org.eltech.ddm.associationrules.ItemSet;
import org.eltech.ddm.associationrules.ItemSets;
import org.eltech.ddm.associationrules.Transaction;
import org.eltech.ddm.associationrules.apriori.AprioriMiningModel;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

public class PartitionK1LargeSetsParallelModel extends AprioriMiningModel {

	public PartitionK1LargeSetsParallelModel(
			AssociationRulesFunctionSettings settings) throws MiningException {
		super(settings);
	}
	
	
	@Override
	public ArrayList<EMiningModel> split(int handlerCount) throws MiningException {
		ArrayList<EMiningModel> models = new ArrayList<EMiningModel>(handlerCount);
		for(int i = 0; i < handlerCount; i++){
			AprioriMiningModel model = (AprioriMiningModel)clone();
			model.setCurrentItemSet(model.getCurrentItemSet() + i);
//			model.putStateParameter(AssociationRulesMiningModel.NAME_CURRENT_ITEM_SET,
//					(Integer)model.getStateParameter(AssociationRulesMiningModel.NAME_CURRENT_ITEM_SET) + i);
			models.add(model);
		}

//		ItemSets prevItemsetList = getLargeItemSetsList().get(getCurrentLargeItemSets() - 1);
//		for(ItemSet attributes : prevItemsetList){
//			 System.out.println(attributes);
//		}
		return models;
	}
	
	@Override
	public void join(List<EMiningModel> models) throws MiningException {
		int maxCurrentItemSet = -1;
//		int i = 0;
		for(EMiningModel model : models){
			//maxCurrentItemSet = Math.max((Integer)model.getStateParameter(AssociationRulesMiningModel.NAME_CURRENT_ITEM_SET), maxCurrentItemSet);
			maxCurrentItemSet = Math.max(((AssociationRulesMiningModel)model).getCurrentItemSet(), maxCurrentItemSet);

//			ItemSets prevItemsetList = ((AprioriMiningModel)model).getLargeItemSetsList().get(getCurrentLargeItemSets());
//			int j = 0;
//			for(ItemSet attributes : prevItemsetList){
//				 System.out.println(i  + " - " + j++ +" ." + attributes);
//			}
//			i++;

		}
		super.join(models);
		//putStateParameter(AssociationRulesMiningModel.NAME_CURRENT_ITEM_SET, maxCurrentItemSet);
		setCurrentItemSet(maxCurrentItemSet);
		//putStateParameter(AssociationRulesMiningModel.NAME_CURRENT_ITEM_SET, maxCurrentItemSet);
//		ItemSets prevItemsetList = getLargeItemSetsList().get(getCurrentLargeItemSets());
//		for(ItemSet attributes : prevItemsetList){
//			 System.out.println(attributes);
//		}

	}


}
