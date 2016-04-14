package com.postman.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Component
@Aspect
public class LoggingAspect {
    private static final Logger LOGGER = LogManager.getLogger();

    @Around("execution(* com.postman.TrackingService.*(..))")
    public Object timeElapsed(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();
        LOGGER.debug("Time elapsed - " + stopWatch.getTotalTimeSeconds());
        return result;
    }
}
