package cn.willon.shiro.advice;
/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */

import cn.willon.shiro.dto.ResponseDTO;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * ErrorHandler
 *
 * @author Wulao (乌佬)
 * @since 2018-12-11
 */
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    public ResponseDTO notFound(NoHandlerFoundException e) {
        ResponseDTO response = new ResponseDTO();
        response.setCode(404);
        response.setMessage(e.getMessage());
        return response;
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseDTO requestError(HttpRequestMethodNotSupportedException e) {
        ResponseDTO response = new ResponseDTO();
        response.setCode(405);
        response.setMessage(e.getMessage());
        return response;
    }


    @ExceptionHandler(value = UnauthenticatedException.class)
    @ResponseBody
    public ResponseDTO unAuth(UnauthenticatedException e) {
        ResponseDTO response = new ResponseDTO();
        response.setCode(401);
        response.setMessage(e.getMessage());
        return response;
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseBody
    public ResponseDTO unAuth(UnauthorizedException e) {
        ResponseDTO response = new ResponseDTO();
        response.setCode(403);
        response.setMessage(e.getMessage());
        return response;
    }

    @ExceptionHandler(value = IncorrectCredentialsException.class)
    @ResponseBody
    public ResponseDTO unAuth(IncorrectCredentialsException e) {
        ResponseDTO response = new ResponseDTO();
        response.setCode(401);
        response.setMessage(e.getMessage());
        return response;
    }


    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseBody
    public ResponseDTO unAuth(AuthenticationException e) {
        ResponseDTO response = new ResponseDTO();
        response.setCode(401);
        response.setMessage(e.getMessage());
        return response;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseDTO internal(Exception e) {
        ResponseDTO response = new ResponseDTO();
        response.setCode(500);
        response.setMessage(e.getMessage());
        return response;
    }


}
