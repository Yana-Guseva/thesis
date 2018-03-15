//package org.eltech.ddm.clustering.cdbase.kmeans;
//
//import org.eltech.ddm.classification.ClassificationFunctionSettings;
//import org.eltech.ddm.classification.naivebayes.NaiveBayesAlgorithm;
//import org.eltech.ddm.classification.naivebayes.NaiveBayesModel;
//import org.eltech.ddm.environment.DataDistribution;
//import org.eltech.ddm.environment.NodeSettings;
//import org.eltech.ddm.handlers.actors.ActorsExecutionEnvironment;
//import org.eltech.ddm.handlers.actors.ActorsExecutionEnvironmentSettings;
//import org.eltech.ddm.inputdata.file.MiningArffStream;
//import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
//import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
//import org.eltech.ddm.miningcore.miningdata.ELogicalData;
//import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningAlgorithmSettings;
//import org.eltech.ddm.miningcore.miningtask.EMiningBuildTask;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.fail;
//
//
//public class NaiveBayesAlgorithmActrosTest {
//
//    private final int NUMBER_HANDLERS = 8;
//
//    protected EMiningAlgorithmSettings miningAlgorithmSettings;
//    protected MiningAlgorithm algorithm;
//
//    @Before
//    public void setUp() throws Exception {
//        // Create mining algorithm settings
//        miningAlgorithmSettings = new EMiningAlgorithmSettings();
//        miningAlgorithmSettings.setName("Naive Bayes");
//        miningAlgorithmSettings.setClassname("org.eltech.ddm.classification.naivebayes.NaiveBayesAlgorithm");
//    }
//
//
//    /**
//     * Cluster nodes must be launched before the test starts.
//     * Example ServiceMain.exe 127.0.0.1 2551 8 4 127.0.0.1:2551
//     */
//    @Test
//    public void test4ActorsIrisSingleNode() {
//
//        try {
//            // Load input data
//            MiningArffStream inputData1 = new MiningArffStream("..\\data\\arff\\weather-nominal.arff");
//            MiningArffStream inputData2 = new MiningArffStream("..\\data\\arff\\weather-nominal2.arff");
//
//
//            ELogicalData logicalData = inputData1.getLogicalData();
//            ELogicalAttribute targetAttribute = logicalData.getAttribute("play");
//
//
//            //Create settings for classification
//            ClassificationFunctionSettings miningSettings = new ClassificationFunctionSettings(logicalData);
//            miningSettings.setTarget(targetAttribute);
//            miningSettings.setAlgorithmSettings(miningAlgorithmSettings);
//            miningSettings.verify();
//
//            ActorsExecutionEnvironmentSettings executionSettings = new ActorsExecutionEnvironmentSettings(
//                    new NodeSettings("127.0.0.1",2551, 4,inputData1),
//                    new NodeSettings("127.0.0.1",2551, 4,inputData2),
//                    new NodeSettings("127.0.0.1",2551, 4)
//            );
//            executionSettings.setDataDistribution(DataDistribution.horizontal_distribution);
//
//            ActorsExecutionEnvironment environment = new ActorsExecutionEnvironment(executionSettings);
//
//            NaiveBayesAlgorithm algorithm = new NaiveBayesAlgorithm(miningSettings);
//
//            EMiningBuildTask buildTask = new EMiningBuildTask();
//            buildTask.setMiningAlgorithm(algorithm);
//            buildTask.setMiningSettings(miningSettings);
//            buildTask.setExecutionEnvironment(environment);
//            NaiveBayesModel model = (NaiveBayesModel) buildTask.execute();
//
//            System.out.println("cluster model: " + model);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail();
//        }
//    }
//
//
//}
