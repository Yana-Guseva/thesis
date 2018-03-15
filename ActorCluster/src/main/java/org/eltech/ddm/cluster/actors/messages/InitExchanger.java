package org.eltech.ddm.cluster.actors.messages;

import java.io.Serializable;

public class InitExchanger implements Serializable {
    private final int workersCount;

    public InitExchanger(int workersCount) {
        this.workersCount = workersCount;
    }

    public int getWorkersCount() {
        return workersCount;
    }
}
