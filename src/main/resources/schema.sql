-- 创建用户表
DROP TABLE IF EXISTS USER;
CREATE TABLE USER (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    age INT,
    phone VARCHAR(20),
    password VARCHAR(255),
    PRIMARY KEY(id)
);

-- 创建系统用户表
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);

-- 插入初始数据
INSERT INTO USER (name, age, phone, password) VALUES ('张三', 25, '13800138001', '123456');
INSERT INTO USER (name, age, phone, password) VALUES ('李四', 30, '13800138002', '123456');
INSERT INTO USER (name, age, phone, password) VALUES ('王五', 28, '13800138003', '123456');

-- 插入测试用户 (密码是123456的BCrypt加密)
INSERT INTO sys_user (username, password, email) VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', 'admin@example.com');
