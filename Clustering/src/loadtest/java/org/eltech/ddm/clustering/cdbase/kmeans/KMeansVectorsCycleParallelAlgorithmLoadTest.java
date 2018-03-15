package org.eltech.ddm.clustering.cdbase.kmeans;

import org.eltech.ddm.clustering.AggregationFunction;
import org.eltech.ddm.clustering.ClusteringFunctionSettings;
import org.eltech.ddm.clustering.ClusteringLoadlTest;
import org.eltech.ddm.clustering.ClusteringMiningModel;
import org.eltech.ddm.clustering.cdbase.CDBaseModelTest;
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
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public class KMeansVectorsCycleParallelAlgorithmLoadTest extends ClusteringLoadlTest{

	private final int NUMBER_HANDLERS = 2;
	
	protected KMeansAlgorithmSettings miningAlgorithmSettings;
	protected MiningAlgorithm algorithm;

	String dataSet;

	@Before
	public void setUp() throws Exception {
		// Create mining algorithm settings
		miningAlgorithmSettings = new KMeansAlgorithmSettings();
		miningAlgorithmSettings.setAlgorithm("KMeans");
		miningAlgorithmSettings.setMaxNumberOfIterations(50);
		miningAlgorithmSettings.setEps(0.5);
	}

	@Test
	public void test_W050tA100C10() throws MiningException {
		dataSet = "W050tA100C10";

		System.out.println(dataSet);
		//inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}

	@Test
	public void test_W100tA100C10() throws MiningException {
		dataSet = "W100tA100C10";

		System.out.println(dataSet);
		//inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}

	@Test
	public void test_W200tA100C10() throws MiningException {
		dataSet = "W200tA100C10";

		System.out.println(dataSet);
		//inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}

	@Test
	public void test_W300tA100C10() throws MiningException {
		dataSet = "W300tA100C10";

		System.out.println(dataSet);
		//inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}

	@Test
	public void test_W400tA100C10() throws MiningException {
		dataSet = "W400tA100C10";

		System.out.println(dataSet);
		//inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}

	@Test
	public void test_W500tA100C10() throws MiningException {
		dataSet = "W500tA100C10";

		System.out.println(dataSet);
		//inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}

	@Test
	public void test_W600tA100C10() throws MiningException {
		dataSet = "W600tA100C10";

		System.out.println(dataSet);
		//inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}

	@Test
	public void test_W700tA100C10() throws MiningException {
		dataSet = "W700tA100C10";

		System.out.println(dataSet);
		//inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}

	@Test
	public void test_W800tA100C10() throws MiningException {
		dataSet = "W800tA100C10";

		System.out.println(dataSet);
		//inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}

	@Test
	public void test_W900tA100C10() throws MiningException {
		dataSet = "W900tA100C10";

		System.out.println(dataSet);
		//inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}

	@Test
	public void test_W1_000tA100C10() throws MiningException {
		dataSet = "W1_000tA100C10";

		System.out.println(dataSet);

	}


	@After
	public void tearDown() {

		ThreadSettings executionSettings = new ThreadSettings();
		executionSettings.setNumberHandlers(NUMBER_HANDLERS);
		executionSettings.setMemoryType(MemoryType.distributed);

		MultiThreadedExecutionEnvironment environment;
		try {
			environment = new MultiThreadedExecutionEnvironment(executionSettings);

			long time = System.currentTimeMillis();

			inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));

			ELogicalData logicalData = inputData.getLogicalData();
			miningSettings = new ClusteringFunctionSettings(logicalData);
			miningSettings.verify();

			miningSettings.setMaxNumberOfClusters(10);
			miningSettings.setAggregationFunction(AggregationFunction.euclidian);
			miningSettings.verify();

			miningAlgorithmSettings.setDataSplitType(DataSplitType.block);
			miningAlgorithmSettings.setDataProcessingStrategy(DataProcessingStrategy.SeparatedDataSet );
			miningAlgorithmSettings.setModelProcessingStrategy(MiningModelProcessingStrategy.SingleMiningModel);
			miningSettings.setAlgorithmSettings(miningAlgorithmSettings);

			algorithm = new KMeanVectorsCycleParallelAlgorithm(miningSettings);
			EMiningBuildTask buildTask = new EMiningBuildTask();
			buildTask.setInputStream(inputData);
			buildTask.setMiningAlgorithm(algorithm);
			buildTask.setMiningSettings(miningSettings);
			buildTask.setExecutionEnvironment(environment);
			System.out.println("Start algorithm");
			model = (ClusteringMiningModel) buildTask.execute();
			System.out.println("Finish algorithm");
			System.out.println("Total time: " + (System.currentTimeMillis() - time) + " ms");

			verifyModel();


		} catch (MiningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}

}
