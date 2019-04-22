package com.fnst.springboot01.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fnst.springboot01.entity.DemoInfo;
import com.fnst.springboot01.service.DemoInfoService;

import javassist.NotFoundException;

@RestController
public class DemoInfoController {  
   
    @Resource  
    private DemoInfoService demoInfoService;  
     
    @RequestMapping("/test")  
    public String test(){  
        
       //������������.  
        DemoInfo demoInfo = new DemoInfo();  
        demoInfo.setName("����");  
        demoInfo.setPwd("123456");  
        DemoInfo demoInfo2 = demoInfoService.save(demoInfo);  
        
        //���߻���.  
        System.out.println(demoInfoService.findById(demoInfo2.getId()));  
        //�߻���.  
        System.out.println(demoInfoService.findById(demoInfo2.getId()));  
         
         
        demoInfo = new DemoInfo();  
        demoInfo.setName("����");  
        demoInfo.setPwd("123456");  
        DemoInfo demoInfo3 = demoInfoService.save(demoInfo);  
         
        //���߻���.  
        System.out.println(demoInfoService.findById(demoInfo3.getId()));  
        //�߻���.  
        System.out.println(demoInfoService.findById(demoInfo3.getId()));  
         
        System.out.println("============�޸�����=====================");  
        //�޸�����.  
        DemoInfo updated = new DemoInfo();  
        updated.setName("����-updated");  
        updated.setPwd("123456");  
        updated.setId(demoInfo3.getId());  
        try {  
           System.out.println(demoInfoService.update(updated));  
       } catch (NotFoundException e) {  
           e.printStackTrace();  
       }  
         
        //�߻���.  
        System.out.println(demoInfoService.findById(updated.getId()));  
         
       return "ok";  
    }  
     
     
}  
