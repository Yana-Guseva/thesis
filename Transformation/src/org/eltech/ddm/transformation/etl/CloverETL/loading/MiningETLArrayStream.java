/**
 * Extension of MiningInputStream for arrays, received from ETL-tools.
 * 
 * Class is used for large datasets that do not fit into memory.
 * It should be used only in that cases where algorithms performed on data are capable
 * to process data divided into batches.
 * 
 * @author Evgeniy Krapivin
 * */

package org.eltech.ddm.transformation.etl.CloverETL.loading;

import javax.datamining.data.AttributeDataType;

import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningDataException;
import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.EAttributeAssignmentSet;
import org.eltech.ddm.miningcore.miningdata.EDirectAttributeAssignment;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningdata.EPhysicalData;
import org.eltech.ddm.miningcore.miningdata.PhysicalAttribute;
import org.eltech.ddm.transformation.MiningETLArrayStreamDataProvider;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeType;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.CategoryProperty;

@SuppressWarnings("serial")
public class MiningETLArrayStream extends MiningInputStream implements Cloneable
{
	private MiningETLArrayStreamDataProvider dataProvider = null;
	
    /** Array of doubles. Could also be directly used for fast access. */
    public double[][] miningArray = null;

    /** Length of mining array **/
    protected int miningArrayLength = -1;
    
    public void setMiningArrayLength(int miningArrayLength) {
		this.miningArrayLength = miningArrayLength;
	}

	protected int recordsRead = -1;
    
    private int batchSize = 1 ;
    
    public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	/**
     * Constructs mining array stream from array of doubles whose meta data
     * is specified in metaData. In case of categorical attributes, the
     * double values are the keys.
     *
     * @param miningArray array of double values
     * @param metaData meta data of attributes
     * @exception MiningException could not create array stream
     */
    public MiningETLArrayStream(ELogicalData logicalData, MiningETLArrayStreamDataProvider aDataProvider) throws MiningException
    {
        this.logicalData = logicalData;
        this.dataProvider = aDataProvider;

//        miningArrayLength = getMiningArrayLength();
        cursorPosition = -1;

        this.open = true;
    }

    /**
     * Returns length of mining array.
     *
     * @return length of mining array, -1 if array is null
     */
    private int getMiningArrayLength() {
    	if (miningArray == null)
    		return -1;
    	else
    		return miningArray.length;
    }

    // -----------------------------------------------------------------------
    //  Getter and setter methods
    // -----------------------------------------------------------------------
    /**
     * Returns supported stream methods.
     *
     * @return supported stream methods
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public java.util.Enumeration getSupportedStreamMethods() {
      java.util.Vector suppmeth = new java.util.Vector();
      suppmeth.addElement("reset");
      suppmeth.addElement("recognize");

      return suppmeth.elements();
    }

    /**
     * Returns number of vectors of mining array.
     *
     * @return number of vectors
     */
    public int getVectorsNumber() throws MiningException {
    	return miningArrayLength;
    }

    /**
     * Returns data matrix of doubles.
     *
     * @return data matrix of doubles
     */
    public double[][] getMatrix() {
      return miningArray;
    }

    // -----------------------------------------------------------------------
    //  General stream methods
    // -----------------------------------------------------------------------
    /**
     * Opens mining array stream.
     *
     * @exception MiningException if a mining source access error occurs
     */
    public void open() throws MiningException {
      this.open = true;
      
      reset();
    }

    /**
     * Closes mining array stream.
     *
     * @exception MiningException if a mining source access error occurs
     */
    public void close() throws MiningException
    {
      if (!open)
        throw new MiningException(MiningErrorCode.INVALID_INPUT_DATA, "Stream is already closed");

      this.open = false;
    }
    
    public void setPhysicalData(EPhysicalData phData){
    	physicalData = phData;
    }
    
    public void setEAttributeAssignmentSet(EAttributeAssignmentSet ea){
    	attributeAssignmentSet = ea;
    }

    /**
     * Recognizes meta data of array.
     *
     * @return meta data of array
     */
    public EPhysicalData recognize() throws MiningException {
    	//comments
    	
        if(!this.isOpen())
          this.open();

        if(this.getCurrentPosition() != -1)
          throw new MiningDataException("Can't recognize if cursor is not before first row");
        
        if( physicalData == null )
        {
            reset();
            physicalData = new EPhysicalData();
            
            attributeAssignmentSet = new EAttributeAssignmentSet();
            physicalData.setName("relation");
            logicalData.setName("relation");
            
			for (int i = 0; i < logicalData.getAttributesNumber(); i++) {
				ELogicalAttribute la = new ELogicalAttribute();
				PhysicalAttribute pa = new PhysicalAttribute();
				EDirectAttributeAssignment da = new EDirectAttributeAssignment();

				la.setName(logicalData.getAttribute(i).getName());
				pa.setName(logicalData.getAttribute(i).getName());
				
				la.setAttributeType(logicalData.getAttribute(i).getAttributeType());
				pa.setAttributeType(logicalData.getAttribute(i).getAttributeType());
				
				if(logicalData.getAttribute(i).getAttributeType()==AttributeType.categorical)
				pa.setDataType(logicalData.getAttribute(i).getCategoricalProperties().getDataType());else
					pa.setDataType(AttributeDataType.doubleType);// to do

				la.getCategoricalProperties().addCategory(
						logicalData.getAttribute(i).getName(),
						CategoryProperty.valid);

				physicalData.addAttribute(pa);
				da.addLogicalAttribute(la);
				da.setAttribute(pa);
				attributeAssignmentSet.addAssignment(da);
			}
        }
        //comments
    	System.out.println("ph stream: "+physicalData.getAttributeCount());
        
        return physicalData;
    }

