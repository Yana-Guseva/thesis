package org.eltech.ddm.associationrules.apriori.dhp;

import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
import org.eltech.ddm.miningcore.algorithms.ParallelByData;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;


public class DHPAlgorithmParallel extends MiningAlgorithm {

	public DHPAlgorithmParallel(EMiningFunctionSettings miningSettings)
			throws MiningException {
		super(miningSettings);
		// TODO Auto-generated constructor stub
	}

	protected DHPMiningModel model;

	@Override
	public void initSteps() throws MiningException {
//		BuildTransactionListStep buildTransactionListStep =
//				new BuildTransactionListStep(getMiningInputStream(), getMiningSettings(), model);
//        CycleByVectors cycleByVectors =
//        		new CycleByVectors(getMiningInputStream(), getMiningSettings(), model);
//        cycleByVectors.getIteration().addStep(buildTransactionListStep);
//		PartitionTransactionListStep partitionTransactionListStep = 
//				new PartitionTransactionListStep(getMiningInputStream(), getMiningSettings(), model);
//	
//		ParallelByData parallel = new ParallelByData(getMiningInputStream(), getMiningSettings(), getParallelExecutionSettings(), model);
//		{
//			SequenceOfSteps s = new SequenceOfSteps();
//			CreateLargeItemSetsStep createGlobalLargeItemSetsStep = 
//					new CreateLargeItemSetsStep(getMiningInputStream(), getMiningSettings(), model);
//			CycleByPartitions cycleByPartitions = 
//					new CycleByPartitions(getMiningInputStream(), getMiningSettings(), model);
//			cycleByPartitions.getIteration().addStep(createGlobalLargeItemSetsStep);
//
//			s.addStep(cycleByPartitions);
//			parallel.setBranche(s);
//
//			CreateGlobalCandidateItemSetsStep createGlobalCandidateItemSetsStep = new CreateGlobalCandidateItemSetsStep(getMiningInputStream(), getMiningSettings(), model);
//			CycleByLargeItemSets cycleByLargeItemSets = 
//					new CycleByLargeItemSets(getMiningInputStream(), getMiningSettings(), model);
//			cycleByLargeItemSets.getIteration().addStep(createGlobalCandidateItemSetsStep);
//			
//			s.addStep(cycleByLargeItemSets);
//
//			parallel.setBranche(s);
//		}
//		GenerateAssosiationRulesStep generateAssosiationRuleStep =
//				new GenerateAssosiationRulesStep(getMiningInputStream(), getMiningSettings(), model);
//		
//        steps = new SequenceOfSteps();
//        steps.addStep(cycleByVectors);
//		steps.addStep(partitionTransactionListStep);
//		steps.addStep(parallel);
//		steps.addStep(generateAssosiationRuleStep);
	}

	@Override
  	public EMiningModel createModel(MiningInputStream inputStream) throws MiningException {
		EMiningModel resultModel = new DHPMiningModel((AssociationRulesFunctionSettings) miningSettings);

		return resultModel;
	}

}
