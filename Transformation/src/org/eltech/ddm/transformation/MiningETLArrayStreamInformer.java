package org.eltech.ddm.transformation;

import org.eltech.ddm.miningcore.miningdata.ELogicalData;

public interface MiningETLArrayStreamInformer {
	void MiningETLArrayStreamPrepared(ELogicalData aLogicalData, MiningETLArrayStreamDataProvider aDataProvider);
}
