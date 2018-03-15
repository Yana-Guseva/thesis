package org.eltech.ddm.associationrules.apriori;

import org.eltech.ddm.associationrules.AssociationRulesAlgorithmSettings;

 public class AprioriAlgorithmSettings extends AssociationRulesAlgorithmSettings {
	private static final long serialVersionUID = 1L;
	
	protected int numberOfTransactions;

	public int getNumberOfPartitions() {
		return numberOfTransactions;
	}

	public void setNumberOfTransactions(int numberOfTransactions) {
		this.numberOfTransactions = numberOfTransactions;
	}
 }