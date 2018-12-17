---
title: '[Shiro]Shiro架构以及原理剖析'
category: Java
tag: Shiro
date: 2018-12-13 00:00:00
---

# Shiro-demo

1. 认证(authentication): 对用户身份进行验证
2. 授权(authorization):  对用户进行授权,如该用户拥有什么角色 , 拥有什么权限
3. 角色(role)的定义: 开发者自定义的角色 , 如 `admin` , `vip` , `user`
4. 权限(permission)的定义:  应该符合一定的规则


# 权限规则定义


1.  Permission 定义

```java

/**
 * JPA/Hibernate persisted permissions that wish to store the parts of the permission string
 * in separate columns (e.g. 'domain', 'actions' and 'targets' columns), which can be used in querying
 * strategies.
 */
public class DomainPermission extends WildcardPermission {
    private String domain;
    private Set<String> actions;
    private Set<String> targets;
}



public class WildcardPermission implements Permission, Serializable {
    protected static final String WILDCARD_TOKEN = "*";
    protected static final String PART_DIVIDER_TOKEN = ":";
    protected static final String SUBPART_DIVIDER_TOKEN = ",";
    protected static final boolean DEFAULT_CASE_SENSITIVE = false;
    private List<Set<String>> parts;
    
	public String toString() {
        StringBuilder buffer = new StringBuilder();
        for (Set<String> part : parts) {
            if (buffer.length() > 0) {
                buffer.append(PART_DIVIDER_TOKEN);
            }
            Iterator<String> partIt = part.iterator();
            while(partIt.hasNext()) {
                buffer.append(partIt.next());
                if (partIt.hasNext()) {
                    buffer.append(SUBPART_DIVIDER_TOKEN);
                }
            }
        }
        return buffer.toString();
    }
}
```



看完源码之后才发现定义应该是这样

```
domain :  action1,action2,action3  : target1,target2,target3
domain :  * : target1,target2
domain : action1 : *
domain: *
```
> 具体匹配算法和流程 org.apache.shiro.authz.permission.WildcardPermission#implies 方法

# Shiro 架构

先明白几个名词: 

1. Subject :  主体, 需要被验证/发起操作者
2. SecurityManager: 安全管理器, 对 Subject 的进行安全操作
3. Authenticator : 认证器, 认证Subject身份
4. Authorizor:  授权器, 给 Subject 授权
5. SessionManager
6. CacheManager
7. SessionDAO:  管理session数据
8. Realm: 数据源, 决定 `认证信息` 和 `授权信息` 来源

## 架构及流程

![](http://blog.willon.cn/images/shiro_arc.png)

1. SecurityManager 是整个框架的核心 , 包含 `Authenticator`  ,  `Authorizor` , `SessionManager` , `CacheManager` , `Realm`  ,  `SessioinDAO`
2.  Realm 提供  Authenticator 的 认证需要的信息源 , 提供  Authorizor 授权信息源
3. SessionManager管理session,  SessionDAO管理session数据

### 认证流程

![](http://blog.willon.cn/images/shiro_authentication.png)

1. 获取用户的token信息
2. Subject.login() , 底层是在 realm 中比较信息差异
3. 如果登录成功, 返回认证信息

### 授权流程

![](/http://blog.willon.cn/images/shiro_authorizer.png)

1. 获取用户的认证信息
2. 获取用户的授权信息
3. 检查用户是否有权限
