package org.eltech.ddm.inputdata.cursor;

import java.util.Enumeration;

import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.EAttributeAssignmentSet;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningdata.EPhysicalData;

abstract public class PartStreamCursor extends MiningInputStream {
	// basic steam
	protected MiningInputStream stream;

	public PartStreamCursor(MiningInputStream stream)
			throws MiningException{

        // No input stream => exception:
        if( stream == null )
             throw new IllegalArgumentException( "MiningInputStream can't be null." );

        this.physicalData   = stream.getPhysicalData();

		this.stream = stream;
	}

    /**
     * Returns true if stream is open.
     *
     * @return boolean true if stream is open
     */
    public boolean isOpen()
    {
        return stream.isOpen();
    }

	@Override
	synchronized public void open() throws MiningException {
        if(!stream.isOpen())
        	throw new MiningException(MiningErrorCode.INVALID_INPUT_DATA,"Stream is already closed. You must open only basic stream.");
	}

	@Override
	synchronized public void close() throws MiningException {
        if(stream.isOpen())
        	throw new MiningException(MiningErrorCode.INVALID_INPUT_DATA,"Stream is already opened. You must close only basic stream.");
	}

	@Override
	synchronized public EPhysicalData recognize() throws MiningException {
		return stream.recognize();
	}

}