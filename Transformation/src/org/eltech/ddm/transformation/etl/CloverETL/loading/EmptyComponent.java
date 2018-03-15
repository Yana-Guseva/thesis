package org.eltech.ddm.transformation.etl.CloverETL.loading;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

import org.jetel.data.DataField;
import org.jetel.data.DataFieldFactory;
import org.jetel.data.DataRecord;
import org.jetel.data.Defaults;
import org.jetel.exception.ComponentNotReadyException;
import org.jetel.exception.ConfigurationProblem;
import org.jetel.exception.ConfigurationStatus;
import org.jetel.graph.InputPortDirect;
import org.jetel.graph.Node;
import org.jetel.graph.OutputPortDirect;
import org.jetel.graph.Result;
import org.jetel.graph.TransformationGraph;
import org.jetel.metadata.DataFieldMetadata;
import org.jetel.metadata.DataRecordMetadata;
import org.jetel.util.SynchronizeUtils;
import org.jetel.util.string.StringUtils;
import org.jetel.data.StringDataField;

/**
 * This component uses for connecting CloverETL graph and graph created in
 * project Transformation.
 * 
 * @author SemenchenkoA
 *
 */
public class EmptyComponent extends Node {

	public final static String COMPONENT_TYPE = "NEW_COMPONENT";

	private final static int READ_FROM_PORT = 0;

	private final static int WRITE_TO_PORT = 0;

	private ByteBuffer recordBuffer;

	public EmptyComponent(String id) {
		super(id);
	}

	public String getType() {
		return COMPONENT_TYPE;
	}

	public void init() throws ComponentNotReadyException {
		if (isInitialized())
			return;
		super.init();

		recordBuffer = ByteBuffer
				.allocateDirect(Defaults.Record.FIELD_LIMIT_SIZE);
		if (recordBuffer == null) {
			throw new ComponentNotReadyException(
					"Can NOT allocate internal record buffer ! Required size:"
							+ Defaults.Record.FIELD_LIMIT_SIZE);
		}
	}

	@Override
	public Result execute() throws Exception {
		InputPortDirect inPort = (InputPortDirect) getInputPort(READ_FROM_PORT);
		// OutputPortDirect outPort = (OutputPortDirect)
		// getOutputPort(WRITE_TO_PORT);
		// System.out.println(inPort.getMetadata().toString());

		DataRecord inRecord1 = new DataRecord(inPort.getMetadata());
		inRecord1.init();
		while (inPort.readRecord(inRecord1) != null && runIt) {
			// in this comments contains needed functions for change values
			// and change meta data, no remove this

			// System.out.println("field num: "+inRecord1.getNumFields());
			// System.out.println(inRecord1.getField(0).toString());
			// System.out.println(inRecord1.getField(1).toString());

			// outPort.writeRecord(inRecord1);

			// inRecord1.getField(0).setValue("my_new_value_hoho");
			// inRecord1.getField(1).setValue(99);
			// DataRecordMetadata metaNew = new
			// DataRecordMetadata(inPort.getMetadata().getField(0).toString());
			// metaNew.addField(new DataFieldMetadata("text","\t"));
			// DataRecordMetadata metaRecord = new DataRecordMetadata("meta",
			// DataRecordMetadata.DELIMITED_RECORD);

			// metaRecord.addField(new DataFieldMetadata("text",
			// DataFieldMetadata.STRING_FIELD, null));
			// metaNew.delField(1);
			// DataRecord inRecord2 = new DataRecord(metaRecord);
			// inRecord2.init();
			// inRecord2.getField(0).setValue("my_new_value_hoho");
			// inRecord2.getField(1).setValue(99);

			writeRecordBroadcast(inRecord1);
			SynchronizeUtils.cloverYield();
		}

		return runIt ? Result.FINISHED_OK : Result.ABORTED;
	}

	@Override
	public synchronized void reset() throws ComponentNotReadyException {
		super.reset();
	}

	@Override
	public ConfigurationStatus checkConfig(ConfigurationStatus status) {
		super.checkConfig(status);

		if (!checkInputPorts(status, 1, 1)
				|| !checkOutputPorts(status, 1, Integer.MAX_VALUE)) {
			return status;
		}
		checkMetadata(status, getInMetadata(), getOutMetadata(), false);

		try {
			init();
		} catch (ComponentNotReadyException e) {
			ConfigurationProblem problem = new ConfigurationProblem(
					e.getMessage(), ConfigurationStatus.Severity.ERROR, this,
					ConfigurationStatus.Priority.NORMAL);
			if (!StringUtils.isEmpty(e.getAttributeName())) {
				problem.setAttributeName(e.getAttributeName());
			}
			status.add(problem);
		} finally {
			free();
		}

		return status;
	}
}
