package org.eltech.ddm.inputdata.cursor;

import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.LogicalData;

public class VertPartStreamCursor extends PartStreamCursor {
	
	// first attribute in basic stream
	protected int firstAttr;

	// last attribute in basic stream
	protected int lastAttr;

	public VertPartStreamCursor(MiningInputStream stream, int firstAttr, int lastAttr) throws MiningException {
		super(stream);

       	this.firstAttr = (firstAttr < 0)? 0 : firstAttr;
        this.lastAttr = (lastAttr > stream.getLogicalData().getAttributesNumber())? stream.getLogicalData().getAttributesNumber(): lastAttr;

        this.logicalData = new ELogicalData();
        
        for(int i = firstAttr; i <= lastAttr; i++){
        	ELogicalAttribute attribute = stream.getLogicalData().getAttribute(i);
        	this.logicalData.addAttribute(attribute);
        }
    	//TODO: It is stub. Target attribute need to be determinted by name? but not as last attribte 
        this.logicalData.addAttribute(stream.getLogicalData().getAttribute(stream.getLogicalData().getAttributesNumber() - 1));
	}

	@Override
	public void reset() throws MiningException {
		stream.reset();
		
	}

	@Override
	public MiningVector readPhysicalRecord() throws MiningException {
		MiningVector mv = stream.next();
			
		return cutMiningVector(mv);
	}

	@Override
	//protected MiningVector move(int position) throws MiningException {
	protected MiningVector movePhysicalRecord(int position) throws MiningException {
		MiningVector mv = stream.getVector(position);
		
		return cutMiningVector(mv);
	}
	
	@Override
	public MiningVector getVector(int rowNumber) throws MiningException {
		 MiningVector mv = stream.getVector(rowNumber);
		
		 return cutMiningVector(mv);
	}
	
	private MiningVector cutMiningVector(MiningVector mv){
		if(mv == null)
			return null;
		
		double[] values = new double[lastAttr-firstAttr+2];
		for(int i = firstAttr; i <= lastAttr; i++){
			values[i-firstAttr] = mv.getValue(i);
		}
		values[lastAttr-firstAttr+1] = mv.getValue(mv.getValues().length - 1);
		
		MiningVector result = new MiningVector(values);
		result.setLogicalData(logicalData);
		return result;		
	}

	
    public int getVectorsNumber() throws MiningException {
    	return stream.getVectorsNumber();
    }
	
	
}
