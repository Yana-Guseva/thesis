package org.eltech.ddm.inputdata.file;

import org.eltech.ddm.miningcore.MiningException;

public interface ClonableStream {
    MiningFileStream deepCopy() throws MiningException;
}
