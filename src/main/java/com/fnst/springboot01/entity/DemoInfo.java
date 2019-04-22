package com.fnst.springboot01.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity  
public class DemoInfo {  
    @Id @GeneratedValue  
    private long id;//Ö÷¼ü.  
    private String name;//Ãû³Æ;  
    private String pwd;//ÃÜÂë;  
    private int state;  
    public long getId() {  
       return id;  
    }  
    public void setId(long id) {  
       this.id = id;  
    }  
    public String getName() {  
       return name;  
    }  
    public void setName(String name) {  
       this.name = name;  
    }  
    public String getPwd() {  
       return pwd;  
    }  
    public void setPwd(String pwd) {  
       this.pwd = pwd;  
    }  
    public int getState() {  
       return state;  
    }  
    public void setState(int state) {  
       this.state = state;  
    }  
    @Override  
    public String toString() {  
       return "DemoInfo [id=" + id + ", name=" + name + ", pwd=" + pwd + ", state=" + state + "]";  
    }  
}  
