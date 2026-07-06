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

-- 创建账单表（仅在表不存在时创建）
CREATE TABLE IF NOT EXISTS bill (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    transaction_date TIMESTAMP,
    amount DECIMAL(15,2) NOT NULL,
    balance DECIMAL(15,2),
    transaction_type VARCHAR(50),
    remark VARCHAR(500),
    source VARCHAR(20),
    category VARCHAR(50),
    bill_type VARCHAR(20),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id),
    FOREIGN KEY(user_id) REFERENCES sys_user(id) ON DELETE CASCADE
);
