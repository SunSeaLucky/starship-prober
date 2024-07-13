# 开发文档
## 1. 后端开发API
### 1.1 用户
#### 1.1.1 注册
- 请求方式：POST
- 请求URL：http://localhost:8080/user/register
- 请求参数：

|参数名称|类型|
|-|-|
|username|String|
|time|String|
|random|String|
|sign|String|
|avatar|String|
|email|String|

#### 1.1.2 登录

第一步

- 请求方式：GET
- 请求URL：http://localhost:8080/user/getUserParam

|参数名称|类型|
|-|-|
|username|String|
---

第二步

- 请求方式：POST
- 请求URL：http://localhost:8080/user/login
- 请求参数：

|参数名称|类型|
|-|-|
|username|String|
|time|String|
|ramdom|String|
|sign|String|

## 后端开发数据表
### 用户表
|字段|类型|
|-|-|
|username|varchar(300)|
|password|varchar(300)|
|random|varchar(300)|
|avatar|varchar(3000)|
|email|varchar(300)|
|register_time|varchar(100)|

创建表的SQL语句
```sql
create table user(
    username varchar(300) primary key,
    password varchar(300),
    random varchar(300),
    avatar varchar(3000),
    email varchar(300),
    register_time varchar(300)
);
```


### 图书表
|字段|类型|
|-|-|
|id|varchar(100)|
|name|varchar(500)|
|description|varchar(3000)|
|picture|varchar(3000)|

创建表的SQL语句
```sql
create table book(
    id varchar(100) primary key,
    name varchar(500),
    description varchar(3000),
    picture varchar(3000)
);
```

用户可以增加的图书：
```sql
insert into weapon values("1","Glock 17","This pistol has a simple design with a minimum of parts. There are only 33 parts, including the magazine. This pistol can be disassembled within a minute using just a pin, or nail. ",)
```


### 用户-图书表

创建表的SQL语句
```sql
create table user_book(
    username varchar(300) references user(username),
    id varchar(100) references book(id),
    number int check(number > 0),
    primary key(username,id)
);
```