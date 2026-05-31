-- 创建用户表（仅在表不存在时创建）
CREATE TABLE IF NOT EXISTS USER (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    age INT,
    phone VARCHAR(20),
    password VARCHAR(255),
    PRIMARY KEY(id)
);

-- 创建系统用户表（仅在表不存在时创建）
CREATE TABLE IF NOT EXISTS sys_user (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);
