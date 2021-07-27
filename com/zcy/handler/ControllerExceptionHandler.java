package com.zcy.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice  //Controller增强器。
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass()); //把当前类的类信息注册进日志工厂中

    @ExceptionHandler(Exception.class)  //标识该方法用于异常处理，处理对象为Exception
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {

        /*ModelAndView:模型和视图显示类
        * 参数：HttpServletRequest：可以获取request里的所有方法和属性，比如url,host等
        * */
        logger.error("Request URL : {}, Exccption : {}",request.getRequestURI(),e);  //记录异常信息，将错误日志打印到控制台

        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){   //通过判断状态码对象存在来让springboot自己处理异常
            throw e;    //如果状态码对象不存在，则让springboot抛出异常，若状态码为
        }

        ModelAndView mv = new ModelAndView();   //创建模型和视图对象，主要用于错误页面的显示
        mv.addObject("url",request.getRequestURI());
        mv.addObject("exception",e);
        mv.setViewName("error/error");
        return mv;
    }
}
