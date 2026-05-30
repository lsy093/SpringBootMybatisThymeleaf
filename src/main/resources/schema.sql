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

-- 插入初始数据
INSERT INTO USER (name, age, phone, password) VALUES ('张三', 25, '13800138001', '123456');
INSERT INTO USER (name, age, phone, password) VALUES ('李四', 30, '13800138002', '123456');
INSERT INTO USER (name, age, phone, password) VALUES ('王五', 28, '13800138003', '123456');