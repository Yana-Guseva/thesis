package org.eltech.ddm.associationrules.apriori.partition;

import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
import org.eltech.ddm.miningcore.algorithms.StepExecuteTimingListner;
import org.eltech.ddm.miningcore.algorithms.VectorsCycleStep;
import org.eltech.ddm.miningcore.algorithms.ParallelByData;
import org.eltech.ddm.miningcore.algorithms.StepSequence;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.apriori.AprioriMiningModel;
import org.eltech.ddm.associationrules.apriori.partition.steps.CreateKItemSetCandidateWithTIDsStep;
import org.eltech.ddm.associationrules.apriori.partition.steps.GenerationTIDListStep;
import org.eltech.ddm.associationrules.apriori.steps.BuildTransactionStep;
import org.eltech.ddm.associationrules.apriori.steps.CreateLarge1ItemSetStep;
import org.eltech.ddm.associationrules.apriori.steps.KLargeItemSetsCycleStep;
import org.eltech.ddm.associationrules.apriori.steps.K_1LargeItemSetsCycleStep;
import org.eltech.ddm.associationrules.apriori.steps.K_1LargeItemSetsFromCurrentCycleStep;
import org.eltech.ddm.associationrules.apriori.steps.LargeItemSetItemsCycleStep;
import org.eltech.ddm.associationrules.apriori.steps.RemoveUnsupportItemSetStep;
import org.eltech.ddm.associationrules.apriori.steps.TransactionItemSetsCycleStep;
import org.eltech.ddm.associationrules.apriori.steps.LargeItemSetListsCycleStep;
import org.eltech.ddm.associationrules.apriori.steps.GenerateAssosiationRuleStep;
import org.eltech.ddm.associationrules.apriori.steps.TransactionItemsCycleStep;
import org.eltech.ddm.associationrules.steps.TransactionsCycleStep;
import org.omg.java.cwm.analysis.datamining.miningcore.miningmodel.MiningModel;

public class PartitionVectorsParallelAlgorithm extends MiningAlgorithm {

	public PartitionVectorsParallelAlgorithm(EMiningFunctionSettings miningSettings) throws MiningException {
		super(miningSettings);
	}


	@Override
	public AssociationRulesFunctionSettings getMiningSettings() {
		return (AssociationRulesFunctionSettings) miningSettings;
	}


	@Override
	public void initSteps() throws MiningException {
		VectorsCycleStep vcs = new VectorsCycleStep(miningSettings,
				new BuildTransactionStep(miningSettings));
		vcs.addListenerExecute(new StepExecuteTimingListner());

		ParallelByData pbd = new ParallelByData(miningSettings,vcs);
		pbd.addListenerExecute(new StepExecuteTimingListner());

		TransactionsCycleStep tcs = new TransactionsCycleStep(miningSettings,
				new TransactionItemsCycleStep(miningSettings,
						new GenerationTIDListStep(miningSettings),
						new CreateLarge1ItemSetStep(miningSettings)));
		tcs.addListenerExecute(new StepExecuteTimingListner());

		LargeItemSetListsCycleStep lislcs = new LargeItemSetListsCycleStep(miningSettings,
				new K_1LargeItemSetsCycleStep(miningSettings,
						new K_1LargeItemSetsFromCurrentCycleStep(miningSettings,
								new CreateKItemSetCandidateWithTIDsStep(miningSettings),
								new RemoveUnsupportItemSetStep(miningSettings)))
		);
		lislcs.addListenerExecute(new StepExecuteTimingListner());

		LargeItemSetListsCycleStep lislcs2 = new LargeItemSetListsCycleStep(miningSettings,
				new KLargeItemSetsCycleStep(miningSettings,
						new LargeItemSetItemsCycleStep(miningSettings,
								new GenerateAssosiationRuleStep(miningSettings))));
		lislcs2.addListenerExecute(new StepExecuteTimingListner());


		steps = new StepSequence(miningSettings,
				pbd,
				tcs,
				lislcs,
				lislcs2);
		
		steps.addListenerExecute(new StepExecuteTimingListner());
	}

	@Override
  	public EMiningModel createModel(MiningInputStream inputStream) throws MiningException {
		EMiningModel resultModel = new AprioriMiningModel((AssociationRulesFunctionSettings) miningSettings);

		return resultModel;
	}

}