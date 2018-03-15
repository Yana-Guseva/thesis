package org.eltech.ddm.cluster.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.FromConfig;
import org.eltech.ddm.cluster.actors.messages.ExecuteJob;

public class ActorRouter extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static final String ACTOR_NAME = "executorService";
    public static final String ACTOR_PATH = "/user/" + ACTOR_NAME + "/singleton";
    public static final String ACTOR_PROXY = "/user/" + ACTOR_NAME + "Proxy";

    ActorRef actorsWorker;

    public ActorRouter() {
        actorsWorker = getContext().actorOf(FromConfig.getInstance().props(Props.create(ActorWorker.class)),
                "workerRouter");
    }

    @Override
    public void preStart() throws Exception {
        log.info("Starting " + this.getClass().getCanonicalName());
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof ExecuteJob) {
            actorsWorker.tell(message, sender());
        } else {
            unhandled(message);
        }
    }
}

