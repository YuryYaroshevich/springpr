package com.yra.springpr.aop.discount;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.yra.springpr.model.User;
import com.yra.springpr.service.discount.DiscountStrategy;

public class DiscountUsageCounterSpringJdbcImpl implements DiscountUsageCounter {
    private NamedParameterJdbcTemplate jdbcTemplate;
    private SimpleJdbcCall discountStatCall;
    
    public DiscountUsageCounterSpringJdbcImpl(
            NamedParameterJdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.discountStatCall = new SimpleJdbcCall(dataSource).withProcedureName("add_to_discount_statistics");
    }

    @Override
    public void countUsage(User user, Class<? extends DiscountStrategy> clazz) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", user.getId());
        params.addValue("discount_type", clazz.toString());
        discountStatCall.execute(params);
    }

    @Override
    public Map<User, Map<Class<? extends DiscountStrategy>, Integer>> getCounter() {
        return jdbcTemplate.query("select user_id, name, email, birth_date, money, discount_type, count from discount_statistics ds inner join user u on ds.user_id = u.user_id",
                (ResultSet rs) -> {
                   Map<User, Map<Class<? extends DiscountStrategy>, Integer>> result = new HashMap<>();
                   while (rs.next()) {
                       User user = new User(rs.getLong("user_id"), rs.getString("name"),
                               rs.getString("email"), rs.getDate("birth_date"), rs.getDouble("money"));
                       Map<Class<? extends DiscountStrategy>, Integer> userCounter = result.get(user);
                       if (userCounter == null) {
                           userCounter = new HashMap<>();
                           result.put(user, userCounter);
                       }
                       try {
                           userCounter.put(
                                   (Class<? extends DiscountStrategy>) Class.forName(rs.getString("discount_type")),
                                       rs.getInt("count"));
                       } catch (Exception e) {
                           throw new RuntimeException(e);
                       }
                   }
                   return result; 
                });
    }

}
