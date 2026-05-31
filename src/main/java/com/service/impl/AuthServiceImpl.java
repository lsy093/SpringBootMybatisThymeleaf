package com.service.impl;

import com.mapper.SysUserMapper;
import com.pojo.SysUser;
import com.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 认证服务实现类
 * 实现用户注册、登录验证和用户管理等业务逻辑
 *
 * @author Auto Generated
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public SysUser findByUsername(String username) {
        return sysUserMapper.findByUsername(username);
    }

    @Override
    public int register(SysUser user) {
        if (sysUserMapper.countByUsername(user.getUsername()) > 0) {
            return 0;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return sysUserMapper.save(user);
    }

    @Override
    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public List<SysUser> findAllUsers() {
        return sysUserMapper.findAll();
    }

    @Override
    public SysUser findUserById(Integer id) {
        return sysUserMapper.findById(id);
    }

    @Override
    public int updateUser(SysUser user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            SysUser existingUser = sysUserMapper.findById(user.getId());
            if (existingUser != null) {
                user.setPassword(existingUser.getPassword());
            }
        }
        return sysUserMapper.update(user);
    }

    @Override
    public int deleteUser(Integer id) {
        return sysUserMapper.deleteById(id);
    }
}
