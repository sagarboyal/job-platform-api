package com.sagarboyal.job_platform_api.aggregator.utils;

import org.springframework.stereotype.Component;

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

}
