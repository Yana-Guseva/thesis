package org.eltech.ddm.associationrules.apriori;

import java.util.ArrayList;
import java.util.List;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.eltech.ddm.associationrules.AssociationRule;
import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.AssociationRulesMiningModel;
import org.eltech.ddm.associationrules.Item;
import org.eltech.ddm.associationrules.ItemSet;
import org.eltech.ddm.associationrules.ItemSets;
import org.eltech.ddm.associationrules.Transaction;

public class AprioriMiningModel  extends AssociationRulesMiningModel{

	protected List<ItemSets> largeItemSetsList = new ArrayList<ItemSets>();

//	static public final String NAME_CURRENT_ITEM_SET_2 = "currentItemSet2";
//
//	static public final String NAME_CURRENT_ITEM_2 = "currentItem2";
//
//	static public final String NAME_CURRENT_CANDIDATE = "currentCandidate";
//
//	static public final String NAME_CURRENT_LARGE_ITEM_SETS = "currentLargeItemSets";

	private int currentItemSet2;

	private int currentItem2;

	private int currentCandidate;

	private int currentLargeItemSets;


	public AprioriMiningModel(AssociationRulesFunctionSettings settings)
			throws MiningException {
		super(settings);
	}

	public int getCurrentItemSet2() {
		return currentItemSet2;
	}

	public void setCurrentItemSet2(int currentItemSet) {
		this.currentItemSet2 = currentItemSet;
	}

	public int getCurrentLargeItemSets() {
		return currentLargeItemSets;
	}

	public void setCurrentLargeItemSets(int currentLargeItemSets) {
		this.currentLargeItemSets = currentLargeItemSets;
	}

	private int getCurrentItem2() {
		return currentItem2;
	}

	private void setCurrentItem2(int currentItem2) {
		this.currentItem2 = currentItem2;
	}

	public int getCurrentCandidate() {
		return currentCandidate;
	}

	public void setCurrentCandidate(int currentCandidate) {
		this.currentCandidate = currentCandidate;
	}

	public synchronized List<ItemSets> getLargeItemSetsList() {
		return largeItemSetsList;
	}
	
	@Override
	public ArrayList<EMiningModel> split(int handlerCount) throws MiningException {
		ArrayList<EMiningModel> models = super.split(handlerCount);
		
		for(int i=0; i < getLargeItemSetsList().size(); i++){
			int iModel = -1;
			int count = Math.round(getLargeItemSetsList().get(i).size()/handlerCount) + 1;
			int k = count;
			for(ItemSet is: getLargeItemSetsList().get(i)){
				if(k == count){
					iModel++;
					k = 0;
					((AprioriMiningModel)models.get(iModel)).getLargeItemSetsList().get(i).clear();
				}
				((AprioriMiningModel)models.get(iModel)).getLargeItemSetsList().get(i).add(is);
				k++;
			}

		}
		
		return models;
	}

	@Override
	public void join(List<EMiningModel> joinModels) throws MiningException {

		super.join(joinModels);

		for(EMiningModel mm: joinModels){
			if(mm == this)
				continue;
			AprioriMiningModel armm = (AprioriMiningModel) mm;
		
			for(int i=0; i < armm.getLargeItemSetsList().size(); i++){
				if(i >= largeItemSetsList.size())
					largeItemSetsList.add(new ItemSets());
				
				
				for(ItemSet is: armm.getLargeItemSetsList().get(i)){
					boolean isContains = false;
					int index = largeItemSetsList.get(i).indexOf(is);
					if(index >= 0){
						ItemSet lis = largeItemSetsList.get(i).get(index);
						lis.setSupportCount(lis.getSupportCount() + is.getSupportCount());
						lis.getTIDList().addAll(is.getTIDList());
						isContains = true;
					}
					if(!isContains)
						largeItemSetsList.get(i).add(is);
				}
			}

		}

	}

	public Object clone() {
		AprioriMiningModel o = null;
		o = (AprioriMiningModel)super.clone();

		if(largeItemSetsList != null){
			o.largeItemSetsList = new ArrayList<ItemSets>();
			for(ItemSets isl: largeItemSetsList)
				o.largeItemSetsList.add((ItemSets)isl.clone());
		}

		o.currentItemSet2 = currentItemSet2;
		o.currentItem2 = currentItem2;
		o.currentLargeItemSets = currentLargeItemSets;

		return o;
	}


}