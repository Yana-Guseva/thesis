package org.eltech.ddm.transformation.etl.CloverETL.loading;

import static org.junit.Assert.*;

import java.util.Date;

import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.apriori.AprioriAlgorithm;
import org.eltech.ddm.associationrules.apriori.AprioriAlgorithmSettings;
import org.eltech.ddm.associationrules.apriori.AprioriMiningModel;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.transformation.MiningETLArrayStreamDataProvider;
import org.eltech.ddm.transformation.MiningETLArrayStreamInformer;
import org.eltech.ddm.transformation.MiningTransformationMetadata;
import org.jetel.graph.Result;
import org.junit.Test;


/**
 * Test for CloverETLSimpleActivityFactory
 * 
 * @author SemenchenkoA
 *
 */
public class TestSimpleCloverActivityFactory implements
		MiningETLArrayStreamInformer {
	private CloverETLSimpleActivityFactory cloverActivity = null;

	/**
	 * Test scheme using
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSchemeLoad() throws Exception {
		cloverActivity = new CloverETLSimpleActivityFactory(
				"..\\data\\grf\\tran_small_remote.grf");

		cloverActivity.setMetaData("relation", "\t", "\n");

		cloverActivity.addMetaDataField("transactId",
				MiningTransformationMetadata.FIELD_TYPE_STRING);
		cloverActivity.addMetaDataField("itemId",
				MiningTransformationMetadata.FIELD_TYPE_STRING);

		cloverActivity.setSettingForActivity(15, "XLSDATA_READER");

		cloverActivity.runActivityFactory(this);
		
		assertEquals("testSchemeLoad unsuccess", ((CloverETLSchemeActivity)cloverActivity.getActivity()).getCloverActivityDis().getResult().get(), Result.FINISHED_OK);
	}

	/**
	 * Test simple source using
	 * 
	 * @throws Exception
	 */
	@Test
	public void testExcelSourceLoad() throws Exception{
		//1.
		cloverActivity = new CloverETLSimpleActivityFactory("https://dl-doc.dropbox.com/s/ybcg5ohd51ytl8c/apriori1.xlsx"+";"+
		"https://dl-doc.dropbox.com/s/rzvkyfoppstu593/apriori2.xlsx");
		//2.
		cloverActivity.setMetaData("relation","\t","\n");
		cloverActivity.addMetaDataField("transactId", MiningTransformationMetadata.FIELD_TYPE_STRING);
		cloverActivity.addMetaDataField("itemId", MiningTransformationMetadata.FIELD_TYPE_STRING);
		//3.
		cloverActivity.setSettingForActivity(15, "");
		//4.
		cloverActivity.runActivityFactory(this);
		
		assertEquals("testExcelSourceLoad unsuccess", cloverActivity.getActivity().getResult().get(), Result.FINISHED_OK);
	}
	
	@Override
	public void MiningETLArrayStreamPrepared(ELogicalData aLogicalData,
			MiningETLArrayStreamDataProvider aDataProvider) {
		try {
			cloverActivity.setMiningInputStream((MiningInputStream) new MiningETLArrayStream(aLogicalData,
					aDataProvider));
			((MiningETLArrayStream) cloverActivity.getMiningInputStream())
					.setBatchSize(cloverActivity.getBatchSize());
			((MiningETLArrayStream)cloverActivity.getMiningInputStream()).setMiningArrayLength(15);

			System.out.println("AprioriAlgorithm");

			Date start = new Date();

			ELogicalData logicalData = cloverActivity.getMiningInputStream().getLogicalData();

			AprioriAlgorithmSettings algorithmSettings = new AprioriAlgorithmSettings();
			algorithmSettings.setNumberOfTransactions(4);

			AssociationRulesFunctionSettings miningSettings = new AssociationRulesFunctionSettings(
					logicalData);
			miningSettings.setTransactionIDsArributeName("transactId");
			miningSettings.setItemIDsArributeName("itemId");

			miningSettings.setMinConfidence(0.6);
			miningSettings.setMinSupport(0.6);
			miningSettings.setAlgorithmSettings(algorithmSettings);

			AprioriAlgorithm aprioriAlgorithm = new AprioriAlgorithm(
					miningSettings);
			AprioriMiningModel miningModel = (AprioriMiningModel) aprioriAlgorithm
					.buildModel(cloverActivity.getMiningInputStream());
			miningModel.getTransactionList().print();

			Date end = new Date();
			long time = (end.getTime() - start.getTime());
			System.out.println("Total time = " + time
					+ "мс \nAssociationRuleSet:  \n"
					+ miningModel.getAssociationRuleSet());
		} catch (MiningException e) {
			e.printStackTrace();
		}
	}
}
