package org.eltech.ddm.inputdata.file;

import com.univocity.parsers.common.AbstractWriter;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.file.common.FileSeparator;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class CsvFileSeparator implements FileSeparator {

    private static final String CHUNK_TEMPLATE = "temp%d.csv";
    private CsvWriterSettings writerSettings = getDefaultWriterSettings();

    @Override
    public List<MiningInputStream> separate(String filePath, int handlerNumber) throws FileNotFoundException {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setDelimiterDetectionEnabled(true);
        settings.setHeaderExtractionEnabled(true);
        CsvParser parser = new CsvParser(settings);

        List<DataOutputStream> list = new ArrayList<>(handlerNumber);

        for (int i = 0; i < handlerNumber; i++) {
            FileOutputStream fos = new FileOutputStream(String.format(CHUNK_TEMPLATE, i));
            list.add(new DataOutputStream(new BufferedOutputStream(fos)));
        }

        parser.beginParsing(getReader(filePath));
        String[] headers = parser.getContext().parsedHeaders();
        parser.stopParsing();

        List<CsvWriter> streams = list.stream()
                .map(OutputStreamWriter::new)
                .map(writer -> new CsvWriter(writer, writerSettings))
                .peek(writer -> writer.writeHeaders(headers))
                .collect(Collectors.toList());

        String[] row;
        Iterator<CsvWriter> iterator = streams.iterator();

        parser = new CsvParser(settings);
        parser.beginParsing(getReader(filePath));
        while ((row = parser.parseNext()) != null) {
            if (!iterator.hasNext()) {
                iterator = streams.iterator();
            }
            iterator.next().writeRow(row);
        }
        parser.stopParsing();
        streams.forEach(AbstractWriter::close);

        return null;
    }


    /**
     * Utility method for getting  input stream of the resource;
     *
     * @return - reader for parser
     */
    private Reader getReader(String fileName) {
        return new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }


    private CsvWriterSettings getDefaultWriterSettings() {
        CsvWriterSettings settings = new CsvWriterSettings();
        settings.setNullValue("0");
        settings.setEmptyValue("0");
        settings.setSkipEmptyLines(true);
        return settings;
    }


}
