package ro.webdata.normalization.inp;

import de.unihd.dbs.heideltime.standalone.HeidelTimeStandalone;
import de.unihd.dbs.heideltime.standalone.exceptions.DocumentCreationTimeMissingException;
import ro.webdata.normalization.HeidelTime;
import ro.webdata.normalization.Timex3CsvFormatter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class InpParser {
    private static final String HEADER = "INP timespan|type|entity|normalized\n";

    public static String toCsvLine(String text) throws DocumentCreationTimeMissingException {
        Timex3CsvFormatter formatter = new Timex3CsvFormatter();
        HeidelTimeStandalone heidelTime = HeidelTime.getInstance();

        return heidelTime.process(text, formatter);
    }

    public static String toCsv(String filepath) throws DocumentCreationTimeMissingException, IOException {
        String content = Files.readString(Path.of(filepath));
        String[] expressions = content.split("\n");

        StringBuilder sb = new StringBuilder();
        sb.append(HEADER);

        for (String expression : expressions) {
            String value = InpParser.toCsvLine(expression);
            if (!value.isEmpty())
                sb.append(value);
            else
                sb.append(expression).append("|").append("|").append("|\n");
        }

        return sb.toString();
    }
}
