package com.demo.common.util;

import com.demo.translation.persistence.enums.Tag;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class TranslationUtil {

    /**
     * Parses a comma-separated string of tags into a Set of Tag enums.
     *
     * @param tagString the string containing tags separated by commas
     * @return a Set of Tag enums, or null if the input is blank
     */
    public static Set<Tag> parseTags(String tagString) {
        if (isBlank(tagString)) {
            return null;
        }
        return Arrays.stream(tagString.split(","))
            .map(String::trim)
            .map(s -> Tag.valueOf(s.toUpperCase()))
            .collect(Collectors.toSet());
    }

    public static Set<Long> parseKeys(String keys) {
        if (isBlank(keys)) {
            return null;
        }
        return Arrays.stream(keys.split(","))
            .map(String::trim)
            .map(Long::valueOf)
            .collect(Collectors.toSet());
    }
}
