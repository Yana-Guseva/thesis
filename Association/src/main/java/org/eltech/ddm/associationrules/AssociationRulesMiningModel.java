package org.eltech.ddm.associationrules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.eltech.ddm.miningcore.miningmodel.IParallelMiningModel;

public class AssociationRulesMiningModel extends EMiningModel implements IParallelMiningModel {

	private static final long serialVersionUID = 1L;

	protected ArrayList<AssociationRule> associationRuleSet = new ArrayList<AssociationRule>();

	protected TransactionList transactionList = new TransactionList();

	private int transactionCount = 0;

	protected Map<String, Item> itemList = new HashMap<String, Item>();

	// ======= current state of model (build model task) ==================
//	static public final String NAME_CURRENT_TRANSACTION = "currentTransaction";
//
//	static public final String NAME_CURRENT_ITEM_SET = "currentItemSet";
//
//	static public final String NAME_CURRENT_ITEM = "currentItem";

	private int currentTransaction = 0;

	private int currentItemSet = 0;

	private int currentItem = 0;

	public AssociationRulesMiningModel(AssociationRulesFunctionSettings settings) throws MiningException {
		super(settings);
	}

	@Override
	public void createModel() throws MiningException {

	}

	public TransactionList getTransactionList() {
		return transactionList;
	}

	public void setTransactionList(TransactionList transactionList) {
		this.transactionList = transactionList;
	}

	public int getTransactionCount() {
		if(transactionCount > 0)
			return transactionCount;
		else
			return this.transactionList.size();
	}

	public List<AssociationRule> getAssociationRuleSet() {
		return associationRuleSet;
	}

	public void setAssociationRuleSet(AssociationRuleSet associationRuleSet) {
		this.associationRuleSet = associationRuleSet;
	}

	public Item getItem(String itemID) {
		return itemList.get(itemID);
	}

	public Map<String, Item> getItems() {
		return itemList;
	}

	public void addItem(Item item) {
		if(! this.itemList.containsKey(item.getItemID()))
			this.itemList.put(item.getItemID(), item);
	}

	public int getCurrentTransaction() {
		return currentTransaction;
	}

	public void setCurrentTransaction(int currentTransaction) {
		this.currentTransaction = currentTransaction;
	}

	public int getCurrentItemSet() {
		return currentItemSet;
	}

	public void setCurrentItemSet(int currentItemSet) {
		this.currentItemSet = currentItemSet;
	}

	public int getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(int currentItem) {
		this.currentItem = currentItem;
	}

	public Object clone() {
		AssociationRulesMiningModel o = null;
		o = (AssociationRulesMiningModel)super.clone();

		if(itemList != null){
			o.itemList = new HashMap<String, Item>();
			for (Item item: itemList.values())
				o.itemList.put(item.getItemID(), (Item)item.clone());
		}

		if(transactionList != null){
			o.transactionList = (TransactionList) transactionList.clone();
		}

		if (associationRuleSet != null) {
			o.associationRuleSet = new ArrayList<AssociationRule>();
			for (AssociationRule assRule: associationRuleSet){
				o.associationRuleSet.add(assRule);
			}
		}

		o.currentItem = currentItem;
		o.currentItemSet = currentItemSet;
		o.currentTransaction = currentTransaction;
		o.transactionCount = transactionCount;

		return o;
	}

	@Override
	public ArrayList<EMiningModel> split(int handlerCount)
			throws MiningException {
		transactionCount = transactionList.size();
		ArrayList<EMiningModel> models = new ArrayList<EMiningModel>(handlerCount);
		for(int i = 0; i < handlerCount; i++){
				models.add((EMiningModel)clone()); 
		}

		if((transactionList != null) && (transactionList.size() > 0)){
			int iModel = -1;
			int count = Math.round(transactionList.size()/handlerCount) + 1;
			int i = count;
			for(Transaction tran : transactionList){
				if(i == count){
					iModel++;
					i = 0;
					((AssociationRulesMiningModel)models.get(iModel)).getTransactionList().clear();
				}
				((AssociationRulesMiningModel)models.get(iModel)).getTransactionList().add(tran);
				i++;
			}
		}

//		if ((associationRuleSet != null) && (associationRuleSet.size() > 0)) {
//			int iModel = -1;
//			int count = Math.round(associationRuleSet.size()/handlerCount) + 1;
//			int i = count;
//			for (AssociationRule assRule: associationRuleSet){
//				if(i == count){
//					iModel++;
//					i = 0;
//					models.add((EMiningModel) clone());
//					((AssociationRulesMiningModel)models.get(iModel)).getAssociationRuleSet().clear();
//				}
//				((AssociationRulesMiningModel)models.get(iModel)).getAssociationRuleSet().add(assRule);
//				i++;
//			}
//		}

		return models;
	}

	@Override
	public void join(List<EMiningModel> joinModels) throws MiningException {
		for(EMiningModel mm: joinModels){
			if(mm == this)
				continue;
			AssociationRulesMiningModel armm = (AssociationRulesMiningModel) mm;
			for(AssociationRule rule: armm.getAssociationRuleSet()){
				if(!associationRuleSet.contains(rule)){
					associationRuleSet.add(rule);
				}
			}
			for(Transaction tran: armm.getTransactionList()){
				if(!transactionList.contains(tran)){
					transactionList.add(tran);
				}
			}

			itemList.putAll(armm.getItems());
		}
		transactionCount = transactionList.size();	
	}


}
