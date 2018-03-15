package org.eltech.ddm.associationrules.apriori;

import static org.junit.Assert.*;

import org.eltech.ddm.handlers.ExecutionSettings;
import org.eltech.ddm.handlers.thread.MultiThreadedExecutionEnvironment;
import org.eltech.ddm.inputdata.DataSplitType;
import org.eltech.ddm.inputdata.MiningArrayStream;
import org.eltech.ddm.inputdata.file.MiningArffStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.DataProcessingStrategy;
import org.eltech.ddm.miningcore.miningfunctionsettings.MiningModelProcessingStrategy;
import org.eltech.ddm.miningcore.miningtask.EMiningBuildTask;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

//@Ignore
public class ApriorVectorsParallelAlgorithmLoadTest extends AprioriLoadlTest {

	private final int NUMBER_HANDLERS = 2;
	protected String dataSets[] = {"T_200", "T_2000", "T_20000", "I_5", "I_10", "I_15", "I_10_20", "I_10_30", "I_10_50"};
	

	@Test
	public void test_T_200() throws MiningException {
		String dataSet = "T_200";
		System.out.println("----- AprioriVectorsParallelAlgorithm (" + NUMBER_HANDLERS + ") for data set " + dataSet + " -------");

		setSettings(dataSet);
	}
	
	@Test
	public void test_T_2000() throws MiningException {
		String dataSet = "T_2000";
		System.out.println("----- AprioriVectorsParallelAlgorithm (" + NUMBER_HANDLERS + ") for data set " + dataSet + " -------");

		setSettings(dataSet);
	}

	@Test
	public void test_T_20000() throws MiningException {
		String dataSet = "T_20000";
		System.out.println("----- AprioriVectorsParallelAlgorithm (" + NUMBER_HANDLERS + ") for data set " + dataSet + " -------");

		setSettings(dataSet);
	}

	@Test
	public void test_I_5() throws MiningException {
		String dataSet = "I_5";
		System.out.println("----- AprioriVectorsParallelAlgorithm (" + NUMBER_HANDLERS + ") for data set " + dataSet + " -------");

		setSettings(dataSet);
	}


	@Test
	public void test_I_10() throws MiningException {
		String dataSet = "I_10";
		System.out.println("----- AprioriVectorsParallelAlgorithm (" + NUMBER_HANDLERS + ") for data set " + dataSet + " -------");

		setSettings(dataSet);
	}
	
	@Test
	public void test_I_15() throws MiningException {
		String dataSet = "I_15";
		System.out.println("----- AprioriVectorsParallelAlgorithm (" + NUMBER_HANDLERS + ") for data set " + dataSet + " -------");

		setSettings(dataSet);
	}


	@Test
	public void test_I_10_20() throws MiningException {
		String dataSet = "I_10_20";
		System.out.println("----- AprioriVectorsParallelAlgorithm (" + NUMBER_HANDLERS + ") for data set " + dataSet + " -------");

		setSettings(dataSet);
	}


	@Test
	public void test_I_10_30() throws MiningException {
		String dataSet = "I_10_30";
		System.out.println("----- AprioriVectorsParallelAlgorithm (" + NUMBER_HANDLERS + ") for data set " + dataSet + " -------");

		setSettings(dataSet);
	}

	
	@Test
	public void test_I_10_50() throws MiningException {
		String dataSet = "I_10_50";
		System.out.println("----- AprioriVectorsParallelAlgorithm (" + NUMBER_HANDLERS + ") for data set " + dataSet + " -------");

		setSettings(dataSet);
	}

	
	@After
	public void tearDown() {
		
		
		try{
				miningSettings.getAlgorithmSettings().setDataSplitType(DataSplitType.block);
				miningSettings.getAlgorithmSettings().setDataProcessingStrategy(DataProcessingStrategy.SeparatedDataSet);
				miningSettings.getAlgorithmSettings().setModelProcessingStrategy(MiningModelProcessingStrategy.SeparatedMiningModel);
				
				ExecutionSettings executionSettings = new ExecutionSettings();
				//executionSettings.setNumberHandlers(NUMBER_HANDLERS);
				 miningSettings.getAlgorithmSettings().setNumberHandlers(NUMBER_HANDLERS);
	
				MultiThreadedExecutionEnvironment environment = new MultiThreadedExecutionEnvironment(executionSettings); 
				
				AprioriVectorsParallelAlgorithm algorithm = new AprioriVectorsParallelAlgorithm(miningSettings);
	
				EMiningBuildTask buildTask = new EMiningBuildTask();
				buildTask.setInputStream(inputData);
				buildTask.setMiningAlgorithm(algorithm); 
				buildTask.setMiningSettings(miningSettings);
				buildTask.setExecutionEnvironment(environment);
				System.out.println("Start algorithm");
				miningModel = (AprioriMiningModel) buildTask.execute();
				System.out.println("Finish algorithm");
				
				verifyModel();
		}
		catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	
	

}
