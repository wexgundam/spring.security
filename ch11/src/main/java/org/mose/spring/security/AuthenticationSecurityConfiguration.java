package org.mose.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.method.DelegatingMethodSecurityMetadataSource;
import org.springframework.security.access.method.MapBasedMethodSecurityMetadataSource;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:Spring Security的java configuration
 *
 * @Author: 靳磊
 * @Date: 2017/7/19 13:47
 */
@EnableWebSecurity
public class AuthenticationSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;

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
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/**").hasRole("USER")
                .and().formLogin().loginPage("/login.jsp").permitAll().loginProcessingUrl("/login")
                .and().rememberMe().tokenRepository(
                persistentTokenRepository())//自动识别tokenRepository类型，启用PersistentTokenBasedRememberMeServices
                .and().csrf().disable();
    }

    /**
     * Description:配置认证细节
     *
     * @param auth
     * @return
     * @Author: 靳磊
     * @Date: 2017/7/21 17:04
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .passwordEncoder(passwordEncoder())//启用密码加密功能
                .dataSource(dataSource);
    }

    /**
     * 获取默认创建的UserDetailsService，开启分组功能，关闭用户直接授权功能，并发布为Spring Bean
     *
     * @param auth
     * @return
     */
    @Bean
    @Autowired
    public UserDetailsService userDetailsService(AuthenticationManagerBuilder auth) {
        UserDetailsService userDetailsService = auth.getDefaultUserDetailsService();
        if (JdbcUserDetailsManager.class.isInstance(userDetailsService)) {
            JdbcUserDetailsManager jdbcUserDetailsManager = (JdbcUserDetailsManager) userDetailsService;
            jdbcUserDetailsManager.setEnableGroups(true);//开启分组功能
            jdbcUserDetailsManager.setEnableAuthorities(false);//关闭用户直接获取权限功能
        }
        return userDetailsService;
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

    /**
     * 可持久化的cookie token服务
     *
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }


}