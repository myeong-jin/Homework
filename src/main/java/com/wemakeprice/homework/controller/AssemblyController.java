package com.wemakeprice.homework.controller;

import com.wemakeprice.homework.model.DivisionResult;
import com.wemakeprice.homework.service.AssemblyAlphabetAndNumericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidParameterException;
import java.util.LinkedList;


/**
 * Created by blurrymargo on 2018. 9. 3..
 */
@RestController
public class AssemblyController {

    @Autowired
    private AssemblyAlphabetAndNumericService assemblyService;


    @RequestMapping(value = "/assemble", method = RequestMethod.POST)
    public ResponseEntity<DivisionResult> assemble(@RequestParam(defaultValue = "") String inputValue, @RequestParam long bundleSize) {
        this.validateParameters(inputValue, bundleSize);

        LinkedList<String> bundles = assemblyService.mixWithSort(inputValue, bundleSize);
        return new ResponseEntity(assemblyService.separateQuotientNReminder(bundles, bundleSize), HttpStatus.OK);
    }


    private void validateParameters(String inputValue, long bundleSize){
        if (inputValue.isEmpty() || bundleSize <= 0) {
            throw new InvalidParameterException();
        }
    }
}
