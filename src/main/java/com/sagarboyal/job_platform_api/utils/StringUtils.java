package com.sagarboyal.job_platform_api.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StringUtils {

    /**
     * Cleans the input text by:
     * - trimming leading/trailing spaces
     * - removing brackets (square, round, curly) and content inside them
     * - replacing multiple spaces with a single space
     *
     * @param text raw input string
     * @return cleaned string
     */
    public String cleanText(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        String cleaned = text.trim();

        // 2. Remove ONLY the brackets characters [ ] ( ) { }
        // but keep the content inside intact
        cleaned = cleaned.replaceAll("[\\[\\](){}]", "");
        cleaned = cleaned.replaceAll("\\s+", " ");

        return cleaned.trim();
    }

    public String cleanURL(String text) {
        if (text == null || text.isBlank()) return "";

        Matcher matcher = Pattern
                .compile("(https?://\\S+)", Pattern.CASE_INSENSITIVE)
                .matcher(text.trim());

        return matcher.find() ? matcher.group(1) : "";
    }

    public String cleanDescription(String text) {
        if (text == null || text.isBlank() || text.trim().isEmpty()) {
            return "No description available. Please refer to the official notification link below for complete details.";
        }
        return text.replaceAll("\\s+", " ").trim();
    }

    public boolean isBlank(String text) {
        return text == null || text.isBlank();
    }
}
