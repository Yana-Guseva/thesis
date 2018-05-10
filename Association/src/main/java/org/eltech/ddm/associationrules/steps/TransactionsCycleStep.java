//package org.eltech.ddm.associationrules.steps;
//
//import org.eltech.ddm.inputdata.MiningInputStream;
//import org.eltech.ddm.miningcore.MiningException;
//import org.eltech.ddm.miningcore.algorithms.CycleStep;
//import org.eltech.ddm.miningcore.algorithms.Step;
//import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
//import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
//import org.eltech.ddm.associationrules.AssociationRulesMiningModel;
//
//public class TransactionsCycleStep extends CycleStep{
//
//	public TransactionsCycleStep(EMiningFunctionSettings settings)
//			throws MiningException {
//		super( settings);
//	}
//
//	public TransactionsCycleStep(EMiningFunctionSettings settings, Step ...steps)
//			throws MiningException {
//		super(settings, steps);
//	}
//
//	/**
//	 *
//	 */
//
//	@Override
//	protected EMiningModel initLoop(MiningInputStream inputData, EMiningModel model ) throws MiningException {
//		((AssociationRulesMiningModel) model).setCurrentTransaction(0);
//		//setStateParameter(model, AssociationRulesMiningModel.NAME_CURRENT_TRANSACTION, 0);
//		return model;
//	}
//
//	@Override
//	protected boolean conditionLoop(MiningInputStream inputData, EMiningModel model ) throws MiningException {
//		return ((AssociationRulesMiningModel) model).getCurrentTransaction() <
//				((AssociationRulesMiningModel) model).getTransactionList().size();
//
////		return (Integer)getStateParameter(model, AssociationRulesMiningModel.NAME_CURRENT_TRANSACTION) <
////				((AssociationRulesMiningModel) model).getTransactionList().size();
//	}
//
//	@Override
//	protected EMiningModel beforeIteration(MiningInputStream inputData, EMiningModel model ) throws MiningException {
//
//		return model;
//	}
//
//	@Override
//	protected EMiningModel afterIteration(MiningInputStream inputData, EMiningModel model ) throws MiningException {
//		((AssociationRulesMiningModel) model).setCurrentTransaction( ((AssociationRulesMiningModel) model).getCurrentTransaction() + 1);
////		setStateParameter(model, AssociationRulesMiningModel.NAME_CURRENT_TRANSACTION,
////				(Integer)getStateParameter(model, AssociationRulesMiningModel.NAME_CURRENT_TRANSACTION) + 1);
//
//		return model;
//	}
//
//
//}
