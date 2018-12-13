package cn.willon.shiro.mapper;

import cn.willon.shiro.bean.Role;
import cn.willon.shiro.bean.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */

/**
 * UserMapperTest
 *
 * @author Wulao (乌佬)
 * @since 2018-12-11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserMapperTest {


    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Test
    public void list() {

        User user = userMapper.get("admin", "admin");
        System.out.println(user.toString());
    }


    @Test
    public void roleList() {

        List<Role> admin = roleMapper.getByUsername("admin");
        Set<String> collect = admin.stream().map(r -> Arrays.asList(r.getPermissions().split(","))).flatMap(Collection::stream).collect(Collectors.toSet());
        System.out.println(collect);
    }
}