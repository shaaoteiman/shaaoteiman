package com.fnst.springboot01.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fnst.springboot01.dao.UserDao;
import com.fnst.springboot01.entity.User;
import com.fnst.springboot01.repository.UserRepository;

@Service
public class UserService {
	@Resource
	private UserRepository userRepository;
	
	@Autowired
	private UserDao userDao;

	@Transactional
	public void save(User user) {
		userRepository.save(user);
	}
	public User getUserById(long id){
		return userDao.getUserById(id);
	}
}
