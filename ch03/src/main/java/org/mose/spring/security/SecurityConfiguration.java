package org.mose.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

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
     * 4.开启对任何地址（"/**"）的访问控制，要求必须句别"ROLE_USER"的角色
     * 5.开启默认form表单形式的用户登入，访问地址为"/login.jsp"，登录成功后自动跳转到用户前一次的访问地址
     * 6.开启remember me设置
     * 7.关闭csrf限制，该功能以后再讲，默认为开启状态<br>
     *
     * @param http
     * @return
     *
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
//                .and().authorizeRequests().accessDecisionManager(unaninmousBased())//修改为全票通过的授权方式
                .and().csrf().disable();
    }

    /**
     * Description:声明全票通过授权方式
     *
     * @param
     * @return
     *
     * @Author: 靳磊
     * @Date: 2017/7/20 10:13
     */
    @Bean
    public AccessDecisionManager unaninmousBased() {
        RoleVoter roleVoter = new RoleVoter();
        AuthenticatedVoter authenticatedVoter = new AuthenticatedVoter();
        List<AccessDecisionVoter<? extends Object>> voters = new ArrayList<>();
        voters.add(roleVoter);
        voters.add(authenticatedVoter);
        UnanimousBased unanimousBased = new UnanimousBased(voters);
        return unanimousBased;
    }
}