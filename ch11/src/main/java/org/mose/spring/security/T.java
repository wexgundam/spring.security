package org.mose.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.method.MapBasedMethodSecurityMetadataSource;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: 靳磊
 * @Date: 2017/7/25:22
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class T extends GlobalMethodSecurityConfiguration {
    /////////////////// Begin Configure Method Security ////////////////////
    //
    // 下面这个网址是说明如何基于java configuration配置method security
    // https://stackoverflow.com/questions/25667694/spring-security-global-method-security-protect-pointcut-with-enableglobalmethod
    @Bean
    public Map<String, List<ConfigAttribute>> pointcutMap() {
        Map<String, List<ConfigAttribute>> map = new HashMap<>();

        // all the necessary rules go here
        map.put("execution (* org.mose.*..*Service.*(..))", SecurityConfig.createList("ROLE_ADMIN"));

        return map;
    }

    @Bean
    public MethodSecurityMetadataSource mappedMethodSecurityMetadataSource() {

        // the key is not to provide the above map here. this class will be populated later by ProtectPointcutPostProcessor
        return new MapBasedMethodSecurityMetadataSource();
    }

    @Override
    protected MethodSecurityMetadataSource customMethodSecurityMetadataSource() {
        return mappedMethodSecurityMetadataSource();
    }

    /**
     * Needed to use reflection because I couldn't find a way to instantiate a
     * ProtectPointcutPostProcessor via a BeanFactory or ApplicationContext. This bean will process
     * the AspectJ pointcut defined in the map; check all beans created by Spring; store the matches
     * in the MapBasedMethodSecurityMetadataSource bean so Spring can use it during its checks
     *
     * @return
     * @throws Exception
     */
    @Bean(name = "protectPointcutPostProcessor")
    Object protectPointcutPostProcessor() throws Exception {
        Class<?> clazz =
                Class.forName("org.springframework.security.config.method.ProtectPointcutPostProcessor");

        Constructor<?> declaredConstructor =
                clazz.getDeclaredConstructor(MapBasedMethodSecurityMetadataSource.class);
        declaredConstructor.setAccessible(true);
        Object instance = declaredConstructor.newInstance(mappedMethodSecurityMetadataSource());
        Method setPointcutMap = instance.getClass().getMethod("setPointcutMap", Map.class);
        setPointcutMap.setAccessible(true);
        setPointcutMap.invoke(instance, pointcutMap());

        return instance;
    }
    /////////////////// End Configure Method Security //////////////////////
}
