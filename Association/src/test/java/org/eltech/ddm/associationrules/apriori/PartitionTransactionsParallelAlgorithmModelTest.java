package org.eltech.ddm.associationrules.apriori;

import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.apriori.partition.PartitionTransactionsParallelAlgorithm;
import org.eltech.ddm.environment.ConcurrencyExecutionEnvironment;
import org.eltech.ddm.inputdata.DataSplitType;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.DataProcessingStrategy;
import org.eltech.ddm.miningcore.miningfunctionsettings.MiningModelProcessingStrategy;
import org.eltech.ddm.miningcore.miningtask.EMiningBuildTask;

public class PartitionTransactionsParallelAlgorithmModelTest extends AprioriModelTest {

	@Override
	protected AprioriMiningModel buildModel(AssociationRulesFunctionSettings miningSettings, MiningInputStream inputData) throws MiningException {
		miningSettings.getAlgorithmSettings().setDataSplitType(DataSplitType.block);
		miningSettings.getAlgorithmSettings().setDataProcessingStrategy(DataProcessingStrategy.SeparatedDataSet);
		miningSettings.getAlgorithmSettings().setModelProcessingStrategy(MiningModelProcessingStrategy.SeparatedMiningModel);
		
		ExecutionEnvironmentSettings executionEnvironmentSettings = new ExecutionEnvironmentSettings();
		executionEnvironmentSettings.setNumberHandlers(2);

		ConcurrencyExecutionEnvironment environment = new ConcurrencyExecutionEnvironment(executionEnvironmentSettings);
		
		PartitionTransactionsParallelAlgorithm algorithm = new PartitionTransactionsParallelAlgorithm(miningSettings);

		EMiningBuildTask buildTask = new EMiningBuildTask();
		buildTask.setInputStream(inputData);
		buildTask.setMiningAlgorithm(algorithm); 
		buildTask.setMiningSettings(miningSettings);
		buildTask.setExecutionEnvironment(environment);
		AprioriMiningModel model = (AprioriMiningModel) buildTask.execute();
		
		return model;
	}

}
