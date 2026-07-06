# 智能记账系统

基于 Spring Boot + MyBatis + Thymeleaf 构建的现代化记账管理系统，支持多平台账单导入、智能分类和数据统计。

## 功能特性

### 核心功能
- **用户认证**：支持用户注册、登录、密码加密
- **账单管理**：支持新增、编辑、删除账单记录
- **CSV导入**：支持招商银行、微信支付、支付宝账单导入（策略模式设计）
- **智能分类**：自动识别交易类型并分类（餐饮、交通、购物等）
- **数据统计**：收支统计、余额计算

### 技术特性
- 采用策略模式设计，便于扩展新的账单来源
- SQL统一管理在XML文件中，便于维护
- 响应式设计，支持移动端访问
- H2内存数据库，无需额外配置

## 技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Java | 8+ | 开发语言 |
| Spring Boot | 2.0.5.RELEASE | 后端框架 |
| MyBatis | 2.1.3 | ORM框架 |
| Thymeleaf | 3.x | 模板引擎 |
| Spring Security | 5.x | 安全框架 |
| H2 Database | 1.4.x | 内存数据库 |

## 项目结构

```
src/main/
├── java/com/
│   ├── Application.java          # 启动类
│   ├── config/                   # 配置类
│   │   └── SecurityConfig.java   # Spring Security配置
│   ├── controller/               # 控制器层
│   │   ├── AuthController.java   # 用户认证控制器
│   │   └── BillController.java   # 账单管理控制器
│   ├── mapper/                   # 数据访问层
│   │   ├── SysUserMapper.java    # 用户Mapper
│   │   └── BillMapper.java       # 账单Mapper
│   ├── pojo/                     # 实体类
│   │   ├── SysUser.java          # 用户实体
│   │   └── Bill.java             # 账单实体
│   └── service/                  # 服务层
│       ├── AuthService.java      # 认证服务接口
│       ├── BillService.java      # 账单服务接口
│       ├── BillImportStrategy.java  # 导入策略接口
│       ├── CustomUserDetailsService.java  # 用户详情服务
│       └── impl/                 # 服务实现类
│           ├── AuthServiceImpl.java
│           ├── BillServiceImpl.java
│           ├── CmbBillImportStrategy.java    # 招商银行导入策略
│           ├── WechatBillImportStrategy.java # 微信支付导入策略（预留）
│           └── AlipayBillImportStrategy.java # 支付宝导入策略（预留）
├── resources/
│   ├── application.properties    # 应用配置
│   ├── schema.sql                # 数据库初始化脚本
│   ├── logback.xml               # 日志配置
│   ├── com/mapper/               # MyBatis映射文件
│   │   ├── SysUserMapper.xml
│   │   └── BillMapper.xml
│   └── templates/                # Thymeleaf模板
│       ├── login.html            # 登录页
│       ├── register.html         # 注册页
│       ├── home.html             # 首页仪表盘
│       ├── bill-import.html      # 账单导入页
│       ├── bill-list.html        # 账单列表页
│       ├── bill-edit.html        # 账单编辑页
│       ├── user-list.html        # 用户列表页
│       └── user-edit.html        # 用户编辑页
```

## 快速开始

### 环境要求
- JDK 8 或更高版本
- Maven 3.6+

### 启动应用

```bash
# 进入项目目录
cd SpringBootMybatisThymeleaf

# 编译并运行
mvn clean spring-boot:run
```

### 访问地址
- 应用首页：http://localhost:8050
- H2控制台：http://localhost:8050/h2-console

### 默认用户
- 用户名：zhangsan
- 密码：1234

## 使用指南

### 1. 用户注册/登录
1. 访问 http://localhost:8050
2. 点击「注册」创建新账户
3. 使用注册的账户登录系统

### 2. 导入账单
1. 登录后点击「导入账单」
2. 选择账单来源（如招商银行）
3. 上传CSV格式的账单文件
4. 系统自动解析并导入账单

### 3. 查看账单
1. 点击「查看账单」查看所有记录
2. 支持按来源筛选
3. 支持编辑和删除操作

## 账单导入格式

### 招商银行CSV格式
```csv
交易日期,交易时间,收入,支出,余额,交易类型,交易备注
20260530,12:08:20,,12.5,323.44,银联快捷支付,美团-美团App多嘴超级肉蟹煲
```

### 字段说明
| 字段 | 说明 |
|------|------|
| 交易日期 | 格式：yyyyMMdd |
| 交易时间 | 格式：HH:mm:ss |
| 收入 | 收入金额（正数） |
| 支出 | 支出金额（正数） |
| 余额 | 交易后余额 |
| 交易类型 | 如：银联快捷支付、网联协议支付 |
| 交易备注 | 交易描述信息 |

## 智能分类规则

系统根据交易备注自动分类：
- **餐饮**：包含"餐饮"、"外卖"、"美团"、"饿了么"等关键词
- **交通**：包含"滴滴"、"打车"、"地铁"、"公交"等关键词
- **购物**：包含"超市"、"淘宝"、"京东"等关键词
- **通讯**：包含"微信"、"话费"等关键词
- **工资**：包含"工资"关键词
- **其他**：未匹配到上述分类的交易

## 数据库配置

默认使用H2内存数据库，数据文件存储在：
```
jdbc:h2:mem:testdb
```

如需持久化数据，修改 `application.properties`：
```properties
spring.datasource.url=jdbc:h2:file:E:/Eclipse-workspace/data/testdb
```

## 项目规范

### 代码规范
- 遵循阿里巴巴Java开发规范
- 类和方法使用Javadoc注释
- SQL语句统一管理在XML文件中

### 命名规范
- 包名：小写字母，如 `com.controller`
- 类名：大驼峰，如 `AuthController`
- 方法名：小驼峰，如 `findByUsername`
- 变量名：小驼峰，如 `userId`

### 目录结构
```
controller/   # REST API控制层
service/      # 业务逻辑层（接口）
service/impl/ # 业务逻辑层（实现）
mapper/       # 数据访问层（接口）
pojo/         # 数据实体
config/       # 配置类
```

## 开发计划

- [x] 用户注册/登录功能
- [x] 用户管理功能
- [x] 账单导入功能（招商银行）
- [ ] 账单导入功能（微信支付）
- [ ] 账单导入功能（支付宝）
- [ ] 手动记账功能
- [ ] 统计报表功能
- [ ] 数据导出功能

## 许可证

MIT License

## 作者

智能记账系统开发团队
