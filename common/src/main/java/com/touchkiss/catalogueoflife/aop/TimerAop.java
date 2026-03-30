package com.touchkiss.catalogueoflife.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created on 2020/03/24 17:18
 * 计时器
 *
 * @author Touchkiss
 */
@Component
@Aspect
@Slf4j
public class TimerAop {

    @Around(value = "@annotation(com.touchkiss.catalogueoflife.aop.Timer)")
    public Object timer(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = proceedingJoinPoint.proceed();
        String functionName;
        Method method = ((MethodSignature) (proceedingJoinPoint.getSignature())).getMethod();
        Timer timer = method.getAnnotation(Timer.class);
        if (StringUtils.isNotBlank(timer.value())) {
            functionName = timer.value();
        } else {
            functionName = proceedingJoinPoint.getTarget().getClass().getName() + ">>" + proceedingJoinPoint.getSignature().getName() + "()";
        }
        log.info("============{}==执行时间：{}毫秒==========", functionName, (System.currentTimeMillis() - startTime));
        return proceed;
    }
}
