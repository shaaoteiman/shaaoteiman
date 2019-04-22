package com.fnst.springboot01.aop;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class WebLogAspect {

    private Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    /**
     * ��������㣬�����Ϊcom.example.aop�µ����к���
     */
    @Pointcut("execution(public * com.fnst.springboot01.controller..*.*(..))")
    public void webLog(){}

    /**
     * ǰ��֪ͨ�������ӵ�֮ǰִ�е�֪ͨ
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // ���յ����󣬼�¼��������
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // ��¼����������
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "ret",pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // ���������󣬷�������
        logger.info("RESPONSE : " + ret);
    }
}
