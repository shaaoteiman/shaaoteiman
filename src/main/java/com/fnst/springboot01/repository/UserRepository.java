package com.fnst.springboot01.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fnst.springboot01.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