    // -----------------------------------------------------------------------
    //  Methods of cursor positioning
    // -----------------------------------------------------------------------
    /**
     * Set cursor before first row.
     *
     * @exception MiningException could not reset cursor
     */
    public void reset() throws MiningException {
        if (!open)
          throw new MiningException(MiningErrorCode.INVALID_INPUT_DATA, "Can't reset closed stream. Call open()");

        cursorPosition = -1;
        miningArrayLength = 1;//-1;
    }

    /**
     * Advance cursor by one position.
     *
     * @return true, if further rows exist, else false
     * @exception MiningException could not advance cursor
     */
    @Override
    public MiningVector readPhysicalRecord() throws MiningException {
   	
        if (!open)
        	throw new MiningException(MiningErrorCode.INVALID_INPUT_DATA,"Can't perform operation on closed stream. Call open()");

        cursorPosition++;
        recordsRead++;
        if (cursorPosition < getMiningArrayLength()){
        	return readVector();
    	}else {
        	this.miningArray = null;
        	try {
				this.miningArray = this.dataProvider.getArrayStreamData();
				this.miningArrayLength = getMiningArrayLength();
				if(this.miningArrayLength != -1) {
					cursorPosition = 0;
					return readVector();
				}
			} catch (Exception e) {
				throw new MiningException(MiningErrorCode.INVALID_INPUT_DATA, "Getting next data chunk failed.");
			}
        }

        return null;
    }

    /**
     * Move cursor to given position.
     *
     * @param position new position of the cursor
     * @return true if cursor could be positioned, false if not
     * @exception MiningException could not move cursor
     */
//    public MiningVector move(int position)throws MiningException  {
    public MiningVector movePhysicalRecord(int position)throws MiningException  {
    	//calculate position in batched stream
    	if(position >= batchSize)position = position % batchSize;
		
		MiningVector mv = null;
		if(getCurrentPosition() < position)
		{
			do{mv = next();}
			while((mv!= null) && (getCurrentPosition() < position));
		}
		else
		{
			reset();
			do{mv = next();}
			while((mv != null) && (getCurrentPosition() < position));
		};
//		if(getCurrentPosition() == position)
//			return mv;
//		else
//			return null;
		return mv;
    }

    // -----------------------------------------------------------------------
    //  Methods of reading from the stream
    // -----------------------------------------------------------------------
    /**
     * Read current mining vector.
     *
     * @return mining vector to read
     * @exception MiningException could not read vector
     */
    public MiningVector readVector() throws MiningException
    {
        if (!open)
          throw new MiningException(MiningErrorCode.INVALID_INPUT_DATA, "Can't perform operation on closed stream. Call open()");

        MiningVector vector = null;
        double[] values = new double[logicalData.getAttributesNumber()/*.getAttributes().size()*/];

        values = miningArray[getCurrentPosition()];

        vector = new MiningVector( values );
        vector.setLogicalData( logicalData );
        
        vector.setIndex(recordsRead);//.setId(recordsRead);

        return vector;
    }

    /**
     * Reads value at given coordinates.
     *
     * @param     rowNumber         the row number
     * @param     attributeIndex    the index of MiningAttribute to read value
     *
     * @return    value of MiningAttribute with index <code>attributeIndex</code> at
     *            <code>rowNumber</code> row
     * @exception MiningException if an error occurs
     */
    public double readAttributeValue(int rowNumber, int attributeIndex) throws MiningException {
    	if (!open)
    		throw new MiningException(MiningErrorCode.INVALID_INPUT_DATA, "Can't perform operation on closed stream. Call open()");

    	return miningArray[rowNumber][attributeIndex];
    }
    
    public Object clone() {
    	MiningETLArrayStream o = null;
		o = (MiningETLArrayStream) super.clone();
		
		if (miningArray != null) {
			o.miningArray = miningArray.clone();
			for (int i = 0; i < miningArray.length; i++)
				if (miningArray[i] != null)
					o.miningArray[i] = miningArray[i].clone();
		}

		return o;
	}
}
