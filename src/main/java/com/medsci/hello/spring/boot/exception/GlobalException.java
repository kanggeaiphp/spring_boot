package com.medsci.hello.spring.boot.exception;

import com.medsci.hello.spring.boot.common.ResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.security.InvalidKeyException;


/**
 * @description:
 * @author: 学长
 * @date: 2020/9/29 09:15
 */
@RestControllerAdvice
public class GlobalException {
    private static final Logger logger = LoggerFactory.getLogger(GlobalException.class);

    @ExceptionHandler(ClassNotFoundException.class)
    public ResponseBean classNotFoundException(ClassNotFoundException e){
        return ResponseBean.error("class not found！");
    }

    @ExceptionHandler(InvalidKeyException.class)
    public ResponseBean invalidKeyException(InvalidKeyException invalidKeyException){
        return ResponseBean.error("私钥或公钥错误！");
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseBean Exception(Exception e){
//        return ResponseBean.error(e.getMessage());
//    }

    @ExceptionHandler(MyRestException.class)
    public ResponseBean Exception(MyRestException e){
        return ResponseBean.error(e.getMessage());
    }
}
