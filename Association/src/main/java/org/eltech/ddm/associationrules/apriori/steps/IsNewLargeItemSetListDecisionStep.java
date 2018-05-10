//package org.eltech.ddm.associationrules.apriori.steps;
//
//import org.eltech.ddm.associationrules.apriori.AprioriMiningModel;
//import org.eltech.ddm.inputdata.MiningInputStream;
//import org.eltech.ddm.miningcore.MiningException;
//import org.eltech.ddm.miningcore.algorithms.DecisionStep;
//import org.eltech.ddm.miningcore.algorithms.Step;
//import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
//import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
//import org.omg.java.cwm.analysis.datamining.miningcore.miningfunctionsettings.MiningFunctionSettings;
//import org.omg.java.cwm.analysis.datamining.miningcore.miningmodel.MiningModel;
//
//public class IsNewLargeItemSetListDecisionStep extends DecisionStep {
//
//	public IsNewLargeItemSetListDecisionStep(EMiningFunctionSettings settings, Step... trueSteps) throws MiningException {
//		super(settings, trueSteps);
//
//	}
//
//	@Override
//	protected boolean condition(MiningInputStream inputData, EMiningModel model)
//			throws MiningException {
//		// TODO Auto-generated method stub
//		return (((AprioriMiningModel) model).getLargeItemSetsList().size() >
//						((AprioriMiningModel) model).getCurrentLargeItemSets());
////								(Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_LARGE_ITEM_SETS)) ;
//	}
//
//}
