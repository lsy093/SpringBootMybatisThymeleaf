package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapper.UserMapper;
import com.pojo.User;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;

	public List<User> userList() {
		return userMapper.userList();
	}

	public void save(User user) {
		// TODO Auto-generated method stub
		userMapper.save(user);;
	}

	public User get(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.findUserById(id);
	}

	public int update(Integer id, User user) {
		// TODO Auto-generated method stub
		return userMapper.update(id, user);
	}

	public void delete(int id) {
		// TODO Auto-generated method stub
		userMapper.delete(id);
	}

}
