package org.mose.spring.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Description:
 * @Author: 靳磊
 * @Date: 2017/7/21:20
 */
@Component
public class UserService {
    @Autowired
    UserDetailsService userDetailsService;

    @PostConstruct
    public void init() {
        System.out.println("---------------------------");
        System.out.println(userDetailsService.getClass());
        System.out.println("---------------------------");
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void doTest() {
        System.out.println("do test");
    }
}
