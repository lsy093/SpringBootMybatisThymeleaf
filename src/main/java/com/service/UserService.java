package com.service;

import java.util.List;
import com.pojo.User;

/**
 * 用户服务接口
 * 提供用户相关业务逻辑的方法定义
 *
 * @author Auto Generated
 */
public interface UserService {

    /**
     * 获取用户列表
     *
     * @return 用户列表
     */
    List<User> userList();

    /**
     * 保存用户
     *
     * @param user 用户对象
     */
    void save(User user);

    /**
     * 根据ID获取用户
     *
     * @param id 用户ID
     * @return 用户对象
     */
    User get(Integer id);

    /**
     * 更新用户
     *
     * @param user 用户对象
     * @return 影响的行数
     */
    int update(User user);

    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    void delete(int id);
}
