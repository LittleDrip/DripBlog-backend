package com.drip.service.impl;

import com.drip.domain.LoginUser;
import com.drip.domain.User;
import com.drip.domain.vo.BlogUserLoginVo;
import com.drip.domain.vo.UserInfoVo;
import com.drip.service.BlogLoginService;
import com.drip.utils.BeanCopyUtils;
import com.drip.utils.JwtUtil;
import com.drip.utils.RedisCache;
import com.drip.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public Result login(User user) {
//        将用户名，密码穿进去封装
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
//        接下来来认证 调用UserDetails认证,默认是基于内存查询，所以要重写实现类UserDetailsServiceImpl
//        之后密码校验 在UserDetailsServiceImpl返回UserDetails对象，之后会自动调用密码校验
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
//        判断是否认证通过
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }
//       如果执行到这里，说明不为空 获取userid,生成token
//        先获取LoginUser对象
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String id = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(id);
//        将用户信息存入redis
        redisCache.setCacheObject("bloglogin" + id, loginUser);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(jwt, userInfoVo);
        return Result.ok(blogUserLoginVo);
    }

    @Override
    public Result logout() {
//        获取token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
//        获取userid
        String id = loginUser.getUser().getId().toString();
//        删除redis用户信息
        redisCache.deleteObject("bloglogin"+id);
        return Result.ok(null);
    }
}
