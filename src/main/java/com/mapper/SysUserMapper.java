package com.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.pojo.SysUser;

import java.util.List;

/**
 * 系统用户Mapper接口
 * 提供用户数据的数据库操作方法
 * 所有SQL语句都写在对应的XML文件中，便于维护
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
    SysUser findByUsername(@Param("username") String username);

    /**
     * 保存新用户信息
     *
     * @param user 用户对象
     * @return 返回影响的行数（1表示成功）
     */
    int save(SysUser user);

    /**
     * 统计指定用户名的用户数量
     * 用于判断用户名是否已存在
     *
     * @param username 用户名
     * @return 返回该用户名的数量（0表示不存在，1表示已存在）
     */
    int countByUsername(@Param("username") String username);

    /**
     * 查询所有用户列表
     *
     * @return 返回所有用户的列表
     */
    List<SysUser> findAll();

    /**
     * 根据用户ID查询用户信息
     *
     * @param id 用户ID
     * @return 返回用户对象，如果不存在返回null
     */
    SysUser findById(@Param("id") Integer id);

    /**
     * 更新用户信息
     *
     * @param user 用户对象（包含要更新的信息）
     * @return 返回影响的行数（1表示成功）
     */
    int update(SysUser user);

    /**
     * 根据用户ID删除用户
     *
     * @param id 用户ID
     * @return 返回影响的行数（1表示成功）
     */
    int deleteById(@Param("id") Integer id);
}
