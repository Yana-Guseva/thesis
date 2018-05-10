//package org.eltech.ddm.associationrules.apriori;
//
//import static org.junit.Assert.fail;
//
//import java.io.FileNotFoundException;
//import java.io.UnsupportedEncodingException;
//import java.util.Date;
//
//import org.eltech.ddm.associationrules.AssociationRule;
//import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
//import org.eltech.ddm.associationrules.Item;
//import org.eltech.ddm.associationrules.ItemSet;
//import org.eltech.ddm.inputdata.MiningArrayStream;
//import org.eltech.ddm.inputdata.MiningInputStream;
//import org.eltech.ddm.inputdata.file.MiningArffStream;
//import org.eltech.ddm.miningcore.MiningException;
//import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
//import org.eltech.ddm.miningcore.miningdata.ELogicalData;
//import org.junit.Assert;
//import org.junit.Test;
//
//public class AprioriAlgoritmModelTest extends AprioriModelTest {
//
//	@Override
//	protected AprioriMiningModel buildModel(AssociationRulesFunctionSettings miningSettings, MiningInputStream inputData) throws MiningException {
//		AprioriAlgorithm algorithm = new AprioriAlgorithm(miningSettings);
//		AprioriMiningModel model = (AprioriMiningModel) algorithm.buildModel(inputData);
//
//		System.out.println("calculation time [s]: " + algorithm.getTimeSpentToBuildModel());
//
//		return model;
//	}
//
//}
