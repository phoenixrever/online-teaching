package com.phoenixhell.security.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenLogoutHandler implements LogoutHandler {

    private RedisTemplate redisTemplate;

    public TokenLogoutHandler(RedisTemplate redisTemplate){
        this.redisTemplate=redisTemplate;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token= request.getHeader("token");
        if(token!=null){
            String username = JwtTokenUtil.getUsernameToken(token);
            redisTemplate.delete(username);
        }
    }
}
