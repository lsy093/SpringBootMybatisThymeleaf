package com.pojo;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 系统用户实体类
 * 用于存储用户登录信息
 *
 * @author Auto Generated
 * @version 1.0
 */
@Data
public class SysUser {

    /**
     * 用户ID - 主键自增
     */
    private Integer id;

    /**
     * 用户名 - 登录账号，唯一不能重复
     */
    private String username;

    /**
     * 密码 - BCrypt加密存储
     */
    private String password;

    /**
     * 邮箱 - 用于找回密码和接收通知（可选）
     */
    private String email;

    /**
     * 创建时间 - 用户注册时间
     */
    private LocalDateTime createTime;
}
