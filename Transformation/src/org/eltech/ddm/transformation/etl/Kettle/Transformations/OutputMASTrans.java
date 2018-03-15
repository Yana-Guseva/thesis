package org.eltech.ddm.transformation.etl.Kettle.Transformations;

import java.util.ArrayList;
import java.util.List;

import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.transformation.MiningETLArrayStreamDataProvider;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeType;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.CategoryProperty;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStep;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;

public class OutputMASTrans extends BaseStep implements StepInterface, MiningETLArrayStreamDataProvider
{
	private static Class<?> PKG = OutputMASTrans.class; // for i18n purposes, needed by Translator2!!   $NON-NLS-1$
	
	private ELogicalData logicalData = null;
	private List<List<Double>> physicalDataTemp = null;
	private OutputMASTransMeta meta = null;
	private int cursorPos = 0;
	
	public OutputMASTrans(StepMeta stepMeta, StepDataInterface stepDataInterface, int copyNr, TransMeta transMeta, Trans trans)
	{
		super(stepMeta, stepDataInterface, copyNr, transMeta, trans);
	}
	
	public boolean processRow(StepMetaInterface smi, StepDataInterface sdi) throws KettleException
	{
		Object[] r=getRow(); // get row, set busy!
		
		if (r != null && first)
		{
			try {
				meta = (OutputMASTransMeta) smi;
				
				prepareLogicalData(getInputRowMeta());
				
				first = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (r==null)  // no more input to be expected...
		{
			meta.getMiningInformer().MiningETLArrayStreamPrepared(logicalData, this);
			
			setOutputDone();
			
			return false;
		}
		
		try {
			accumulateRow(r, getInputRowMeta());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		putRow(getInputRowMeta(), r);     // copy row to possible alternate rowset(s).

        if (checkFeedback(getLinesRead())) 
        {
        	if(log.isBasic()) logBasic(BaseMessages.getString(PKG, "DummyTrans.Log.LineNumber")+getLinesRead()); //$NON-NLS-1$
        }
			
		return true;
	}
	
	private void prepareLogicalData(RowMetaInterface aMeta) throws Exception {
		logicalData = new ELogicalData();
		
//		String[] a = aMeta.getFieldNames();
		List<ValueMetaInterface> b = aMeta.getValueMetaList();
		
		for(int i = 0; i < b.size(); i++) {
//			String attributeName = String.format("attr_%s", fieldName);
			ValueMetaInterface field = b.get(i);
			
			String attributeName = field.getName();
			int fieldMetaType = field.getType();
			
			ELogicalAttribute attribute;
			if(fieldMetaType == ValueMetaInterface.TYPE_INTEGER)
				attribute = new ELogicalAttribute(attributeName, AttributeType.numerical);
			else if(fieldMetaType == ValueMetaInterface.TYPE_BOOLEAN) // treat as categorial field
				attribute = new ELogicalAttribute(attributeName, AttributeType.categorical); 
			else if(fieldMetaType == ValueMetaInterface.TYPE_STRING)
				attribute = new ELogicalAttribute(attributeName, AttributeType.categorical);
			else if(fieldMetaType == ValueMetaInterface.TYPE_NUMBER)
				attribute = new ELogicalAttribute(attributeName, AttributeType.numerical);
			else
				throw new Exception("Unknown metadata field type: " + fieldMetaType);
			
			logicalData.addAttribute(attribute);
		}
	}
	
	private void accumulateRow(Object[] r, RowMetaInterface aMeta) throws Exception {
		int attributeCount = logicalData.getAttributesNumber();
		
		if(physicalDataTemp == null) {
			physicalDataTemp = new ArrayList<List<Double>>(attributeCount);
			for(int i = 0; i < attributeCount; i++)
				physicalDataTemp.add(new ArrayList<Double>());
		}
		
		List<ValueMetaInterface> valueMetaList = aMeta.getValueMetaList();
		
		for(int i = 0; i < r.length; i++) {
			if(r[i] == null)
				continue;
			
			ValueMetaInterface field = valueMetaList.get(i);
			int fieldMetaType = field.getType();
			
			if(fieldMetaType == ValueMetaInterface.TYPE_INTEGER) {
				// integer attr
				
				Integer integerAttrValue = (Integer) r[i];
				
				physicalDataTemp.get(i).add(new Double(integerAttrValue));
			}
			else if(fieldMetaType == ValueMetaInterface.TYPE_NUMBER) {
				Double doubleAttrValue = (Double) r[i];
				
				physicalDataTemp.get(i).add(doubleAttrValue);
			}
			else if(fieldMetaType == ValueMetaInterface.TYPE_STRING || 
					fieldMetaType == ValueMetaInterface.TYPE_BOOLEAN) {
				// logical attr treated as categorial
				// categorial attr
				
				String categorialAttrValue;
				if(fieldMetaType == ValueMetaInterface.TYPE_STRING)
					categorialAttrValue = (String) r[i];
				else
					categorialAttrValue = ((Boolean) r[i]).toString();
				
				// find corresponding categorial attr
				
				ELogicalAttribute categorialAttribute = logicalData.getAttribute(field.getName());
				if(categorialAttribute.getAttributeType() != AttributeType.categorical)
					throw new Exception("Incompatible attribute type with name: " + field.getName());
				
				// get cetegory collection
				
				Integer categoryIndex = categorialAttribute.getCategoricalProperties().getIndex(categorialAttrValue);
				if(categoryIndex == null) // add to category collection
					categoryIndex = categorialAttribute.getCategoricalProperties().addCategory(categorialAttrValue, CategoryProperty.valid);
				
				physicalDataTemp.get(i).add(new Double(categoryIndex));
			}
		}
	}

	@Override
	public double[][] getArrayStreamData() throws Exception {
		int attributeCount = logicalData.getAttributesNumber();
		int recordCount = physicalDataTemp.get(0).size();
		int batchSize = meta.getBatchSize();
		
		double[][] miningSourceArray = null;
		
		if(batchSize <= 0 || batchSize > recordCount)
			batchSize = recordCount;
		
		if(recordCount != 0 && cursorPos < recordCount) {
			miningSourceArray = new double[batchSize][attributeCount];
			for(int attributeIndex = 0; attributeIndex < attributeCount; attributeIndex++) {
				for(int cursorPosition = 0; cursorPosition < batchSize; cursorPosition++)
					miningSourceArray[cursorPosition][attributeIndex] = physicalDataTemp.get(attributeIndex).get(cursorPos + cursorPosition);
			}
		}
		
		cursorPos += batchSize;
		
		return miningSourceArray;
	}
}