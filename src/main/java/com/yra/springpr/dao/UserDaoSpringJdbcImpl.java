package com.yra.springpr.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.yra.springpr.model.User;

public class UserDaoSpringJdbcImpl implements UserDao {
	
	private NamedParameterJdbcTemplate jdbcTemplate;

	public UserDaoSpringJdbcImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void save(User user) {
	    SqlParameterSource namedParams = new BeanPropertySqlParameterSource(user);
		KeyHolder idHolder = new GeneratedKeyHolder();
		jdbcTemplate.update("insert into user(name,email,birth_date,money) values(:name, :email, :dateOfBirth, :balance)",
		        namedParams, idHolder);
		user.setId((Long) idHolder.getKey());
	}
	
	@Override
	public void update(User user) {
	    SqlParameterSource namedParams = new BeanPropertySqlParameterSource(user);
	    jdbcTemplate.update("update user set name=:name, email=:email, birth_date=:dateOfBirth, money=:balance", 
	           namedParams);
	}

	@Override
	public void remove(User user) {
		Map<String, Object> paramMap = Collections.singletonMap("id", user.getId());
		jdbcTemplate.update("delete from user where id = :id", paramMap);
	}

	@Override
	public User get(int id) {		
		return jdbcTemplate.getJdbcOperations().
		        queryForObject("select * from user where id = ?", this::mapUser, id);
	}

	@Override
	public User getByEmail(String email) {
		return jdbcTemplate.getJdbcOperations()
		        .queryForObject("select * from user where email = ?", this::mapUser, email);
	}
	
	private User mapUser(ResultSet rs, int rowNum) throws SQLException {
		return new User(rs.getInt("user_id"), rs.getString("name"), rs.getString("email"), 
				rs.getDate("birth_date"), rs.getDouble("money"));
	}
}
