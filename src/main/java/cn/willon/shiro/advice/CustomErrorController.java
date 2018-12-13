package cn.willon.shiro.advice;

import cn.willon.shiro.dto.ResponseDTO;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ErrorController
 *
 * @author Wulao (乌佬)
 * @since 2018-12-11
 */
@Controller
public class CustomErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";


    @ResponseBody
    @GetMapping(ERROR_PATH)
    public ResponseDTO error() {
        ResponseDTO response = new ResponseDTO();
        response.setCode(-1);
        response.setMessage("error");
        return response;
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
