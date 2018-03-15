package org.eltech.ddm.inputdata.file;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.EAttributeAssignmentSet;
import org.eltech.ddm.miningcore.miningdata.EDirectAttributeAssignment;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningdata.EPhysicalData;
import org.eltech.ddm.miningcore.miningdata.PhysicalAttribute;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeType;

import javax.datamining.data.AttributeDataType;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Stream;

public class MiningCsvStream extends MiningFileStream {

    private CsvParser parser;
    private CsvParserSettings settings;


    public MiningCsvStream(String file, CsvParserSettings settings) throws MiningException {
        super(file);
        this.settings = settings;
        this.parser = new CsvParser(settings);
        if (logicalData == null) {
            physicalData = recognize();
        }
        open();

    }

    @Override
    public MiningVector readPhysicalRecord() {
        String[] row = parser.parseNext();
        if (row != null) {
            System.out.println(Arrays.toString(row));
            double[] values = Stream.of(row).mapToDouble(Double::valueOf).toArray();
            MiningVector vector = new MiningVector(values);
            vector.setLogicalData(logicalData);
            vector.setIndex(++cursorPosition);
            return vector;

        }
        return null;

    }

    @Override
    protected MiningVector movePhysicalRecord(int position) throws MiningException {
        MiningVector vector = null;
        for (int i = 0; i < position; i++) {
            vector = readPhysicalRecord();
            if (vector == null) {
                reset();
                vector = readPhysicalRecord();
            }
        }
        return vector;

    }

    @Override
    public void open() {
        this.parser.beginParsing(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(fileName)));
        this.open = true;
    }

    @Override
    public void close() {
        this.parser.stopParsing();
    }


    @Override
    public EPhysicalData recognize() throws MiningException {
        if (logicalData == null && physicalData == null && attributeAssignmentSet == null) {
            boolean wasOpen;
            if (!(wasOpen = isOpen())) {
                this.open();
            }
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


            if (wasOpen) {
                reset();
            } else {
                close();
            }
            this.settings.setHeaderExtractionEnabled(true);
            return physicalData;
        }
        return physicalData;
    }


}