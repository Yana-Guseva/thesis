package org.eltech.ddm.inputdata.file;

import com.univocity.parsers.common.ParsingContext;
import com.univocity.parsers.common.processor.AbstractRowProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.*;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeType;

import javax.datamining.data.AttributeDataType;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * This class is an adapter for CSV Parser from Univocity Team.
 * Basically, it encapsulates logic for reading CSV data effectively
 * without loading it into memory.
 * Parser could be configured in a different way such as:
 * <p>
 * 1) Read rows of the file (Horizontal Separation)
 * 2) Read columns of the file (Vertical Separation)
 * 3) Batching input in case of the large data
 * 4) Separate thread option in order not to lock the main thread
 * <p>
 * All settings could be found in {@link CsvParserSettings}
 * For different type of row processing please see implementation of
 *
 * @author etitkov
 * @see com.univocity.parsers.common.processor.RowProcessor
 * @see com.univocity.parsers.common.Context
 * @see com.univocity.parsers.common.processor.RowListProcessor
 * @see com.univocity.parsers.common.processor.ColumnProcessor
 */
public class MiningCsvStream extends MiningFileStream implements ClonableStream {

    /*
     * Required fields for using parser
     */
    private CsvParser parser;
    private final CsvParserSettings settings;

    /**
     * Default constructor with configuration provider. If configuration is {@code null}
     * the the default one will be used instead;
     *
     * @param file     - relative path to the data file
     * @param settings - parser setting to apply
     * @throws MiningException - in case of failure
     */
    public MiningCsvStream(String file, CsvParserSettings settings) throws MiningException {
        super(file);
        this.settings = settings;
        if (logicalData == null) {
            physicalData = recognize();
        }
    }

    /**
     * Default constructor with configuration provider. If configuration is {@code null}
     * the the default one will be used instead;
     *
     * @param file     - relative path to the data file
     * @param settings - parser setting to apply
     * @throws MiningException - in case of failure
     */
    public MiningCsvStream(String file,
                           CsvParserSettings settings,
                           ELogicalData logicalData,
                           EPhysicalData physicalData) throws MiningException {
        super(file, logicalData);
        this.physicalData = physicalData;
        this.settings = settings;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MiningVector readPhysicalRecord() {
        open();
        String[] row = parser.parseNext();
        if (row != null) {
            double[] values = Stream.of(row).mapToDouble(value -> value == null ? 0d : Double.valueOf(value)).toArray();
            MiningVector vector = new MiningVector(values);
            vector.setLogicalData(logicalData);
            vector.setIndex(++cursorPosition);
            return vector;
        }
        return null;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        cursorPosition = -1;
        parser.stopParsing();
        parser.beginParsing(getReader());
        open = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void open() {
        if (!isOpen()) {
            this.open = true;
            this.parser = new CsvParser(settings);
            this.parser.beginParsing(getReader());
        }
    }

    /**
     * Utility method for getting  input stream of the resource;
     *
     * @return - reader for parser
     */
    private Reader getReader() {
        return new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        this.open = false;
        this.parser.stopParsing();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EPhysicalData recognize() throws MiningException {
        if (logicalData == null && physicalData == null && attributeAssignmentSet == null) {
            open();
            logicalData = new ELogicalData();
            physicalData = new EPhysicalData();
            attributeAssignmentSet = new EAttributeAssignmentSet();
            for (String attrName : parser.getContext().parsedHeaders()) {
                ELogicalAttribute la = new ELogicalAttribute(attrName, AttributeType.numerical);
                PhysicalAttribute pa = new PhysicalAttribute(attrName, AttributeType.numerical, AttributeDataType.doubleType);
                EDirectAttributeAssignment da = new EDirectAttributeAssignment();
                logicalData.addAttribute(la);
                physicalData.addAttribute(pa);
                da.addLogicalAttribute(la);
                da.setAttribute(pa);
                attributeAssignmentSet.addAssignment(da);
            }
            close();
            return physicalData;
        }
        return physicalData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected MiningVector movePhysicalRecord(int position) throws MiningException {
        if (getCurrentPosition() > position) {
            reset();
        }
        return advancePosition(position);
    }

    /**
     * Advance current position forward to the  value passed in the
     * method.
     *
     * @param position - position to reach
     * @return - mining vector for reached position
     * @throws MiningException - in case of failure during file parsing
     */
    private MiningVector advancePosition(int position) throws MiningException {
        MiningVector mv;
        do {
            mv = next();
        }
        while ((mv != null) && (getCurrentPosition() < position));
        return mv;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getVectorsNumber() {
        if (vectorsNumber <= 0) {
            RowCountProcessor processor = new RowCountProcessor();
            CsvParserSettings settings = new CsvParserSettings();
            settings.setProcessor(processor);
            CsvParser parser = new CsvParser(settings);
            parser.parse(getReader());
            vectorsNumber = processor.rowCount;
        }
        return vectorsNumber;
    }

    @Override
    public MiningFileStream deepCopy() throws MiningException {
        MiningCsvStream stream = new MiningCsvStream(this.fileName, this.settings, logicalData, physicalData);
        stream.setVectorsNumber(this.vectorsNumber);
        return stream;
    }

    private static class RowCountProcessor extends AbstractRowProcessor {
        private int rowCount;

        @Override
        public void rowProcessed(String[] row, ParsingContext context) {
            if (!Arrays.equals(row, context.parsedHeaders())) {
                rowCount++;
            }
        }
    }

    private void setVectorsNumber(int number) {
        this.vectorsNumber = number;
    }

    @Override
    public String toString() {
        return "MiningCsvStream{" +
                ", currentPosition=" + cursorPosition +
                '}';
    }
}