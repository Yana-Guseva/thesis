package org.eltech.ddm.associationrules.apriori;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.eltech.ddm.associationrules.AssociationRule;
import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.Item;
import org.eltech.ddm.associationrules.ItemSet;
import org.eltech.ddm.inputdata.MiningArrayStream;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.file.MiningArffStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AprioriModelTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	protected abstract AprioriMiningModel buildModel(AssociationRulesFunctionSettings miningSettings, MiningInputStream inputData) throws MiningException;
	

	/**
	 * Source data attributes:
	 * 		TID   Itemset
	 * 		 0   - 1 3 4
	 * 		 1   - 2 3 5 2
	 *  	 2	 - 5 2 1 2 3
	 *  	 3 	 - 2 5 
	 *  Result large itemset for min support 0.6
	 *      {2,5}
	 * @throws MiningException
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testTransactSmallData() throws MiningException, FileNotFoundException, UnsupportedEncodingException {
		System.out.println("AprioriAlgorithm");

		MiningInputStream miningInputStream = new MiningArffStream("../data/arff/association/transact_small.arff");
		ELogicalData logicalData = miningInputStream.getLogicalData();

        AprioriAlgorithmSettings algorithmSettings = new AprioriAlgorithmSettings();

        AssociationRulesFunctionSettings miningSettings = new AssociationRulesFunctionSettings(logicalData);
        miningSettings.setTransactionIDsArributeName("transactId");
        miningSettings.setItemIDsArributeName("itemId");
        miningSettings.setMinConfidence(0.6);
        miningSettings.setMinSupport(0.6);
        miningSettings.setAlgorithmSettings(algorithmSettings);

        AprioriMiningModel miningModel = buildModel(miningSettings, miningInputStream);

		System.out.println("AssociationRuleSet:  \n" + miningModel.getAssociationRuleSet());

		Assert.assertEquals(2, miningModel.getAssociationRuleSet().size());
		// verify first rule
		Assert.assertEquals(true, miningModel.getAssociationRuleSet().contains(
				new AssociationRule(new ItemSet(new Item("5")), new ItemSet(new Item("2")), 0.0, 0.0)));
		Assert.assertEquals(true, miningModel.getAssociationRuleSet().contains(
				new AssociationRule(new ItemSet(new Item("2")), new ItemSet(new Item("5")), 0.0, 0.0)));
	}

	/**
	 * Source data attributes:
	 * 		TID   Itemset
	 * 		 0   - 1 3 4
	 * 		 1   - 2 3 5
	 *  	 2	 - 4 2 1 3
	 *  	 3 	 - 1 5 3
	 *  	 4   - 1 2 4
	 *  	 5   - 2 3 0
	 *       6   - 5 2 1 3
	 *       7	 - 1 2 3
	 *      10   - 5 3 1
	 * 		11   - 1 3 5
	 *  	12	 - 3 4 1 2
	 *  	13 	 - 4 5 2
	 *  	14   - 3 2 1
	 *  	15   - 0 3 5
	 *      16   - 0 2 1 4
	 *      17	 - 5 4 3
	 *  Result large itemset for min support 0.5
	 *      {1,3};
	 * @throws MiningException
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testTransactData() throws MiningException, FileNotFoundException, UnsupportedEncodingException {
		MiningInputStream miningInputStream = new MiningArffStream("../data/arff/association/transact.arff");
        ELogicalData logicalData = miningInputStream.getLogicalData();

        AprioriAlgorithmSettings algorithmSettings = new AprioriAlgorithmSettings();
        //algorithmSettings.setNumberOfTransactions(3);

        AssociationRulesFunctionSettings miningSettings = new AssociationRulesFunctionSettings(logicalData);
        miningSettings.setTransactionIDsArributeName("transactId");
        miningSettings.setItemIDsArributeName("itemId");
        miningSettings.setMinConfidence(0.5);
        miningSettings.setMinSupport(0.5);
        miningSettings.setAlgorithmSettings(algorithmSettings);
   		miningSettings.verify();

        AprioriMiningModel miningModel = buildModel(miningSettings, miningInputStream);

        System.out.println("AssociationRuleSet  \n" + miningModel.getAssociationRuleSet());

        Assert.assertEquals(2, miningModel.getAssociationRuleSet().size());
		// verify first rule
		Assert.assertEquals(true, miningModel.getAssociationRuleSet().contains(
				new AssociationRule(new ItemSet(new Item("3")), new ItemSet(new Item("1")), 0.0, 0.0)));
		Assert.assertEquals(true, miningModel.getAssociationRuleSet().contains(
				new AssociationRule(new ItemSet(new Item("1")), new ItemSet(new Item("3")), 0.0, 0.0)));
	}

	/**
	 * Source data attributes:
	 * 		TID   Itemset
	 * 		 cust_1   - item_1 item_3 item_4 item_2 item_3 item_5
	 * 		 cust_2   - item_1 item_2 item_3 item_5 item_2 item_2 item_5 item_2
	 *  	 cust_3	 -  item_3 item_5 item_2 item_1 item_5 item_4 item_3 item_1 item_5 item_2
	 *  Result large itemset for min support 0.6
	 *      {1,3}; {1,4}; {1,2}; {1,5}; {3,4}; {2,3}; {3,5}; {2,4}; {4,5}; {2,5};
	 *      {1,3,4}; {1,2,3}; {1,3,5}; {1,2,4}; {1,4,5}; {1,2,5}; {2,3,4}; {3,4,5}; {2,3,5}; {2,4,5};
	 *      {1,2,3,4}; {1,3,4,5}; {1,2,3,5}; {1,2,4,5}; {2,3,4,5};
	 *      {1,2,3,4,5};
	 * @throws MiningException
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	@Test	
	public void testCustomTransactData() throws MiningException, FileNotFoundException, UnsupportedEncodingException {

		MiningInputStream miningInputStream = new MiningArffStream("../data/arff/association/custom-transact.arff");
        ELogicalData logicalData = miningInputStream.getLogicalData();

        AprioriAlgorithmSettings algorithmSettings = new AprioriAlgorithmSettings();
        algorithmSettings.setNumberOfTransactions(3);

        AssociationRulesFunctionSettings miningSettings = new AssociationRulesFunctionSettings(logicalData);
        miningSettings.setTransactionIDsArributeName("customerId");
        miningSettings.setItemIDsArributeName("itemId");
        miningSettings.setMinConfidence(0.6);
        miningSettings.setMinSupport(0.6);
        miningSettings.setAlgorithmSettings(algorithmSettings);

        AprioriMiningModel miningModel = buildModel(miningSettings, miningInputStream);

        System.out.println("AssociationRuleSet  \n" + miningModel.getAssociationRuleSet());
        System.out.println("AssociationRuleSet: number of rules = " + miningModel.getAssociationRuleSet().size());
        int l=0;
        for(List<ItemSet> list: miningModel.getLargeItemSetsList()){
        	System.out.println("Level " + l++ + " number of rules " + list.size());
        }

        Assert.assertEquals(75, miningModel.getAssociationRuleSet().size());
	}




}
