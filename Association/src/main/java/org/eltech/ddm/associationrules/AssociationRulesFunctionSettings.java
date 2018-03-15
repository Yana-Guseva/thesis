package org.eltech.ddm.associationrules;

import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningfunctionsettings.MiningFunction;

public class AssociationRulesFunctionSettings extends FrequentItemSetFunctionSettings {

	private final String TAG_NAME_ITEM_ID_ATTRIBUTE = "itemIDsAttributeName";
	
	private final String TAG_NAME_TRANSACTION_ID_ATTRIBUTE = "transactionIDsAttributeName";

	/**
	 * This specifies the minimum confidence value for each association rule to
	 * be found.
	 */
	private final String TAG_MIN_CONFIDIENCE = "minimumConfidence";

	/**
	 * This is the maximum length of the antecedent and consequent item attributes
	 * sizes.
	 */
	private final String TAG_MAX_RULE_LENGTH = "maximumRuleLength";
	
	
	public AssociationRulesFunctionSettings(ELogicalData ld) {
		super(ld);
		addTaggedValue(TAG_NAME_ITEM_ID_ATTRIBUTE, null, "String");
		addTaggedValue(TAG_NAME_TRANSACTION_ID_ATTRIBUTE, null, "String");
		addTaggedValue(TAG_MIN_CONFIDIENCE, null, "double");
		addTaggedValue(TAG_MAX_RULE_LENGTH, null, "int");
	}

	public double getMinConfidence() {
		String v = getTaggedValue(TAG_MIN_CONFIDIENCE);
		if(v == null) 
			return 0.1;
		else
			return Double.parseDouble(v);
	}

	public void setMinConfidence(double minimumConfidence) {
		setTaggedValue(TAG_MIN_CONFIDIENCE, String.valueOf(minimumConfidence)); 
	}

	@Override
	public MiningFunction getMiningFunction() {
		return MiningFunction.associationRules;
	}

	public void setItemIDsArributeName(String itemIDsAttributeName) {
		setTaggedValue(TAG_NAME_ITEM_ID_ATTRIBUTE, String.valueOf(itemIDsAttributeName)); 
	}

	public void setTransactionIDsArributeName(String transactionIDsAttributeName) {
		setTaggedValue(TAG_NAME_TRANSACTION_ID_ATTRIBUTE, String.valueOf(transactionIDsAttributeName)); 
	}
	
	public String getItemIDsAttributeName() {
		String v = getTaggedValue(TAG_NAME_ITEM_ID_ATTRIBUTE);
		if(v == null) 
			return "";
		else
			return v;
	}

	public String getTransactionIDsAttributeName() {
		String v = getTaggedValue(TAG_NAME_TRANSACTION_ID_ATTRIBUTE);
		if(v == null) 
			return "";
		else
			return v;
	}

	public int getMaximumRuleLength() {
		String v = getTaggedValue(TAG_MAX_RULE_LENGTH);
		if(v == null) 
			return 1;
		else
			return Integer.parseInt(v);
	}

	public void setMaximumRuleLength(int maximumRuleLength) {
		setTaggedValue(TAG_MAX_RULE_LENGTH, String.valueOf(maximumRuleLength)); 
	}
	
}
