---
title: '[Shiro]Shiro架构理解及源码走读'
category: Java
tag: Shiro
date: 2018-12-13 00:00:00
---

本项目为简单的shiro入门demo ， 其中会涉及本人shiro架构理解，以及简单的源码走读

 # 通用名词

   1. 认证(authentication):  你是谁? 
   2. 授权(authorization):  你可以做什么?
   3. 角色(role)的定义: 开发者自定义的角色 , 如 `admin` , `vip` , `user`
   4. 权限(permission)

   # Shiro 架构

   shiro结构中的名词: 

   1. Subject :  主体, 需要被验证/发起操作者
   2. SecurityManager: 安全管理器, 对 Subject 的进行安全操作
   3. Authenticator : 认证器, 认证Subject身份
   4. Authorizor:  授权器, 给 Subject 授权
   5. SessionManager
   6. CacheManager
   7. SessionDAO:  管理session数据
   8. Realm: 数据源, 决定 `认证信息` 和 `授权信息` 来源

   ## 工作原理

   ![shiro架构图](https://willon.cn/images/shiro_arc.png)

   1. SecurityManager 是整个框架的核心 , 包含 `Authenticator`  ,  `Authorizor` , `SessionManager` , `CacheManager` , `Realm`  ,  `SessioinDAO`
   2. Realm 提供  Authenticator 的 认证需要的信息源 , 提供  Authorizor 授权信息源
   3. SessionManager管理session,  SessionDAO管理session数据

   ### 认证流程

   ![](https://willon.cn/images/shiro_authentication.png)

   1. 获取用户的token信息
   2. Subject.login() , 底层是在 realm 中比较信息差异
   3. 如果登录成功, 返回认证信息

   ### 授权流程

   ![](https://willon.cn/images/shiro_authorizer.png)

   1. 获取用户的认证信息
   2. 获取用户的授权信息
   3. 检查用户是否有权限

   # 权限规则定义

   1. Permission 定义

   ```java
   /**
    * the permission string
    * in separate columns (e.g. 'domain', 'actions' and 'targets' columns)
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

   ```ini
   资源标识 : 动作... : 资源Id...
   domain :  action1,action2,action3  : target1,target2,target3
   domain :  * : target1,target2
   domain : action1 : *
   domain: *
   ```

   > 具体匹配算法和流程 org.apache.shiro.authz.permission.WildcardPermission#implies 方法



   # Realm

   Realm 是认证信息、授权信息数据来源的组件

   ## IniRealm

   简单的 `ini` 文件 , 按照一定的规则定义  用户, 角色, 权限 

   ```ini
   [users]   #写死的
   用户=密码,角色...
   
   [roles]   #写死的
   角色=权限...
   ```

   ## 自定义Realm

   1. 继承 `org.apache.shiro.realm.AuthorizingRealm` 
   2. 重写  `doGetAuthenticationInfo`  获取认证信息
   3. 重写 `doGetAuthorizationInfo`    获取授权信息

   ## 如何使用Realm

   在自定义的 `ShiroConfig` 类中添加:

   ```java
   @Bean
   public CustomRealm customRealm() {
   	return new CustomRealm();
   }
   
   @Bean
   public WebSecurityManager securityManager() {
   	DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
   	securityManager.setRealm(customRealm());
   	return securityManager;
   }
   ```

   # Shiro注解

   1. `@RequiresAuthentication` : 表明这个接口的访问需要经过认证

      ```java
      //the current Subject to have been authenticated during their current session
      @Target({ElementType.TYPE, ElementType.METHOD})
      @Retention(RetentionPolicy.RUNTIME)
      public @interface RequiresAuthentication {
      }
      ```

   2. `@RequiresPermissions` 

      ```java
      @Target({ElementType.TYPE, ElementType.METHOD})
      @Retention(RetentionPolicy.RUNTIME)
      public @interface RequiresPermissions {
          String[] value(); // 权限列表
          Logical logical() default Logical.AND;  //验证权限,满足的逻辑关系, and/or
      }
      ```

      > @RequiresPermissions(value={"admin","vip"}, logical=Logical.OR)    admin/vip  可以访问

   3. `@RequiresRoles` : 表明接口访问需要角色

      ```java
      @Target({ElementType.TYPE, ElementType.METHOD})
      @Retention(RetentionPolicy.RUNTIME)
      public @interface RequiresRoles {
          String[] value(); //角色列表
          Logical logical() default Logical.AND; //验证角色, 满足的逻辑关系 and/or
      }
      ```

   4. `@RequiresUser`   , `@RequiresGuest` : 就是用户和访客的区别



   ## 启用注解要注意的坑

   1. maven依赖

      ```xml
      <dependency>
          <groupId>org.apache.shiro</groupId>
              <artifactId>shiro-spring</artifactId>
              <version>1.3.2</version>
      </dependency>
      ```

   2. shiro-bean 生命周期管理 , AOP 配置

      ```java
          /**
           * 管理shiroBean的生命周期 , 这个方法必须是静态的,
           * 在类加载之前就完成实例化,  否则Spring启动异常, 属性注入异常
           */
          @Bean
          public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
              return new LifecycleBeanPostProcessor();
          }
      
          /**
           * 解决 Shiro注解不生效
           */
          @Bean
          public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
              DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator =
                  new DefaultAdvisorAutoProxyCreator();
              defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
              return defaultAdvisorAutoProxyCreator;
          }
      
          /**
           * 开启shiro aop支持
           */
          @Bean
          public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
              AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
                  new AuthorizationAttributeSourceAdvisor();
              authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
              return authorizationAttributeSourceAdvisor;
          }
      ```



   # 完整Spring Boot Shiro Demo

   git地址：

   1. [https://github.com/willon295/spring-boot-shiro-demo](https://github.com/willon295/spring-boot-shiro-demo)
