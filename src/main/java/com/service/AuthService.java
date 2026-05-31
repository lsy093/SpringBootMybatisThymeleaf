package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.mapper.SysUserMapper;
import com.pojo.SysUser;

/**
 * 认证服务类
 * 处理用户注册和密码验证等认证相关业务逻辑
 *
 * @author Auto Generated
 */
@Service
public class AuthService {

    /**
     * 用户Mapper接口，用于数据库操作
     */
    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 密码加密器，用于BCrypt加密和验证
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户对象，不存在返回null
     */
    public SysUser findByUsername(String username) {
        return sysUserMapper.findByUsername(username);
    }

    /**
     * 用户注册方法
     * 1. 检查用户名是否已存在
     * 2. 对密码进行BCrypt加密
     * 3. 保存用户信息到数据库
     *
     * @param user 用户对象（包含用户名、密码、邮箱）
     * @return 返回影响的行数（0表示用户名已存在，大于0表示成功）
     */
    public int register(SysUser user) {
        // 检查用户名是否已存在
        if (sysUserMapper.countByUsername(user.getUsername()) > 0) {
            return 0;
        }
        // 对密码进行BCrypt加密存储，确保安全
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 保存用户信息
        return sysUserMapper.save(user);
    }

    /**
     * 验证密码是否正确
     * 使用BCrypt的matches方法进行密码比对
     *
     * @param rawPassword     原始密码（用户输入的密码）
     * @param encodedPassword 加密后的密码（数据库中存储的密码）
     * @return 密码正确返回true，否则返回false
     */
    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
