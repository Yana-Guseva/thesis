package org.eltech.ddm.associationrules;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.Arrays;
import java.util.List;

public class AssociationRulesMiningModel extends EMiningModel {

    private static final long serialVersionUID = 1L;

    public final static int ITEM_SET = 1;
    public final static int TRANSACTION_LIST_SET = 2;
    public final static int ASSOCIATION_RULE_SET = 3;

    public final static int CURRENT_ITEM = -1;
    public static final int[] INDEX_TRANSACTION_LIST_SET = {TRANSACTION_LIST_SET};
    public static final int[] INDEX_CURRENT_TRANSACTION_ITEM = {TRANSACTION_LIST_SET, CURRENT_ITEM};

    public AssociationRulesMiningModel(AssociationRulesFunctionSettings settings) throws MiningException {
        super(settings);
        sets.add(ITEM_SET, new ItemSet("itemSet"));
        sets.add(TRANSACTION_LIST_SET, new TransactionList("transactionList"));
        sets.add(ASSOCIATION_RULE_SET, new AssociationRuleSet("associationRuleSet") {
            @Override
            protected String propertiesToString() {
                return "";
            }

            @Override
            public void merge(List<MiningModelElement> elements) throws MiningException {

            }
        });
    }

    @Override
    public void initModel() throws MiningException {

    }

    public Item getItem(String itemId) throws MiningException {
        MiningModelElement items = getElement(EMiningModel.index(AssociationRulesMiningModel.ITEM_SET));
        return (Item) items.getElement(itemId);
    }

    public Item getItem(int transIndex, int itemIndex) throws MiningException {
        Transaction transaction = getTransaction(transIndex);
        String itemId = transaction.getItemIDList().get(itemIndex);
        return getItem(itemId);
    }

    public Transaction getTransaction(String transId) throws MiningException {
        MiningModelElement transactions = getElement(EMiningModel.index(AssociationRulesMiningModel.TRANSACTION_LIST_SET));
        return (Transaction) transactions.getElement(transId);
    }

    public Transaction getTransaction(int transIndex) throws MiningException {
        return (Transaction) getElement(EMiningModel.index(AssociationRulesMiningModel.TRANSACTION_LIST_SET, transIndex));
    }

    public AssociationRule getAssociationRule(String ruleId) throws MiningException {
        MiningModelElement associationRules = getElement(EMiningModel.index(AssociationRulesMiningModel.ASSOCIATION_RULE_SET));
        return (AssociationRule) associationRules.getElement(ruleId);
    }

    public MiningModelElement getAssociationRuleSet() throws MiningException {
        return getElement(EMiningModel.index(AssociationRulesMiningModel.ASSOCIATION_RULE_SET));
    }

    public int getCurrentTransactionIndex() {
        Integer index = currents.get(Arrays.toString(INDEX_TRANSACTION_LIST_SET));
        if (index == null)
            return -1;
        return index;
    }

    public int getCurrentItemIndex() {
        Integer index = currents.get(Arrays.toString(INDEX_CURRENT_TRANSACTION_ITEM));
        if (index == null)
            return -1;
        return index;
    }
}
