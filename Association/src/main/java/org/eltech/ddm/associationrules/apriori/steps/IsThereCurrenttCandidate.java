//package org.eltech.ddm.associationrules.apriori.steps;
//
//import org.eltech.ddm.associationrules.AssociationRulesMiningModel;
//import org.eltech.ddm.associationrules.apriori.AprioriMiningModel;
//import org.eltech.ddm.inputdata.MiningInputStream;
//import org.eltech.ddm.miningcore.MiningException;
//import org.eltech.ddm.miningcore.algorithms.DecisionStep;
//import org.eltech.ddm.miningcore.algorithms.Step;
//import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
//import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
//
//public class IsThereCurrenttCandidate extends DecisionStep {
//
//	public IsThereCurrenttCandidate(EMiningFunctionSettings settings, Step... trueSteps) throws MiningException {
//		super(settings, trueSteps);
//	}
//
//	@Override
//	protected boolean condition(MiningInputStream inputData, EMiningModel model)
//			throws MiningException {
//
//		//return ((Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_CANDIDATE) >= 0);
//		return (((AprioriMiningModel)model).getCurrentCandidate() >= 0);
//	}
//
//}
