Jeecg-Cloud 快速开发平台
===============

当前最新版本： 1.0.0-beta（发布日期：****）

- 基于Spring Boot 2.1.3、 Spring Cloud Greenwich.SR3 & Alibaba、 Shiro、Jwt的快速开发平台
- 前端采用 ant-design-vue，简单易用封装各种组件，轻松满足企业级项目需求
- 更多功能介绍，请参考 [JeecgBoot版本](https://github.com/zhangdaiscott/jeecg-boot)

### 技术体系


- 1、Nacos 服务注册和发现
- 2、Nacos 统一配置中心
- 3、熔断降级限流 sentinel
- 4、feign配合sentinel使用
- 5、SpringCloud Gateway
- 6、服务监控 actuator
- 7、Shiro+Jwt 权限控制
- 8、分布式文件系统 minio、阿里OSS
- 9、Spring Boot Admin服务监控
- 10、链路跟踪 skywalking

## 技术架构
- 基础框架：Spring Boot 2.1.3.RELEASE
-  Spring Cloud Greenwich.SR3
-  Spring Cloud Alibaba 2.1.0.RELEASE
- 持久层框架：Mybatis-plus_3.1.2
- 安全框架：Apache Shiro 1.4.0，Jwt_3.7.0
- 数据库连接池：阿里巴巴Druid 1.1.10
- 缓存框架：redis
- 日志打印：logback
- 其他：fastjson，poi，Swagger-ui，quartz, lombok（简化代码）等。



## 开发环境

- 语言：Java 8
- IDE： IDEA 或 Eclipse （安装lombok插件）
- 依赖管理：Maven
- 数据库：MySQL5.7  &  Oracle 11g
- 缓存：Redis

#### 核心依赖 


依赖 | 版本
---|---
Spring Boot |  Spring Boot 2.1.3.RELEASE
Spring Cloud | Greenwich.SR3
Spring Cloud Alibaba  | 2.1.0.RELEASE
Mybatis Plus | 3.1.2
Shiro | 1.4.0
Jwt | 3.7.0

#### 模块说明
```
# 前端项目源码
ant-design-vue-jeecg  --> https://github.com/zhangdaiscott/jeecg-boot/tree/master/ant-design-vue-jeecg

# 后端项目结构
jeecg-cloud
├── jeecg-common -- 系统公共模块 
│    ├── jeecg-common-core -- 公共工具类核心包
│    ├── jeecg-common-cloud -- 集成springcloud依赖
│    ├── jeecg-common-cloud-starter -- jeecg cloud quick starter
├── jeecg-cloud-gateway -- Spring Cloud Gateway网关[9999]
├── jeecg-cloud-system -- 用户权限系统管理模块
│    └── jeecg-cloud-system-api -- 用户权限管理系统——公共api
│    └── jeecg-cloud-system-biz -- 用户权限管理系统——业务管理模块[8001]
├── jeecg-cloud-demo -- 微服务示例模块
│    ├── jeecg-cloud-demo-api -- 微服务示例模块——公共api
│    ├── jeecg-cloud-demo-biz -- 微服务示例模块——业务管理模块[8002]
```

### 聚合swaggerAPI文档
```
http://localhost:9999/doc.html
```

### 测试请求

```
-  直接访问demo服务请求
http://localhost:8002/demo/hello

-  直接访问system服务请求
http://localhost:8001/sys/randomImage/12121

-  网关跳转demo请求：
http://localhost:9999/demo/hello

-  网关跳转system服务请求
验证码：  http://localhost:9999/sys/randomImage/12121
登录接口：http://localhost:9999/sys/login
```

登录参数 JSON
 ``` xml
{
	"captcha": "aqun",
	"checkKey": "12121",
	"password": "123456",
	"username": "admin"
}
 ```