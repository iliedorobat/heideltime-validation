package ro.webdata.normalization.ronec;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RonecReader {
    public record Entity(
            int sentenceId,
            String text,
            String type,
            int startToken,
            int endToken
    ) {}

    public static List<RonecSentence> load(Path path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(
                path.toFile(),
                new TypeReference<>() {}
        );
    }

    public static List<Entity> extractTimeExpressions(RonecSentence sentence) {
        Set<String> dateTypes = Set.of("DATETIME", "DATE", "PERIOD");
        return extractEntities(sentence, dateTypes);
    }

    public static List<Entity> extractEntities(RonecSentence sentence) {
        return extractEntities(sentence, null);
    }

    public static List<Entity> extractEntities(RonecSentence sentence, Set<String> allowedTypes) {
        List<Entity> entities = new ArrayList<>();

        if (sentence.tokens == null || sentence.nerTags == null || sentence.spaceAfter == null) {
            throw new IllegalArgumentException("Missing tokens, ner_tags, or space_after");
        }

        int size = sentence.tokens.size();

        if (sentence.nerTags.size() != size || sentence.spaceAfter.size() != size) {
            throw new IllegalArgumentException(
                    "Mismatched token and annotation lengths " + "in sentence " + sentence.id
            );
        }

        int start = -1;
        String currentType = null;

        for (int i = 0; i < size; i++) {
            String tag = sentence.nerTags.get(i);

            if (tag == null) {
                throw new IllegalArgumentException("Null NER tag at token " + i);
            }

            if (tag.equals("O")) {
                if (start >= 0) {
                    entities.add(createEntity(sentence, start, i - 1, currentType));
                    start = -1;
                    currentType = null;
                }
                continue;
            }

            if (tag.startsWith("B-")) {
                if (start >= 0) {
                    entities.add(createEntity(sentence, start, i - 1, currentType));
                }

                start = i;
                currentType = tag.substring(2);
                continue;
            }

            if (tag.startsWith("I-")) {
                String type = tag.substring(2);

                // Recover gracefully if an I-tag starts a span
                // or follows an entity of a different type.
                if (start < 0 || !type.equals(currentType)) {
                    if (start >= 0) {
                        entities.add(createEntity(sentence, start, i - 1, currentType));
                    }

                    start = i;
                    currentType = type;
                }
                continue;
            }

            throw new IllegalArgumentException("Unknown NER tag: " + tag);
        }

        // Close an entity that reaches the end of the sentence.
        if (start >= 0) {
            entities.add(createEntity(sentence, start, size - 1, currentType));
        }

        // Filter entities by allowed types if specified
        if (allowedTypes != null && !allowedTypes.isEmpty()) {
            entities.removeIf(entity -> !allowedTypes.contains(entity.type()));
        }

        return entities;
    }

    private static Entity createEntity(RonecSentence sentence, int start, int end, String type) {
        StringBuilder text = new StringBuilder();

        for (int i = start; i <= end; i++) {
            text.append(sentence.tokens.get(i));

            // Reconstruct whitespace only between entity tokens.
            if (i < end && Boolean.TRUE.equals(
                    sentence.spaceAfter.get(i))) {
                text.append(' ');
            }
        }

        return new Entity(sentence.id, text.toString(), type, start, end);
    }
}
