package org.eltech.ddm.classification.naivebayes.continious;

import org.eltech.ddm.classification.ClassificationMiningModel;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.LogicalAttributeElement;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Represents model for Continues bayes Classifier algorithm.
 * Extends base classification mining model {@link ClassificationMiningModel}
 *
 * @author Evgenii Titkov
 * @value model             - base values model for mining algorithm. It's used for
 * store information with the next provided structure
 * [class-value] ----> { mean : [attr1,attr2 .. attrN] , deviation :[attr1,attr2 .. attrN]}
 * @value currentClassIndex - this value specifies current class index in order to determine current
 * class value during execution phase.
 * @value classValues       - array of all class values
 */
public class ContinuousBayesModel extends ClassificationMiningModel {

    private final static int BAYES_MODEL = 1;
    private final static int CLASS_LENGTHS = 2;


    private int attrCount;
    private Double currentClassValue;

    protected int length;
    protected final Map<Double, double[][]> model = new HashMap<>();
    protected final Map<Double, Integer> classLengths = new HashMap<>();

    protected Set<Double> classValues = new LinkedHashSet<>();

    protected Iterator<Double> iterator;

    /**
     * Default constructor for a class
     *
     * @param settings - setting for mining function
     * @throws MiningException - in case of unexpected situations
     */
    public ContinuousBayesModel(EMiningFunctionSettings settings) throws MiningException {
        super(settings);

        sets.add(BAYES_MODEL, new MiningModelElement("Input") {
            @Override
            protected String propertiesToString() {
                return "";
            }

            @Override
            public void merge(List<MiningModelElement> elements) throws MiningException {

            }
        });
        sets.add(CLASS_LENGTHS, new MiningModelElement("Class-Length") {
            @Override
            protected String propertiesToString() {
                return "";
            }

            @Override
            public void merge(List<MiningModelElement> elements) throws MiningException {

            }
        });


    }

    @Override
    public void initModel() throws MiningException {
        MiningModelElement attrs = getElement(INDEX_ATTRIBUTE_SET);
        LogicalAttributeElement tlattr = (LogicalAttributeElement)attrs.getElement(indexTarget);

    }


    /**
     * {@inheritDoc}
     */
    @Override
    public double apply(MiningVector miningVector) throws MiningException {
        throw new IllegalAccessError("Not implemented");
    }

    /**
     * Calculates final attribute probability
     *
     * @param value     - attribute value
     * @param mean      - attribute mean
     * @param deviation - attribute deviation
     * @return - probability value
     */
    private double attrProbability(double value, double mean, double deviation) {
        double exponent = Math.exp(-(Math.pow(value - mean, 2) / (2 * Math.pow(deviation, 2))));
        return (1 / (Math.sqrt(2 * Math.PI) * deviation)) * exponent;
    }

    /**
     * Applies trained model to the input data
     *
     * @param inputData - input data for algorithm
     * @return - result collection with the next structure {class-value} --> {probability}
     */
    public Map<Double, Double> apply(double[] inputData) {
        Map<Double, Double> probabilities = new HashMap<>();
        model.keySet().forEach(key -> {
            probabilities.put(key, 1D);
            double[][] classAttributeList = model.get(key);

            for (int i = 0; i < classAttributeList.length; i++) {
                double mean = classAttributeList[i][0];
                double dev = classAttributeList[i][1];
                double probability = probabilities.get(key) * attrProbability(inputData[i], mean, dev);
                probabilities.put(key, probability);
            }
        });

        return probabilities;
    }

    /**
     * Gets model
     *
     * @return - algorithm model
     */
    public Map<Double, double[][]> getModel() {
        return model;
    }

    /**
     * Put method for a  model
     *
     * @param key    - key for a value
     * @param values - value to store
     */
    public void putValue(double key, double[] values) {
        double[][] data = model.get(key) == null ? new double[values.length][2] : model.get(key);
        fillArray(key, data, values);
        length++;
        classValues.add(key);
    }

    /**
     * Calculates sums which are required to calculate mean and dev in 1 pass
     *
     * @param key    - current class key
     * @param data   - current array data
     * @param values - values in fill in
     */
    private void fillArray(double key, double[][] data, double[] values) {
        IntStream.range(0, values.length).forEach(attr -> {
            data[attr][0] += values[attr];
            data[attr][1] += values[attr] * values[attr];
        });
        model.put(key, data);
        Integer length = classLengths.get(key);
        if (length == null) {
            classLengths.put(key, 1);
        } else {
            classLengths.put(key, ++length);
        }
    }

    public void initIterator() {
        this.iterator = classValues.iterator();
    }

    public double[][] getCurrentModelValue() {
        return model.get(currentClassValue);
    }

    public Double getCurrentClassValue() {
        return currentClassValue;
    }

    public int getClassLength(double key) {
        return this.classLengths.get(key);
    }

    public void next() {
        currentClassValue = iterator.hasNext() ? iterator.next() : null;
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public int getLength() {
        return length;
    }

    public int getAttrCount() {
        return attrCount;
    }

    public void setAttrCount(int attrCount) {
        this.attrCount = attrCount;
    }
}