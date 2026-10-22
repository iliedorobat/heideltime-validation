package ro.webdata.normalization.inp;

import de.unihd.dbs.heideltime.standalone.exceptions.DocumentCreationTimeMissingException;
import ro.webdata.echo.commons.File;

import java.io.IOException;

public class InpDemo {
    private static final String PATH_ADDITIONAL_TIMESPAN_FILE = File.PATH_OUTPUT_DIR + File.FILE_SEPARATOR + "timespan_additional";
    private static final String PATH_ALL_TIMESPAN_FILE = File.PATH_OUTPUT_DIR + File.FILE_SEPARATOR + "timespan_all";
    private static final String PATH_UNIQUE_TIMESPAN_FILE = File.PATH_OUTPUT_DIR + File.FILE_SEPARATOR + "timespan_unique";

    private static final String PATH_INPUT_ADDITIONAL_TIMESPAN_FILE = PATH_ADDITIONAL_TIMESPAN_FILE +
            File.EXTENSION_SEPARATOR + File.EXTENSION_TXT;
    private static final String PATH_INPUT_ALL_TIMESPAN_FILE = PATH_ALL_TIMESPAN_FILE +
            File.EXTENSION_SEPARATOR + File.EXTENSION_TXT;
    private static final String PATH_INPUT_UNIQUE_TIMESPAN_FILE = PATH_UNIQUE_TIMESPAN_FILE +
            File.EXTENSION_SEPARATOR + File.EXTENSION_TXT;

    private static final String PATH_OUTPUT_ADDITIONAL_TIMESPAN_FILE = PATH_ADDITIONAL_TIMESPAN_FILE + "_heideltime" +
            File.EXTENSION_SEPARATOR + File.EXTENSION_CSV;
    private static final String PATH_OUTPUT_ALL_TIMESPAN_FILE = PATH_ALL_TIMESPAN_FILE + "_heideltime" +
            File.EXTENSION_SEPARATOR + File.EXTENSION_CSV;
    private static final String PATH_OUTPUT_UNIQUE_TIMESPAN_FILE = PATH_UNIQUE_TIMESPAN_FILE + "_heideltime" +
            File.EXTENSION_SEPARATOR + File.EXTENSION_CSV;

    static void main(String[] args) throws DocumentCreationTimeMissingException, IOException {
        String timespan_additional = InpParser.toCsv(PATH_INPUT_ADDITIONAL_TIMESPAN_FILE);
        String timespan_all = InpParser.toCsv(PATH_INPUT_ALL_TIMESPAN_FILE);
        String timespan_unique = InpParser.toCsv(PATH_INPUT_UNIQUE_TIMESPAN_FILE);

        File.write(timespan_additional, PATH_OUTPUT_ADDITIONAL_TIMESPAN_FILE, false);
        File.write(timespan_all, PATH_OUTPUT_ALL_TIMESPAN_FILE, false);
        File.write(timespan_unique, PATH_OUTPUT_UNIQUE_TIMESPAN_FILE, false);

        System.out.println();
    }
}
