package org.eltech.ddm.classification.naivebayes;

import static org.junit.Assert.*;

import org.eltech.ddm.classification.ClassificationLoadlTest;
import org.eltech.ddm.classification.ClassificationMiningModel;
import org.eltech.ddm.handlers.ExecutionSettings;
import org.eltech.ddm.handlers.thread.MemoryType;
import org.eltech.ddm.handlers.thread.MultiThreadedExecutionEnvironment;
import org.eltech.ddm.handlers.thread.ThreadSettings;
import org.eltech.ddm.inputdata.DataSplitType;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
import org.eltech.ddm.miningcore.miningfunctionsettings.DataProcessingStrategy;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningAlgorithmSettings;
import org.eltech.ddm.miningcore.miningfunctionsettings.MiningModelProcessingStrategy;
import org.eltech.ddm.miningcore.miningtask.EMiningBuildTask;
import org.junit.Ignore;
import org.junit.Test;

public class NaiveBayesVectorsCycleParallelAlgorithmLoadTest extends ClassificationLoadlTest {
	private final int NUMBER_HANDLERS = 4;
	protected MiningAlgorithm algorithm;


	@Test
	public void testSharedMDSM(){
		ThreadSettings executionSettings = new ThreadSettings();
		executionSettings.setNumberHandlers(NUMBER_HANDLERS);
		executionSettings.setMemoryType(MemoryType.shared);

		MultiThreadedExecutionEnvironment environment;
		try {
			environment = new MultiThreadedExecutionEnvironment(executionSettings);


			System.out.println("----- NaiveBayesVectorsCycleParallelAlgorithm (shared MDSM) -------");

			for(int i=0; i < dataSets.length; i++){
				setSettings(i);
				miningSettings.setAlgorithmSettings(new EMiningAlgorithmSettings());
				miningSettings.getAlgorithmSettings().setDataSplitType(DataSplitType.block);
				miningSettings.getAlgorithmSettings().setDataProcessingStrategy(DataProcessingStrategy.SeparatedDataSet);
				miningSettings.getAlgorithmSettings().setModelProcessingStrategy(MiningModelProcessingStrategy.SingleMiningModel);

		        algorithm = new NaiveBayesVectorsCycleParallelAlgorithm(miningSettings);
				EMiningBuildTask buildTask = new EMiningBuildTask();
				buildTask.setInputStream(inputData);
				buildTask.setMiningAlgorithm(algorithm);
				buildTask.setMiningSettings(miningSettings);
				buildTask.setExecutionEnvironment(environment);
				System.out.println("Start algorithm");
				miningModel = (ClassificationMiningModel) buildTask.execute();
				System.out.println("Finish algorithm");


				verifyModel();

			}

		} catch (MiningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}

	@Ignore // this strategy equals distributed SM
	@Test
	public void testSharedMDMM(){
		ThreadSettings executionSettings = new ThreadSettings();
		executionSettings.setNumberHandlers(NUMBER_HANDLERS);
		executionSettings.setMemoryType(MemoryType.shared);

		MultiThreadedExecutionEnvironment environment;
		try {
			environment = new MultiThreadedExecutionEnvironment(executionSettings);


			System.out.println("----- NaiveBayesVectorsCycleParallelAlgorithm (shared MDMM) -------");

			for(int i=0; i < dataSets.length; i++){
				setSettings(i);
				miningSettings.setAlgorithmSettings(new EMiningAlgorithmSettings());
				miningSettings.getAlgorithmSettings().setDataSplitType(DataSplitType.block);
				miningSettings.getAlgorithmSettings().setDataProcessingStrategy(DataProcessingStrategy.SeparatedDataSet);
				miningSettings.getAlgorithmSettings().setModelProcessingStrategy(MiningModelProcessingStrategy.SeparatedMiningModel);

		        algorithm = new NaiveBayesVectorsCycleParallelAlgorithm(miningSettings);
				EMiningBuildTask buildTask = new EMiningBuildTask();
				buildTask.setInputStream(inputData);
				buildTask.setMiningAlgorithm(algorithm);
				buildTask.setMiningSettings(miningSettings);
				buildTask.setExecutionEnvironment(environment);
				System.out.println("Start algorithm");
				miningModel = (ClassificationMiningModel) buildTask.execute();
				System.out.println("Finish algorithm");


				verifyModel();

			}

		} catch (MiningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}

	@Ignore // this strategy equals distributed SM but executed join that give model error (data duplicate)
	@Test
	public void testSharedSDMM(){
		ThreadSettings executionSettings = new ThreadSettings();
		executionSettings.setNumberHandlers(NUMBER_HANDLERS);
		executionSettings.setMemoryType(MemoryType.shared);

		MultiThreadedExecutionEnvironment environment;
		try {
			environment = new MultiThreadedExecutionEnvironment(executionSettings);


			System.out.println("----- NaiveBayesVectorsCycleParallelAlgorithm (shared SDMM) -------");

			for(int i=0; i < dataSets.length; i++){
				setSettings(i);
				miningSettings.setAlgorithmSettings(new EMiningAlgorithmSettings());
				miningSettings.getAlgorithmSettings().setDataSplitType(DataSplitType.block);
				miningSettings.getAlgorithmSettings().setDataProcessingStrategy(DataProcessingStrategy.SingleDataSet);
				miningSettings.getAlgorithmSettings().setModelProcessingStrategy(MiningModelProcessingStrategy.SeparatedMiningModel);

		        algorithm = new NaiveBayesVectorsCycleParallelAlgorithm(miningSettings);
				EMiningBuildTask buildTask = new EMiningBuildTask();
				buildTask.setInputStream(inputData);
				buildTask.setMiningAlgorithm(algorithm);
				buildTask.setMiningSettings(miningSettings);
				buildTask.setExecutionEnvironment(environment);
				System.out.println("Start algorithm");
				miningModel = (ClassificationMiningModel) buildTask.execute();
				System.out.println("Finish algorithm");


				verifyModel();

			}

		} catch (MiningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}

	@Ignore
	@Test
	public void testDistrSDSM(){
		ThreadSettings executionSettings = new ThreadSettings();
		executionSettings.setNumberHandlers(NUMBER_HANDLERS);
		executionSettings.setMemoryType(MemoryType.distributed);

		MultiThreadedExecutionEnvironment environment;
		try {
			environment = new MultiThreadedExecutionEnvironment(executionSettings);


			System.out.println("----- NaiveBayesVectorsCycleParallelAlgorithm (distr SDSM) -------");

			for(int i=0; i < dataSets.length; i++){
				setSettings(i);
				miningSettings.setAlgorithmSettings(new EMiningAlgorithmSettings());
				miningSettings.getAlgorithmSettings().setDataSplitType(DataSplitType.block);
				miningSettings.getAlgorithmSettings().setDataProcessingStrategy(DataProcessingStrategy.SingleDataSet);
				miningSettings.getAlgorithmSettings().setModelProcessingStrategy(MiningModelProcessingStrategy.SingleMiningModel);

		        algorithm = new NaiveBayesVectorsCycleParallelAlgorithm(miningSettings);
				EMiningBuildTask buildTask = new EMiningBuildTask();
				buildTask.setInputStream(inputData);
				buildTask.setMiningAlgorithm(algorithm);
				buildTask.setMiningSettings(miningSettings);
				buildTask.setExecutionEnvironment(environment);
				System.out.println("Start algorithm");
				miningModel = (ClassificationMiningModel) buildTask.execute();
				System.out.println("Finish algorithm");


				verifyModel();

			}

		} catch (MiningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}


	@Test
	public void testDistrMDSM(){
		ThreadSettings executionSettings = new ThreadSettings();
		executionSettings.setNumberHandlers(NUMBER_HANDLERS);
		executionSettings.setMemoryType(MemoryType.distributed);

		MultiThreadedExecutionEnvironment environment;
		try {
			environment = new MultiThreadedExecutionEnvironment(executionSettings);


			System.out.println("----- NaiveBayesVectorsCycleParallelAlgorithm (distr MDSM) -------");

			for(int i=0; i < dataSets.length; i++){
				setSettings(i);
				miningSettings.setAlgorithmSettings(new EMiningAlgorithmSettings());
				miningSettings.getAlgorithmSettings().setDataSplitType(DataSplitType.block);
				miningSettings.getAlgorithmSettings().setDataProcessingStrategy(DataProcessingStrategy.SeparatedDataSet);
				miningSettings.getAlgorithmSettings().setModelProcessingStrategy(MiningModelProcessingStrategy.SingleMiningModel);

		        algorithm = new NaiveBayesVectorsCycleParallelAlgorithm(miningSettings);
				EMiningBuildTask buildTask = new EMiningBuildTask();
				buildTask.setInputStream(inputData);
				buildTask.setMiningAlgorithm(algorithm);
				buildTask.setMiningSettings(miningSettings);
				buildTask.setExecutionEnvironment(environment);
				System.out.println("Start algorithm");
				miningModel = (ClassificationMiningModel) buildTask.execute();
				System.out.println("Finish algorithm");


				verifyModel();

			}

		} catch (MiningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}

	@Ignore // this strategy equals distributed SM
	@Test
	public void testDistrMDMM(){
		ThreadSettings executionSettings = new ThreadSettings();
		executionSettings.setNumberHandlers(NUMBER_HANDLERS);
		executionSettings.setMemoryType(MemoryType.distributed);

		MultiThreadedExecutionEnvironment environment;
		try {
			environment = new MultiThreadedExecutionEnvironment(executionSettings);


			System.out.println("----- NaiveBayesVectorsCycleParallelAlgorithm (distr MDMM) -------");

			for(int i=0; i < dataSets.length; i++){
				setSettings(i);
				miningSettings.setAlgorithmSettings(new EMiningAlgorithmSettings());
				miningSettings.getAlgorithmSettings().setDataSplitType(DataSplitType.block);
				miningSettings.getAlgorithmSettings().setDataProcessingStrategy(DataProcessingStrategy.SeparatedDataSet);
				miningSettings.getAlgorithmSettings().setModelProcessingStrategy(MiningModelProcessingStrategy.SeparatedMiningModel);

		        algorithm = new NaiveBayesVectorsCycleParallelAlgorithm(miningSettings);
				EMiningBuildTask buildTask = new EMiningBuildTask();
				buildTask.setInputStream(inputData);
				buildTask.setMiningAlgorithm(algorithm);
				buildTask.setMiningSettings(miningSettings);
				buildTask.setExecutionEnvironment(environment);
				System.out.println("Start algorithm");
				miningModel = (ClassificationMiningModel) buildTask.execute();
				System.out.println("Finish algorithm");


				verifyModel();

			}

		} catch (MiningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}

	@Ignore // this strategy equals distributed SM
	@Test
	public void testDistrSDMM(){
		ThreadSettings executionSettings = new ThreadSettings();
		executionSettings.setNumberHandlers(NUMBER_HANDLERS);
		executionSettings.setMemoryType(MemoryType.distributed);

		MultiThreadedExecutionEnvironment environment;
		try {
			environment = new MultiThreadedExecutionEnvironment(executionSettings);


			System.out.println("----- NaiveBayesVectorsCycleParallelAlgorithm (distr SDMM)-------");

			for(int i=0; i < dataSets.length; i++){
				setSettings(i);
				miningSettings.setAlgorithmSettings(new EMiningAlgorithmSettings());
				miningSettings.getAlgorithmSettings().setDataSplitType(DataSplitType.block);
				miningSettings.getAlgorithmSettings().setDataProcessingStrategy(DataProcessingStrategy.SingleDataSet);
				miningSettings.getAlgorithmSettings().setModelProcessingStrategy(MiningModelProcessingStrategy.SeparatedMiningModel);

		        algorithm = new NaiveBayesVectorsCycleParallelAlgorithm(miningSettings);
				EMiningBuildTask buildTask = new EMiningBuildTask();
				buildTask.setInputStream(inputData);
				buildTask.setMiningAlgorithm(algorithm);
				buildTask.setMiningSettings(miningSettings);
				buildTask.setExecutionEnvironment(environment);
				System.out.println("Start algorithm");
				miningModel = (ClassificationMiningModel) buildTask.execute();
				System.out.println("Finish algorithm");


				verifyModel();

			}

		} catch (MiningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}


}
