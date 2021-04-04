package com.phoenixhell.security.config;

import com.phoenixhell.security.filter.TokenAuthenticationFilter;
import com.phoenixhell.security.filter.TokenLoginFilter;
import com.phoenixhell.security.utils.MyPasswordEncoder;
import com.phoenixhell.security.utils.TokenLogoutHandler;
import com.phoenixhell.security.utils.UnAuthenticatedEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new MyPasswordEncoder());
    }

    //只在配置那边用一次就不用注入容器了 直接new吧
    //角色的权限路径限制
    @Override
    protected void configure(HttpSecurity http) throws Exception {
         http.exceptionHandling()
                .authenticationEntryPoint(new UnAuthenticatedEntryPoint())
                .and().csrf().disable()//ajax的登錄需要禁用
                .authorizeRequests()//需要登陆路径request匹配下面的模式
                .anyRequest().authenticated() //所有url 认证用户都可以访问
                .and()
                .logout().logoutUrl("/acl/logout")
                .addLogoutHandler(new TokenLogoutHandler(redisTemplate))
                .and()
                .addFilter(new TokenLoginFilter(authenticationManagerBean(),redisTemplate))
                .addFilter(new TokenAuthenticationFilter(authenticationManagerBean(),redisTemplate))
                .httpBasic();
    }

    //一般用于配置全局的某些通用事物，例如静态资源等
    //ignore 不用认证可以反问的路径
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/**","/acl/logout",
                "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**"
        );
    }
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
