package com.service;

import com.pojo.SysUser;

import java.util.List;

/**
 * 认证服务接口
 * 提供用户认证和用户管理相关业务逻辑的方法定义
 *
 * @author Auto Generated
 */
public interface AuthService {

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户对象，不存在返回null
     */
    SysUser findByUsername(String username);

    /**
     * 用户注册方法
     *
     * @param user 用户对象（包含用户名、密码、邮箱）
     * @return 返回影响的行数（0表示用户名已存在，大于0表示成功）
     */
    int register(SysUser user);

    /**
     * 验证密码是否正确
     *
     * @param rawPassword     原始密码（用户输入的密码）
     * @param encodedPassword 加密后的密码（数据库中存储的密码）
     * @return 密码正确返回true，否则返回false
     */
    boolean validatePassword(String rawPassword, String encodedPassword);

    /**
     * 查询所有用户列表
     *
     * @return 返回所有用户的列表，按创建时间倒序排列
     */
    List<SysUser> findAllUsers();

    /**
     * 根据用户ID查询用户信息
     *
     * @param id 用户ID
     * @return 用户对象，不存在返回null
     */
    SysUser findUserById(Integer id);

    /**
     * 更新用户信息
     *
     * @param user 用户对象（包含要更新的信息）
     * @return 返回影响的行数（1表示成功）
     */
    int updateUser(SysUser user);

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 返回影响的行数（1表示成功）
     */
    int deleteUser(Integer id);
}
