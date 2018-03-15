package org.eltech.ddm.associationrules.apriori;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.file.MiningArffStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.apriori.AprioriAlgorithmSettings;
import org.eltech.ddm.associationrules.apriori.AprioriMiningModel;
import org.eltech.ddm.associationrules.apriori.partition.PartitionAlgorithm;
import org.junit.Test;
import org.junit.Assert;

public class PartitionAlgoritmModelTest extends AprioriAlgoritmModelTest{

	@Override
	protected AprioriMiningModel buildModel(AssociationRulesFunctionSettings miningSettings, MiningInputStream inputData) throws MiningException {
		PartitionAlgorithm algorithm = new PartitionAlgorithm(miningSettings);
		AprioriMiningModel model = (AprioriMiningModel) algorithm.buildModel(inputData);

		System.out.println("calculation time [s]: " + algorithm.getTimeSpentToBuildModel());
		
		return model;
	}

}