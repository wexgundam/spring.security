package org.mose.spring.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Description:定义修改用户密码的控制器
 *
 * @Author: 靳磊
 * @Date: 2017/7/20 14:52
 */
@Controller("/")
public class UserServiceController {
    //获取用户信息服务
    @Autowired
    UserDetailsService userDetailsService;

    /**
     * Description:跳转到用户密码修改界面
     *
     * @return
     *
     * @Author: 靳磊
     * @Date: 2017/7/20 14:59
     */
    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public String changePassword() {
        return "redirect:/changePassword.jsp";
    }

    /**
     * Description:
     * 1.获取已认证用户
     * 2.获取用户管理服务
     * 3.修改密码
     * 4.刷新认证
     *
     * @param password
     * @return
     *
     * @Author: 靳磊
     * @Date: 2017/7/20 14:31
     */
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public String changePassword(@RequestParam("password") String password) {
        //获得当前用户
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (InMemoryUserDetailsManager.class.isInstance(userDetailsService)) {
            InMemoryUserDetailsManager manager = (InMemoryUserDetailsManager) userDetailsService;
            manager.changePassword(principal.getPassword(), password);//spring security已实现了密码修改功能
        }
        SecurityContextHolder.clearContext();//终止当前认证用户
        return "redirect:/index.jsp";
    }
}
