package com.fnst.springboot01.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fnst.springboot01.entity.User;

@Repository
public class UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public User getUserById(long id){
		String sql = "select * from User where id=?";
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		return jdbcTemplate.queryForObject(sql, rowMapper, id);
	}
}
