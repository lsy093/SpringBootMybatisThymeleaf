package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.pojo.SysUser;
import com.service.AuthService;

/**
 * 认证控制器
 * 处理用户登录、注册相关请求
 *
 * @author Auto Generated
 */
@Controller
public class AuthController {

    /**
     * 认证服务接口
     */
    @Autowired
    private AuthService authService;

    /**
     * 跳转到注册页面
     *
     * @return 返回注册页面视图
     */
    @GetMapping("/register")
    public String toRegister() {
        return "/register";
    }

    /**
     * 处理用户注册请求
     *
     * @param user   用户对象，包含用户名、密码、邮箱
     * @param model  Model对象，用于向前端传递数据
     * @return 注册成功跳转到登录页，失败返回注册页并显示错误信息
     */
    @PostMapping("/register")
    public String register(SysUser user, Model model) {
        // 检查用户名是否已存在
        if (authService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "用户名已存在");
            return "/register";
        }

        // 调用服务进行用户注册
        int result = authService.register(user);

        // 根据注册结果进行页面跳转
        if (result > 0) {
            // 注册成功，跳转到登录页并显示注册成功提示
            return "redirect:/login?registered=true";
        }

        // 注册失败，返回注册页并显示错误信息
        model.addAttribute("error", "注册失败，请重试");
        return "/register";
    }

    /**
     * 跳转到登录页面
     *
     * @return 返回登录页面视图
     */
    @GetMapping("/login")
    public String login() {
        return "/login";
    }
}
