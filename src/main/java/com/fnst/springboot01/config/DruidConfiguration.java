package com.fnst.springboot01.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

@Configuration
public class DruidConfiguration {

	/**
	 * 
	 * ע��һ��StatViewServlet
	 * 
	 * @return
	 * 
	 */
	@Bean
	public ServletRegistrationBean DruidStatViewServle2() {
		// org.springframework.boot.context.embedded.ServletRegistrationBean�ṩ��Ľ���ע��.
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),
				"/druid/*");
		// ��ӳ�ʼ��������initParams
		// ��������
		servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
		// IP������ (���ڹ�ͬʱ��deny������allow) : �������deny�Ļ���ʾ:Sorry, you are not
		// permitted to view this page.
		servletRegistrationBean.addInitParameter("deny", "192.168.1.73");
		// ��¼�鿴��Ϣ���˺�����.
		servletRegistrationBean.addInitParameter("loginUsername", "admin");
		servletRegistrationBean.addInitParameter("loginPassword", "123456");
		// �Ƿ��ܹ���������.
		servletRegistrationBean.addInitParameter("resetEnable", "false");
		return servletRegistrationBean;
	}

	/**
	 * 
	 * ע��һ����filterRegistrationBean
	 * 
	 * @return
	 * 
	 */
	
	@Bean
	public FilterRegistrationBean druidStatFilter2() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
		// ��ӹ��˹���.
		filterRegistrationBean.addUrlPatterns("/*");
		// ��Ӳ���Ҫ���Եĸ�ʽ��Ϣ.
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid2/*");
		return filterRegistrationBean;
	}

}