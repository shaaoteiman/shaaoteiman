package com.fnst.springboot01.service;

import com.fnst.springboot01.entity.DemoInfo;

import javassist.NotFoundException;

public interface DemoInfoService {  
	   
    void delete(Long id);  
   
    DemoInfo update(DemoInfo updated) throws NotFoundException;  
   
    DemoInfo findById(Long id);  
   
    DemoInfo save(DemoInfo demoInfo);  
   
}  