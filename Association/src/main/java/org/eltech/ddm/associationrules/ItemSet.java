package org.eltech.ddm.associationrules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemSet {
	private List<String> itemIDList = new ArrayList<String>();
	private Set<String> tidList = new HashSet<String>();
	private int supportCount = 0;

	public ItemSet(Item... items) {
		for(Item item : items) {
			addItem(item);
		}
		Collections.sort(this.itemIDList);
	}

//	public ItemSet(List<Item> items) {
//		for(Item item : items) {
//			addItem(item);
//		}
//		Collections.sort(this.itemIDList);
//	}

	public ItemSet(List<String> itemIDs) {
		for(String itemID : itemIDs) {
			itemIDList.add(itemID);
		}
		Collections.sort(this.itemIDList);
	}

	public void addItem(Item item) {
		itemIDList.add(item.getItemID());
		if(itemIDList.size()==1){
			tidList.addAll(item.getTidList());
		}else{
			HashSet<String> newTids = new HashSet<String>();
			for(String id: item.getTidList()){
				if(tidList.contains(id))
					newTids.add(id);
			}
			tidList = newTids;
		}
		Collections.sort(itemIDList);
	}

	public List<String> getItemIDList() {
		return itemIDList;
	}

	public void setItemIDList(List<String> itemIDList) {
		this.itemIDList = itemIDList;
	}

	public Set<String> getTIDList() {
		return tidList;
	}

	public void setTIDList(Set<String> tids) {
		tidList = tids;

	}

//	public void setTidList(List<Integer> tidList) {
//		this.	tidList = tidList;
//	}

	@Override
	public String toString() {
		return "items " + itemIDList + ", 	tidList" + tidList ;
	}

	@Override
	public boolean equals(Object o) {

		if(itemIDList.size() != ((ItemSet)o).getItemIDList().size())
			return false;
		for(int i=0; i < itemIDList.size(); i++){
			if(!(itemIDList.get(i).equals(((ItemSet)o).getItemIDList().get(i))))
				return false;
		}
		return true;
	}

	public int getSupportCount() {
		if(tidList.isEmpty())
			return supportCount;
		else
			return tidList.size();
	}

	public void setSupportCount(int supportCount) {
		this.supportCount = supportCount;
	}

	public Object clone() {
		ItemSet o = new ItemSet();

		if(itemIDList != null){
			o.itemIDList = new ArrayList<String>();
			for(String itemID: itemIDList)
				o.itemIDList.add(new String(itemID));
		}

		if(tidList != null){
			o.tidList = new HashSet<String>();
			for(String id: tidList)
				o.tidList.add(new String(id));
			o.supportCount = supportCount ;
		}

		return o;
	}



}
