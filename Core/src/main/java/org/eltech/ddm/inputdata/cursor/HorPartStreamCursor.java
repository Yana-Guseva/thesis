package org.eltech.ddm.inputdata.cursor;

import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;

public class HorPartStreamCursor extends PartStreamCursor {

	// start position for cursor in basic stream
	protected int startStreamPosition;

	// current position of cursor in basic stream
	protected int currentStreamCursorPosition;

	// end position for cursor in basic stream
	protected int endStreamPosition;

	// stem of moving
	protected int step;
	
	public HorPartStreamCursor(MiningInputStream stream, int startPosition,
			int endPosition, int step) throws MiningException {
		super(stream);
		
        this.logicalData    = stream.getLogicalData();

		this.startStreamPosition = startPosition;
		this.endStreamPosition = endPosition;
		this.cursorPosition = -1;
		this.currentStreamCursorPosition = startPosition;
		this.step = step;
	}
	
	@Override
	synchronized public void reset() throws MiningException {
		currentStreamCursorPosition = startStreamPosition;
		this.cursorPosition= 0;
	}

	@Override
	synchronized public MiningVector readPhysicalRecord() throws MiningException {
		int position = currentStreamCursorPosition + step;
		if (position >= stream.getVectorsNumber())
			return null;

		if (position > endStreamPosition)
			return null;

		currentStreamCursorPosition = position;
		MiningVector mv = stream.getVector(currentStreamCursorPosition);
	//	mv.setIndex(mv.getIndex() - getOffsetVectorIndex() );
		cursorPosition++;
		return mv;
	}

	@Override
	//synchronized protected MiningVector move(int pos) throws MiningException {
	synchronized protected MiningVector movePhysicalRecord(int pos) throws MiningException {
		int position = startStreamPosition + pos*step;
		if (position >= stream.getVectorsNumber())
			return null;

		if (position > endStreamPosition)
			return null;

		currentStreamCursorPosition = position;
		MiningVector mv = stream.getVector(currentStreamCursorPosition);
//		mv.setIndex(mv.getIndex() - getOffsetVectorIndex() );
		cursorPosition = pos;
		return mv;
	}
	
    /**
     * Determines the number of vectors. <p>
     *
     * In this most simple implementation, it utilizes the
     * reset and next methods. The cursor position is stored
     * into a temporary variable and finally recovered. <p>
     *
     * Most implementations of <code>MiningInputStream</code> will
     * overwrite this method.
     *
     * @return number of vectors
     * @exception MiningException method reset not implemented
     */
    public int getVectorsNumber() throws MiningException
    {
		return (endStreamPosition - startStreamPosition )/step + 1;

    }

	@Override
	public int getOffsetVectorIndex() {
		return startStreamPosition;
	}

	public int getCurrentStreamCursorPosition() {
		return currentStreamCursorPosition;
	}




}
