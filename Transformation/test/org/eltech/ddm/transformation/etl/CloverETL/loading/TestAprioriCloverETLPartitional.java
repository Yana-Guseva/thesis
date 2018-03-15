package org.eltech.ddm.transformation.etl.CloverETL.loading;


import java.util.Date;

import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.apriori.AprioriAlgorithm;
import org.eltech.ddm.associationrules.apriori.AprioriAlgorithmSettings;
import org.eltech.ddm.associationrules.apriori.AprioriMiningModel;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.transformation.MiningTransformation;
import org.eltech.ddm.transformation.MiningTransformationMetadata;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationActivity;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationMetadata;
import org.eltech.ddm.transformation.etl.CloverETL.transformations.CloverETLOutputMASTransformation;
import org.junit.Test;

/**
 * This test can be deprecated.
 * 
 * @author SemenchenkoA
 *
 */
public class TestAprioriCloverETLPartitional {
	
	@Test
	public void testAprioriAlgorithmTransactSmall() throws Exception {
		
		String graphPath = "..\\data\\grf\\new-graph4.grf";
		String lastComponentID = "XLSDATA_READER";

		CloverETLTransformationActivity cloverActivity = new CloverETLTransformationActivity();

		CloverETLTransformationMetadata meta = (CloverETLTransformationMetadata) cloverActivity
				.createMetadata();

		meta.addFieldDescription("transactId",
				MiningTransformationMetadata.FIELD_TYPE_STRING);
		meta.addFieldDescription("itemId",
				MiningTransformationMetadata.FIELD_TYPE_STRING);
		meta.setParameter(
				MiningTransformationMetadata.METADATA_PROP_FIELD_DELIMETER,
				"\t");
		meta.setParameter(
				MiningTransformationMetadata.METADATA_PROP_RECORD_DELIMETER,
				"\n");

		CloverETLDisplacedTransformationActivity cloverActivityDis = new CloverETLDisplacedTransformationActivity(
				graphPath, lastComponentID, meta);

		CloverETLOutputMASTransformation output = (CloverETLOutputMASTransformation) cloverActivityDis
				.createTransformation(MiningTransformation.TRANSFORMATION_TYPE_OUTPUT_MAS);
		output.setParameter(
				MiningTransformation.TRANSFORMATION_PARAM_DATABATCHSIZE, 14);
		//CloverETLSaveResultInMIS in = new CloverETLSaveResultInMIS();
		CloverETLInputStream in = new CloverETLInputStream();
		output.setInformer(in);
		output.init();

		cloverActivityDis.setWelcomeComponent(output);

		cloverActivityDis.transform();	
		
		System.out.println("AprioriAlgorithm");

		Date start = new Date();
		MiningInputStream miningInputStream = in.miningInputStream;//new MiningArffStream("../data/arff/association/transact_small.arff");
		miningInputStream.open();
		System.out.println(miningInputStream.getVectorsNumber());
		ELogicalData logicalData = miningInputStream.getLogicalData();
		System.out.println(logicalData.toString());
    
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
	}		
}
