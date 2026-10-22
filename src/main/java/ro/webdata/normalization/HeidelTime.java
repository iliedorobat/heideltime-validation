package ro.webdata.normalization;

import de.unihd.dbs.heideltime.standalone.DocumentType;
import de.unihd.dbs.heideltime.standalone.HeidelTimeStandalone;
import de.unihd.dbs.heideltime.standalone.OutputType;
import de.unihd.dbs.heideltime.standalone.POSTagger;
import de.unihd.dbs.uima.annotator.heideltime.resources.Language;

import java.nio.file.Path;

public class HeidelTime {
    public static HeidelTimeStandalone instance = null;

    public static HeidelTimeStandalone getInstance() {
        if (instance == null)
            instance = init();

        return instance;
    }

    private static HeidelTimeStandalone init() {
        Path config = Path.of(
                "src/main/resources/config.props"
        );

        HeidelTimeStandalone.readConfigFile(
                config.toAbsolutePath().toString()
        );

        return new HeidelTimeStandalone(
                Language.getLanguageFromString("auto-romanian"),
                DocumentType.NARRATIVES,
                OutputType.TIMEML,
                config.toString(),
//                POSTagger.TREETAGGER,
                POSTagger.NO,
                false
        );
    }
}
