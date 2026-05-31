package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.pojo.SysUser;
import com.service.AuthService;

import java.util.List;

/**
 * 认证控制器
 * 处理用户登录、注册和用户管理相关请求
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

    /**
     * 跳转到用户列表页面
     *
     * @param model Model对象，用于向前端传递数据
     * @return 返回用户列表页面视图
     */
    @GetMapping("/users")
    public String userList(Model model) {
        // 获取当前登录用户信息
        String currentUsername = getCurrentUsername();
        model.addAttribute("currentUser", currentUsername);

        // 查询所有用户列表
        List<SysUser> users = authService.findAllUsers();
        model.addAttribute("users", users);

        return "/user-list";
    }

    /**
     * 跳转到用户编辑页面
     *
     * @param id    用户ID
     * @param model Model对象，用于向前端传递数据
     * @return 返回用户编辑页面视图
     */
    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model) {
        // 查询用户信息
        SysUser user = authService.findUserById(id);
        model.addAttribute("user", user);

        return "/user-edit";
    }

    /**
     * 处理用户更新请求
     *
     * @param user  用户对象，包含要更新的信息
     * @param model Model对象，用于向前端传递数据
     * @return 更新成功跳转到用户列表页，失败返回编辑页并显示错误信息
     */
    @PostMapping("/users/update")
    public String updateUser(SysUser user, Model model) {
        // 检查用户名是否与其他用户冲突
        SysUser existingUser = authService.findByUsername(user.getUsername());
        if (existingUser != null && !existingUser.getId().equals(user.getId())) {
            model.addAttribute("error", "用户名已存在");
            model.addAttribute("user", user);
            return "/user-edit";
        }

        // 调用服务更新用户信息
        int result = authService.updateUser(user);

        if (result > 0) {
            // 更新成功，跳转到用户列表页并显示成功提示
            return "redirect:/users?updated=true";
        }

        // 更新失败，返回编辑页并显示错误信息
        model.addAttribute("error", "更新失败，请重试");
        model.addAttribute("user", user);
        return "/user-edit";
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 删除成功跳转到用户列表页
     */
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        authService.deleteUser(id);
        return "redirect:/users?deleted=true";
    }

    /**
     * 获取当前登录用户名
     *
     * @return 当前登录用户名
     */
    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return principal.toString();
    }

    /**
     * 跳转到首页（记账软件Dashboard）
     *
     * @param model Model对象，用于向前端传递数据
     * @return 返回首页视图
     */
    @GetMapping("/home")
    public String home(Model model) {
        String currentUsername = getCurrentUsername();
        model.addAttribute("currentUser", currentUsername);
        return "/home";
    }
}
