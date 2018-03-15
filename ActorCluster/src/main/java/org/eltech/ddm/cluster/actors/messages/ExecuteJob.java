package org.eltech.ddm.cluster.actors.messages;


import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import java.io.Serializable;

public class ExecuteJob implements Serializable {
    //private final MiningBlock block;
    private final EMiningModel miningModel;

    public ExecuteJob(EMiningModel model) {
        miningModel = model;
       //this.block = block;
    }

//    public MiningBlock getBlock() {
//        return block;
//    }

    public EMiningModel getMiningModel() {
        return miningModel;
    }

}


