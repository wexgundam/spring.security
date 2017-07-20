package org.mose.spring.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Description:Spring Security的java configuration
 *
 * @Author: 靳磊
 * @Date: 2017/7/19 13:47
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    /**
     * Description：配置Spring Security
     * 1.开启对任何地址（"/**"）的访问控制，要求必须具备"ROLE_USER"的角色
     * 2.开启默认form表单形式的用户登入，访问地址为"/login"，登录成功后自动跳转到用户前一次的访问地址
     * 3.关闭csrf限制，该功能以后再讲，默认为开启状态<br>
     *
     * @param http
     * @return
     *
     * @Author: 靳磊
     * @Date: 2017/7/19 13:47
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/**").hasRole("USER").and().formLogin().and().csrf().disable();
    }
}