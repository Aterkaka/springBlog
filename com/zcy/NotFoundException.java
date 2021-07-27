package com.zcy;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) //改变HTTP响应的状态码，若是资源找不到则会根据状态码返回到相应的页面（如404页面）
public class NotFoundException extends RuntimeException {  //这里不继承Exception,因为RuntimeException才可以捕获异常

    public NotFoundException(){
    }

    public NotFoundException(String message){
        super(message);
    }

    public NotFoundException(String message, Throwable cause){
        super(message,cause);
    }
}
