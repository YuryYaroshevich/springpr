package com.yra.springpr;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.common.collect.Lists;
import com.yra.springpr.dao.util.DaoCleaner;

public class DaoCleanerRunner {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        DaoCleaner daoCleaner = ctx.getBean(DaoCleaner.class);
        List<String> tables = Lists.newArrayList("booking", "timetable", "event", "user");
        daoCleaner.cleanTables(tables);
    }
}
