package org.eltech.ddm.cluster.actors.messages;

import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import java.io.Serializable;


public class ExecuteResult implements Serializable {
    private final EMiningModel model;

    public ExecuteResult(EMiningModel model) {
        this.model = model;
    }

    public EMiningModel getModel() {
        return model;
    }
}
