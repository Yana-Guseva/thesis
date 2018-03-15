package org.eltech.ddm.associationrules;

import java.util.ArrayList;





import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.Category;
import org.omg.java.cwm.analysis.datamining.miningcore.miningfunctionsettings.MiningAlgorithmSettings;

/**
 * CWM Class
 * 
 * This is a subclass of MiningFunctionSettings that specifies the parameters
 * specific to frequent itemset algorithms.
 * 
 * @author Ivan Holod
 * 
 */
public abstract class FrequentItemSetFunctionSettings<T extends MiningAlgorithmSettings>
		extends EMiningFunctionSettings {

	/**
	 * This specifies the minimum support of each frequent itemset to be found.
	 */
	private final String TAG_MIN_SUPPORT = "minimumSupport";

	/**
	 * This specifies the maximum number of items to be included in any frequent
	 * itemset to be found.
	 */
	private final String TAG_MAX_SET_SIZE = "maximumSetSize";
	
	/**
	 * This represents a attributes of items to be excluded from consideration during
	 * the execution of frequent itemset algorithm.
	 */
	private ArrayList<Category> exclusion;
	
	public FrequentItemSetFunctionSettings(ELogicalData ld) {
		super(ld);
		addTaggedValue(TAG_MIN_SUPPORT, null, "double");
		addTaggedValue(TAG_MAX_SET_SIZE, null, "int");
		
	}


	public double getMinSupport() {
		String v = getTaggedValue(TAG_MIN_SUPPORT);
		if(v == null) 
			return 0.1;
		else
			return Double.parseDouble(v);
	}

	public void setMinSupport(double minimumSupport) {
		setTaggedValue(TAG_MIN_SUPPORT, String.valueOf(minimumSupport)); 
	}
	
}
