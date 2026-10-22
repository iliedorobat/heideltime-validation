package ro.webdata.normalization.ronec;

import ro.webdata.echo.commons.File;

import java.nio.file.Path;
import java.util.List;

import static ro.webdata.normalization.ronec.RonecReader.extractTimeExpressions;
import static ro.webdata.normalization.ronec.RonecReader.load;

public class RonecDemo {
    private static final String VALIDATION_PATH = File.PATH_FILES_DIR
            + File.FILE_SEPARATOR + "data"
            + File.FILE_SEPARATOR + "valid"
            + File.EXTENSION_SEPARATOR + File.EXTENSION_JSON;

    // TODO: implement the parser
    static void main(String[] args) throws Exception {
        Path path = Path.of(VALIDATION_PATH);
        List<RonecSentence> sentences = load(path);

        int entityCount = 0;

        for (RonecSentence sentence : sentences) {
            for (RonecReader.Entity entity : extractTimeExpressions(sentence)) {
                System.out.println(sentence.getPlainSentence());
                System.out.printf(
                        "sentence=%d, tokens=%d-%d, type=%s, text=%s%n",
                        entity.sentenceId(),
                        entity.startToken(),
                        entity.endToken(),
                        entity.type(),
                        entity.text()
                );
                System.out.println();

                entityCount++;
            }
        }

        System.out.printf(
                "%nSentences: %d%nEntities: %d%n",
                sentences.size(),
                entityCount
        );
    }
}
