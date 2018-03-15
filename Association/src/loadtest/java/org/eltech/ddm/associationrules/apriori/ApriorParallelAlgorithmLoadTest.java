package org.eltech.ddm.associationrules.apriori;

import static org.junit.Assert.*;

import org.eltech.ddm.handlers.ExecutionSettings;
import org.eltech.ddm.handlers.thread.MultiThreadedExecutionEnvironment;
import org.eltech.ddm.inputdata.DataSplitType;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.DataProcessingStrategy;
import org.eltech.ddm.miningcore.miningfunctionsettings.MiningModelProcessingStrategy;
import org.eltech.ddm.miningcore.miningtask.EMiningBuildTask;
import org.junit.Test;

//@Ignore
public class ApriorParallelAlgorithmLoadTest extends AprioriLoadlTest {

	private final int NUMBER_HANDLERS = 1;
	
	@Test
	public void testAprioriVectorsParallelAlgorithm() throws MiningException {
		System.out.println("----- AprioriVectorsParallelAlgorithm (" + NUMBER_HANDLERS + ")-------");
		
		try{
			for(int i=0; i < dataSets.length; i++){
				setSettings(dataSets[i]);
			
				miningSettings.getAlgorithmSettings().setDataSplitType(DataSplitType.block);
				miningSettings.getAlgorithmSettings().setDataProcessingStrategy(DataProcessingStrategy.SeparatedDataSet);
				miningSettings.getAlgorithmSettings().setModelProcessingStrategy(MiningModelProcessingStrategy.SingleMiningModel);
				
				ExecutionSettings executionSettings = new ExecutionSettings();
				executionSettings.setNumberHandlers(NUMBER_HANDLERS);
	
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
		}
		catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	

	@Test
	public void testAprioriLargeSetsParallelAlgorithm()  {
		System.out.println("----- AprioriLargeSetsParallelAlgorithm (" + NUMBER_HANDLERS + ")-------");
		
		try{
			for(int i=0; i < dataSets.length; i++){
				setSettings(dataSets[i]);
			
				miningSettings.getAlgorithmSettings().setDataSplitType(DataSplitType.block);
				miningSettings.getAlgorithmSettings().setDataProcessingStrategy(DataProcessingStrategy.SeparatedDataSet);
				miningSettings.getAlgorithmSettings().setModelProcessingStrategy(MiningModelProcessingStrategy.SeparatedMiningModel);
				
				ExecutionSettings executionSettings = new ExecutionSettings();
				executionSettings.setNumberHandlers(NUMBER_HANDLERS);
	
				MultiThreadedExecutionEnvironment environment = new MultiThreadedExecutionEnvironment(executionSettings); 
				
				AprioriLargeSetsParallelAlgorithm algorithm = new AprioriLargeSetsParallelAlgorithm(miningSettings);
	
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
		}
		catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testAprioriAssRulesParallelAlgorithm()  {
		System.out.println("----- AprioriAssRulesParallelAlgorithm (" + NUMBER_HANDLERS + ")-------");
		
		try{
			for(int i=0; i < dataSets.length; i++){
				setSettings(dataSets[i]);
			
				miningSettings.getAlgorithmSettings().setDataSplitType(DataSplitType.block);
				miningSettings.getAlgorithmSettings().setDataProcessingStrategy(DataProcessingStrategy.SeparatedDataSet);
				miningSettings.getAlgorithmSettings().setModelProcessingStrategy(MiningModelProcessingStrategy.SeparatedMiningModel);
				
				ExecutionSettings executionSettings = new ExecutionSettings();
				executionSettings.setNumberHandlers(NUMBER_HANDLERS);
	
				MultiThreadedExecutionEnvironment environment = new MultiThreadedExecutionEnvironment(executionSettings); 
				
				AprioriAssRulesParallelAlgorithm algorithm = new AprioriAssRulesParallelAlgorithm(miningSettings);
	
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
		}
		catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testAprioriVectorsLargeSetsRulesParallelAlgorithm()  {
		System.out.println("----- AprioriVectorsLargeSetsRulesParallelAlgorithm (" + NUMBER_HANDLERS + ")-------");
		
		try{
			for(int i=0; i < dataSets.length; i++){
				setSettings(dataSets[i]);
			
				miningSettings.getAlgorithmSettings().setDataSplitType(DataSplitType.block);
				miningSettings.getAlgorithmSettings().setDataProcessingStrategy(DataProcessingStrategy.SeparatedDataSet);
				miningSettings.getAlgorithmSettings().setModelProcessingStrategy(MiningModelProcessingStrategy.SeparatedMiningModel);
				
				ExecutionSettings executionSettings = new ExecutionSettings();
				executionSettings.setNumberHandlers(NUMBER_HANDLERS);
	
				MultiThreadedExecutionEnvironment environment = new MultiThreadedExecutionEnvironment(executionSettings); 
				
				AprioriVectorsLargeSetsRulesParallelAlgorithm algorithm = new AprioriVectorsLargeSetsRulesParallelAlgorithm(miningSettings);
	
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
		}
		catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	

}
