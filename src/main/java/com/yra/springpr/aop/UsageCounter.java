package com.yra.springpr.aop;

import java.util.HashMap;
import java.util.Map;

public class UsageCounter<T> {
    private Map<T, Integer> counterData = new HashMap<>();

    public void countObjectUsage(T o) {
        Integer count = counterData.get(o);
        if (count == null) {
            counterData.put(o, 1);
        } else {
            counterData.put(o, count + 1);
        }
    }

    public Map<T, Integer> getCounter() {
        return counterData;
    }

    @Override
    public String toString() {
        return "UsageCounter [counterData=" + counterData + "]";
    }

}
