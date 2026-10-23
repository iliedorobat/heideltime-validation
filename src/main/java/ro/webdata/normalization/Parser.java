package ro.webdata.normalization;

import de.unihd.dbs.heideltime.standalone.HeidelTimeStandalone;
import de.unihd.dbs.heideltime.standalone.exceptions.DocumentCreationTimeMissingException;

public class Parser {
    public static String toCsvLine(String text) throws DocumentCreationTimeMissingException {
        Timex3CsvFormatter formatter = new Timex3CsvFormatter();
        HeidelTimeStandalone heidelTime = HeidelTime.getInstance();

        return heidelTime.process(text, formatter);
    }
}
