package com.fnst.springboot01.repository;

import org.springframework.data.repository.CrudRepository;

import com.fnst.springboot01.entity.DemoInfo;

public interface DemoInfoRepository extends CrudRepository<DemoInfo, Long> {

}
