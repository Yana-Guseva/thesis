package org.eltech.ddm.handlers;

import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.algorithms.MiningExecutor;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;

public abstract class MiningExecutorFactory {


    public abstract MiningExecutor create(MiningBlock block) throws ParallelExecutionException;

    public abstract MiningExecutor create(MiningBlock block, MiningInputStream data) throws ParallelExecutionException;

}
