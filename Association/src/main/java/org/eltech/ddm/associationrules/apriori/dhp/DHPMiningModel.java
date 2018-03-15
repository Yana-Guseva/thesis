package org.eltech.ddm.associationrules.apriori.dhp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.AssociationRulesMiningModel;
import org.eltech.ddm.associationrules.ItemSets;
import org.eltech.ddm.associationrules.TransactionList;
import org.eltech.ddm.miningcore.MiningException;


public class DHPMiningModel extends AssociationRulesMiningModel {
	private static final long serialVersionUID = 1L;
	
	protected List<TransactionList> partitions = new ArrayList<TransactionList>();
	
	protected List<ItemSets> largeItemSetsOfPartition = new ArrayList<ItemSets>();

	public DHPMiningModel(AssociationRulesFunctionSettings settings) throws MiningException {
		super(settings);
	}

	public List<TransactionList> getPartitions() {
		return partitions;
	}

	public void setPartitions(List<TransactionList> partitions) {
		this.partitions = partitions;
	}

	public synchronized List<ItemSets> getLargeItemSetsOfPartition() {
		return largeItemSetsOfPartition;
	}

	public void setLargeItemSetsOfPartition(List<ItemSets> largeItemSetsOfPartition) {
		this.largeItemSetsOfPartition = largeItemSetsOfPartition;
	}

}
