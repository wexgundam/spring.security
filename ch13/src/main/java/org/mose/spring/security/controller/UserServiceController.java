package org.mose.spring.security.controller;

import org.mose.spring.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @Autowired
    private SessionRegistry sessionRegistry;

    @ModelAttribute("activeSessionCount")
    public int activeSessionCount() {
        return sessionRegistry.getAllPrincipals().size();
    }

    @RequestMapping("/user/annotation")
    @ResponseBody
    public String annotation() {
        return userService.preAuthorizeMethod() + activeSessionCount();
    }

    @RequestMapping("/user/javaConfiguration")
    @ResponseBody
    public String javaConfiguration() {
        return userService.javaConfiguredMethod();
    }

    @RequestMapping("/user/postFilter")
    @ResponseBody
    public String postFilter() {
        return userService.postFilter().toString();
    }
}
