package cn.willon.shiro.controller;

import cn.willon.shiro.bean.User;
import cn.willon.shiro.dto.ResponseDTO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * UserController
 *
 * @author Wulao (乌佬)
 * @since 2018-12-11
 */
@Controller
public class UserController {


    @ResponseBody
    @PostMapping("/check")
    public ResponseDTO check(@RequestBody User user) {
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            subject.login(token);
            responseDTO.setCode(200);
            responseDTO.setMessage("success");
            return responseDTO;
        } catch (AuthenticationException e) {
            responseDTO.setCode(401);
            responseDTO.setMessage(e.getMessage());
            return responseDTO;
        }
    }

    @GetMapping("/logout")
    @ResponseBody
    public ResponseDTO logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(200);
        responseDTO.setMessage("退出成功");
        return responseDTO;
    }


    @ResponseBody
    @RequiresPermissions(value = "data:select")
    @GetMapping("/select")
    public ResponseDTO select() {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(200);
        responseDTO.setMessage("select Success!");
        return responseDTO;
    }

    @ResponseBody
    @RequiresPermissions(value = "data:delete")
    @GetMapping("/delete")
    public ResponseDTO delete() {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(200);
        responseDTO.setMessage("delete Success!");
        return responseDTO;
    }

    @ResponseBody
    @RequiresPermissions(value = "data:batchDelete")
    @GetMapping("/batchDelete")
    public ResponseDTO insert() {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(200);
        responseDTO.setMessage("delete Success!");
        return responseDTO;
    }

}
