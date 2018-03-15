package org.eltech.ddm.classification.naivebayes.continious;

import org.eltech.ddm.classification.naivebayes.continious.steps.CalculateSumStep;
import org.eltech.ddm.classification.naivebayes.continious.steps.FindMeanAndDeviationCycle;
import org.eltech.ddm.classification.naivebayes.continious.steps.FindMeanAndDeviationStep;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
import org.eltech.ddm.miningcore.algorithms.MiningLoopVectors;
import org.eltech.ddm.miningcore.algorithms.MiningSequence;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;


/**
 * Continuous version for Naive Bayes classifier
 *
 * @author Evgenii Titkov
 */
public class ContinuousNaiveBayesAlgorithm extends MiningAlgorithm {

    /**
     * {@inheritDoc}
     */
    ContinuousNaiveBayesAlgorithm(EMiningFunctionSettings miningSettings) throws MiningException {
        super(miningSettings);
    }


    @Override
    public MiningSequence getSequenceAlgorithm() throws MiningException {
        return new MiningSequence(miningSettings,
                new CalculateSumStep(miningSettings),
                new FindMeanAndDeviationCycle(miningSettings, new FindMeanAndDeviationStep(miningSettings)));
    }

    @Override
    public MiningSequence getCentralizedParallelAlgorithm() throws MiningException {
        return getSequenceAlgorithm();
    }

    @Override
    public MiningSequence getHorDistributedAlgorithm() throws MiningException {
        return getSequenceAlgorithm();
    }

    @Override
    public MiningSequence getVerDistributedAlgorithm() throws MiningException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EMiningModel createModel() throws MiningException {
        return new ContinuousBayesModel(miningSettings);
    }

}
