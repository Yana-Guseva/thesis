package org.eltech.ddm.associationrules.apriori.steps;

import org.eltech.ddm.associationrules.*;
import org.eltech.ddm.associationrules.apriori.AprioriMiningModel;
import org.eltech.ddm.associationrules.dhp.DHPModel;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.List;

import static org.eltech.ddm.miningcore.miningmodel.EMiningModel.index;

public class CreateLarge1ItemSetStep extends MiningBlock {

	final protected double minSupport;

	public CreateLarge1ItemSetStep(EMiningFunctionSettings settings) throws MiningException {
		super(settings);
		minSupport = ((AssociationRulesFunctionSettings)settings).getMinSupport();
	}

	@Override
	protected EMiningModel execute(EMiningModel model) throws MiningException {
		DHPModel modelA = (DHPModel) model;
		HashMapMiningModelElement hashTable = modelA.getHashTable(modelA.getCurrentHashTableIndex() + 1);
		if (hashTable == null) {
			hashTable = new HashTable(String.valueOf(modelA.getCurrentHashTableIndex() + 1));
            model.addElement(index(DHPModel.HASH_TABLE_SET), hashTable);
		}
		Item item = modelA.getItem(modelA.getCurrentTransactionIndex(), modelA.getCurrentItemIndex());
		double supp = calcSupport(item, modelA);
		if(supp >= minSupport){
            ItemSet itemSet = (ItemSet) hashTable.getElement(item.getID());
            if (itemSet == null) {
                itemSet = new ItemSet(item);
                hashTable.put(item.getID(), itemSet);
            }
            itemSet.setSupportCount(item.getSupportCount());
		}
 		return modelA;
	}


	protected double calcSupport(Item item, AprioriMiningModel modelA) throws MiningException {
		// TODO Auto-generated method stub
		return ((double)item.getSupportCount()) /
                ((double)modelA.getElement(index(AssociationRulesMiningModel.TRANSACTION_LIST_SET)).size());
	}

}