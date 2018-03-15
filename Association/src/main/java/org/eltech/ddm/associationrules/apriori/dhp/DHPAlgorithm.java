package org.eltech.ddm.associationrules.apriori.dhp;

import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.apriori.AprioriMiningModel;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.VectorsCycleStep;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
import org.eltech.ddm.miningcore.algorithms.StepSequence;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;


public class DHPAlgorithm extends MiningAlgorithm {

	private static final long serialVersionUID = 1L;

	protected DHPMiningModel model;
	
	public DHPAlgorithm (AssociationRulesFunctionSettings miningSettings) throws MiningException {
		super(miningSettings);
	}


	@Override
	public void initSteps() throws MiningException {
//		buildTransactionListStep buildTransactionListStep =
//				new BuildTransactionListStep(getMiningInputStream(), getMiningSettings(), model);
//        CycleByVectors cycleByVectors =
//        		new CycleByVectors(getMiningInputStream(), getMiningSettings(), model);
//        cycleByVectors.getIteration().addStep(buildTransactionListStep);
//		PartitionTransactionListStep partitionTransactionListStep = 
//				new PartitionTransactionListStep(getMiningInputStream(), getMiningSettings(), model);
//		CreateLargeItemSetsStep createLargeItemSetsStep = 
//				new CreateLargeItemSetsStep(getMiningInputStream(), getMiningSettings(), model);
//		CycleByPartitions cycleByPartitions = 
//				new CycleByPartitions(getMiningInputStream(), getMiningSettings(), model);
//		cycleByPartitions.getIteration().addStep(createLargeItemSetsStep);
//		
//		CreateGlobalCandidateItemSetsStep createGlobalCandidateItemSetsStep = new CreateGlobalCandidateItemSetsStep(getMiningInputStream(), getMiningSettings(), model);
//		CycleByLargeItemSets cycleByLargeItemSets = 
//				new CycleByLargeItemSets(getMiningInputStream(), getMiningSettings(), model);
//		cycleByLargeItemSets.getIteration().addStep(createGlobalCandidateItemSetsStep);
//		
//		GenerateAssosiationRulesStep generateAssosiationRuleStep =
//				new GenerateAssosiationRulesStep(getMiningInputStream(), getMiningSettings(), model);
//        steps = new SequenceOfSteps();
//        steps.addStep(cycleByVectors);
//		steps.addStep(partitionTransactionListStep);
//		steps.addStep(cycleByPartitions);
//		steps.addStep(cycleByLargeItemSets);
//		steps.addStep(generateAssosiationRuleStep);
	}

	@Override
  	public EMiningModel createModel(MiningInputStream inputStream) throws MiningException {
		EMiningModel resultModel = new DHPMiningModel((AssociationRulesFunctionSettings) miningSettings);

		return resultModel;
	}
}