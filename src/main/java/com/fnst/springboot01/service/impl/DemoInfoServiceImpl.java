package com.fnst.springboot01.service.impl;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fnst.springboot01.entity.DemoInfo;
import com.fnst.springboot01.repository.DemoInfoRepository;
import com.fnst.springboot01.service.DemoInfoService;

import javassist.NotFoundException;

@Service  
public class DemoInfoServiceImpl implements DemoInfoService {  
     
    //����ĵ����Ų����٣�����ᱨ����ʶ����һ������;  
    public static final String CACHE_KEY = "'demoInfo'";  
    
     @Resource  
     private DemoInfoRepository demoInfoRepository;  
     
    /** 
     * value���Ա�ʾʹ���ĸ�������ԣ����������ehcache.xml 
    */  
    public static final String DEMO_CACHE_NAME = "demo";  
     
    /** 
     * ��������. 
     * @param demoInfo 
     */  
    @CacheEvict(value=DEMO_CACHE_NAME,key=CACHE_KEY)  
    @Override  
    public DemoInfo save(DemoInfo demoInfo){  
       return demoInfoRepository.save(demoInfo);  
    }  
     
    /** 
     * ��ѯ����. 
     * @param id 
     * @return 
     */  
    @Cacheable(value=DEMO_CACHE_NAME,key="'demoInfo_'+#id")  
    @Override  
    public DemoInfo findById(Long id){  
       System.err.println("û���߻��棡"+id);  
       DemoInfo demoInfo = new DemoInfo();
       Optional<DemoInfo> optional = demoInfoRepository.findById(id);
       if(optional.isPresent()){
    	   demoInfo = optional.get();  
       }
       return demoInfo;
    }  
     
    /** 
     * http://www.mincoder.com/article/2096.shtml: 
     * 
     * �޸�����. 
     * 
     * ��֧��Spring Cache�Ļ����£�����ʹ��@Cacheable��ע�ķ�����Spring��ÿ��ִ��ǰ������Cache���Ƿ������ͬkey�Ļ���Ԫ�أ�������ھͲ���ִ�и÷���������ֱ�Ӵӻ����л�ȡ������з��أ�����Ż�ִ�в������ؽ������ָ���Ļ����С�
     * @CachePut Ҳ��������һ������֧�ֻ��湦�ܡ���@Cacheable��ͬ����ʹ��@CachePut��ע�ķ�����ִ��ǰ����ȥ��黺�����Ƿ����֮ǰִ�й��Ľ��������ÿ�ζ���ִ�и÷���������ִ�н���Լ�ֵ�Ե���ʽ����ָ���Ļ����С� 
     * @CachePut Ҳ���Ա�ע�����Ϻͷ����ϡ�ʹ��@CachePutʱ���ǿ���ָ�������Ը�@Cacheable��һ���ġ� 
     * 
     * @param updated 
     * @return 
     * 
     * @throws NotFoundException 
     */  
    @CachePut(value = DEMO_CACHE_NAME,key = "'demoInfo_'+#updated.getId()")  
    //@CacheEvict(value = DEMO_CACHE_NAME,key = "'demoInfo_'+#updated.getId()")//�����������.  
    @Override  
    public DemoInfo update(DemoInfo updated) throws NotFoundException{  
       DemoInfo demoInfo = demoInfoRepository.findById(updated.getId()).get();  
       if(demoInfo == null){  
           throw new NotFoundException("No find");  
       }  
       demoInfo.setName(updated.getName());  
       demoInfo.setPwd(updated.getPwd());  
       return demoInfo;  
    }  
     
     
    /** 
     * ɾ������. 
     * @param id 
     */  
    @CacheEvict(value = DEMO_CACHE_NAME,key = "'demoInfo_'+#id")//�����������.  
    @Override  
    public void delete(Long id){  
       demoInfoRepository.deleteById(id);  
    }  
        
     
}  