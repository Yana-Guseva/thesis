package org.eltech.ddm.transformation.etl.CloverETL.loading;

import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.transformation.MiningETLArrayStreamDataProvider;
import org.eltech.ddm.transformation.MiningETLArrayStreamInformer;

public class CloverETLInputStream implements MiningETLArrayStreamInformer{
	public MiningInputStream miningInputStream;

	@Override
	public void MiningETLArrayStreamPrepared(ELogicalData aLogicalData,
			MiningETLArrayStreamDataProvider aDataProvider) {
		try {
			miningInputStream =( new MiningETLArrayStream(aLogicalData,
					aDataProvider));
			System.out.println(miningInputStream.getLogicalData());
			
			miningInputStream.open();			
			
	        System.out.println("metaData: " + miningInputStream.getLogicalData().toString());
			MiningVector mv = miningInputStream.next();
	        while (mv != null) {
				System.out.println("vector: " + mv.toString());
				mv = miningInputStream.next();
			}
		} catch (MiningException e) {
			e.printStackTrace();
		} finally{
			if(miningInputStream.isOpen())
				try {
					miningInputStream.close();
				} catch (MiningException e) {
					e.printStackTrace();
				}
		}
	}
}
