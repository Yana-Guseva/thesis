package org.eltech.ddm.associationrules.apriori.steps;

import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.AssociationRulesMiningModel;
import org.eltech.ddm.associationrules.Item;
import org.eltech.ddm.associationrules.Transaction;
import org.eltech.ddm.associationrules.TransactionList;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.Step;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

public class BuildTransactionStep extends Step {

	protected final String itemIDsAttributeName;
	protected final String transactionIDsAttributeName;

	public BuildTransactionStep(EMiningFunctionSettings settings) throws MiningException {
		super(settings);

		itemIDsAttributeName = ((AssociationRulesFunctionSettings) settings).getLogicalData()
				.getAttribute(((AssociationRulesFunctionSettings) settings).getItemIDsAttributeName()).getName();
		transactionIDsAttributeName = ((AssociationRulesFunctionSettings) settings).getLogicalData()
				.getAttribute(((AssociationRulesFunctionSettings) settings).getTransactionIDsAttributeName()).getName();

	}


	@Override
	protected EMiningModel execute(MiningInputStream inputData, EMiningModel model ) throws MiningException {

		AssociationRulesMiningModel modelA = (AssociationRulesMiningModel) model;
		
		int currentVector = model.getCurrentVectorIndex(); //(Integer)getStateParameter(model, EMiningModel.NAME_CURRENT_VECTOR);
		MiningVector vector = inputData.getVector(currentVector);
		String transId = (String) vector.getValueCategory(transactionIDsAttributeName).getValue();
		String itemId = (String) vector.getValueCategory(itemIDsAttributeName).getValue();

		//System.out.println("Thread: " + Thread.currentThread().getName() + " transaction=" + transId + " item=" + itemId);

		Item item = createItem(itemId, modelA);
		Transaction transaction = createTransaction(transId, item, modelA);
		
		return modelA;
	}
	
	protected Transaction createTransaction(String transId, Item item, AssociationRulesMiningModel modelA){
		TransactionList transactions = modelA.getTransactionList();
		Transaction transaction = null;
		if (transactions.containsTransaction(transId)) {
			transaction = transactions.getTransaction(transId);
		} else {
			transaction = new Transaction();
			transaction.setTID(transId);
			transactions.add(transaction);
		}
		int currentTransaction = transactions.indexOf(transaction);
		modelA.setCurrentTransaction(currentTransaction);
		//setStateParameter(modelA, AssociationRulesMiningModel.NAME_CURRENT_TRANSACTION, currentTransaction);

		if(!transaction.getItemIDList().contains(item.getItemID())) // transaction has not duplicated items
			transaction.getItemIDList().add(item.getItemID());

		return transaction;
	}
	
	protected Item createItem(String itemId, AssociationRulesMiningModel modelA){
		Item item = modelA.getItem(itemId);
		if (item == null) {
			item = new Item(itemId);
			modelA.addItem(item);
		}	
		
		return item;
	}


}
