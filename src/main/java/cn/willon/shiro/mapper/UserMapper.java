package cn.willon.shiro.mapper;
/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */

import cn.willon.shiro.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * UserMapper
 *
 * @author Wulao (乌佬)
 * @since 2018-12-11
 */
public interface UserMapper {

    /**
     * 查询所有用户信息
     *
     * @return 用户列表
     */
    List<User> list();

    /**
     * 通过名字获取用户信息
     *
     * @param name     名字
     * @param password 密码
     * @return 用户信息
     */
    User get(@Param("name") String name, @Param("password") String password);
}
