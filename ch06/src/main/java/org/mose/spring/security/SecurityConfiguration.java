package org.mose.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

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
     * 1.允许任何人访问登录地址（"/loging.html"）
     * 2.用户认证处理地址("/login")
     * 3.允许任何人访问静态资源目录（"/assets/**"）
     * 4.开启对任何地址（"/**"）的访问控制，要求必须具备"ROLE_USER"的角色、
     * 5.开启默认form表单形式的用户登入，访问地址为"/login.jsp"，登录成功后自动跳转到用户前一次的访问地址
     * 6.开启remember me设置
     * 7.设置用户信息获取服务
     * 8.关闭csrf限制，该功能以后再讲，默认为开启状态<br>
     *
     * @param http
     * @return
     * @Author: 靳磊
     * @Date: 2017/7/19 13:47
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/**").hasRole("USER")
                .and().formLogin().loginPage("/login.jsp").permitAll().loginProcessingUrl("/login")
                .and().rememberMe()
                .and().csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, DataSource dataSource) throws Exception {
        auth
                .jdbcAuthentication()
                .passwordEncoder(passwordEncoder())//启用密码加密功能
                .dataSource(dataSource);
    }

    /**
     * 密码加密算法
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}