package cn.willon.shiro.mapper;
/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */

import cn.willon.shiro.bean.Role;

import java.util.List;

/**
 * RoleMapper
 *
 * @author Wulao (乌佬)
 * @since 2018-12-11
 */
public interface RoleMapper {

    /**
     * 获取所有权限列表
     *
     * @return 权限列表
     */
    List<Role> list();


    /**
     * 获取用户的权限
     *
     * @param username 用户名称
     * @return 角色信息
     */
    List<Role> getByUsername(String username);


}
