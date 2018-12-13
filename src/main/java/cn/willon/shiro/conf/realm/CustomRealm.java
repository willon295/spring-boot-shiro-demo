package cn.willon.shiro.conf.realm;
/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */

import cn.willon.shiro.bean.Role;
import cn.willon.shiro.bean.User;
import cn.willon.shiro.mapper.RoleMapper;
import cn.willon.shiro.mapper.UserMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * CustomRealm
 *
 * @author Wulao (乌佬)
 * @since 2018-12-11
 */
@Component
public class CustomRealm extends AuthorizingRealm {


    private static final String CUSTOM_REALM = "customRealm";
    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    /**
     * 认证
     *
     * @param token token
     * @return 认证信息
     * @throws AuthenticationException 认证失败
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String username = (String) token.getPrincipal();
        char[] credentials = (char[]) token.getCredentials();
        String password = String.valueOf(credentials);
        User user = userMapper.get(username, password);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password, CUSTOM_REALM);
        if (user != null) {
            return info;
        } else {
            throw new AuthenticationException("用户不存在");
        }

    }

    /**
     * 授权
     *
     * @param principals 凭证信息
     * @return 授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        String username = (String) principals.getPrimaryPrincipal();
        List<Role> userRole = roleMapper.getByUsername(username);
        Set<String> roles = userRole.stream().map(Role::getName).collect(Collectors.toSet());
        Set<String> permissions = userRole.stream().map(r -> new HashSet<>(Arrays.asList(r.getPermissions().split(",")))).flatMap(Collection::stream).collect(Collectors.toSet());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roles);
        info.setStringPermissions(permissions);
        return info;
    }


}
