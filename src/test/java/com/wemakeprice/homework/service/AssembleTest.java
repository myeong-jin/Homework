package com.wemakeprice.homework.service;

import com.wemakeprice.homework.support.SortByAlphabetAndNumericComparator;
import com.wemakeprice.homework.util.StringUtil;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by blurrymargo on 2018. 9. 3..
 *
 * 리팩토링 후 제거하는 테스트들도 다 남겨두었습니다.
 * 그래서...테스트마다 중복코드가 많습니다..
 *
 */
public class AssembleTest {

    final String NUMERIC_REGEX = "[^\\d]";
    final String ALPHABET_REGEX = "[^a-zA-Z]";
    final String BUNDLE_REGEX = "(?<=\\G.{{{BUNDLE_SIZE}}})";

    static String INPUT_VALUES;
    static String RAW_NUMERIC;
    static String RAW_ALPHABET;
    static String SORTED_NUMERIC;
    static String SORTED_ALPHABET;
    static String FULL_SORTED_TEXT;
    static int BUNDLE_SIZE;
    static String BUNDLED_TEXT;

    @BeforeClass
    public static void setUp() throws Exception {
//        setTestValues_1();
//        setTestValues_2();
//        setTestValues_3();
        setTestValues_4();
    }

    private static void setTestValues_1(){
        INPUT_VALUES = "  !@# 8 a79A || | } { +_) 3(*---||}}{?><:\"  &^  %$!@#  $%^~`` \n y \t m \r Y  0x z Cc                ";
        RAW_NUMERIC = "87930";
        RAW_ALPHABET = "aAymYxzCc";
        SORTED_NUMERIC = "03789";
        SORTED_ALPHABET = "AaCcmxYyz";
        FULL_SORTED_TEXT = "A0a3C7c8m9xYyz";
        BUNDLE_SIZE = 3;
        BUNDLED_TEXT = "A0a,3C7,c8m,9xY,yz";
    }
    private static void setTestValues_2(){
        INPUT_VALUES = "87930";
        RAW_NUMERIC = "87930";
        RAW_ALPHABET = "";
        SORTED_NUMERIC = "03789";
        SORTED_ALPHABET = "";
        FULL_SORTED_TEXT = "03789";
        BUNDLE_SIZE = 3;
        BUNDLED_TEXT = "037,89";
    }
    private static void setTestValues_3(){
        INPUT_VALUES = "aAymYxzCc";
        RAW_NUMERIC = "";
        RAW_ALPHABET = "aAymYxzCc";
        SORTED_NUMERIC = "";
        SORTED_ALPHABET = "AaCcmxYyz";
        FULL_SORTED_TEXT = "AaCcmxYyz";
        BUNDLE_SIZE = 3;
        BUNDLED_TEXT = "AaC,cmx,Yyz";
    }
    private static void setTestValues_4(){
        INPUT_VALUES = "+_)(*&^%$#@!~|}{\":?><;\r\n\t";
        RAW_NUMERIC = "";
        RAW_ALPHABET = "";
        SORTED_NUMERIC = "";
        SORTED_ALPHABET = "";
        FULL_SORTED_TEXT = "";
        BUNDLE_SIZE = 3;
        BUNDLED_TEXT = "";
    }

    @Test
    public void extractOnlyNumericTest(){

        String onlyNumeric = this.getOnlyNumeric(INPUT_VALUES);

        assertEquals(RAW_NUMERIC, onlyNumeric);
    }


    @Test
    public void extractOnlyAlphabetTest(){

        String onlyAlphabet = this.getOnlyAlphabet(INPUT_VALUES);

        assertEquals(RAW_ALPHABET, onlyAlphabet);
    }

    @Test
    public void sortByNumericTest(){
        List<String> list = StringUtil.splitByRegex(INPUT_VALUES, NUMERIC_REGEX);

        Collections.sort(list);

        assertEquals(SORTED_NUMERIC, String.join("", list));
    }

    @Test
    public void sortByAlphabetTest_1(){
        String onlyAlphabet = this.getOnlyAlphabet(INPUT_VALUES);

        List<String> list = Arrays.asList(onlyAlphabet.split(""));
        Collections.sort(list, new SortByAlphabetAndNumericComparator());

        assertEquals(SORTED_ALPHABET, String.join("", list));
    }

    @Test
    public void sortByAlphabetTest_2(){
        List<String> list = StringUtil.splitByRegex(INPUT_VALUES, ALPHABET_REGEX);

        Collections.sort(list, new SortByAlphabetAndNumericComparator());

        assertEquals(SORTED_ALPHABET, String.join("", list));
    }

    @Test
    public void mixTest(){

        String fullSortedText = this.getFullSortedText();

        assertEquals(FULL_SORTED_TEXT, fullSortedText.toString());
    }

    @Test
    public void bundleTest(){

        String fullSortedText = this.getFullSortedText();

        String[] bundledText = this.getBundledTextBy(fullSortedText, BUNDLE_SIZE);

        assertEquals(BUNDLED_TEXT, String.join(",", bundledText));
    }

    private String getFullSortedText(){
        List<String> alphabets = StringUtil.splitByRegex(INPUT_VALUES, ALPHABET_REGEX);
        List<String> numerics = StringUtil.splitByRegex(INPUT_VALUES, NUMERIC_REGEX);
        Collections.sort(numerics);
        Collections.sort(alphabets, new SortByAlphabetAndNumericComparator());

        Iterator<String> alphabetsIterator = alphabets.iterator();
        Iterator<String> numericsIterator = numerics.iterator();

        StringBuilder builder = new StringBuilder();

        while (alphabetsIterator.hasNext() || numericsIterator.hasNext()) {
            builder.append(alphabetsIterator.hasNext() ? alphabetsIterator.next() : "");
            builder.append(numericsIterator.hasNext() ? numericsIterator.next() : "");
        }
        return builder.toString();
    }

    private String getOnlyNumeric(String str) {
        return str.replaceAll(NUMERIC_REGEX, "");
    }

    private String getOnlyAlphabet(String str) {
        return str.replaceAll(ALPHABET_REGEX, "");
    }

    private String[] getBundledTextBy(String value, int bundleSize) {
        return value.split(BUNDLE_REGEX.replace("{{BUNDLE_SIZE}}", bundleSize+""));
    }

}