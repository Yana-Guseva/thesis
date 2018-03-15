//package org.eltech.ddm.cluster.actors;
//
//import akka.actor.ActorRef;
//import akka.actor.UntypedActor;
//import akka.event.Logging;
//import akka.event.LoggingAdapter;
//import org.eltech.ddm.cluster.actors.messages.InitExchanger;
//import org.eltech.ddm.cluster.actors.messages.JobFailed;
//import org.eltech.ddm.cluster.actors.messages.MergeJob;
//import org.eltech.ddm.cluster.actors.messages.MergeResult;
//import org.eltech.ddm.miningcore.MiningException;
//import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Hashtable;
//import java.util.Map;
//
//public class ModelExchanger extends UntypedActor {
//
//    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
//
//    @Override
//    public void preStart() throws Exception {
//        log.info("Starting " + this.getClass().getCanonicalName());
//    }
//
//    public static final String ACTOR_NAME = "modelExchanger";
//    public static final String ACTOR_PATH = "/user/" + ACTOR_NAME + "/singleton";
//    public static final String ACTOR_PROXY = "/user/" + ACTOR_NAME + "Proxy";
//
//    //ActorRef[] actors;
//    private int workersCount;
//
//    Hashtable<ActorRef, EMiningModel> models = new Hashtable<ActorRef, EMiningModel>();
//
//    @Override
//    public void onReceive(Object o) throws Exception {
//        if (o instanceof InitExchanger) {
//            workersCount = ((InitExchanger) o).getWorkersCount();
//        } else if (o instanceof MergeJob) {
//            EMiningModel model = ((MergeJob) o).getModel();
//            models.put(sender(), model);
//            if (models.size() == workersCount) {
//                // models collected, merge
//                Collection<EMiningModel> values = models.values();
//                ArrayList<EMiningModel> mergeModels = new ArrayList<EMiningModel>(values.size());
//                for (EMiningModel value : values) mergeModels.add(value);
//
//                EMiningModel mdl = mergeModels.get(mergeModels.size() - 1);
//
//                try {
//                    mdl.join(mergeModels);
//                    for (Map.Entry<ActorRef, EMiningModel> entry : models.entrySet()) {
//                        entry.getKey().tell(new MergeResult(mdl), self());
//                    }
//                } catch (MiningException e) {
//                    for (Map.Entry<ActorRef, EMiningModel> entry : models.entrySet()) {
//                        entry.getKey().tell(new JobFailed(e), self());
//                    }
//                }
//                models.clear();
//            }
//
//
//        } else {
//            unhandled(o);
//        }
//    }
//}
