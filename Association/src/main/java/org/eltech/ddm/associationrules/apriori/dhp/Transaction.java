package org.eltech.ddm.associationrules.apriori.dhp;

import org.eltech.ddm.associationrules.ItemSet;
import org.eltech.ddm.associationrules.ItemSets;

public class Transaction {
	protected String tid;
	protected ItemSets itemsetList = new ItemSets();

	public Transaction() {
		
	}
	
	public Transaction(String tid, ItemSet... itemSets)   {
		this.tid = tid;
		 for(ItemSet itemset : itemSets) {
			 itemset.getTIDList().add(tid);
			 itemsetList.add(itemset);
	     }
	}
	
	public String getTID() {
		return tid;
	}
	
	public void setTid(String tid) {
		this.tid = tid;
	}
	
	public ItemSets getItemsetList() {
		return itemsetList;
	}
	
	public void setItemsetList(ItemSets itemsetList) {
		this.itemsetList = itemsetList;
	}
	
	@Override
	public String toString() {
		return "tid = " + tid + ", " + itemsetList;
	}
}
