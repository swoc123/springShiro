package com.jaychen.springshiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Mycontroller {
    @RequestMapping("/")
    public String toIndex(Model model){
        model.addAttribute("msg","含窜");
        return "index";
    }

    @RequestMapping("/user/add")
    public String add(){
        return "user/add";
    }

    @RequestMapping("/user/update")
    public String update(){
        return "user/update";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/loginNow")
    public String loginNow(@RequestParam(name="username") String username, @RequestParam(name="password") String password, Model model){
        Subject subject=SecurityUtils.getSubject();
        AuthenticationToken token = new UsernamePasswordToken(username,password);
        try {
            subject.login(token);
            return "success";

        } catch (UnknownAccountException e ){
            model.addAttribute("msg","wrong username");
            e.printStackTrace();
            return "forward:/login";

        } catch(IncorrectCredentialsException e){
            model.addAttribute("msg","wrong pwd");
            e.printStackTrace();
            return "forward:/login";

        }
        catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }
}
