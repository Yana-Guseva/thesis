//package org.eltech.ddm.classification.naivebayes.continious.parallel;
//
//import org.eltech.ddm.classification.ClassificationFunctionSettings;
//import org.eltech.ddm.classification.naivebayes.continious.ContinuousNaiveBayesAlgorithmTest;
//import org.eltech.ddm.handlers.HandlerType;
//import org.eltech.ddm.handlers.thread.MemoryType;
//import org.eltech.ddm.handlers.thread.MultiThreadedExecutionEnvironment;
//import org.eltech.ddm.handlers.thread.ThreadSettings;
//import org.eltech.ddm.inputdata.DataSplitType;
//import org.eltech.ddm.inputdata.MiningArrayStream;
//import org.eltech.ddm.inputdata.file.MiningArffStream;
//import org.eltech.ddm.miningcore.MiningException;
//import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
//import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
//import org.eltech.ddm.miningcore.miningdata.ELogicalData;
//import org.eltech.ddm.miningcore.miningfunctionsettings.DataProcessingStrategy;
//import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningAlgorithmSettings;
//import org.eltech.ddm.miningcore.miningfunctionsettings.MiningModelProcessingStrategy;
//import org.eltech.ddm.miningcore.miningtask.EMiningBuildTask;
//import org.junit.Before;
//import org.junit.Test;
//import org.omg.java.cwm.analysis.datamining.miningcore.miningmodel.MiningModel;
//
//import static org.junit.Assert.fail;
//
//public class ContinuousBayesParallelAlgorithmTest extends ContinuousNaiveBayesAlgorithmTest {
//
//    private static final String ALGO_NAME = "NaiveBayesAlgorithm_Parallel";
//    private static final String ALGO_PATH = "org.eltech.ddm.classification.naivebayes.continious.parallel.ContinuousBayesParallelAlgorithm";
//
//    private ClassificationFunctionSettings miningSettings;
//    private MiningAlgorithm algorithm;
//    private MiningModel model;
//
//
//    @Before
//    public void setUp() throws Exception {
//        this.inputData = new MiningArrayStream(new MiningArffStream("..\\data\\arff\\student-1000.arff"));
//        createMiningSettings();
//    }
//
//
//    @Test
//    public void test() throws MiningException {
//        ContinuousBayesParallelModel model = null;
//        algorithm = new ContinuousBayesParallelAlgorithm(miningSettings);
//
//        try {
//            EMiningBuildTask task = createTask();
//            System.out.println("Start algorithm");
//            model = (ContinuousBayesParallelModel) task.execute();
//            System.out.println("calculation time [s]: " + algorithm.getTimeSpentToBuildModel());
//            System.out.println("Finish algorithm");
//        } catch (MiningException e) {
//            e.printStackTrace();
//            fail();
//        }
//        verifyModel(model);
//    }
//
//    private EMiningBuildTask createTask() throws MiningException {
//        ThreadSettings executionSettings = new ThreadSettings();
//        executionSettings.setNumberHandlers(4);
//        executionSettings.setMemoryType(MemoryType.shared);
//        executionSettings.setSystemType(HandlerType.ActorExecutionHandler);
//
//        MultiThreadedExecutionEnvironment environment = new MultiThreadedExecutionEnvironment(executionSettings);
//        EMiningBuildTask buildTask = new EMiningBuildTask();
//        buildTask.setInputStream(inputData);
//        buildTask.setMiningAlgorithm(algorithm);
//        buildTask.setMiningSettings(miningSettings);
//        buildTask.setExecutionEnvironment(environment);
//        return buildTask;
//    }
//
//    private void createMiningSettings() throws MiningException {
//        ELogicalData logicalData = inputData.getLogicalData();
//        ELogicalAttribute targetAttribute = logicalData.getAttribute("class");
//
//
//        EMiningAlgorithmSettings algorithmSettings = new EMiningAlgorithmSettings();
//        algorithmSettings.setName(ALGO_NAME);
//        algorithmSettings.setClassname(ALGO_PATH);
//        algorithmSettings.setMemoryType(MemoryType.shared);
//        algorithmSettings.setDataSplitType(DataSplitType.block);
//        algorithmSettings.setSystemType(HandlerType.ActorExecutionHandler);
//        algorithmSettings.setNumberHandlers(4);
//        algorithmSettings.setDataProcessingStrategy(DataProcessingStrategy.SeparatedDataSet);
//        algorithmSettings.setModelProcessingStrategy(MiningModelProcessingStrategy.SeparatedMiningModel);
//
//        miningSettings = new ClassificationFunctionSettings(logicalData);
//        miningSettings.setAlgorithmSettings(algorithmSettings);
//        miningSettings.setTarget(targetAttribute);
//        miningSettings.verify();
//    }
//
//
//}