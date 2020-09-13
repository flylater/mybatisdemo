package com.rszhang.mybatisdemo.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/shiro")
public class LoginController {

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {

        Subject currentUser = SecurityUtils.getSubject();

        if (!currentUser.isAuthenticated()) {
            // 把用户名和密码封装为 UsernamePasswordToken对象
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            // 设置记住我
            token.setRememberMe(true);

            try {
                // 执行登录
                currentUser.login(token);
            } catch (AuthenticationException ae) {
                logger.error("登录失败： " + ae.getMessage());
            }
        }

        return "redirect:/empsj.html";
    }
}
