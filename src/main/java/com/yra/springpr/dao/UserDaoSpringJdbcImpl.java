package com.yra.springpr.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.yra.springpr.model.Purse;
import com.yra.springpr.model.User;

public class UserDaoSpringJdbcImpl implements UserDao {
	
	private JdbcTemplate jdbcTemplate;

	public UserDaoSpringJdbcImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void save(User user) {	
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("name", user.getName());
		paramMap.put("email", user.getEmail());
		paramMap.put("birth_date", new Date(user.getDateOfBirth().getTime()));
		paramMap.put("money", user.getPurse().getBalance());
		jdbcTemplate.update("insert into user values(:name,:email,:birth_date,:money)", paramMap);
	}

	@Override
	public void remove(User user) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", user.getId());
		jdbcTemplate.update("delete from user where id = :id", paramMap);
	}

	@Override
	public User get(int id) {		
		return jdbcTemplate.queryForObject("select * from user where id = ?", this::mapUser, id);
	}

	@Override
	public User getByEmail(String email) {
		return jdbcTemplate.queryForObject("select * from user where email = ?", this::mapUser, email);
	}
	
	private User mapUser(ResultSet rs, int rowNum) throws SQLException {
		return new User(rs.getInt("user_id"), rs.getString("name"), rs.getString("email"), 
				rs.getDate("birth_date"), new Purse(rs.getDouble("money")));
	}

}
