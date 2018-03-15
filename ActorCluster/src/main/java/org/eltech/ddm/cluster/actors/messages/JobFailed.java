package org.eltech.ddm.cluster.actors.messages;

import org.eltech.ddm.miningcore.MiningException;

import java.io.Serializable;

public class JobFailed implements Serializable {
    private final MiningException exception;

    public JobFailed(MiningException e) {
        this.exception = e;
    }

    public MiningException getException() {
        return exception;
    }
}
