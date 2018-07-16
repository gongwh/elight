package com.snow.core;

import com.snow.lib.result.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @create by SNOW 2018.07.12
 */
@Controller
//@RequestMapping("${server.error.path:${error.path:/error}}")
public class FundamentalErrorController implements ErrorController {
    private static final String PATH = "/error";

    @Autowired
    private ErrorAttributes errorAttributes;

    @Override
    public String getErrorPath() {
        return PATH;
    }

    @RequestMapping
    @ResponseBody
    public ResultVO doHandleError(HttpServletRequest request) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Map<String, Object> errorAttributesData = errorAttributes.getErrorAttributes(requestAttributes, true);

        Integer status = (Integer) errorAttributesData.get("status");
        String error = (String)errorAttributesData.get("error");
        String exception = (String)errorAttributesData.get("exception");
        String path = (String)errorAttributesData.get("path");
        String message = (String)errorAttributesData.get("message");

        ResultVO resultVO = new ResultVO();
        resultVO.setStatus(status);
        resultVO.setError(error);
        resultVO.setException(exception);
        resultVO.setPath(path);
        resultVO.setMessage(message);
        return resultVO;
    }

}