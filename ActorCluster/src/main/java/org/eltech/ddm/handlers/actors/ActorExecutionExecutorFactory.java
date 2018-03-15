package org.eltech.ddm.handlers.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import org.eltech.ddm.cluster.actors.ActorWorker;
import org.eltech.ddm.handlers.MiningExecutorFactory;
import org.eltech.ddm.handlers.ParallelExecutionException;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.algorithms.MiningExecutor;

public class ActorExecutionExecutorFactory extends MiningExecutorFactory {

    private final ActorSystem actorSystem;

    private int countActor;

    public ActorExecutionExecutorFactory(ActorSystem actorSystem, ActorsExecutionEnvironmentSettings settings) {
        this.actorSystem = actorSystem;
    }

    @Override
    public MiningExecutor create(MiningBlock block) throws ParallelExecutionException {

        ActorRef actor = actorSystem.actorOf(ActorWorker.props(block), "ActorWorker"+countActor++);

        return new ActorMiningExecutor(block, actor, actorSystem);
    }


    @Override
    public MiningExecutor create(MiningBlock block, MiningInputStream data) throws ParallelExecutionException {

        ActorRef actor = actorSystem.actorOf(ActorWorker.props(block, data), "ActorWorker"+countActor++);
        ActorMiningExecutor executor = new ActorMiningExecutor(block, actor, actorSystem);
        executor.setData(data);
        return executor;
    }

}
