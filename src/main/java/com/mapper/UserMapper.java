package com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.pojo.User;

@Repository
public interface UserMapper {

	public List<User> userList();
	
	public void save(User user);
	
	public User findUserById(@Param("id") int id);
	
	public int update(@Param("id") int id, User user);
	
	public int delete(@Param("id") int id);	
	
}
