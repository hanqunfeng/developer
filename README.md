# developer
项目基于Spring Boot开发，整体分为如下层次：
* bean: model层
* dao: 数据库层
* service: 服务层
* web: 页面层



# 工具使用
项目中用到的各个组件，可以参考下面的方式进行设置。
## Redis
```shell
brew update
brew install redis
```

### 启动
`redis-server  /usr/local/etc/redis.conf`

### 关闭
`Ctrl+C`

## MongoDB
```shell
sudo mkdir /usr/local/Frameworks
sudo chown -R hanqf /usr/local/Frameworks
brew update
brew install mongodb
```

### 启动：
`mongod --config /usr/local/etc/mongod.conf`

* 带访问控制启动
```
mongod --config /usr/local/etc/mongod.conf --auth
```


### 关闭：
`Ctrl+C`

### 登录：
`mongo --host 127.0.0.1:27017`

* 带访问控制登录
```
mongo --host 127.0.0.1:27017 -utest -p123456 --authenticationDatabase test
```

### 创建或切换数据库
`> use test;` 会创建名称为test的数据库

### 创建用户及分配角色
* 管理员
```
> use admin

> db.createUser(
   {
     user: "adminUser",
     pwd: "adminPass",
     roles: [ { role: "userAdminAnyDatabase", db: "admin" } ]
   }
 )
```

* 普通用户
```shell
> db.createUser(
   {
     user: "test",
     pwd: "123456",
     roles: [ { role: "dbOwner", db: "test" } ]
   }
);
```

### 退出登录
`Ctrl+C` 或者 `> exit`


## RabbitMQ
```shell
# 需要授权一些目录的读权限给当前用户
brew update
brew install erlang
brew install rabbitmq
```

### 需要将`/usr/local/sbin/`加入环境变量$PATH


### 启动：
`rabbitmq-server`

### 开启webUI：
`rabbitmq-plugins enable rabbitmq_management`
http://127.0.0.1:15672 默认guest/guest，我已经修改了，admin/rabbitmq

### 关闭webUI：
`rabbitmq-plugins disable rabbitmq_management`
