package org.eltech.ddm.classification.ruleset.onerule;

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
import org.junit.Test;

public class OneRRulesCycleParallelAlgorithmLoadTest extends ClassificationLoadlTest {
	private final int NUMBER_HANDLERS = 2;
	protected MiningAlgorithm algorithm;


	@Test
	public void test1R(){
		ThreadSettings executionSettings = new ThreadSettings();
		executionSettings.setNumberHandlers(NUMBER_HANDLERS);
		executionSettings.setMemoryType(MemoryType.shared);

		MultiThreadedExecutionEnvironment environment;
		try {
			environment = new MultiThreadedExecutionEnvironment(executionSettings);


			System.out.println("----- 1RRulesCycleParallelAlgorithm  -------");

			for(int i=0; i < dataSets.length; i++){
				setSettings(i);
				miningSettings.setAlgorithmSettings(new EMiningAlgorithmSettings());
				miningSettings.getAlgorithmSettings().setDataSplitType(DataSplitType.block);
				miningSettings.getAlgorithmSettings().setDataProcessingStrategy(DataProcessingStrategy.SingleDataSet);
				miningSettings.getAlgorithmSettings().setModelProcessingStrategy(MiningModelProcessingStrategy.SeparatedMiningModel);

		        algorithm = new OneRuleAttributesCycleParallelAlgorithm(miningSettings);
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
