package org.eltech.ddm.classification.ruleset.onerule;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eltech.ddm.classification.ClassificationFunctionSettings;
import org.eltech.ddm.classification.ClassificationMiningModel;
import org.eltech.ddm.classification.ruleset.RuleSetModel;
import org.eltech.ddm.inputdata.MiningArrayStream;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.inputdata.file.MiningArffStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ECategory;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.junit.Assert;

public class TestOneRule {

	
	protected static String dataSets[] = {
//			"W1000A10C5", 
//			"W1000A100C5", 
//			"W1000A100C10", 
//			"W1000A100C50", 
//			"W1000A100C100",  
//			"W10000A10C5", 
//			"W30000A10C5", 
//			"W50000A10C5", 
//			"W100000A10C5", 
//			"W1000A100C5", 
//			"W1000A300C5", 
//			"W1000A500C5", 
//			"W1000A1000C5",
		//"W050tA100C10"
		//"W100tA100C10"
		"W1_000tA100C10"
		    //"W10_000A10C10"
			}; 
	
	protected static ClassificationFunctionSettings miningSettings;
	protected static MiningInputStream inputData;
	protected static ClassificationMiningModel miningModel; 
	
	protected static void setSettings(int index) throws  MiningException{
		
		System.out.println(dataSets[index]);
		inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSets[index] + ".arff"));
		
        ELogicalData logicalData = inputData.getLogicalData();
        miningSettings = new ClassificationFunctionSettings(logicalData);
		miningSettings.setTarget(logicalData.getAttribute("target"));
   		miningSettings.verify();

	}
	

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("----- 1R Algoritm -------");
		try {	
			for(int i=0; i < dataSets.length; i++){
	
					setSettings(i);
		
			
				OneRuleMonoAlgorithm algorithm = new OneRuleMonoAlgorithm(miningSettings);
				//OneRuleAlgorithm algorithm = new OneRuleAlgorithm(miningSettings);
				System.out.println("Start algorithm");

//				while(true){
					miningModel = (RuleSetModel) algorithm.buildModel(inputData);
//					for(int j = 0; j < inputData.getVectorsNumber(); i++){
//						MiningVector mv = inputData.getVector(j);
//						mv.isMissing();
//					}
				
//				}
					
				System.out.println("Finish algorithm. Calculation time: " + algorithm.getTimeSpentToBuildModel() + " ms");
				
				//verifyModel();
			}
			
		} catch (MiningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	protected double verifyModel(MiningInputStream inputData, ClassificationMiningModel model) {
		try
		{
			//System.out.println(model);

           // Verify builded model
			ELogicalAttribute targetAttribute = model.getTarget();
			inputData.reset();
            double wrong = 0;
            int i = 0;
            System.out.println(" Prediction:");
            MiningVector vector = inputData.next();
            while (vector != null) {
                    // Classify each vector
                    double predicted    = model.apply(vector);

                    double realTarCat   = vector.getValue( targetAttribute.getName() );
                    ECategory tarCat     = ((ELogicalAttribute)targetAttribute).
                    					getCategoricalProperties().getCategory((int)realTarCat);
                    ECategory predTarCat = ((ELogicalAttribute)targetAttribute).
                    					getCategoricalProperties().getCategory((int)predicted);

                    i++;
                    //System.out.println(" " + i +": " + vector.toVector() + " -> " + predTarCat.getName());

                    if (! predTarCat.equals( tarCat) )
                            wrong = wrong + 1;
                    vector = inputData.next();
            };
            double rate = (100.0 - ((double) wrong / i)*100.0);
            System.out.println("\n classification rate = " +  rate);

            return rate;

		}
		catch( Exception ex )
		{
 		  ex.printStackTrace();
 		  fail();
		}

		return 0;
	}


}
