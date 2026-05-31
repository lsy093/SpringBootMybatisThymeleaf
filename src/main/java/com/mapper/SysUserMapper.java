package com.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.pojo.SysUser;

/**
 * 系统用户Mapper接口
 * 提供用户数据的数据库操作方法
 *
 * @author Auto Generated
 */
@Repository
public interface SysUserMapper {

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 返回用户对象，如果不存在返回null
     */
    @Select("SELECT * FROM sys_user WHERE username = #{username}")
    SysUser findByUsername(@Param("username") String username);

    /**
     * 保存新用户信息
     *
     * @param user 用户对象
     * @return 返回影响的行数（1表示成功）
     */
    @Insert("INSERT INTO sys_user(username, password, email) VALUES(#{username}, #{password}, #{email})")
    int save(SysUser user);

    /**
     * 统计指定用户名的用户数量
     * 用于判断用户名是否已存在
     *
     * @param username 用户名
     * @return 返回该用户名的数量（0表示不存在，1表示已存在）
     */
    @Select("SELECT COUNT(*) FROM sys_user WHERE username = #{username}")
    int countByUsername(@Param("username") String username);
}
