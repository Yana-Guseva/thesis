//package org.eltech.ddm.associationrules.apriori.partition.steps;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
//import org.eltech.ddm.associationrules.Item;
//import org.eltech.ddm.associationrules.ItemSet;
//import org.eltech.ddm.associationrules.ItemSets;
//import org.eltech.ddm.associationrules.apriori.AprioriMiningModel;
//import org.eltech.ddm.associationrules.apriori.steps.CreateKItemSetCandidateStep;
//import org.eltech.ddm.inputdata.MiningInputStream;
//import org.eltech.ddm.miningcore.MiningException;
//import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
//import org.omg.java.cwm.analysis.datamining.miningcore.miningfunctionsettings.MiningFunctionSettings;
//import org.omg.java.cwm.analysis.datamining.miningcore.miningmodel.MiningModel;
//
///**
// * Block create new n-item attributes and add him to list of large item attributes if his supp > minSuppurt
// * @author iholod
// *
// */
//public class CreateKItemSetCandidateWithTIDsStep extends CreateKItemSetCandidateStep {
//
//	public CreateKItemSetCandidateWithTIDsStep(EMiningFunctionSettings  settings) throws MiningException {
//		super(settings);
//	}
//
//	protected ItemSet union(ItemSet itemSet, ItemSet itemSet2) {
//		ItemSet newItemSet = super.union(itemSet, itemSet2);
//
//		Set<String> tids = intersection(itemSet.getTIDList(), itemSet2.getTIDList());
//		newItemSet.setTIDList(tids);
//		newItemSet.setSupportCount(newItemSet.getTIDList().size());
//
//		return  newItemSet;
//	 }
//
//	protected Set<String> intersection(Set<String> list1, Set<String> list2) {
//	        Set<String> list = new HashSet<String>();
//	        for (String t : list1) {
//	            if(list2.contains(t)) {
//	                list.add(t);
//	            }
//	        }
//	        return list;
//	}
//
//}
