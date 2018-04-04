package org.eltech.ddm.classification.naivebayes.continious;

import com.univocity.parsers.csv.CsvParserSettings;
import org.eltech.ddm.classification.ClassificationFunctionSettings;
import org.eltech.ddm.environment.ConcurrencyExecutionEnvironment;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.inputdata.file.MiningArffStream;
import org.eltech.ddm.inputdata.file.MiningCsvStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningAlgorithmSettings;
import org.eltech.ddm.miningcore.miningtask.EMiningBuildTask;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.Map;

public class ContinuousNaiveBayesAlgorithmTest {

    private static final String ALGO_NAME = "NaiveBayesAlgorithm";
    private static final String ALGO_PATH = "org.eltech.ddm.classification.naivebayes.continious.ContinuousNaiveBayesAlgorithmTest";

    protected MiningCsvStream inputData;
    private ClassificationFunctionSettings miningSettings;



    @Before
    public void setUp() throws Exception {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setDelimiterDetectionEnabled(true);
        settings.setHeaderExtractionEnabled(true);
        this.inputData = new MiningCsvStream("di.csv", settings);
    }


    @Test
    public void test() throws MiningException {
        createMiningSettings();
        ContinuousBayesModel resultModel = (ContinuousBayesModel) createBuidTask().execute();
        verifyModel(resultModel);
    }

    private EMiningBuildTask createBuidTask() throws MiningException {
        MiningAlgorithm algorithm = new ContinuousNaiveBayesAlgorithm(miningSettings);
        ConcurrencyExecutionEnvironment environment = new ConcurrencyExecutionEnvironment(inputData);

        EMiningBuildTask buildTask = new EMiningBuildTask();
        buildTask.setMiningAlgorithm(algorithm);
        buildTask.setMiningSettings(miningSettings);
        buildTask.setExecutionEnvironment(environment);
        return buildTask;
    }

    protected void verifyModel(ContinuousBayesModel model) throws MiningException {
        MiningArffStream stream = new MiningArffStream("..\\data\\arff\\diabet-data.arff");
        MiningVector current = stream.next();
        while (current != null) {
            Map<Double, Double> result = model.apply(current.getValues());
            System.out.println(result);
            System.out.println(predictions(result));
            current = stream.next();
        }
    }

    private Double predictions(Map<Double, Double> probabilityList) {
        return probabilityList.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get().getKey();
    }

    private void createMiningSettings() throws MiningException {
        ELogicalData logicalData = inputData.getLogicalData();
        ELogicalAttribute targetAttribute = logicalData.getAttribute("Outcome");

        EMiningAlgorithmSettings algorithmSettings = new EMiningAlgorithmSettings();
        algorithmSettings.setName(ALGO_NAME);
        algorithmSettings.setClassname(ALGO_PATH);

        miningSettings = new ClassificationFunctionSettings(logicalData);
        miningSettings.setTarget(targetAttribute);
        miningSettings.setAlgorithmSettings(algorithmSettings);
        miningSettings.verify();
    }


}