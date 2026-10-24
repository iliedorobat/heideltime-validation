package ro.webdata.normalization;

import de.unihd.dbs.heideltime.standalone.components.ResultFormatter;
import de.unihd.dbs.uima.types.heideltime.Timex3;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;

public class Timex3CsvFormatter implements ResultFormatter {
    @Override
    public String format(JCas jcas) throws Exception {
        StringBuilder csv = new StringBuilder();
        FSIterator<?> iterator =
                jcas.getAnnotationIndex(Timex3.type).iterator();

        while (iterator.hasNext()) {
            Timex3 timex = (Timex3) iterator.next();

            String sentence = jcas.getDocumentText().strip();
            String expression = sentence.substring(timex.getBegin(), timex.getEnd());
            String timexValue = timex.getTimexValue();

            // input value|type|prepared value|tid
            csv.append(sentence).append('|')
                    .append(escape(timex.getTimexType())).append('|')
                    .append(expression).append("|")
                    .append(filter(escape(timexValue), timex.getTimexType()))
                    .append('\n');
        }

        return csv.toString();
    }

    private static String filter(String value, String type) {
        if (type.equals("DURATION") || type.equals("SET"))
            return "";
        else if (value.equals("PRESENT_REF"))
            return "";
        else if (value.startsWith("XXXX"))
            return "";
        return value;
    }

    private static String escape(String value) {
        if (value == null) {
            return "";
        }

        // Quote fields containing the separator, quotes, or newlines.
        if (value.contains("|") || value.contains("\"")
                || value.contains("\n") || value.contains("\r")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }

        return value;
    }
}
