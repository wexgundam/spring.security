package org.mose.spring.security.controller;

import org.mose.spring.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description:
 * @Author: 靳磊
 * @Date: 2017/7/25:22
 */
@Controller
@RequestMapping("/")
public class UserServiceController {
    @Autowired
    private UserService userService;

    @RequestMapping("/user/test")
    public void test() {
        userService.doTest();
    }
}
