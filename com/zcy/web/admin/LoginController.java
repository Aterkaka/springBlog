package com.zcy.web.admin;

import com.zcy.po.User;
import com.zcy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author: 张诚耀
 * @create: 2021-02-23 15:44
 */

@Controller
@RequestMapping("/admin")   //全局映射（映射请求），表示该类中的所有响应请求的方法都以该地址作为父路径
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping  //获取请求（默认/admin），并返回到login登陆页面
    public String loginPage(){
        return "admin/login";
    }

    @GetMapping("/login")
    public String LoginPage1() {
        return "admin/login";
    }

    @PostMapping("/login")   //发送请求，接收用户提交的用户名和密码参数
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.checkUser(username,password);
        if(user != null ){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index";
        }else {
            attributes.addFlashAttribute("message","用户名和密码错误");
            return "redirect:/admin";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
