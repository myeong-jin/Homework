package com.wemakeprice.homework.support;

import java.util.Comparator;

/**
 * Created by blurrymargo on 2018. 9. 4..
 */
public class SortByAlphabetAndNumericComparator implements Comparator<String> {
    public int compare(String a, String b){
        if(a.equalsIgnoreCase(b)){
            return a.charAt(0) - b.charAt(0);
        }
        return a.toUpperCase().compareTo(b.toUpperCase());
    }
}