package org.eltech.ddm.clustering;

import org.eltech.ddm.inputdata.MiningArrayStream;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.file.MiningArffStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.junit.Assert;

import static org.junit.Assert.assertTrue;

public abstract class ClusteringLoadlTest extends ClusteringMiningModelTest{

	protected String dataSets[] = {
//			"W1000A10C5", 
//			"W1000A100C5", 
//			"W1000A100C10", 
//			"W1000A100C50", 
//			"W1000A100C100",  
//			"W10000A10C5", 
//			"W30000A10C5", 
//			"W50000A10C5", 
//			"W100000A10C5", 
//			"W1000A100C5", 
//			"W1000A300C5", 
//			"W1000A500C5", 
//			"W1000A1000C5"

			"W050tA100C10",
			"W100tA100C10",
			"W200tA100C10",
			"W300tA100C10",
			"W400tA100C10",
			"W500tA100C10",
			"W600tA100C10",
			"W700tA100C10",
			"W800tA100C10",
			"W900tA100C10",
			"W1_000tA100C10"
			}; 
	
	protected ClusteringFunctionSettings miningSettings;
	protected MiningInputStream inputData;

	protected void setSettings(int index) throws  MiningException{
		
		System.out.println(dataSets[index]);
		inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSets[index] + ".arff"));
		
        ELogicalData logicalData = inputData.getLogicalData();
        miningSettings = new ClusteringFunctionSettings(logicalData);
   		miningSettings.verify();

	}
	

	protected void verifyModel() throws MiningException {
        Assert.assertNotNull(model);
        showClusters(model);
	}

}
