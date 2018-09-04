package com.wemakeprice.homework.service;

import com.wemakeprice.homework.model.DivisionResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

/**
 * Created by blurrymargo on 2018. 9. 4..
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AssemblyAlphabetAndNumericServiceTest {
    final String INPUT_VALUES = "  !@# 8 a79A || | } { +_) 3(*---||}}{?><:\"  &^  %$!@#  $%^~`` \n y \t m \r Y  0x z Cc                ";
    final int BUNDLE_SIZE = 3;

    @Autowired
    private AssemblyAlphabetAndNumericService assemblyService;


    @Test
    public void bundleByAlphabetAndNumericTest() throws Exception {
        final String BUNDLED_TEXT = "A0a,3C7,c8m,9xY,yz";

        LinkedList<String> bundles = assemblyService.mixWithSort(INPUT_VALUES, BUNDLE_SIZE);
        assertEquals(BUNDLED_TEXT, String.join(",", bundles));
    }

    @Test
    public void separateWithQuotientAndReminderTest() throws Exception {
        final String QUOTIENT = "A0a,3C7,c8m,9xY";
        final String REMINDER = "yz";

        DivisionResult divisionResult = new DivisionResult();
        LinkedList<String> bundles = assemblyService.mixWithSort(INPUT_VALUES, BUNDLE_SIZE);

        if (bundles.getLast().length() < BUNDLE_SIZE) {
            divisionResult.setReminder(bundles.removeLast());
        }

        divisionResult.setQuotient(String.join(",", bundles));

        assertEquals(QUOTIENT, divisionResult.getQuotient());
        assertEquals(REMINDER, divisionResult.getReminder());
    }

}