package org.eltech.ddm.classification.naivebayes;

import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.eltech.ddm.classification.ClassificationLoadlTest;
import org.eltech.ddm.inputdata.MiningArrayStream;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.file.MiningArffStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

//@Ignore
public class NaiveBayesAlgoritmLoadTest extends ClassificationLoadlTest {
	
	@Before
	public void setUp() throws Exception {
	}

	
	@Test
	public void testNaiveBayesAlgoritm() throws MiningException {
		System.out.println("----- Naive Bayes Algoritm -------");
		
		for(int i=0; i < dataSets.length; i++){
			setSettings(i);
		
			NaiveBayesAlgorithm algorithm = new NaiveBayesAlgorithm(miningSettings);
			System.out.println("Start algorithm");
			miningModel = (NaiveBayesModel) algorithm.buildModel(inputData);

			System.out.println("Finish algorithm. Calculation time: " + algorithm.getTimeSpentToBuildModel());
			
			verifyModel();
			
		}
	}
	
}
