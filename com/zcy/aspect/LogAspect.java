package com.zcy.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
//声明这个类是切面类AOP
@Aspect
//带此注解的类看为组件，当使用基于注解的配置和类路径扫描的时候，这些类就会被实例化
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass()); //通过反射将自己的类信息传给LoggerFactory

    @Pointcut("execution(* com.lrm.web.*.*(..))")  //该注解定义该方法为切面方法，让该方法负责拦截web包下的所有Controller类的所有方法
    public void log(){}

    @Before("log()")  //在执行web下的各个Controller类之前（也可说在执行log()之前）执行下面方法。
    public void doBefore(JoinPoint joinPoint){  //JoinPoint对象封装了SpringAop中切面方法的信息

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest(); //获取requestd对象

        String url = request.getRequestURI();
        String ip  = request.getRemoteAddr();

        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(); //前一部分获取到类名，后一部分获取方法名
        Object[] args = joinPoint.getArgs();    //获取请求的参数
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);  //构造对象，并传入参数
        logger.info("Request : {}", requestLog); //记录到日志记录器里
    }

    @After("log()")
    public void doAfter(){
//        logger.info("------doAfter-------");
    }

    @AfterReturning(returning = "result", pointcut = "log()")  //捕获各个方法的返回内容，返回内容传给result,以log()方法为切面点
    public void doAfterReturn(Object result){
        logger.info("Result : {}", result);
    }

    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        private RequestLog(String url, String ip, String classMethod, Object[] args){
            this.url = url;
            this.ip  = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString(){
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
