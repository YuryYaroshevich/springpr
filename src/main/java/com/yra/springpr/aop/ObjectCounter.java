package com.yra.springpr.aop;

import java.util.HashMap;
import java.util.Map;

public class ObjectCounter<T> {
    private Map<T, Integer> counter = new HashMap<>();

    public void count(T o) {
        Integer count = counter.get(o);
        if (count == null) {
            counter.put(o, 1);
        } else {
            counter.put(o, count + 1);
        }
    }

    public Map<T, Integer> getCounter() {
        return counter;
    }

    @Override
    public String toString() {
        return "ObjectCounter [counter=" + counter + "]";
    }
}
