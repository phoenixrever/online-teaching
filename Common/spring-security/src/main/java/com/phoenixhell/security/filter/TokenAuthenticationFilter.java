package com.phoenixhell.security.filter;

import com.phoenixhell.security.utils.JwtTokenUtil;
import com.phoenixhell.security.utils.ResponseUtil;
import com.phoenixhell.utils.CommonResult;
import jdk.nashorn.internal.ir.ContinueNode;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TokenAuthenticationFilter extends BasicAuthenticationFilter {

    private RedisTemplate redisTemplate;

    public TokenAuthenticationFilter(AuthenticationManager authenticationManager,RedisTemplate redisTemplate) {
        super(authenticationManager);
        this.redisTemplate=redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        System.out.println("================="+request.getRequestURI());
//        if(!request.getRequestURI().contains("admin")) {
//            chain.doFilter(request, response);
//            return;
//        }
        try {
            //获取当前认证成功用户权限信息
            UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
            if (authentication != null) {
                //当前权限信息放入权限上下文
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ResponseUtil.out(response, CommonResult.error());
        }
        chain.doFilter(request, response);
    }

    //根据requet返回 username token  用户权限信息
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        //header里面获取token
        String token = request.getHeader("token");
        if (token != null && !"".equals(token.trim())) {
            //从token里面获取用户名
            String username = JwtTokenUtil.getUsernameToken(token);
            //从redis 获取对应权限列表
            List<String> permissions = (List<String>) redisTemplate.opsForValue().get(username);
            //Collection<? extends GrantedAuthority> authorities
            Collection<GrantedAuthority> authorities = new ArrayList<>();

            //SimpleGrantedAuthority 是GrantedAuthority的实现类
            if(StringUtils.isEmpty(permissions)){
                authorities=null;
            }else{
                for(String p:permissions){
                    authorities.add(new SimpleGrantedAuthority(p));
                }
            }
            return new UsernamePasswordAuthenticationToken(username, token, authorities);
        }
        return null;
    }
}