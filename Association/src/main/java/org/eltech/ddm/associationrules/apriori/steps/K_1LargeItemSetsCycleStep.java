//package org.eltech.ddm.associationrules.apriori.steps;
//
//
//import org.eltech.ddm.inputdata.MiningInputStream;
//import org.eltech.ddm.miningcore.MiningException;
//import org.eltech.ddm.miningcore.algorithms.CycleStep;
//import org.eltech.ddm.miningcore.algorithms.Step;
//import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
//import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
//import org.eltech.ddm.associationrules.apriori.AprioriMiningModel;
//import org.omg.java.cwm.analysis.datamining.miningcore.miningfunctionsettings.MiningFunctionSettings;
//import org.omg.java.cwm.analysis.datamining.miningcore.miningmodel.MiningModel;
//
///**
// * Cycle for all itemsets from the attributes of large k-1-itemsets (those with minimum support).
// * @author Ivan
// *
// */
//public class K_1LargeItemSetsCycleStep extends KLargeItemSetsCycleStep{
//
//	public K_1LargeItemSetsCycleStep(EMiningFunctionSettings settings)
//			throws MiningException {
//		super(settings);
//	}
//
//    public K_1LargeItemSetsCycleStep(EMiningFunctionSettings settings, Step ...steps) throws MiningException {
//		super(settings, steps);
//	}
//
//	@Override
//	protected boolean conditionLoop(MiningInputStream inputData, EMiningModel model) throws MiningException {
////		return ((Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_ITEM_SET) <
////				((AprioriMiningModel) model).getLargeItemSetsList().get(
////						(Integer)getStateParameter(model, AprioriMiningModel.NAME_CURRENT_LARGE_ITEM_SETS) - 1).size());// attributes of large k-1-itemsets
//		return (((AprioriMiningModel) model).getCurrentItemSet() <
//				((AprioriMiningModel) model).getLargeItemSetsList().get(
//						((AprioriMiningModel) model).getCurrentLargeItemSets() - 1).size());// attributes of large k-1-itemsets
//
//
//	}
//}
