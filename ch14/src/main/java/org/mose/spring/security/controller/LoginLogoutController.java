package org.mose.spring.security.controller;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 登录、注销、未授权异常处理
 *
 * Created by Administrator on 2017/8/1.
 */
@Controller
@RequestMapping("/")
public class LoginLogoutController {
    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public ModelAndView accessDenied(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        AccessDeniedException exception = (AccessDeniedException) request.getAttribute(WebAttributes.ACCESS_DENIED_403);
        modelAndView.getModelMap().addAttribute("errorDetails", exception.getMessage());
        StringWriter stringWriter = new StringWriter();
        exception.printStackTrace(new PrintWriter(stringWriter));
        modelAndView.getModelMap().addAttribute("errorTrace", stringWriter.toString());
        modelAndView.setViewName("accessDenied");
        return modelAndView;
    }
}
