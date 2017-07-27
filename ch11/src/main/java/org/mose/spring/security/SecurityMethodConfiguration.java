package org.mose.spring.security;

import org.mose.spring.security.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.method.MapBasedMethodSecurityMetadataSource;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * @Description:
 * @Author: 靳磊
 * @Date: 2017/7/25:22
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityMethodConfiguration extends GlobalMethodSecurityConfiguration {
    /**
     * 当前版本主要使用了ProtectPointcutPostProcessor实现Aop AspectJ的表达式方式的权限控制
     * 其原理是Spring没创建一个bean都要通过一组Aop的遍历，如果符合Aop表达式，则将这个bean的方法
     * 注册到MethodSecurityMetadataSource中。当调用某bean的方法时，则检查该方法是否已经在注册
     * 列表中，如果存在则检查是否具有对应权限。
     *
     * 但是使用Java Configuration时出现了问题
     * 1.ProtectPointcutPostProcessor不能被访问、被继承
     * 2.可以通过代码复制或者反射实例化该对象
     * 3.如果将其发布成Spring Bean，并按照xml的方式配置属性，运行时会报错，尚未研究报错具体内容
     *
     * @return
     */
    @Override
    protected MethodSecurityMetadataSource customMethodSecurityMetadataSource() {
        MapBasedMethodSecurityMetadataSource metadataSource = new MapBasedMethodSecurityMetadataSource();
        metadataSource.addSecureMethod(UserService.class, "*", SecurityConfig.createList("ROLE_ADMIN"));
        return metadataSource;
    }
}
