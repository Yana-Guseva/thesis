//package org.eltech.ddm.associationrules.apriori.steps;
//
//
//import org.eltech.ddm.inputdata.MiningInputStream;
//import org.eltech.ddm.miningcore.MiningException;
//import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
//import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
//import org.eltech.ddm.associationrules.apriori.AprioriMiningModel;
//import org.omg.java.cwm.analysis.datamining.miningcore.miningfunctionsettings.MiningFunctionSettings;
//import org.omg.java.cwm.analysis.datamining.miningcore.miningmodel.MiningModel;
//
//
//public class TransactionItemSetsFromCurrentCycleStep extends TransactionItemSetsCycleStep{
//
//
//	public TransactionItemSetsFromCurrentCycleStep(EMiningFunctionSettings settings)
//			throws MiningException {
//		super(settings);
//		// TODO Auto-generated constructor stub
//	}
//
//	@Override
//	protected EMiningModel initLoop(MiningInputStream inputData, EMiningModel model) throws MiningException {
//		super.initLoop(inputData,  model);
//		((AprioriMiningModel) model).setCurrentItemSet2(((AprioriMiningModel) model).getCurrentItemSet() + 1);
////		setStateParameter(model, AprioriMiningModel.NAME_CURRENT_ITEM_SET_2,
////				(Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_ITEM_SET) + 1);
//
//		return model;
//	}
//
//
//}
