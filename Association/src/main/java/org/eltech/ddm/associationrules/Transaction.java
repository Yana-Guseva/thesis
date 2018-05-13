package org.eltech.ddm.associationrules;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Transaction extends MiningModelElement {
    protected String tid;
    protected List<String> itemIDList = new ArrayList<>();

    public Transaction(String tid) {
        super(tid);
        this.tid = tid;
    }

    @Override
    protected String propertiesToString() {
        return null;
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

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {
////        elements.forEach(element -> ((Transaction) element).getItemIDList()
////                .forEach(e -> {
////                    if (!this.getItemIDList().contains(e)) {
////                        this.addItem((Item) element.getElement(e));
////                        this.getItemIDList().add(e);
////                    } else {
////                        Item item = (Item) this.getElement(e);
////                        item.setSupportCount(item.getSupportCount() + ((Item) element.getElement(e)).getSupportCount());
////                    }
////                }));
//        elements.forEach(element -> ((Transaction) element).getItemIDList()
//                .stream()
//                .filter(e -> !this.getItemIDList().contains(e))
//                .forEach(e -> {
//                    this.addItem((Item) element.getElement(e));
//                    this.getItemIDList().add(e);
//                }));
    }

    public void addItem(Item element) {
        super.add(element);
        itemIDList.add(element.getID());
    }

    @Override
    public Object clone() {
        Transaction o = new Transaction(getID());

//		if(set != null){
//			o.set = new ArrayList<>();
//			for(MiningModelElement element : set)
//				o.set.add(getElement(element.getID()));
//		}
        if (itemIDList != null) {
            o.itemIDList = new ArrayList<>();
            for (String id : itemIDList) {
                o.itemIDList.add(id);
            }
        }
        o.tid = tid;
        return o;
    }

    @Override
    public void union(List<MiningModelElement> elems) throws MiningException {
        merge(elems);
    }
}
