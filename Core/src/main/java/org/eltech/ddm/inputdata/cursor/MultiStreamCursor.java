package org.eltech.ddm.inputdata.cursor;

import java.util.ArrayList;

import org.eltech.ddm.handlers.ParallelExecutionException;
import org.eltech.ddm.inputdata.DataSplitType;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.EPhysicalData;
import org.eltech.ddm.miningcore.miningfunctionsettings.ParallelType;

public class MultiStreamCursor extends MiningInputStream {

	ArrayList<MiningInputStream> streams;

	private int vectorsNumber = 0;

	public MultiStreamCursor(ArrayList<MiningInputStream> streams)
			throws MiningException{

        // No input stream => exception:
        if( streams == null )
             throw new IllegalArgumentException( "MiningInputStream can't be null." );

        this.logicalData    = streams.get(0).getLogicalData();
        this.physicalData   = streams.get(0).getPhysicalData();


		this.streams = streams;
	}

	public ArrayList<MiningInputStream> getStreams() {
		return streams;
	}

	@Override
	synchronized public void open() throws MiningException {
		for (MiningInputStream stream : streams) {
			stream.open();
		}
		open = true;
	}

	@Override
	synchronized public void close() throws MiningException {
		for (MiningInputStream stream : streams) {
			stream.close();
		}
		open = false;
	}

	@Override
	synchronized public EPhysicalData recognize() throws MiningException {
		for (MiningInputStream stream : streams) {
			stream.recognize();
		}
		return streams.get(0).getPhysicalData();
	}

	@Override
	synchronized public void reset() throws MiningException {
		for (MiningInputStream stream : streams) {
			stream.reset();
		}
		cursorPosition = -1;
	}

	@Override
	//synchronized public boolean next() throws MiningException {
	synchronized public MiningVector readPhysicalRecord() throws MiningException {
		if(cursorPosition >= getVectorsNumber()-1)
			return null;
		else {
			cursorPosition++;
			return readVector(getCurrentPosition());
		}
	}

	@Override
	//synchronized protected MiningVector move(int position) throws MiningException {
	synchronized protected MiningVector movePhysicalRecord(int position) throws MiningException {
		if(position >= getVectorsNumber()) // TODO
			return null;
		else {
			cursorPosition = position;
			return readVector(getCurrentPosition());
		}
	}

	private MiningVector readVector(int positionStream) throws MiningException {
		for (MiningInputStream stream : streams) {
			if(positionStream >= stream.getVectorsNumber())
				positionStream -= stream.getVectorsNumber();
			else
				return stream.getVector(positionStream);
		}
		return null;
	}


    /**
     * Determines the number of vectors in all streams. <p>
     *
     * @return number of vectors in all streams
     * @exception MiningException method reset not implemented
     */
    public int getVectorsNumber() throws MiningException
    {
		if(vectorsNumber <= 0)
		{
			vectorsNumber = 0;
			for (MiningInputStream stream : streams) {
				stream.setOffsetVectorIndex(vectorsNumber);
				vectorsNumber += stream.getVectorsNumber();
			}
		}
    	return vectorsNumber;

    }

    public ArrayList<MiningInputStream> split(int handlerCount, DataSplitType type) throws MiningException {

		return getStreams();
	}
}
