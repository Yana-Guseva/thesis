package org.eltech.ddm.associationrules;

import java.util.ArrayList;
import java.util.List;

public class Item implements Comparable<Item>{
	private String itemID;
	private List<String> tidList = new ArrayList<String>();
	private int supportCount = 0;

	public Item(String itemID) {
		this.itemID = itemID;

	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public String getItemID() {
		return this.itemID;
	}

	public List<String> getTidList() {
		return tidList;
	}

	public void setTidList(List<String> tidList) {
		this.tidList = tidList;
	}

	@Override
	public String toString() {
		return "itemId " + itemID ;
	}

	@Override
	public boolean equals(Object o) {
		boolean equal = false;
		if(((Item)o).getItemID().equals(itemID)) {
			equal = true;
		} else {
			equal = false;
		}
		return equal;
	}

	public int getSupportCount() {
		return supportCount;
	}

	public void setSupportCount(int supportCount) {
		this.supportCount = supportCount;
	}

	@Override
	public int compareTo(Item o) {
		return itemID.compareTo(o.getItemID());
	}

	public Object clone() {
		Item o = new Item(new String(itemID));

		if(tidList != null){
			o.tidList = new ArrayList<String>();
			for(String id: tidList)
				o.tidList.add(new String(id));
			o.supportCount = supportCount ;
		}

		return o;
	}


}
