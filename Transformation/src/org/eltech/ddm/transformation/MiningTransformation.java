/**
 * Core CWM Transformation class implementation.
 * 
 * @author Evgeniy Krapivin
 * */

package org.eltech.ddm.transformation;

//import org.eltech.ddm.miningcore.MiningException;
import org.omg.java.cwm.analysis.transformation.*;

@SuppressWarnings("serial")
public abstract class MiningTransformation extends Transformation {

	// source/target transformations availability
	public static int NO_SOURCE_AVAILABLE_FOR_TRANSFORMATION = -1;
    public static int NO_TARGET_AVAILABLE_FOR_TRANSFORMATION = -1;
    
    // transformation types
    public static int TRANSFORMATION_TYPE_INPUT_DB = 1;
    public static int TRANSFORMATION_TYPE_INPUT_FILE = 2;
    public static int TRANSFORMATION_TYPE_MERGE = 10;
    public static int TRANSFORMATION_TYPE_REFORMAT = 11;
    public static int TRANSFORMATION_TYPE_OUTPUT_FILE = 20;
    public static int TRANSFORMATION_TYPE_OUTPUT_MAS = 25;
    
    public static int TRANSFORMATION_TYPE_TRANSACTION_GENERATOR = 77;//
    
    
    //test
    public static int TRANSFORMATION_TYPE_EMPTY = 99;
    public static int TRANSFORMATION_TYPE_XML_GRAPH_IN = 666;//
    public static int TRANSFORMATION_TYPE_INPUTXLS_FILE = 777;
    public static int TRANSFORMATION_TYPE_TRASH = 888;
    public static int TRANSFORMATION_TYPE_PHYSICALDATA = 999;
    
    // transformations parameters
    public static int TRANSFORMATION_PARAM_FILENAME = 0;
    public static int TRANSFORMATION_PARAM_USERNAME = 1;
    public static int TRANSFORMATION_PARAM_PASSWORD = 2;
    public static int TRANSFORMATION_PARAM_CHARSET = 5;
    public static int TRANSFORMATION_PARAM_METHOD_NAME = 8;
    public static int TRANSFORMATION_PARAM_DB_URL = 10;
    public static int TRANSFORMATION_PARAM_DB_TYPE = 11;
    public static int TRANSFORMATION_PARAM_JDBC_DRIVER = 12;
    public static int TRANSFORMATION_PARAM_SQL_STRING = 13;
    public static int TRANSFORMATION_PARAM_MERGE_KEY = 20;
    public static int TRANSFORMATION_PARAM_DATABATCHSIZE = 30;
    
    // assignments
    public static int ASSIGNMENT_TRANSFORMATION = 31;
    public static int DIRECT_ASSIGNMENT_TRANSFORMATION = 32;
    public static int PIVOT_ASSIGNMENT_TRANSFORMATION = 33;
    public static int REVERSE_PIVOT_ASSIGNMENT_TRANSFORMATION = 34;
    public static int SET_ASSIGNMENT_TRANSFORMATION = 35;
    
    // charsets
    public static String CHARSET_UTF8 = "UTF-8";
    public static String CHARSET_CP1251 = "windows-1251";
  
    /**
     * Generalizes setting specific text parameters.
     * */
    public abstract void setParameter(int aId, String aValue) throws Exception;
    /**
     * Generalizes setting specific integer parameters.
     * */
    public abstract void setParameter(int aId, int aValue) throws Exception;
    
    // internal methods
    
    /**
     * Initializes transformation object having all minimal-necessary settings provided.
     * */
    public abstract void init() throws Exception;

    /**
     * Returns internal object that represents transformation in specific tool.
     * */
    public abstract Object getObject();
}