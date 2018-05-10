package org.eltech.ddm.associationrules;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.*;
import java.util.stream.Collectors;

public class HashMapMiningModelElement extends MiningModelElement {

    private HashMap<String, Integer> map;
    private List<String> allKeyElements;

    public HashMapMiningModelElement(String id) {
        super(id);
        map = new HashMap<>();
        set = new ArrayList<>();
    }

    @Override
    public MiningModelElement createNewCopyElement() {
        return new HashMapMiningModelElement(getID());
    }

    @Override
    protected String propertiesToString() {
        return "";
    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {

    }

    public MiningModelElement getElement(String key) {
        if (map.containsKey(key)) {
            return super.getElement(map.get(key));
        }
        return null;
    }

    @Override
    protected synchronized void add(MiningModelElement element) {
        super.add(element);
        map.put(element.getID(), super.size() - 1);
    }

    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    public synchronized void put(String key, MiningModelElement value) {
        super.add(value);
        map.put(key, super.size() - 1);
    }

    public HashMap<String, Integer> getEntry() {
        return map;
    }

    public List<MiningModelElement> getSet() {
        return super.set;
    }

    public void setSet(List<MiningModelElement> newSet) {
        map = new HashMap<>();
        for (int i = 0; i < newSet.size(); i++) {
            map.put(newSet.get(i).getID(), i);
        }
        super.set = newSet;
    }

    public List<String> getAllKeyElements() {
        if (allKeyElements == null) {
            allKeyElements = new ArrayList<>();
            map.keySet().stream().map(s -> s.split(";")).collect(Collectors.toList()).forEach(s -> allKeyElements.addAll(Arrays.asList(s)));
        }
        return allKeyElements;
    }

    public void setAllKeyElements(List<String> allKeyElements) {
        this.allKeyElements = allKeyElements;
    }

//    public void union(List<MiningModelElement> elems) throws MiningException {
//        // 1 merge all properties of this mining element
//        merge(elems);
//
//        if (set == null) // if this element has not children elements
//            return;
//
//        // 2. ?????
//        // forming lists for each element of this set
//        int includingSelf = 0;
//        Map<String, List<MiningModelElement>> lists = new TreeMap<>();
//        for (MiningModelElement setm : elems) {
//            if (this == setm) { // if this element is same setm
//                includingSelf++;
//                continue;
//            }
//
//            if (((HashMapMiningModelElement) setm).set != null) {
//                for (MiningModelElement entm : ((HashMapMiningModelElement) setm).set) {
//                    List<MiningModelElement> list = lists.get(entm.getID());
//                    if (list == null) {
//                        list = new ArrayList<>();
//                        lists.put(entm.getID(), list);
//                    }
//                    list.add(entm);
//                }
//            }
//
//            List<MiningModelElement> remElem = new ArrayList<>();
//            // process each element from this set
//            for (MiningModelElement ent : set) {
//                List<MiningModelElement> list = lists.get(ent.getID());
//                lists.remove(ent.getID());
//                if (list == null || (list.size() < (elems.size() - includingSelf))) { // if element was deleted by parallel function
//                    // save removed element
//                    remElem.add(ent);
//                } else {// if element was changed (may be)
//                    ent.union(list);
//                }
//            }
//
////        // remove removed elements
////        for(MiningModelElement elmt : remElem){
////            set.remove(elmt);
////            System.out.println("Thread-" + Thread.currentThread().getName() + " remove " + elmt);
////        }
//
//            // add new elements
//            for (List<MiningModelElement> list : lists.values()) {
//                if (list.isEmpty())
//                    continue;
//
//                MiningModelElement nset = list.get(0).createNewCopyElement();
//                //list.remove(0);
//                nset.union(list);
//                add(nset);
//                //System.out.println("Thread-" + Thread.currentThread().getName() + " add " + nset);
//            }
//        }
//
//    }

    @Override
    public Object clone() {
        HashMapMiningModelElement o = new HashMapMiningModelElement(getID());
        if (set != null) {
            o.set = new ArrayList<>();
            for (MiningModelElement element : set)
                o.set.add((MiningModelElement) element.clone());
            o.setSet(o.set);
        }
        return o;
    }
}
