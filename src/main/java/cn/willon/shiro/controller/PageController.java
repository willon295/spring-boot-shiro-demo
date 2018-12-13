package cn.willon.shiro.controller;
/*
 * Copyright (C) 2009-2018 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * PageController
 *
 * @author Wulao (乌佬)
 * @since 2018-12-11
 */
@Controller
public class PageController {


    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/index")
    public String index() {
        return "/index";
    }

    @GetMapping("/401")
    public String error() {
        return "/login";
    }

}
