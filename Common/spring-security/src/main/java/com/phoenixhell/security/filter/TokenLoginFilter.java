package com.phoenixhell.security.filter;

import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixhell.security.entity.SecurityUser;
import com.phoenixhell.security.entity.User;
import com.phoenixhell.security.utils.JwtTokenUtil;
import com.phoenixhell.security.utils.ResponseUtil;
import com.phoenixhell.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private RedisTemplate redisTemplate;

    private AuthenticationManager authenticationManager;

    public TokenLoginFilter(AuthenticationManager authenticationManager,RedisTemplate redisTemplate) {
        this.authenticationManager=authenticationManager;
        this.redisTemplate=redisTemplate;
        this.setPostOnly(false);//所有请求都经过过滤器
        //设置登录的路径与提交方式
        this.setRequiresAuthenticationRequestMatcher(
                new AntPathRequestMatcher("/acl/login", "POST"));
    }

    //获取表单提交的用户名密码
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            //jackson json java 对象转换  从user json字符窜反序列化生产user对象
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getUsername(), user.getPassword(), new ArrayList<GrantedAuthority>()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("转换user失败");
        }
    }

    //认证成功调用 需要调用userDetailsService比对数据库
    //userDetailsService里面查出权限放入user
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//认证成功,得到认证成功之后的用户信息
        SecurityUser user = (SecurityUser) authResult.getPrincipal();
        System.out.println(authResult.getPrincipal());
        //根据用户名生产token
        String token = JwtTokenUtil.createUsernameToken(user.getUsername());
      /*  redisTemplate.opsForValue();//操作字符串
        redisTemplate.opsForHash();//操作hash
        redisTemplate.opsForList();//操作list
        redisTemplate.opsForSet();//操作set
        redisTemplate.opsForZSet();//操作有序set
      */
        //用户名和权限列表键值对存入redis
        redisTemplate.opsForValue().set(user.getUsername(), user.getPermissionList());
        ResponseUtil.out(response, CommonResult.ok().data("token", token));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ResponseUtil.out(response,CommonResult.error().data("message","登錄失敗"));
    }
}
