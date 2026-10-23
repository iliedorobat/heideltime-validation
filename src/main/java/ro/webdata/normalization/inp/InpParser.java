package ro.webdata.normalization.inp;

import de.unihd.dbs.heideltime.standalone.exceptions.DocumentCreationTimeMissingException;
import ro.webdata.normalization.Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class InpParser {
    private static final String HEADER = "INP timespan|type|entity|normalized\n";

    public static String toCsv(String filepath) throws DocumentCreationTimeMissingException, IOException {
        String content = Files.readString(Path.of(filepath));
        String[] expressions = content.split("\n");

        StringBuilder sb = new StringBuilder();
        sb.append(HEADER);

        for (String timex : expressions) {
            String value = Parser.toCsvLine(timex);

            if (!value.isEmpty()) {
                String[] rows = value.split("\n");

                for (String row : rows) {
                    sb.append(row).append("\n");
                }
            } else {
                sb.append(timex).append("|").append("|").append("|\n");
            }
        }

        return sb.toString();
    }
}
