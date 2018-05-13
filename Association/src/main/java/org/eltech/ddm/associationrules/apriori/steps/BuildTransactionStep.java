package org.eltech.ddm.associationrules.apriori.steps;

import org.eltech.ddm.associationrules.*;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.DataMiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

public class BuildTransactionStep extends DataMiningBlock {

    private final String itemIDsAttributeName;
    private final String transactionIDsAttributeName;

    /**
     * Constructor of algorithm's calculation step (not using data set)
     *
     * @param settings - settings for build model
     */
    public BuildTransactionStep(EMiningFunctionSettings settings) throws MiningException {
        super(settings);

        itemIDsAttributeName = ((AssociationRulesFunctionSettings) settings).getItemIDsAttributeName();
        transactionIDsAttributeName = ((AssociationRulesFunctionSettings) settings).getTransactionIDsAttributeName();
    }

    @Override
    protected EMiningModel execute(MiningInputStream data, EMiningModel model) throws MiningException {
        AssociationRulesMiningModel modelA = (AssociationRulesMiningModel) model;
        MiningVector mv = data.getVector(model.getCurrentVectorIndex());
        String transId = String.valueOf(mv.getValue(transactionIDsAttributeName));
        String itemId = String.valueOf(mv.getValue(itemIDsAttributeName));

        Transaction transaction = createTransaction(transId, itemId, modelA);
//        System.out.println(Thread.currentThread().getName() + " " + transId + " " + itemId);

        return model;
    }

    private Transaction createTransaction(final String transId, final String itemId, AssociationRulesMiningModel model) throws MiningException {
        ItemSet items = (ItemSet) model.getElement(EMiningModel.index(AssociationRulesMiningModel.ITEM_SET));
        Item item = (Item) items.createOrGetElement(itemId);
        TransactionList transactions = (TransactionList) model.getElement(EMiningModel.index(AssociationRulesMiningModel.TRANSACTION_LIST_SET));
        Transaction transaction = (Transaction) transactions.createOrGetElement(transId);
        if (!transaction.getItemIDList().contains(item.getItemID())) { // transaction has not duplicated items
            transaction.addItem(item);
        }
        return transaction;
    }
}
