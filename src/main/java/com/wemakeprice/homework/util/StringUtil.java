package com.wemakeprice.homework.util;

import java.util.Arrays;
import java.util.List;

/**
 * Created by blurrymargo on 2018. 9. 3..
 */
public class StringUtil {

    public final static String NUMERIC_REGEX = "[^\\d]";
    public final static String ALPHABET_REGEX = "[^a-zA-Z]";

    public static List<String> splitByRegex(String value, String regex) {
        return Arrays.asList(value.replaceAll(regex, "").split(""));
    }

    private static List<String> splitOnlyNumeric(String str) {
        return StringUtil.splitByRegex(str, NUMERIC_REGEX);
    }

    private static List<String> splitOnlyAlphabet(String str) {
        return StringUtil.splitByRegex(str, ALPHABET_REGEX);
    }

}
