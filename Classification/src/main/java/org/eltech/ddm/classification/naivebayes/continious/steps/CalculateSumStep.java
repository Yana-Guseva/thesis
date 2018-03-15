package org.eltech.ddm.classification.naivebayes.continious.steps;

import org.eltech.ddm.classification.naivebayes.continious.ContinuousBayesModel;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.DataMiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import java.util.Arrays;

/**
 * Class represent logic for separating training sample by
 * class value for further calculations steps
 *
 *
 * @author Evgenii Titkov
 */
public class CalculateSumStep extends DataMiningBlock {

    /**
     * Constructor of algorithm's step for all or part of input data
     *
     * @param settings - settings for build model
     */
    public CalculateSumStep(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
    }

    /**
     * Main execution method
     *
     * @param inputData - mining algorithm input data
     * @param model     - mining model
     * @return          - mining model
     * @throws MiningException - in case of unexpected situations
     */
    @Override
    public EMiningModel execute(MiningInputStream inputData, EMiningModel model) throws MiningException {
        ContinuousBayesModel algModel = (ContinuousBayesModel) model;
        double[] values = inputData.getVector(model.getCurrentVectorIndex()).getValues();
        algModel.setAttrCount(values.length - 1);
        algModel.putValue((int) values[values.length - 1], array(values));
        return algModel;
    }

    /**
     * Simple syntax sugar for generating array from the set of values
     *
     * @param args - passed arguments
     * @return     - created array from passed args
     */
    private double[] array(double... args) {
        return Arrays.copyOfRange(args, 0, args.length - 1);
    }
}