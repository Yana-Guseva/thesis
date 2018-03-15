package org.eltech.ddm.handlers.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import org.eltech.ddm.cluster.actors.ActorRouter;
import org.eltech.ddm.cluster.actors.messages.ExecuteJob;
import org.eltech.ddm.cluster.actors.messages.ExecuteResult;
import org.eltech.ddm.cluster.actors.messages.JobFailed;
import org.eltech.ddm.handlers.ParallelExecutionException;
import org.eltech.ddm.miningcore.algorithms.MiningExecutor;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ActorMiningExecutor extends MiningExecutor {

    private final Inbox inbox;
    private final ActorRef actor;
    private final FiniteDuration timeout;


    public ActorMiningExecutor(MiningBlock block, ActorRef actor, ActorSystem system) throws ParallelExecutionException {
        super(block);
        this.actor = actor;
        inbox = Inbox.create(system);

        timeout = FiniteDuration.apply(100000, TimeUnit.MILLISECONDS);
//        ActorSelection selection = actorSystem.actorSelection(ActorRouter.ACTOR_PROXY);
//        Future<ActorRef> actorRefFuture = selection.resolveOne(timeout);
//
//        try {
//            actorRouter = Await.result(actorRefFuture, timeout);
//        } catch (Exception e) {
//            throw new ParallelExecutionException(e.getLocalizedMessage());
//        }
//        this.inbox = Inbox.create(actorSystem);
    }

    @Override
    public void start(EMiningModel model) throws ParallelExecutionException {
        //actor.tell(new ExecuteJob(model, block));
        inbox.send(actor, new ExecuteJob(model));
    }

    @Override
    public EMiningModel getModel() throws ParallelExecutionException {
        Object message = null;
        try {
            message = inbox.receive(timeout);
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        if (message instanceof ExecuteResult) {
            return ((ExecuteResult) message).getModel();
        } else if (message instanceof JobFailed) {
            ParallelExecutionException ex = new ParallelExecutionException("Actor get result failed.");
            ex.initCause(((JobFailed) message).getException());
            throw ex;
        } else {
            throw new ParallelExecutionException("Unsupported message received from the actor.");
        }
    }
}

