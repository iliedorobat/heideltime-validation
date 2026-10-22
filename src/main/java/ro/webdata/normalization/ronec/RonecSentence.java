package ro.webdata.normalization.ronec;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RonecSentence {
    public int id;

    public List<String> tokens;

    @JsonProperty("ner_tags")
    public List<String> nerTags;

    @JsonProperty("ner_ids")
    public List<Integer> nerIds;

    @JsonProperty("space_after")
    public List<Boolean> spaceAfter;

    public String getPlainSentence() {
        if (this.tokens == null || this.tokens.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < this.tokens.size(); i++) {
            result.append(this.tokens.get(i));

            // Add space if spaceAfter is defined and true for this token
            if (this.spaceAfter != null && i < this.spaceAfter.size() && this.spaceAfter.get(i)) {
                result.append(" ");
            }
        }

        return result.toString();
    }
}
