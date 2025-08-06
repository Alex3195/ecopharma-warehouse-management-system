package uz.duol.ecopharmwarehouse.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    private final ObjectMapper objectMapper;

    public LoggingAspect(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Pointcut("execution(* uz.duol.ecopharmwarehouse.controller..*(..))")
    public void controllerMethods() {
    }

    @Pointcut("execution(* uz.duol.ecopharmwarehouse.module..service..*(..))")
    public void serviceMethods() {
    }

    @Pointcut("execution(* uz.duol.ecopharmwarehouse.repositories..*(..))")
    public void repositoryMethods() {
    }

    @Pointcut("execution(* uz.duol.ecopharmwarehouse.jobs..*(..))")
    public void jobMethods() {
    }

    @Around("controllerMethods() || serviceMethods() || repositoryMethods()||jobMethods()")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className = signature.getDeclaringTypeName();
        String methodName = signature.getName();
        String[] paramNames = signature.getParameterNames();
        Object[] paramValues = joinPoint.getArgs();

        Map<String, Object> paramsMap = new HashMap<>();
        for (int i = 0; i < paramNames.length; i++) {
            Object val = paramValues[i];
            try {
                paramsMap.put(paramNames[i], objectMapper.writeValueAsString(val));
            } catch (Exception e) {
                paramsMap.put(paramNames[i], String.valueOf(val));
            }
        }

        long start = System.currentTimeMillis();

        // Log entry
        log.info(toJson(Map.of(
                "event", "enter",
                "layer", "AOP",
                "class", className,
                "method", methodName,
                "params", paramsMap
        )));

        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - start;

            // Log exit
            log.info(toJson(Map.of(
                    "event", "exit",
                    "layer", "AOP",
                    "class", className,
                    "method", methodName,
                    "duration_ms", duration,
                    "result", safeToJson(result)
            )));
            return result;
        } catch (Throwable ex) {
            long duration = System.currentTimeMillis() - start;

            // Log error
            log.error(toJson(Map.of(
                    "event", "exception",
                    "layer", "AOP",
                    "class", className,
                    "method", methodName,
                    "duration_ms", duration,
                    "exception", ex.getClass().getName(),
                    "message", ex.getMessage()
            )), ex);

            throw ex;
        }
    }

    private String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return "{\"error\": \"Failed to serialize log message\"}";
        }
    }

    private String safeToJson(Object result) {
        try {
            return objectMapper.writeValueAsString(result);
        } catch (Exception e) {
            return String.valueOf(result);
        }
    }

}
