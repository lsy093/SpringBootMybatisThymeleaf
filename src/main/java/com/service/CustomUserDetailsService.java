package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.pojo.SysUser;
import java.util.Collections;

/**
 * Spring Security 用户详情服务实现类
 * 实现UserDetailsService接口，用于Spring Security的认证流程
 *
 * @author Auto Generated
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * 认证服务接口，用于查询用户信息
     */
    @Autowired
    private AuthService authService;

    /**
     * 根据用户名加载用户详情
     * 此方法在用户登录时由Spring Security自动调用
     *
     * @param username 用户名
     * @return UserDetails 对象，包含用户的认证信息
     * @throws UsernameNotFoundException 如果用户不存在抛出此异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 通过认证服务查询用户
        SysUser sysUser = authService.findByUsername(username);

        // 如果用户不存在，抛出异常
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }

        // 将SysUser转换为Spring Security的User对象
        // 并设置用户的角色为 ROLE_USER
        return new User(
                sysUser.getUsername(),           // 用户名
                sysUser.getPassword(),           // 加密后的密码
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")) // 用户角色
        );
    }
}
