package org.eltech.ddm.associationrules.dhp;

import org.eltech.ddm.environment.ConcurrencyExecutionEnvironment;
import org.eltech.ddm.environment.ConcurrentCSTExecutionEnvironment;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningAlgorithmSettings;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;
import org.eltech.ddm.miningcore.miningtask.EMiningBuildTask;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DHPAlgorithmParallelTest extends DHPModelTest {
    private final int NUMBER_HANDLERS = 4;
    private static final String ALGO_NAME = "DHPAlgorithm";
    private static final String ALGO_PATH = "org.eltech.ddm.associationrules.dhp.DHPAlgorithm";
    protected EMiningAlgorithmSettings algorithmSettings;

    @Before
    public void setUp() throws Exception {
        algorithmSettings = new EMiningAlgorithmSettings();
        algorithmSettings.setName(ALGO_NAME);
        algorithmSettings.setClassname(ALGO_PATH);
        algorithmSettings.setNumberHandlers(NUMBER_HANDLERS);
    }

    @Test
    public void test() throws Exception {
        setMiningSettings(algorithmSettings);
        EMiningBuildTask buildTask = createBuildTask("transactions_1Gb.csv");
//        EMiningBuildTask buildTask = createBuildTask("T_200.csv");
        model = (DHPModel) buildTask.execute();

        verifyModel();
    }

    protected void verifyModel() throws MiningException {
        Assert.assertNotNull(model);
        System.out.println("AssociationRuleSet: number of rules = " + model.getAssociationRuleSet().size());
        int l = 0;
        MiningModelElement largeItemSets = model.getLargeItemSets();
        for (int i = 0; i < largeItemSets.size(); i++) {
            System.out.println("Level " + l++ + " number of rules " + largeItemSets.getElement(i).size());

        }
    }

    private EMiningBuildTask createBuildTask(String targetFile) throws Exception {
        MiningAlgorithm algorithm = new DHPAlgorithm(miningSettings);
        ConcurrentCSTExecutionEnvironment environment = new ConcurrentCSTExecutionEnvironment(targetFile, NUMBER_HANDLERS);
//        ConcurrencyExecutionEnvironment environment = new ConcurrencyExecutionEnvironment(NUMBER_HANDLERS, inputData);

        EMiningBuildTask buildTask = new EMiningBuildTask();
        buildTask.setMiningAlgorithm(algorithm);
        buildTask.setMiningSettings(miningSettings);
        buildTask.setExecutionEnvironment(environment);

        return buildTask;
    }
}
