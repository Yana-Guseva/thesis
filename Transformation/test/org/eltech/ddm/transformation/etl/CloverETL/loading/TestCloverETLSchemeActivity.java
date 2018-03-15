package org.eltech.ddm.transformation.etl.CloverETL.loading;

import static org.junit.Assert.assertEquals;

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
 * Test for CloverETLSchemeActivity with Apriori algorithm,
 *  data in CloverETL loading from remote source - dropbox.
 * 
 * @author SemenchenkoA
 *
 */
public class TestCloverETLSchemeActivity implements MiningETLArrayStreamInformer{
	private MiningInputStream miningInputStream = null;
	private CloverETLSchemeActivity cloverSchemeActivity = null;
	
	/**
	 * From GUI interface needs:
	 * 1) Name meta data; //relation
	 * 2) Fields names and types; // transactId (String), itemId (String)
	 * 3) Fields delimeter; // \t
	 * 4) Records delimeter; // \n
	 * 5) Batch size; // 15
	 * 6) Scheme path; // ..\\data\\grf\\tran_small_remote.grf
	 * 7) Last component in graph ID; // XLSDATA_READER
	 * 8) MiningETLArrayStreamPrepared needs forming from projects and algorithm settings.
	 * @throws Exception
	 */
	@Test
	public void testCloverETLSchemeActivity() throws Exception{		
		cloverSchemeActivity = new CloverETLSchemeActivity();
	
		cloverSchemeActivity.setMetaData("relation","\t","\n");
		
		//field types take from map getDataFieldsTypes()
		cloverSchemeActivity.addMetaDataField("transactId", MiningTransformationMetadata.FIELD_TYPE_STRING);
		cloverSchemeActivity.addMetaDataField("itemId", MiningTransformationMetadata.FIELD_TYPE_STRING);
		cloverSchemeActivity.setBatchSize(15);
		cloverSchemeActivity.setGraphPath("..\\data\\grf\\tran_small_remote.grf");
		cloverSchemeActivity.setLastComponentID("XLSDATA_READER");
		
		// set informer and run activity
		cloverSchemeActivity.runActivity(this); 
		
		assertEquals("testCloverETLSchemeActivity unsuccess", cloverSchemeActivity.getCloverActivityDis().getResult().get(), Result.FINISHED_OK);
	}	

	@Override
	public void MiningETLArrayStreamPrepared(ELogicalData aLogicalData,
			MiningETLArrayStreamDataProvider aDataProvider) {
		try{
			miningInputStream = new MiningETLArrayStream(aLogicalData, aDataProvider);
			((MiningETLArrayStream)miningInputStream).setBatchSize(cloverSchemeActivity.getBatchSize());
			((MiningETLArrayStream)miningInputStream).setMiningArrayLength(15);
			
			System.out.println("AprioriAlgorithm");
	
			Date start = new Date();
	
			ELogicalData logicalData = miningInputStream.getLogicalData();
	
		    AprioriAlgorithmSettings algorithmSettings = new AprioriAlgorithmSettings();
		    algorithmSettings.setNumberOfTransactions(4);
	
		    AssociationRulesFunctionSettings miningSettings = new AssociationRulesFunctionSettings(logicalData);
		    miningSettings.setTransactionIDsArributeName("transactId");
		    miningSettings.setItemIDsArributeName("itemId");
		    
		    miningSettings.setMinConfidence(0.6);
		    miningSettings.setMinSupport(0.6);
		    miningSettings.setAlgorithmSettings(algorithmSettings);
	
		    AprioriAlgorithm aprioriAlgorithm = new AprioriAlgorithm(miningSettings);
		    AprioriMiningModel miningModel = (AprioriMiningModel) aprioriAlgorithm.buildModel(miningInputStream);
			miningModel.getTransactionList().print();		
		   
			Date end = new Date();
			long time = (end.getTime() - start.getTime());
			System.out.println("Total time = " +  time + "мс \nAssociationRuleSet:  \n" + miningModel.getAssociationRuleSet());	
		} catch (MiningException e) {
			e.printStackTrace();
		}
	}
}
