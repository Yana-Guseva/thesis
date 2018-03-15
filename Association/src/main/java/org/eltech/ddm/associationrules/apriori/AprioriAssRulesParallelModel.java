package org.eltech.ddm.associationrules.apriori;

import java.util.ArrayList;

import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.AssociationRulesMiningModel;
import org.eltech.ddm.associationrules.ItemSet;
import org.eltech.ddm.associationrules.ItemSets;
import org.eltech.ddm.associationrules.Transaction;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

public class AprioriAssRulesParallelModel extends AprioriMiningModel {

	public AprioriAssRulesParallelModel(
			AssociationRulesFunctionSettings settings) throws MiningException {
		super(settings);
	}
	
	
	@Override
	public ArrayList<EMiningModel> split(int handlerCount) throws MiningException {
		ArrayList<EMiningModel> models = new ArrayList<EMiningModel>(handlerCount);
		for(int i = 0; i < handlerCount; i++){
				models.add((EMiningModel)clone()); 
		}
		
		if((largeItemSetsList != null) ){
			for(int l = 0; l < largeItemSetsList.size(); l++){
				for(int i = 0; i < handlerCount; i++){
					((AprioriMiningModel)models.get(i)).getLargeItemSetsList().get(l).clear();
				}
				ItemSets isl = largeItemSetsList.get(l);
				int iModel = -1;
				int count = Math.round(isl.size()/handlerCount) + 1;
				int i = count;
				for(ItemSet is : isl){
					if(i == count){
						iModel++;
						i = 0;
					}
					((AprioriMiningModel)models.get(iModel)).getLargeItemSetsList().get(l).add(is);
					i++;
				}
			}
		}

		return models;
	}


}
