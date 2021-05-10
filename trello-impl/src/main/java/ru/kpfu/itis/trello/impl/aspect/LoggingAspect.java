package ru.kpfu.itis.trello.impl.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@Aspect
@Component
public class LoggingAspect {
    @Before("@annotation(Logging)")
    public void beforeMethod(JoinPoint joinPoint){
        System.out.println("executing method = " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    }
}
