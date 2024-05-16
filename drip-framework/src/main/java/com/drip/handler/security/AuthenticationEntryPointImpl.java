package com.drip.handler.security;

import com.alibaba.fastjson.JSON;
import com.drip.utils.Result;
import com.drip.utils.ResultCodeEnum;
import com.drip.utils.WebUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
//自定义认证失败的处理器。ResponseResult、AppHttpCodeEnum是我们在huanf-framework工程写的类
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        //输出异常信息
        authException.printStackTrace();

        //判断是登录才出现异常(返回'用户名或密码错误')，还是没有登录就访问特定接口才出现的异常(返回'需要登录后访问')，还是其它情况(返回'出现错误')
        Result result = null;
        if(authException instanceof BadCredentialsException){
            //第一个参数返回的是响应码，AppHttpCodeEnum是我们写的实体类。第二个参数是返回具体的信息
            result = Result.build(null, ResultCodeEnum.LOGIN_ERROR);
        } else if(authException instanceof InsufficientAuthenticationException){
            //第一个参数返回的是响应码，AppHttpCodeEnum是我们写的实体类
            result = Result.build(null, ResultCodeEnum.NEED_LOGIN);
        } else {
            //第一个参数返回的是响应码，AppHttpCodeEnum是我们写的实体类。第二个参数是返回具体的信息
            result = Result.build(null,ResultCodeEnum.SYSTEM_ERROR.getCode(),"认证或授权失败");
        }

        //使用spring提供的JSON工具类，把上一行的result转换成JSON，然后响应给前端。WebUtils是我们写的类
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}