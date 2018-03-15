package org.eltech.ddm.cluster.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.omg.java.cwm.objectmodel.core.*;

import java.lang.String;

public class ServiceMain {
    public static void main(String[] args) {

        try {
            String host = args[0];
            int port = Integer.decode(args[1]);
            int workers = Integer.decode(args[2]);
            int perNode = Integer.decode(args[3]);
            String seed = args[4];

            initSystem(host, port, workers, perNode, seed);

        } catch (Exception e) {
            System.out.println("Usage:");
            System.out.println("\t<app> <host> <port> <workerCount> <workerPerNodeCount> <seedNode: host:port>");
            System.out.println(e.toString());
        }

    }

    public static ActorSystem initSystem(String host, int port, int workerCount, int workerPerNode, String seedNode) {
        Config config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port);
        config = ConfigFactory.parseString("akka.remote.netty.tcp.hostname=" + host).withFallback(config);

        if (workerCount > 0) {
            config = ConfigFactory
                    .parseString("akka.actor.deployment./executorService/singleton/workerRouter.nr-of-instances = " + workerCount)
                    .withFallback(config);
        }

        if (workerPerNode > 0) {
            config = ConfigFactory
                    .parseString("akka.actor.deployment./executorService/singleton/workerRouter.cluster.max-nr-of-instances-per-node = " + workerPerNode)
                    .withFallback(config);
        }

        if (seedNode != null) {
            config = ConfigFactory
                    .parseString("akka.cluster.seed-nodes = [\"akka.tcp://MiningClusterSystem@" + seedNode + "\"]")
                    .withFallback(config);
        }

        config = config.withFallback(ConfigFactory.load());

        String systemName = config.getString("akka.cluster.system-name");
        ActorSystem system = ActorSystem.create(systemName, config);

        // singleton manager for executor service
//        ActorRef execService = system.actorOf(ClusterSingletonManager.defaultProps(
//                Props.create(ActorRouter.class),
//                "singleton",
//                PoisonPill.getInstance(),
//                null), ActorRouter.ACTOR_NAME);
//
//        ActorRef execServicePrx = system.actorOf(ClusterSingletonProxy.defaultProps(ActorRouter.ACTOR_PATH, null),
//                ActorRouter.ACTOR_NAME + "Proxy");

        // singleton manager for model exchanger
//        ActorRef modelExch = system.actorOf(ClusterSingletonManager.defaultProps(
//                Props.create(ModelExchanger.class),
//                "singleton",
//                PoisonPill.getInstance(),
//                null), ModelExchanger.ACTOR_NAME);

//        ActorRef modelExchPrx = system.actorOf(ClusterSingletonProxy.defaultProps(ModelExchanger.ACTOR_PATH, null),
//                ModelExchanger.ACTOR_NAME + "Proxy");

        return system;
    }
}
