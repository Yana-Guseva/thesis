package org.eltech.ddm.associationrules.apriori;

import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.apriori.steps.BuildTransactionStep;
import org.eltech.ddm.associationrules.apriori.steps.CalculateKItemSetSupportStep;
import org.eltech.ddm.associationrules.apriori.steps.Calculate1ItemSetSupportStep;
import org.eltech.ddm.associationrules.apriori.steps.CreateLarge1ItemSetStep;
import org.eltech.ddm.associationrules.apriori.steps.CreateKItemSetCandidateStep;
import org.eltech.ddm.associationrules.apriori.steps.GenerateAssosiationRuleStep;
import org.eltech.ddm.associationrules.apriori.steps.IsThereCurrenttCandidate;
import org.eltech.ddm.associationrules.apriori.steps.KLargeItemSetsCycleStep;
import org.eltech.ddm.associationrules.apriori.steps.K_1LargeItemSetsCycleStep;
import org.eltech.ddm.associationrules.apriori.steps.K_1LargeItemSetsFromCurrentCycleStep;
import org.eltech.ddm.associationrules.apriori.steps.LargeItemSetItemsCycleStep;
import org.eltech.ddm.associationrules.apriori.steps.LargeItemSetListsCycleStep;
import org.eltech.ddm.associationrules.apriori.steps.RemoveUnsupportItemSetStep;
import org.eltech.ddm.associationrules.apriori.steps.TransactionItemSetsCycleStep;
import org.eltech.ddm.associationrules.apriori.steps.TransactionItemsCycleStep;
import org.eltech.ddm.associationrules.steps.TransactionsCycleStep;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.DecisionStep;
import org.eltech.ddm.miningcore.algorithms.ParallelByData;
import org.eltech.ddm.miningcore.algorithms.StepExecuteTimingListner;
import org.eltech.ddm.miningcore.algorithms.VectorsCycleStep;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
import org.eltech.ddm.miningcore.algorithms.StepSequence;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.omg.java.cwm.analysis.datamining.miningcore.miningmodel.MiningModel;

public class AprioriVectorsLargeSetsRulesParallelAlgorithm extends MiningAlgorithm {

	public AprioriVectorsLargeSetsRulesParallelAlgorithm (AssociationRulesFunctionSettings miningSettings) throws MiningException {
		super(miningSettings);
	}

	@Override
	public AssociationRulesFunctionSettings getMiningSettings() {
		return (AssociationRulesFunctionSettings) miningSettings;
	}


	@Override
  	public EMiningModel createModel(MiningInputStream inputStream) throws MiningException {
		EMiningModel resultModel = new AprioriMiningModel((AssociationRulesFunctionSettings) miningSettings);

		return resultModel;
	}

	@Override
	public void initSteps() throws MiningException {

		VectorsCycleStep vcs = new VectorsCycleStep(miningSettings,
				new BuildTransactionStep(miningSettings));
		vcs.addListenerExecute(new StepExecuteTimingListner());

		ParallelByData pvcs = new ParallelByData(miningSettings,vcs);
		pvcs.addListenerExecute(new StepExecuteTimingListner());

		TransactionsCycleStep tcs = new TransactionsCycleStep(miningSettings,
				new TransactionItemsCycleStep(miningSettings,
						new Calculate1ItemSetSupportStep(miningSettings),
						new CreateLarge1ItemSetStep(miningSettings)));
		tcs.addListenerExecute(new StepExecuteTimingListner());

		LargeItemSetListsCycleStep lislcs = new LargeItemSetListsCycleStep(miningSettings,
				new K_1LargeItemSetsCycleStep(miningSettings,
						new K_1LargeItemSetsFromCurrentCycleStep(miningSettings,
								new CreateKItemSetCandidateStep(miningSettings),
								new IsThereCurrenttCandidate(miningSettings,
										new TransactionsCycleStep(miningSettings,
												new CalculateKItemSetSupportStep(miningSettings)),
										new RemoveUnsupportItemSetStep(miningSettings))
						)
				)
		);
		lislcs.addListenerExecute(new StepExecuteTimingListner());
		ParallelByData plislcs = new ParallelByData(miningSettings,lislcs);
		plislcs.addListenerExecute(new StepExecuteTimingListner());


		LargeItemSetListsCycleStep lislcs2 = new LargeItemSetListsCycleStep(miningSettings,
				new KLargeItemSetsCycleStep(miningSettings,
						new LargeItemSetItemsCycleStep(miningSettings,
								new GenerateAssosiationRuleStep(miningSettings))));
		lislcs2.addListenerExecute(new StepExecuteTimingListner());
		ParallelByData plislcs2 = new ParallelByData(miningSettings,lislcs2);
		plislcs2.addListenerExecute(new StepExecuteTimingListner());

		steps = new StepSequence(miningSettings,
				pvcs,
				tcs,
				plislcs,
				plislcs2);
		steps.addListenerExecute(new StepExecuteTimingListner());
	}
}
