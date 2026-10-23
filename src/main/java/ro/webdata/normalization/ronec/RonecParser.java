package ro.webdata.normalization.ronec;

import de.unihd.dbs.heideltime.standalone.exceptions.DocumentCreationTimeMissingException;
import ro.webdata.normalization.Parser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static ro.webdata.normalization.ronec.RonecReader.extractTimeExpressions;
import static ro.webdata.normalization.ronec.RonecReader.load;

public class RonecParser {
    private static final String HEADER = "sentence|ronec timespan|tag_type|entity|normalized\n";

    public static String toCsv(String filepath) throws DocumentCreationTimeMissingException, IOException {
        List<RonecSentence> sentences = load(Path.of(filepath));
        StringBuilder sb = new StringBuilder();
        sb.append(HEADER);

        for (RonecSentence sentence : sentences) {
            for (RonecReader.Entity timex : extractTimeExpressions(sentence)) {
                String value = Parser.toCsvLine(timex.text());

                if (!value.isEmpty()) {
                    String[] rows = value.split("\n");

                    for (String row : rows) {
                        sb.append(sentence.getPlainSentence()).append("|").append(row).append("\n");
                    }
                } else {
                    sb.append(sentence.getPlainSentence()).append("|").append(timex).append("|").append("|").append("|\n");
                }
            }
        }

        return sb.toString();
    }
}
