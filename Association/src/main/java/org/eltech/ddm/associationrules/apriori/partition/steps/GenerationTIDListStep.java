package org.eltech.ddm.associationrules.apriori.partition.steps;

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
import org.omg.java.cwm.analysis.datamining.miningcore.miningfunctionsettings.MiningFunctionSettings;
import org.omg.java.cwm.analysis.datamining.miningcore.miningmodel.MiningModel;

public class GenerationTIDListStep extends Step {

	public GenerationTIDListStep(EMiningFunctionSettings settings)
			throws MiningException {
		super(settings);
	}

	@Override
	protected EMiningModel execute(MiningInputStream inputData, EMiningModel model)
			throws MiningException {

		AssociationRulesMiningModel modelA = (AssociationRulesMiningModel)model;

		Transaction transaction =  modelA.getTransactionList().get(modelA.getCurrentTransaction());
				//(Integer)getStateParameter(model, AssociationRulesMiningModel.NAME_CURRENT_TRANSACTION));
		String itemID = transaction.getItemIDList().get(modelA.getCurrentItem());
				//(Integer)getStateParameter(model, AssociationRulesMiningModel.NAME_CURRENT_ITEM));
		Item item = ((AssociationRulesMiningModel)model).getItem(itemID);

		if(!item.getTidList().contains(transaction.getTID())){
			item.getTidList().add(transaction.getTID());
		}
		item.setSupportCount(item.getTidList().size());

		return model;
	}

}
