package com.service.impl;

import com.mapper.UserMapper;
import com.pojo.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务实现类
 * 实现用户相关业务逻辑
 *
 * @author Auto Generated
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> userList() {
        return userMapper.userList();
    }

    @Override
    public void save(User user) {
        userMapper.save(user);
    }

    @Override
    public User get(Integer id) {
        return userMapper.findUserById(id);
    }

    @Override
    public int update(User user) {
        return userMapper.update(user);
    }

    @Override
    public void delete(int id) {
        userMapper.delete(id);
    }
}
