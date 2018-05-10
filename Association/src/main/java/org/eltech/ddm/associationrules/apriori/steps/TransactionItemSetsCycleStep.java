//package org.eltech.ddm.associationrules.apriori.steps;
//
//
//import org.eltech.ddm.inputdata.MiningInputStream;
//import org.eltech.ddm.miningcore.MiningException;
//import org.eltech.ddm.miningcore.algorithms.Step;
//import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
//import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
//import org.eltech.ddm.associationrules.apriori.AprioriMiningModel;
//import org.omg.java.cwm.analysis.datamining.miningcore.miningfunctionsettings.MiningFunctionSettings;
//import org.omg.java.cwm.analysis.datamining.miningcore.miningmodel.MiningModel;
//
//
//public class TransactionItemSetsCycleStep extends K_1LargeItemSetsCycleStep{
//
//	public TransactionItemSetsCycleStep(EMiningFunctionSettings settings)
//			throws MiningException {
//		super(settings);
//	}
//
//    public TransactionItemSetsCycleStep(EMiningFunctionSettings settings, Step ...steps) throws MiningException {
//		super(settings, steps);
//	}
//
//	@Override
//	protected boolean conditionLoop(MiningInputStream inputData, EMiningModel model) throws MiningException {
////		return ((Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_ITEM) <
////				((AprioriMiningModel) model).getTransactionList().get(
////						(Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_TRANSACTION)).getItemIDList().size());
//
//		return (((AprioriMiningModel) model).getCurrentItem() <
//				((AprioriMiningModel) model).getTransactionList().get(
//						((AprioriMiningModel) model).getCurrentTransaction()).getItemIDList().size());
//
//	}
//
//}
