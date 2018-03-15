/**
 * Core mining transformation activity factory class.
 * 
 * @author Evgeniy Krapivin
 * */

package org.eltech.ddm.transformation;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformationActivity;
import org.eltech.ddm.transformation.etl.Kettle.KettleTransformationActivity;

@SuppressWarnings("serial")
public class MiningTransformationFactory extends org.omg.java.cwm.objectmodel.core.Class {
	
    // activity types
    public static int ACTIVITY_TYPE_CLOVERETL = 0;
    public static int ACTIVITY_TYPE_KETTLE = 1;
//    public static int ACTIVITY_TYPE_TOS = 2;
    
	public static MiningTransformationActivity createActivity(int aId) throws MiningException {
		MiningTransformationActivity activity;

        if(aId == ACTIVITY_TYPE_CLOVERETL)
        	activity = new CloverETLTransformationActivity();
        else if (aId == ACTIVITY_TYPE_KETTLE)
            activity = new KettleTransformationActivity();
//        else if (aId == ACTIVITY_TYPE_TOS)
//            activity = new TOSTransformationActivity();
        else
            throw new IllegalArgumentException("Argument aId: unknown ETL transformation activity type.");

        return activity;		
	}
}
