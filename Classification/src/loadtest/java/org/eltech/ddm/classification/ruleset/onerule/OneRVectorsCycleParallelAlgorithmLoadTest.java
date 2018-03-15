package org.eltech.ddm.classification.ruleset.onerule;

import static org.junit.Assert.*;

import org.eltech.ddm.classification.ClassificationFunctionSettings;
import org.eltech.ddm.classification.ClassificationLoadlTest;
import org.eltech.ddm.classification.ClassificationMiningModel;
import org.eltech.ddm.handlers.ExecutionSettings;
import org.eltech.ddm.handlers.thread.MemoryType;
import org.eltech.ddm.handlers.thread.MultiThreadedExecutionEnvironment;
import org.eltech.ddm.handlers.thread.ThreadSettings;
import org.eltech.ddm.inputdata.DataSplitType;
import org.eltech.ddm.inputdata.MiningArrayStream;
import org.eltech.ddm.inputdata.file.MiningArffStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningfunctionsettings.DataProcessingStrategy;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningAlgorithmSettings;
import org.eltech.ddm.miningcore.miningfunctionsettings.MiningModelProcessingStrategy;
import org.eltech.ddm.miningcore.miningtask.EMiningBuildTask;
import org.junit.After;
import org.junit.Test;

public class OneRVectorsCycleParallelAlgorithmLoadTest extends ClassificationLoadlTest {
	private final int NUMBER_HANDLERS = 2;
	protected MiningAlgorithm algorithm;


	@Test
	public void test_W050tA100C10() throws MiningException {
		String dataSet = "W050tA100C10";

		System.out.println(dataSet);
		inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}

	@Test
	public void test_W100tA100C10() throws MiningException {
		String dataSet = "W100tA100C10";

		System.out.println(dataSet);
		inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}

	@Test
	public void test_W200tA100C10() throws MiningException {
		String dataSet = "W200tA100C10";

		System.out.println(dataSet);
		inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}

	@Test
	public void test_W300tA100C10() throws MiningException {
		String dataSet = "W300tA100C10";

		System.out.println(dataSet);
		inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}

	@Test
	public void test_W400tA100C10() throws MiningException {
		String dataSet = "W400tA100C10";

		System.out.println(dataSet);
		inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}

	@Test
	public void test_W500tA100C10() throws MiningException {
		String dataSet = "W500tA100C10";

		System.out.println(dataSet);
		inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}

	@Test
	public void test_W600tA100C10() throws MiningException {
		String dataSet = "W600tA100C10";

		System.out.println(dataSet);
		inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}

	@Test
	public void test_W700tA100C10() throws MiningException {
		String dataSet = "W700tA100C10";

		System.out.println(dataSet);
		inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}

	@Test
	public void test_W800tA100C10() throws MiningException {
		String dataSet = "W800tA100C10";

		System.out.println(dataSet);
		inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}

	@Test
	public void test_W900tA100C10() throws MiningException {
		String dataSet = "W900tA100C10";

		System.out.println(dataSet);
		inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}

	@Test
	public void test_W1_000tA100C10() throws MiningException {
		String dataSet = "W1_000tA100C10";

		System.out.println(dataSet);
		inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}


	@After
	public void tearDown() {
		ThreadSettings executionSettings = new ThreadSettings();
		executionSettings.setNumberHandlers(NUMBER_HANDLERS);
		executionSettings.setMemoryType(MemoryType.shared);

		MultiThreadedExecutionEnvironment environment;
		try {
			environment = new MultiThreadedExecutionEnvironment(executionSettings);

			ELogicalData logicalData = inputData.getLogicalData();
			//System.out.println("Number of vectors = " + inputData.getVectorsNumber());

	        miningSettings = new ClassificationFunctionSettings(logicalData);
			miningSettings.setTarget(logicalData.getAttribute("target"));
	   		miningSettings.verify();

			miningSettings.setAlgorithmSettings(new EMiningAlgorithmSettings());
			miningSettings.getAlgorithmSettings().setDataSplitType(DataSplitType.block);
			miningSettings.getAlgorithmSettings().setDataProcessingStrategy(DataProcessingStrategy.SeparatedDataSet);
			miningSettings.getAlgorithmSettings().setModelProcessingStrategy(MiningModelProcessingStrategy.SingleMiningModel);
			miningSettings.getAlgorithmSettings().setNumberHandlers(NUMBER_HANDLERS);

	        algorithm = new OneRuleVectorsCycleParallelAlgorithm(miningSettings);
			EMiningBuildTask buildTask = new EMiningBuildTask();
			buildTask.setInputStream(inputData);
			buildTask.setMiningAlgorithm(algorithm);
			buildTask.setMiningSettings(miningSettings);
			buildTask.setExecutionEnvironment(environment);
			System.out.println("Start algorithm");
			miningModel = (ClassificationMiningModel) buildTask.execute();
			System.out.println("Finish algorithm");


			//verifyModel();


		} catch (MiningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}



}
