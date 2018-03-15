//package org.eltech.ddm.classification.naivebayes.continious.parallel;
//
//import org.eltech.ddm.classification.naivebayes.continious.steps.CalculateSumStep;
//import org.eltech.ddm.classification.naivebayes.continious.steps.FindMeanAndDeviationCycle;
//import org.eltech.ddm.classification.naivebayes.continious.steps.FindMeanAndDeviationStep;
//import org.eltech.ddm.inputdata.MiningInputStream;
//import org.eltech.ddm.miningcore.MiningException;
//import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
//import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
//import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
//
///**
// * Continuous parallel version for Naive Bayes classifier
// *
// * @see ContinuousBayesParallelModel
// * @see org.eltech.ddm.classification.naivebayes.continious.ContinuousBayesModel
// * @see CalculateSumStep
// * @see FindMeanAndDeviationStep
// * @see FindMeanAndDeviationCycle
// *
// * @author Evgenii Titkov
// */
//public class ContinuousBayesParallelAlgorithm extends MiningAlgorithm {
//
//    /**
//     * {@inheritDoc}
//     */
//    ContinuousBayesParallelAlgorithm(EMiningFunctionSettings miningSettings) throws MiningException {
//        super(miningSettings);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public EMiningModel createModel(MiningInputStream inputStream) throws MiningException {
//        EMiningModel resultModel = new ContinuousBayesParallelModel(miningSettings);
//        resultModel.setVectorCount(inputStream.getVectorsNumber());
//        return resultModel;
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    protected void initSteps() throws MiningException {
//        Step attributeAncClassCycle = new FindMeanAndDeviationCycle(miningSettings, new FindMeanAndDeviationStep(miningSettings));
//        attributeAncClassCycle.addListenerExecute(new StepExecuteTimingListner());
//
//        CalculateSumStep calculateSumStep = new CalculateSumStep(miningSettings);
//
//        Step vectorCycle = new VectorsCycleStep(miningSettings, calculateSumStep);
//        vectorCycle.addListenerExecute(new StepExecuteTimingListner());
//
//        ParallelByData vectorsCycleParallel = new ParallelByData(miningSettings, vectorCycle);
//        vectorsCycleParallel.addListenerExecute(new ParallelStepExecuteTimingListner());
//
//        steps = new StepSequence(miningSettings, vectorsCycleParallel, attributeAncClassCycle);
//        steps.addListenerExecute(new StepExecuteTimingListner());
//    }
//
//}
