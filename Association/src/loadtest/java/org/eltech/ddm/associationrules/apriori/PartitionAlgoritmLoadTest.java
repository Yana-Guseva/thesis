package org.eltech.ddm.associationrules.apriori;

import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.eltech.ddm.associationrules.AssociationRule;
import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.Item;
import org.eltech.ddm.associationrules.ItemSet;
import org.eltech.ddm.associationrules.apriori.dhp.DHPAlgorithm;
import org.eltech.ddm.associationrules.apriori.dhp.DHPMiningModel;
import org.eltech.ddm.associationrules.apriori.partition.PartitionAlgorithm;
import org.eltech.ddm.inputdata.MiningArrayStream;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.file.MiningArffStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

//@Ignore
public class PartitionAlgoritmLoadTest extends AprioriLoadlTest {
	
	@Test
	public void testT_200() throws MiningException {
		System.out.println("----- PartitionAlgorithm T_200 -------");
		
		setSettings("T_200");

	}
	
	@Test
	public void testI_2000() throws MiningException {
		System.out.println("----- PartitionAlgorithm T_2000 -------");
		
		setSettings("T_2000");
 
	}
	
	@Test
	public void testT_20000() throws MiningException {
		System.out.println("----- PartitionAlgorithm T_20000 -------");
		
		setSettings("T_20000");
	}

	@Test
	public void testI_5() throws MiningException {
		System.out.println("----- PartitionAlgorithm I_5-------");
		
		setSettings("I_5");
	}
	
	@Test
	public void testI_10() throws MiningException {
		System.out.println("----- PartitionAlgorithm I_10-------");
		
		setSettings("I_10");
	}
	
	@Test
	public void testI_15() throws MiningException {
		System.out.println("----- PartitionAlgorithm I_15-------");
		
		setSettings("I_15");
	}
	
	@Test
	public void testI_10_20() throws MiningException {
		System.out.println("----- PartitionAlgorithm I_10_20-------");
		
		setSettings("I_10_20");
        miningSettings.setMinConfidence(0.7);
        miningSettings.setMinSupport(0.7);
	}
	
	@Test
	public void testI_10_30() throws MiningException {
		System.out.println("----- PartitionAlgorithm I_10_30-------");
		
		setSettings("I_10_30");
        miningSettings.setMinConfidence(0.7);
        miningSettings.setMinSupport(0.7);
	}
	
	@Test
	public void testI_10_50() throws MiningException {
		System.out.println("----- PartitionAlgorithm I_10_50-------");
		
		setSettings("I_10_50");
        miningSettings.setMinConfidence(0.7);
        miningSettings.setMinSupport(0.7);
	}
	
	@After
	public void tearDown() {
		try {
			PartitionAlgorithm algorithm = new PartitionAlgorithm(miningSettings);
			System.out.println("Start algorithm");
			miningModel = (AprioriMiningModel) algorithm.buildModel(inputData);

			System.out.println("Finish algorithm. Calculation time: " + algorithm.getTimeSpentToBuildModel());

		} catch (MiningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		verifyModel();
	}

	
}
