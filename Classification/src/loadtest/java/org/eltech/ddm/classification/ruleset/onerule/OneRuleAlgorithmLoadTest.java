package org.eltech.ddm.classification.ruleset.onerule;


import org.eltech.ddm.classification.ClassificationFunctionSettings;
import org.eltech.ddm.classification.ClassificationLoadlTest;
import org.eltech.ddm.classification.ruleset.RuleSetModel;
import org.eltech.ddm.inputdata.MiningArrayStream;
import org.eltech.ddm.inputdata.file.MiningArffStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OneRuleAlgorithmLoadTest extends ClassificationLoadlTest {
	
	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void test_W050tA100C10() throws MiningException {
		String dataSet = "W050tA100C10";
		
		System.out.println(dataSet);
		inputData = new MiningArrayStream(new MiningArffStream("..\\data\\arff\\classification\\" + dataSet + ".arff"));
	}

	@Test
	public void test_W100tA100C10() throws MiningException {
		String dataSet = "W100tA100C10";
		
		System.out.println(dataSet);
		inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}

	@Test
	public void test_W200tA100C10() throws MiningException {
		String dataSet = "W200tA100C10";
		
		System.out.println(dataSet);
		inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}

	@Test
	public void test_W300tA100C10() throws MiningException {
		String dataSet = "W300tA100C10";
		
		System.out.println(dataSet);
		inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}

	@Test
	public void test_W400tA100C10() throws MiningException {
		String dataSet = "W400tA100C10";
		
		System.out.println(dataSet);
		inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}
	
	@Test
	public void test_W500tA100C10() throws MiningException {
		String dataSet = "W500tA100C10";
		
		System.out.println(dataSet);
		inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}
	
	@Test
	public void test_W600tA100C10() throws MiningException {
		String dataSet = "W600tA100C10";
		
		System.out.println(dataSet);
		inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}
	
	@Test
	public void test_W700tA100C10() throws MiningException {
		String dataSet = "W700tA100C10";
		
		System.out.println(dataSet);
		inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}
	
	@Test
	public void test_W800tA100C10() throws MiningException {
		String dataSet = "W800tA100C10";
		
		System.out.println(dataSet);
		inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}
	
	@Test
	public void test_W900tA100C10() throws MiningException {
		String dataSet = "W900tA100C10";
		
		System.out.println(dataSet);
		inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}
	
	@Test
	public void test_W1_000tA100C10() throws MiningException {
		String dataSet = "W1_000tA100C10";
		
		System.out.println(dataSet);
		inputData = new MiningArrayStream(new MiningArffStream("../data/arff/classification/" + dataSet + ".arff"));
	}
	
	@After
	public void tearDown() {
		
		
        ELogicalData logicalData;
		try {
			logicalData = inputData.getLogicalData();

	        miningSettings = new ClassificationFunctionSettings(logicalData);
			miningSettings.setTarget(logicalData.getAttribute("target"));
	   		miningSettings.verify();
			
			OneRuleAlgorithm algorithm = new OneRuleAlgorithm(miningSettings);
			//OneRuleMonoAlgorithm algorithm = new OneRuleMonoAlgorithm(miningSettings);
			//OneRuleMonoWEKAAlgorithm algorithm = new OneRuleMonoWEKAAlgorithm(miningSettings);
			System.out.println("Start algorithm");
			miningModel = (RuleSetModel) algorithm.buildModel(inputData);
	
			System.out.println("Finish algorithm. Calculation time: " + algorithm.getTimeSpentToBuildModel() + " ms");
			
			//verifyModel();
			
			inputData.close();
		} catch (MiningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
