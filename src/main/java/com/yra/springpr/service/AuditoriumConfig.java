package com.yra.springpr.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.yra.springpr.model.Auditorium;
import com.yra.springpr.util.StringToSet;

@Configuration
public class AuditoriumConfig {
    private static final int AUDITORIUMS_NUM = 3;
    
    //@Autowired
    private Converter<String, Set<Integer>> stringToSetConverter = new StringToSet();
    
    @Bean(name = "auditoriums")
    public Map<Integer, Auditorium> createAuditoriums() throws IOException {
        Resource resource = new ClassPathResource("config/auditoriums.properties");
        Properties auditoriumProps = PropertiesLoaderUtils.loadProperties(resource);
        Map<Integer, Auditorium> auditoriums = new HashMap<>();
        for (int i = 1; i <= AUDITORIUMS_NUM; i++) {
            Auditorium auditorium = new Auditorium(
                    Integer.valueOf(auditoriumProps.getProperty("aud" + i + ".id")), 
                    Integer.valueOf(auditoriumProps.getProperty("aud" + i + ".seatsNumber")), 
                    stringToSetConverter.convert(auditoriumProps.getProperty("aud" + i + ".vipSeats")), 
                    Integer.valueOf(auditoriumProps.getProperty("aud" + i + ".vipSeatPrice")));
            auditoriums.put(auditorium.getId(), auditorium);
        }
        return auditoriums;
    }
}
