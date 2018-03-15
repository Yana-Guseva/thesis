/**
 * Core mining transformation metadata class.
 * Describes common way of metadata operation in specific transformation tools.
 * 
 * @author Evgeniy Krapivin
 * */

package org.eltech.ddm.transformation;

import org.omg.java.cwm.objectmodel.core.Classifier;

@SuppressWarnings("serial")
public abstract class MiningTransformationMetadata extends Classifier {
	
    // field types
    public static int FIELD_TYPE_INTEGER = 0;
    public static int FIELD_TYPE_STRING = 1;
    public static int FIELD_TYPE_LOGIC = 2; // boolean
    public static int FIELD_TYPE_DOUBLE = 3;
    
    // metadata general properties
    public static int METADATA_PROP_SKIP_SOURCE_ROW = 0;
    public static int METADATA_PROP_FIELD_DELIMETER = 1;
    public static int METADATA_PROP_RECORD_DELIMETER = 2;
    
    /**
     * Adds data-record field description to the metadata description collection.
     * */
    public abstract void addFieldDescription(String aName, int aType);

    /**
     * Generalizes setting specific text parameters.
     * */
    public abstract void setParameter(int aId, String aValue);
    /**
     * Generalizes setting specific integer parameters.
     * */
    public abstract void setParameter(int aId, int aValue);

    /**
     * Returns internal object that represents metadata in specific tool.
     * */
    public abstract Object getObject();
}
