package com.wemakeprice.homework.service;

import com.wemakeprice.homework.model.DivisionResult;

import java.util.LinkedList;

/**
 * Created by blurrymargo on 2018. 9. 4..
 */
public interface AssemblyService {

    LinkedList<String> mixWithSort(String text, long bundleSize);
    DivisionResult separateQuotientNReminder(LinkedList<String> bundles, long bundleSize);
}
