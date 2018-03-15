/**
 * Core CWM TransformationActivity class implementation.
 * 
 * @author Evgeniy Krapivin
 * */

package org.eltech.ddm.transformation;

import java.util.ArrayList;
import java.util.List;

import org.jetel.connection.jdbc.DBConnection;

//import org.eltech.ddm.miningcore.MiningException;
import org.omg.java.cwm.analysis.transformation.*;


public abstract class MiningTransformationActivity extends TransformationActivity {
	
	/**
	 * Amount of opened active database connections.
	 * */
	protected int DBConnectionsCount = 0;
	
	/**
	 * Collection of unique transformations used in activity.
	 * */
	private List<MiningTransformation> usedTransformations = new ArrayList<MiningTransformation>();
	
    /**
     * Creates specific transformation by id and adds it to the transformation collection.
     * */
    public abstract MiningTransformation createTransformation(int aId) throws Exception;

    /**
     * Creates specific transformation task metadata.
     * */
    public abstract MiningTransformationMetadata createMetadata() throws Exception;

    /**
     * Creates specific transformation task and adds it to the transformation task collection.
     * */
    public abstract MiningTransformationTask createTask(MiningTransformationMetadata aMetadata) throws Exception;

    /**
     * Creates specific transformation step, initializes it and adds it to the activity.
     * */
    public abstract MiningTransformationStep createStep() throws Exception;

    /**
     * Executes activity transformation chain.
     * */
    public abstract void transform() throws Exception;
    
    /**
     * Notifies activity that all transformations are provided.
     * */
    public abstract void transformationsSupplied() throws Exception;
    
    // internal methods
    
    /**
     * Returns amount of opened active database connections.
     * */
    public int getDBConnectionCount() {
        return this.DBConnectionsCount;
    }

    /**
     * Adds database connection handle to connection pool.
     * */
    public abstract void addDBConnection(DBConnection aConnection);

    /**
     * Adds transformation to the collection of unique transformations used in activity.
     * */
    public boolean useTransformation(MiningTransformation aTransformation) {
        boolean usingTransformation = false;

        if(aTransformation != null) {
            if(!this.usedTransformations.contains(aTransformation)) {
            	usedTransformations.add(aTransformation);
            	usingTransformation = true;
            }
        }

        return usingTransformation;
    }
}