package org.eltech.ddm.handlers.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eltech.ddm.handlers.MiningExecutorFactory;
import org.eltech.ddm.handlers.ParallelExecutionException;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.algorithms.MiningExecutor;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;

public class ConcurrencyExecutorFactory extends MiningExecutorFactory {
    ExecutorService service;


    public ConcurrencyExecutorFactory(int numThreds) {
        service = Executors.newCachedThreadPool(); //newFixedThreadPool(numThreds);
    }

    @Override
    public MiningExecutor create(MiningBlock block) throws ParallelExecutionException {
        return new ConcurrencyMiningExecutor(block, service);
    }

    @Override
    public MiningExecutor create(MiningBlock block, MiningInputStream data) throws ParallelExecutionException {
        ConcurrencyMiningExecutor executor = new ConcurrencyMiningExecutor(block, service);
        executor.setData(data);
        return executor;
    }

}
