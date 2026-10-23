package ro.webdata.normalization.ronec;

import ro.webdata.echo.commons.File;

public class RonecDemo {
    private static final String DATA_PATH = File.PATH_FILES_DIR
            + File.FILE_SEPARATOR + "data";

    private static final String VALID_INPUT_PATH = DATA_PATH + File.FILE_SEPARATOR + "valid"
            + File.EXTENSION_SEPARATOR + File.EXTENSION_JSON;
    private static final String TEST_INPUT_PATH = DATA_PATH + File.FILE_SEPARATOR + "test"
            + File.EXTENSION_SEPARATOR + File.EXTENSION_JSON;

    private static final String VALID_OUTPUT_PATH = File.PATH_OUTPUT_DIR + File.FILE_SEPARATOR
            + "ronec_validdation" + File.EXTENSION_SEPARATOR + File.EXTENSION_CSV;
    private static final String TEST_OUTPUT_PATH = File.PATH_OUTPUT_DIR + File.FILE_SEPARATOR
            + "ronec_test" + File.EXTENSION_SEPARATOR + File.EXTENSION_CSV;

    static void main(String[] args) throws Exception {
        String ronec_validation = RonecParser.toCsv(VALID_INPUT_PATH);
        File.write(ronec_validation, VALID_OUTPUT_PATH, false);

        String ronec_test = RonecParser.toCsv(VALID_INPUT_PATH);
        File.write(ronec_test, VALID_OUTPUT_PATH, false);
    }
}
