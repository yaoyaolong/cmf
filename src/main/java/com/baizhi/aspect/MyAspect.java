package com.baizhi.aspect;

import com.baizhi.dao.LoggsDao;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Loggs;
import org.aspectj.lang.ProceedingJoinPoint;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.UUID;

@Component
@Aspect
public class MyAspect {
    @Autowired
    LoggsDao loggsDao;
    @Autowired
    HttpSession session;
    @Around(value = "@annotation(com.baizhi.aspect.LogAnnotation)")
    public Object addLogs(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Loggs loggs = new Loggs();
        Date date = new Date();
        //将时间放进loggs对象
        loggs.setTime(date);
        //生成uuid
        loggs.setId(UUID.randomUUID().toString());
        System.out.println(date);
        Admin admin = (Admin) session.getAttribute("admin");
        System.out.println(admin);
        //session中取出登录名字
        loggs.setName(admin.getUsername());
        String name = proceedingJoinPoint.getSignature().getName();
        System.out.println(name);
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        LogAnnotation annotation = signature.getMethod().getAnnotation(LogAnnotation.class);
        System.out.println(annotation.value());
        loggs.setMethod(annotation.value());
        Object proceed = proceedingJoinPoint.proceed();

        loggsDao.insert(loggs);

        return proceed;
    }


}
