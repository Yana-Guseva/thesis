package org.eltech.ddm.environment;

import org.eltech.ddm.handlers.MiningExecutorFactory;
import org.eltech.ddm.handlers.ParallelExecutionException;
import org.eltech.ddm.handlers.thread.ConcurrencyExecutorFactory;
import org.eltech.ddm.inputdata.file.common.FileSeparator;
import org.eltech.ddm.inputdata.file.csv.CsvFileSeparator;
import org.eltech.ddm.inputdata.file.csv.MiningCsvStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Custom CSV Execution environment provides performance boost in case of
 * parallel execution by separating input file into several input streams
 * Creates temporary files in order to be able to read from N resources.
 * <p>
 * Be aware: it's a user responsibility to clean up all temporary files
 * under project directory
 *
 * @author Evgenii Ray
 */
public class ConcurentCSVExecutionEnvironment extends ExecutionEnvironment {

    private int threadNumber = 1;
    private static final int START_POSITION = 0;
    private String mainDataFile;
    private FileSeparator<MiningCsvStream> separator = new CsvFileSeparator();

    /**
     * Main constructor for the environment
     *
     * @param file         - data file of the resource
     * @param threadNumber - count of threads to use
     * @throws ParallelExecutionException - in case of the parallel exec error
     */
    public ConcurentCSVExecutionEnvironment(String file, int threadNumber) throws ParallelExecutionException {
        this.mainDataFile = file;
        this.threadNumber = threadNumber;
        initEnvironment();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initEnvironment() {
        miningExecutorFactory = new ConcurrencyExecutorFactory(threadNumber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<MiningExecutor> createExecutors(MiningBlock block) throws MiningException {
        List<MiningExecutor> execs = new ArrayList<>();
        List<MiningCsvStream> streams = threadNumber == 1
                ? Collections.singletonList(new MiningCsvStream(mainDataFile, null, false))
                : separator.separate(mainDataFile, threadNumber);
        if (block instanceof MiningLoopVectors) {
            MiningLoopVectors bl = (MiningLoopVectors) block;
            for (int i = 0; i < threadNumber; i++) {
                MiningLoopVectors mlv = new MiningLoopVectors(bl.getFunctionSettings(), START_POSITION, streams.get(i).getVectorsNumber(), (MiningSequence) bl.getIteration().clone());
                MiningExecutor executor = getMiningExecutorFactory().create(mlv, streams.get(i));
                execs.add(executor);
            }
        } else {
            MiningExecutor executor;
            if (block.isDataBlock()) {
                executor = getMiningExecutorFactory().create(block, null);
            } else {
                executor = getMiningExecutorFactory().create(block);
            }
            execs.add(executor);
        }
        return execs;
    }

    /**
     * {@inheritDoc}
     */
    public void deploy(MiningAlgorithm algorithm) throws MiningException {
        mainExecutor = createExecutorTree(algorithm.getCentralizedParallelAlgorithm());
    }

    /**
     * {@inheritDoc}
     */
    public MiningExecutorFactory getMiningExecutorFactory() {
        return miningExecutorFactory;
    }
}
