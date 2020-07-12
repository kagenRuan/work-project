package com.ruan.yuanyuan.exception;

import com.ruan.yuanyuan.entity.ResultObject;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User: ruanyuanyuan
 * Date: 2019-07-26
 * Time: 16:06
 * version:
 * Description: 全局异常配置
 */
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    private static ResultObject resultObject = new ResultObject();

    @ExceptionHandler(value = Exception.class)
    public ResultObject defaultErrorHandler(Exception exception) {
        exception.printStackTrace();
        if (exception instanceof BusinessException) {
            BusinessException businessException = (BusinessException) exception;
            resultObject.setCode(businessException.getCode());
            resultObject.setMsg(businessException.getMessage());
            return resultObject;
        }else if(exception instanceof UnauthorizedException){
            resultObject.setCode(ExceptionUtil.SystemExceptionEnum.UNAUTHORIZED.getCode());
            resultObject.setMsg(ExceptionUtil.SystemExceptionEnum.UNAUTHORIZED.getMessage());
            return resultObject;
        }
        resultObject.setCode(-99999);
        resultObject.setMsg("系统异常");
        return resultObject;
    }


}
