package org.eltech.ddm.associationrules;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemSet extends HashMapMiningModelElement {
    private List<String> itemIDList = new ArrayList<String>();
    private Set<String> tidList = new HashSet<String>();
    private int supportCount = 0;

    public ItemSet(String id) {
        super(id);
    }

    public ItemSet(Item... items) {
        super(Stream.of(items).map(Item::getItemID)
                .collect(Collectors.joining(", ")));
        for (Item item : items) {
            addItem(item);
        }
        Collections.sort(this.itemIDList);
    }

    public void addItem(Item item) {
        itemIDList.add(item.getItemID());
        if (itemIDList.size() == 1) {
            tidList.addAll(item.getTidList());
        } else {
            HashSet<String> newTids = new HashSet<String>();
            for (String id : item.getTidList()) {
                if (tidList.contains(id))
                    newTids.add(id);
            }
            tidList = newTids;
        }
        Collections.sort(itemIDList);
    }

    public ItemSet(String id, List<String> itemIDs) {
        super(id);
        StringBuilder sb = new StringBuilder();
        for (String itemID : itemIDs) {
            itemIDList.add(itemID);
        }
        Collections.sort(this.itemIDList);
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

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {
        elements.forEach(element -> ((HashMapMiningModelElement) element).getSet()
                .stream()
                .filter(e -> !this.containsKey(e.getID()))
                .forEach(e -> {
                    add(e);
                    supportCount += ((Item) e).getSupportCount();
                    ((Item) e).getTidList()
                            .stream()
                            .filter(t -> !tidList.contains(t))
                            .forEach(tidList::add);
                }));
    }

    public int getSupportCount() {
        if (tidList.isEmpty())
            return supportCount;
        else
            return tidList.size();
    }

    public void setSupportCount(int supportCount) {
        this.supportCount = supportCount;
    }

    @Override
    protected String propertiesToString() {
        return null;
    }
}
