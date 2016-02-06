package com.yra.springpr.util;

import java.util.HashSet;
import java.util.Set;

import org.springframework.core.convert.converter.Converter;

public class StringToSet implements Converter<String, Set<Integer>> {

    @Override
    public Set<Integer> convert(String numsStr) {
        String[] numsArr = numsStr.split(",");
        Set<Integer> set = new HashSet<>();
        for (String num : numsArr) {
            set.add(Integer.valueOf(num));
        }        
        return set;
    }

}
