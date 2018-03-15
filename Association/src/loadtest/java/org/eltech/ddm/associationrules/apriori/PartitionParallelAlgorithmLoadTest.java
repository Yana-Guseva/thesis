package org.eltech.ddm.associationrules.apriori;

import static org.junit.Assert.*;

import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.apriori.partition.PartitionAssRulesParallelAlgorithm;
import org.eltech.ddm.associationrules.apriori.partition.PartitionLargeSetsParallelAlgorithm;
import org.eltech.ddm.associationrules.apriori.partition.PartitionTransactionsParallelAlgorithm;
import org.eltech.ddm.associationrules.apriori.partition.PartitionVectorsLargeSetsRulesParallelAlgorithm;
import org.eltech.ddm.associationrules.apriori.partition.PartitionVectorsParallelAlgorithm;
import org.eltech.ddm.handlers.ExecutionSettings;
import org.eltech.ddm.handlers.thread.MultiThreadedExecutionEnvironment;
import org.eltech.ddm.inputdata.DataSplitType;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.DataProcessingStrategy;
import org.eltech.ddm.miningcore.miningfunctionsettings.MiningModelProcessingStrategy;
import org.eltech.ddm.miningcore.miningtask.EMiningBuildTask;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

//@Ignore
public class PartitionParallelAlgorithmLoadTest extends AprioriLoadlTest {

	private final int NUMBER_HANDLERS = 2;
	
	
	@Test
	public void testPartitionVectorsParallelAlgorithm() throws MiningException {
		System.out.println("----- PartitionVectorsParallelAlgorithm (" + NUMBER_HANDLERS + ") -------");
		
		try{
			for(int i=0; i < dataSets.length; i++){
				setSettings(dataSets[i]);
			
				miningSettings.getAlgorithmSettings().setDataSplitType(DataSplitType.block);
				miningSettings.getAlgorithmSettings().setDataProcessingStrategy(DataProcessingStrategy.SeparatedDataSet);
				miningSettings.getAlgorithmSettings().setModelProcessingStrategy(MiningModelProcessingStrategy.SingleMiningModel);
				
				ExecutionSettings executionSettings = new ExecutionSettings();
				executionSettings.setNumberHandlers(NUMBER_HANDLERS);

				MultiThreadedExecutionEnvironment environment = new MultiThreadedExecutionEnvironment(executionSettings); 
				
				PartitionVectorsParallelAlgorithm algorithm = new PartitionVectorsParallelAlgorithm(miningSettings);

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
	public void testPartitionTransactionsParallelAlgorithm() throws MiningException {
		System.out.println("----- PartitionTransactionsParallelAlgorithm (" + NUMBER_HANDLERS + ")-------");
		
		try{
			for(int i=0; i < dataSets.length; i++){
				setSettings(dataSets[i]);
			
				miningSettings.getAlgorithmSettings().setDataSplitType(DataSplitType.block);
				miningSettings.getAlgorithmSettings().setDataProcessingStrategy(DataProcessingStrategy.SeparatedDataSet);
				miningSettings.getAlgorithmSettings().setModelProcessingStrategy(MiningModelProcessingStrategy.SeparatedMiningModel);

				
				ExecutionSettings executionSettings = new ExecutionSettings();
				executionSettings.setNumberHandlers(NUMBER_HANDLERS);

				MultiThreadedExecutionEnvironment environment = new MultiThreadedExecutionEnvironment(executionSettings); 
				
				PartitionTransactionsParallelAlgorithm algorithm = new PartitionTransactionsParallelAlgorithm(miningSettings);

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
	public void testPartitionLargeSetsParallelAlgorithm() throws MiningException {
		System.out.println("----- PartitionLargeSetsParallelAlgorithm (" + NUMBER_HANDLERS + ")-------");
		
		try{
			for(int i=0; i < dataSets.length; i++){
				setSettings(dataSets[i]);
			
				miningSettings.getAlgorithmSettings().setDataSplitType(DataSplitType.block);
				miningSettings.getAlgorithmSettings().setDataProcessingStrategy(DataProcessingStrategy.SeparatedDataSet);
				miningSettings.getAlgorithmSettings().setModelProcessingStrategy(MiningModelProcessingStrategy.SeparatedMiningModel);

				
				ExecutionSettings executionSettings = new ExecutionSettings();
				executionSettings.setNumberHandlers(NUMBER_HANDLERS);

				MultiThreadedExecutionEnvironment environment = new MultiThreadedExecutionEnvironment(executionSettings); 
				
				PartitionLargeSetsParallelAlgorithm algorithm = new PartitionLargeSetsParallelAlgorithm(miningSettings);

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
	public void testPartitionAssRulesParallelAlgorithm() throws MiningException {
		System.out.println("----- PartitionAssRulesParallelAlgorithm (" + NUMBER_HANDLERS + ")-------");
		
		try{
			for(int i=0; i < dataSets.length; i++){
				setSettings(dataSets[i]);
			
				miningSettings.getAlgorithmSettings().setDataSplitType(DataSplitType.block);
				miningSettings.getAlgorithmSettings().setDataProcessingStrategy(DataProcessingStrategy.SeparatedDataSet);
				miningSettings.getAlgorithmSettings().setModelProcessingStrategy(MiningModelProcessingStrategy.SeparatedMiningModel);

				
				ExecutionSettings executionSettings = new ExecutionSettings();
				executionSettings.setNumberHandlers(NUMBER_HANDLERS);

				MultiThreadedExecutionEnvironment environment = new MultiThreadedExecutionEnvironment(executionSettings); 
				
				PartitionAssRulesParallelAlgorithm algorithm = new PartitionAssRulesParallelAlgorithm(miningSettings);

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
	public void testPartitionVectorsLargeSetsRulesParallelAlgorithm() throws MiningException {
		System.out.println("----- PartitionVectorsLargeSetsRulesParallelAlgorithm (" + NUMBER_HANDLERS + ")-------");
		
		try{
			for(int i=0; i < dataSets.length; i++){
				setSettings(dataSets[i]);
			
				miningSettings.getAlgorithmSettings().setDataSplitType(DataSplitType.block);
				miningSettings.getAlgorithmSettings().setDataProcessingStrategy(DataProcessingStrategy.SeparatedDataSet);
				miningSettings.getAlgorithmSettings().setModelProcessingStrategy(MiningModelProcessingStrategy.SeparatedMiningModel);

				
				ExecutionSettings executionSettings = new ExecutionSettings();
				executionSettings.setNumberHandlers(NUMBER_HANDLERS);

				MultiThreadedExecutionEnvironment environment = new MultiThreadedExecutionEnvironment(executionSettings); 
				
				PartitionVectorsLargeSetsRulesParallelAlgorithm algorithm = new PartitionVectorsLargeSetsRulesParallelAlgorithm(miningSettings);

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
