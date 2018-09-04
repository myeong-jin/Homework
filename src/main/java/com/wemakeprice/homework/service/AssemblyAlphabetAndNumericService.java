package com.wemakeprice.homework.service;

import com.wemakeprice.homework.model.DivisionResult;
import com.wemakeprice.homework.support.SortByAlphabetAndNumericComparator;
import com.wemakeprice.homework.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by blurrymargo on 2018. 9. 4..
 */
@Service
public class AssemblyAlphabetAndNumericService implements AssemblyService{
    final String BUNDLE_SIZE_TAG = "{{BUNDLE_SIZE}}";
    final String BUNDLE_REGEX = "(?<=\\G.{" + BUNDLE_SIZE_TAG + "})";

    /***
     * 입력된 텍스트를 정렬된 알파벳,숫자 순으로 믹스
     * @param text
     * @param bundleSize
     * @return
     */
    @Override
    public LinkedList<String> mixWithSort(String text, long bundleSize){

        List<String> sortedAlphabet = this.extractByAlphabetWithSort(text);
        List<String> sortedNumeric = this.extractByNumericWithSort(text);

        String mixedText = this.mixElements(sortedAlphabet, sortedNumeric);

        return this.bundleBy(mixedText, bundleSize);
    }

    /***
     * Element 를 bundleSize 크기로 묶어 몫과 나머지로 분리
     * @param bundles
     * @param bundleSize
     * @return
     */
    @Override
    public DivisionResult separateQuotientNReminder(LinkedList<String> bundles, long bundleSize){
        DivisionResult divisionResult = new DivisionResult();

        if (bundles.getLast().length() < bundleSize) {
            divisionResult.setReminder(bundles.removeLast());
        }

        divisionResult.setQuotient(String.join(",", bundles));
        return divisionResult;
    }

    private List<String> extractByNumericWithSort(String text){
        List<String> list = StringUtil.splitByRegex(text, StringUtil.NUMERIC_REGEX);
        Collections.sort(list);
        return list;
    }

    private List<String> extractByAlphabetWithSort(String text){
        List<String> list = StringUtil.splitByRegex(text, StringUtil.ALPHABET_REGEX);
        Collections.sort(list, new SortByAlphabetAndNumericComparator());
        return list;
    }

    private String mixElements(List<String> firstElements, List<String> secondElements){

        Iterator<String> firstIterator = firstElements.iterator();
        Iterator<String> secondIterator = secondElements.iterator();

        StringBuilder builder = new StringBuilder();

        while (firstIterator.hasNext() || secondIterator.hasNext()) {
            builder.append(firstIterator.hasNext() ? firstIterator.next() : "");
            builder.append(secondIterator.hasNext() ? secondIterator.next() : "");
        }
        return builder.toString();
    }

    private LinkedList<String> bundleBy(String text, long bundleSize) {
        String[] bundles = text.split(BUNDLE_REGEX.replace(BUNDLE_SIZE_TAG, String.valueOf(bundleSize)));
        return new LinkedList(Arrays.asList(bundles));
    }
}
