package cn.willon.shiro.bean;
/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */

import lombok.Data;

import java.io.Serializable;

/**
 * Role
 *
 * @author Wulao (乌佬)
 * @since 2018-12-11
 */
@Data
public class Role implements Serializable {
    private Integer id;
    private String name;
    private String permissions;
}
