package org.eltech.ddm.handlers.actors;

import org.eltech.ddm.environment.DataDistribution;
import org.eltech.ddm.environment.NodeSettings;
import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActorsExecutionEnvironmentSettings implements Serializable, Cloneable {

    private static final long defaultTimeout = 1000 * 60 * 60;    // 1 hour by default

    private final long timeoutMillis = 1000;
    private NodeSettings seedNode;

    private DataDistribution dataDistribution;
    protected final List<NodeSettings> srcNodes;
    protected final List<NodeSettings> compNodes;

    public ActorsExecutionEnvironmentSettings(NodeSettings ...nodes) throws MiningException {
        compNodes = new ArrayList<>();
        srcNodes = new ArrayList<>();
        seedNode = nodes[0];
        for(NodeSettings node: nodes){
            if(node.getData() == null){
                compNodes.add(node);
            }else{
                srcNodes.add(node);
            }
        }
        if(srcNodes.size() < 1)
            throw new MiningException(MiningErrorCode.INVALID_INPUT_DATA, "There is not a data source.");
    }

    public long getTimeout() {
        return timeoutMillis;
    }

    public NodeSettings getSeedNode() {
        return seedNode;
    }

    public void setSeedNode(NodeSettings seedNode) {
        this.seedNode = seedNode;
    }

    public DataDistribution getDataDistribution() {
        return dataDistribution;
    }

    public void setDataDistribution(DataDistribution dataDistribution) {
        this.dataDistribution = dataDistribution;
    }

    public List<NodeSettings> getSrcNodes(){
        return srcNodes;
    }

    public List<NodeSettings> getCompNodes(){
        return compNodes;
    }
}
