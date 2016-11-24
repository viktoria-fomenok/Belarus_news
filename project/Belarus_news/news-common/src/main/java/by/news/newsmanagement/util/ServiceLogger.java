package by.epam.newsmanagement.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * <p>This class implements the function of logging calling methods and throwing exceptions</p>
 */
@Component
@Aspect
public class ServiceLogger {
    private static final Logger LOG = LogManager.getLogger(ServiceLogger.class);

    @Around("execution(* by.epam.newsmanagement.service.*.*(..))")
    public Object logAroundService(ProceedingJoinPoint joinPoint) throws Throwable {
        long timeBeforeExecution = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long timeAfterExecution = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        sb.append(joinPoint.getTarget().getClass().getName());
        sb.append(".");
        sb.append(joinPoint.getSignature().getName());
        sb.append("(");
        for (Object obj : joinPoint.getArgs()) {
            sb.append(obj).append(",");
        }
        if (joinPoint.getArgs().length > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(")");
        sb.append(" execution time: ");
        sb.append(timeAfterExecution - timeBeforeExecution);
        sb.append(" ms");
        LOG.info(sb.toString());
        return result;
    }

    @AfterThrowing(pointcut = "execution(* by.epam.newsmanagement.service.*.*(..))", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        StringBuilder sb = new StringBuilder();
        sb.append(joinPoint.getTarget().getClass().getName());
        sb.append(".");
        sb.append(joinPoint.getSignature().getName());
        sb.append("(");
        for (Object obj : joinPoint.getArgs()) {
            sb.append(obj).append(",");
        }
        if (joinPoint.getArgs().length > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(")");
        sb.append(" throws exception ");
        sb.append(exception.getClass().getName());
        LOG.error(sb.toString(), exception);
    }

}