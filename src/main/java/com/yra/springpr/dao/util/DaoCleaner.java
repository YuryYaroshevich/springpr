package com.yra.springpr.dao.util;

import java.util.List;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class DaoCleaner {
    private NamedParameterJdbcTemplate jdbcTemplate;

    public DaoCleaner(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public void cleanTables(List<String> tableNames) {        
        tableNames.forEach(
                name -> jdbcTemplate.getJdbcOperations().update("delete from " + name));
    }    
}
