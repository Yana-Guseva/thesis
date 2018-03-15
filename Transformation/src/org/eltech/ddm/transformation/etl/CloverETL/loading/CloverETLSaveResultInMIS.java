package org.eltech.ddm.transformation.etl.CloverETL.loading;

import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.transformation.MiningETLArrayStreamDataProvider;
import org.eltech.ddm.transformation.MiningETLArrayStreamInformer;

public class CloverETLSaveResultInMIS implements MiningETLArrayStreamInformer {

	@Override
	public void MiningETLArrayStreamPrepared(ELogicalData aLogicalData,
			MiningETLArrayStreamDataProvider aDataProvider) {
		MiningInputStream min = null;
		try {
			min = new MiningETLArrayStream(aLogicalData,
					aDataProvider);
			min.open();			
			
	        System.out.println("metaData: " + min.getLogicalData().toString());
			MiningVector mv = min.next();
	        while (mv != null) {
				System.out.println("vector: " + mv.toString());
				mv = min.next();
			}	
		} catch (MiningException e) {
			e.printStackTrace();
		} finally {
			try {
				if(min!=null && min.isOpen())min.close();
			} catch (MiningException e) {
				e.printStackTrace();
			}
		}

	}
}
