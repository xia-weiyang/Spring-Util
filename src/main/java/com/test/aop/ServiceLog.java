package com.test.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zk on 2018/8/8.
 */
@Aspect
@Component
public class ServiceLog {
    private static final Logger logger = LoggerFactory.getLogger(ServiceLog.class);

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 存储请求开始时间
        request.setAttribute("time", System.currentTimeMillis());

        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());

        Object[] args = joinPoint.getArgs();
        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < argNames.length; i++)
            stringBuilder.append(argNames[i]).append(":").append(args[i]);
        logger.info("PARAMS : " + stringBuilder.toString());
    }

    @AfterReturning(returning = "obj", pointcut = "log()")
    public void doAfter(Object obj) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        logger.info("RESULT : " + obj);
        logger.info("TIME : " + (System.currentTimeMillis() - (long) request.getAttribute("time")));
    }
}
