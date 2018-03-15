//package org.eltech.ddm.classification.naivebayes.continious.parallel;
//
//import org.eltech.ddm.classification.ClassificationMiningModel;
//import org.eltech.ddm.classification.naivebayes.continious.ContinuousBayesModel;
//import org.eltech.ddm.miningcore.MiningException;
//import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
//import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
//import org.eltech.ddm.miningcore.miningmodel.IShareableMiningModel;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
///**
// * Represents parallel model for Continues bayes Classifier algorithm.
// * Extends base classification mining model {@link ClassificationMiningModel}
// *
// * @author Evgenii Titkov
// * @value model             - base values model for mining algorithm. It's used for
// * store information with the next provided structure
// * [class-value] ----> { mean : [attr1,attr2 .. attrN] , deviation :[attr1,attr2 .. attrN]}
// * @value currentClassIndex - this value specifies current class index in order to determine current
// * class value during execution phase.
// * @value classValues       - array of all class values
// */
//public class ContinuousBayesParallelModel extends ContinuousBayesModel implements IShareableMiningModel, Cloneable {
//
//    /**
//     * Default constructor for a class
//     *
//     * @param settings - setting for mining function
//     * @throws MiningException - in case of unexpected situations
//     */
//    ContinuousBayesParallelModel(EMiningFunctionSettings settings) throws MiningException {
//        super(settings);
//    }
//
//
//    @Override
//    public void join(List<EMiningModel> joinModels) throws MiningException {
//        final int attrCount = ((ContinuousBayesParallelModel) joinModels.get(0)).getAttrCount();
//
//        long startTime = System.nanoTime();
//
//        /*
//         * Here we're joining model metadata
//         */
//        joinModels.forEach(model -> {
//            ContinuousBayesParallelModel cast = (ContinuousBayesParallelModel) model;
//            this.length += cast.length;
//            this.classValues.addAll(cast.getClassValues());
//            cast.classLengths.forEach((k, v) -> this.classLengths.merge(k, v, Integer::sum));
//        });
//
//        /*
//         * Merging of data model
//         */
//        Map<Double, List<double[][]>> merged = joinModels.stream()
//                .map(ContinuousBayesParallelModel.class::cast)
//                .map(ContinuousBayesModel::getModel)
//                .flatMap(en -> en.entrySet().stream())
//                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
//
//        /*
//         * Partials sum summary
//         */
//        Map<Double, double[][]> result = new HashMap<>();
//        merged.keySet().forEach(key -> {
//            double[][] reduced = merged.get(key).stream()
//                    .reduce(new double[2][attrCount], (initial, current) -> {
//                        IntStream.range(0, current.length).forEach(index -> {
//                            initial[0][index] += current[0][index];
//                            initial[1][index] += current[1][index];
//                        });
//                        return initial;
//                    });
//            result.put(key, reduced);
//        });
//
//
//        this.iterator = this.classValues.iterator();
//        this.model.putAll(result);
//
//        long estimatedTime = System.nanoTime() - startTime;
//
//        System.out.println(estimatedTime*Math.pow(10,-6));
//    }
//
//
//    /**
//     * Initiates model according to handler threads count
//     *
//     * @param handlerCount - handler threads count
//     *
//     * @return - initiated models
//     * @throws MiningException - in case of errors
//     */
//    @Override
//    public List<EMiningModel> split(int handlerCount) throws MiningException {
//        return IntStream.range(0, handlerCount).mapToObj(index -> (EMiningModel) this.clone()).collect(Collectors.toList());
//    }
//
//
//
//
//}