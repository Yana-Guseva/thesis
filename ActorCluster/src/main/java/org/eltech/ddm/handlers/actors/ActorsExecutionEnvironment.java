package org.eltech.ddm.handlers.actors;

import akka.actor.*;
import com.typesafe.config.ConfigFactory;
import org.eltech.ddm.environment.DataDistribution;
import org.eltech.ddm.environment.ExecutionEnvironment;
import org.eltech.ddm.environment.NodeSettings;
import org.eltech.ddm.handlers.ParallelExecutionException;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.*;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import java.util.ArrayList;
import java.util.List;


public class ActorsExecutionEnvironment extends ExecutionEnvironment {

    private ActorsExecutionEnvironmentSettings settings;

    public ActorsExecutionEnvironment(ActorsExecutionEnvironmentSettings settings) throws ParallelExecutionException {
        super();
        this.settings = settings;
        initEnvironment();
    }

    @Override
    protected void initEnvironment() throws ParallelExecutionException {
        ActorSystem system = ActorSystem.create("MiningSystem", ConfigFactory.load());

        //ServiceMain.initSystem(settings.getHost(), settings.getPort(), settings.getTotalActors(), settings.getActorsPerNode(), settings.getSeedNode());

        ActorExecutionExecutorFactory actorFac = new ActorExecutionExecutorFactory(system, settings);
        miningExecutorFactory = actorFac;
    }

    public void deploy(MiningAlgorithm algorithm) throws MiningException {

        MiningSequence sequence = null;
        switch(settings.getDataDistribution()){
            case centralization:{
                sequence = algorithm.getCentralizedParallelAlgorithm();
                break;
            }
            case horizontal_distribution:{
                sequence = algorithm.getHorDistributedAlgorithm();
                break;
            }
            case vertical_distribution:{
                sequence = algorithm.getVerDistributedAlgorithm();
                break;
            }
        }

        if(sequence == null)
            throw new MiningException(MiningErrorCode.PARALLEL_EXECUTION_ERROR,
                    "The algorithm has not structure for data distribution " + settings.getDataDistribution());

        mainExecutor = createExecutorTree(sequence);
    }


    protected List<MiningExecutor> createExecutors(MiningBlock block) throws MiningException{
        List<MiningExecutor> execs = new ArrayList<>();
        if(block.isDataBlock()){
            for(NodeSettings node: settings.getSrcNodes()){
                MiningInputStream data = node.getData();
                MiningBlock mb;
                if (block instanceof MiningLoopVectors)
                    mb = new MiningLoopVectors(block.getFunctionSettings(), 0, data.getVectorsNumber(),
                            ((MiningLoop)block).getIteration());
                else if(block instanceof MiningLoopElement)
                     mb = new MiningLoopElement(block.getFunctionSettings(), ((MiningLoopElement)block).getIndexSet(),
                             ((MiningLoop)block).getIteration());
                else
                     mb = (MiningBlock)block.clone();
                MiningExecutor executor = getMiningExecutorFactory().create(mb, data); // TODO: create actor in data source node
                execs.add(executor);
            }
            return execs;
        }
        else{
            MiningExecutor executor = getMiningExecutorFactory().create(block); // TODO: create actor in computing node
            execs.add(executor);
        }

        return execs;
    }

}

