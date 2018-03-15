package org.eltech.ddm.associationrules;

import java.util.ArrayList;
import java.util.List;


public class Transaction {
	protected String tid;
	protected List<String> itemIDList = new ArrayList<String>();

	public Transaction() {

	}

	public Transaction(String tid)   {
		this.tid = tid;
	}

	public String getTID() {
		return tid;
	}

	public void setTID(String tid) {
		this.tid = tid;
	}

	public List<String> getItemIDList() {
		return itemIDList;
	}

	public void setItemIDList(List<String> itemList) {
		this.itemIDList = itemList;
	}

	@Override
	public String toString() {
		return "tid = " + tid + ", " + itemIDList;
	}
}
