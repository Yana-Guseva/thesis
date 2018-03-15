/**
 * CloverETL database reader node implementation of MiningTransformation.
 * Performs SQL-query on a given database and reads all records in input.
 * 
 * @author Evgeniy Krapivin
 * */

package org.eltech.ddm.transformation.etl.CloverETL.transformations;

import java.util.Properties;
import org.jetel.component.DBInputTable;
import org.jetel.connection.jdbc.DBConnection;
import org.eltech.ddm.transformation.*;
import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformation;

@SuppressWarnings("serial")
public class CloverETLInputDBTransformation extends CloverETLTransformation {
    
    private Properties nodeProperties = new Properties();
    private String SQLString = "";
    
	public CloverETLInputDBTransformation(MiningTransformationActivity aActivity) {
		if(outputPortCount == MiningTransformation.NO_TARGET_AVAILABLE_FOR_TRANSFORMATION)
			outputPortCount = 0;
		
		this.parentActivity = aActivity;
		
        this.id = String.format("node_input_db_%d", instanceCount++);

        this.nodeProperties.setProperty("passwordEncrypted", "false");
        this.nodeProperties.setProperty("threadSafeConnection", "true");	
	}
	
	@Override
	public void setParameter(int aId, String aValue) throws Exception {
        if(aId == MiningTransformation.TRANSFORMATION_PARAM_DB_URL)
            this.nodeProperties.setProperty("dbURL", aValue);
        else if(aId == MiningTransformation.TRANSFORMATION_PARAM_DB_TYPE)
            this.nodeProperties.setProperty("database", aValue);
        else if(aId == MiningTransformation.TRANSFORMATION_PARAM_JDBC_DRIVER)
            this.nodeProperties.setProperty("jdbcSpecific", aValue);
        else if(aId == MiningTransformation.TRANSFORMATION_PARAM_USERNAME)
            this.nodeProperties.setProperty("user", aValue);
        else if(aId == MiningTransformation.TRANSFORMATION_PARAM_PASSWORD)
            this.nodeProperties.setProperty("password", aValue);
        else if(aId == MiningTransformation.TRANSFORMATION_PARAM_SQL_STRING)
            this.SQLString = aValue;
	}

	@Override
	public void setParameter(int aId, int aValue) throws Exception {
		// no implementation
	}

	@Override
	public void init() throws Exception {
        if(this.parentActivity == null)
            throw new IllegalArgumentException("Field parentActivity: parent activity cannot be null.");

        if(this.SQLString.isEmpty())
            throw new IllegalAccessException("Field SQLString: SQL string cannot be empty.");

        // create database props & connection

        int connectionsCount = this.parentActivity.getDBConnectionCount();
        String DBConnectionId = String.format("DBConnection%d", connectionsCount);
        DBConnection dbConnection = new DBConnection(DBConnectionId, this.nodeProperties);

        dbConnection.init();
        dbConnection.setName(DBConnectionId);

        this.parentActivity.addDBConnection(dbConnection);

        // create node

        this.nodeObject = new DBInputTable(this.id, dbConnection.getName(), this.SQLString);
        ((DBInputTable) this.nodeObject).setPolicyType(org.jetel.exception.PolicyType.CONTROLLED);
	}
}
