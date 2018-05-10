package org.eltech.ddm.associationrules;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.ArrayList;
import java.util.List;

public class Item extends MiningModelElement implements Comparable<Item> {
    private String itemID;
    private List<String> tidList = new ArrayList<>();
    private int supportCount = 0;

    public Item(String itemID) {
        super(itemID);
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
        return "itemId " + itemID;
    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {
//        for (MiningModelElement element : elements) {
//            Item item = (Item) element;
//            if (itemID.equals(item.getItemID())) {
//                item.getTidList().forEach(id -> {
//                    if (!this.getTidList().contains(id)) {
//                        this.getTidList().add(id);
//                    }
//                });
//                this.supportCount += item.getSupportCount();
//            }
//        }
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
        Item o = (Item) super.clone();

        if (tidList != null) {
            o.tidList = new ArrayList<>();
            for (String id : tidList)
                o.tidList.add(id);
        }
        o.supportCount = supportCount;

        return o;
    }

    @Override
    protected String propertiesToString() {
        return "itemId " + itemID;
    }


}
