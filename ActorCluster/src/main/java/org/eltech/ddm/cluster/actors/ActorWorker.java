package org.eltech.ddm.cluster.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.eltech.ddm.cluster.actors.messages.ExecuteJob;
import org.eltech.ddm.cluster.actors.messages.ExecuteResult;
import org.eltech.ddm.cluster.actors.messages.JobFailed;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

public class ActorWorker extends UntypedAbstractActor {

    private ActorRef caller;

    private MiningBlock block;

    private MiningInputStream data;

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public ActorWorker(){

    }

    public ActorWorker(MiningBlock block){
        this.block = block;
    }

    public ActorWorker(MiningBlock block, MiningInputStream data){
        this.block = block;
        this.data = data;
    }

    public static Props props(MiningBlock block) {
        return Props.create(ActorWorker.class, block);
    }

    public static Props props(MiningBlock block,  MiningInputStream data) {
        return Props.create(ActorWorker.class, block, data);
    }

    @Override
    public void preStart() throws Exception {
        log.info("Starting " + this.getClass().getCanonicalName() + " with block " + block);
    }

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof ExecuteJob) {
            this.caller = sender();
            ExecuteJob msg = (ExecuteJob) o;
           // MiningInputStream inputStream = msg.getMiningInputStream();
            EMiningModel model = msg.getMiningModel();
            //MiningBlock block = msg.getBlock();
            try {
                EMiningModel resultModel = block.run(model);
                caller.tell(new ExecuteResult(resultModel), self());
            } catch (MiningException e) {
                caller.tell(new JobFailed(e), self());
            }
        } else
            unhandled(o);
    }
}


