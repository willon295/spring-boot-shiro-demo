package cn.willon.shiro.exception;
/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * UnauthException
 *
 * @author Wulao (乌佬)
 * @since 2018-12-11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UnauthException extends Throwable {
    private Integer code;
    private String message;
}
