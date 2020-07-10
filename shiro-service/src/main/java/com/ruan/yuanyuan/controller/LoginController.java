package com.ruan.yuanyuan.controller;

import com.ruan.yuanyuan.dto.UserDto;
import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.exception.BusinessAssert;
import com.ruan.yuanyuan.exception.BusinessException;
import com.ruan.yuanyuan.exception.ExceptionUtil;
import com.ruan.yuanyuan.util.VerifyCodeUtil;
import com.ruan.yuanyuan.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @ClassName: LoginController
 * @author: ruanyuanyuan
 * @date: 2020/7/5 19:24
 * @version: 1.0
 * @description: 登录控制器
 **/
@RequestMapping("/api/login")
@RestController
public class LoginController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * 获取验证码方法
     * @param request
     * @param response
     */
    @RequestMapping("/genCaptcha")
    public void genCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Pragma","no-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires",0);
        //生成验证码
        String verifyCode = VerifyCodeUtil.generateTextCode();
        //将验证码存入到session中
        request.getSession().setAttribute("VALIDATE_CODE",verifyCode);
        logger.info("此次生成的验证码为%c",verifyCode);
        response.setContentType("image/jpeg");
        /**
         * 将验证码转为图片
         */
        BufferedImage bufferedImage = VerifyCodeUtil.generateImageCode(verifyCode,100,100);
        //然后再将图片写到浏览器
        ImageIO.write(bufferedImage,"JPEG",response.getOutputStream());
    }

    /**
     * 登录接口
     * @param userDto 参数
     * @param request request
     * @return ResultObject
     */
    @RequestMapping(value = "/main",method = RequestMethod.POST)
    public ResultObject login(@RequestBody UserDto userDto,HttpServletRequest request){
        logger.info("/api/login/main  登录接口->userName:{} passWord:{} remember:{} code:{}",userDto.getUsername(),userDto.getPassword(),userDto.getRememberMe(),userDto.getCode());
        BusinessAssert.notBlank(userDto.getUsername(), ExceptionUtil.UserExceptionEnum.USER_NAME_NOT_NULL);
        BusinessAssert.notBlank(userDto.getPassword(), ExceptionUtil.UserExceptionEnum.USER_PASSWORD_NOT_NULL);
        BusinessAssert.notBlank(userDto.getRememberMe(), ExceptionUtil.UserExceptionEnum.USER_REMEMBER_NOT_NULL);
//        BusinessAssert.notBlank(userDto.getCode(), ExceptionUtil.UserExceptionEnum.USER_CODE_NOT_NULL);
        //获取到Session中的验证码
//        HttpSession session = request.getSession();
//        String code = (String) session.getAttribute("VALIDATE_CODE");
//        BusinessAssert.isTrue(userDto.getCode().toLowerCase().equalsIgnoreCase(code.toLowerCase()),ExceptionUtil.UserExceptionEnum.USER_CODE_NOT_EQ);
        //获取到当前用户
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userDto.getUsername(),userDto.getPassword(),userDto.getRememberMe());
        ResultObject resultObject = new ResultObject();
        try{
            subject.login(token);
            //判断用户是否认证
            if(subject.isAuthenticated()){
                //然后sessionID发送给前端保存
                UserVo userVo = new UserVo();
                BeanUtils.copyProperties(subject.getPrincipal(),userVo);
                userVo.setToken(subject.getSession().getId().toString());
                resultObject.setData(userVo);
                resultObject.setCode(ExceptionUtil.SystemExceptionEnum.SUCCESS.getCode());
                resultObject.setMsg(ExceptionUtil.UserExceptionEnum.USER_LOGIN_SUCCESS.getMessage());
            }
        }catch (IncorrectCredentialsException e){//密码错误异常
            resultObject.setCode(ExceptionUtil.UserExceptionEnum.USER_LOGIN_PASSWORD_ERROR.getCode());
            resultObject.setMsg(ExceptionUtil.UserExceptionEnum.USER_LOGIN_PASSWORD_ERROR.getMessage());
        }catch (ExcessiveAttemptsException e){//登录失败次数过多异常
            resultObject.setCode(ExceptionUtil.UserExceptionEnum.USER_LOGIN_EXCESSIVE.getCode());
            resultObject.setMsg(ExceptionUtil.UserExceptionEnum.USER_LOGIN_EXCESSIVE.getMessage());
        }catch (LockedAccountException e){//用户被锁定异常
            resultObject.setCode(ExceptionUtil.UserExceptionEnum.USER_LOGIN_LOCKED.getCode());
            resultObject.setMsg(ExceptionUtil.UserExceptionEnum.USER_LOGIN_LOCKED.getMessage());
        }catch (DisabledAccountException e){//用户禁用异常
            resultObject.setCode(ExceptionUtil.UserExceptionEnum.USER_LOGIN_DISABLE.getCode());
            resultObject.setMsg(ExceptionUtil.UserExceptionEnum.USER_LOGIN_DISABLE.getMessage());
        }catch (UnknownAccountException e){//用户不存在异常
            resultObject.setCode(ExceptionUtil.UserExceptionEnum.USER_LOGIN_UNKNOWN.getCode());
            resultObject.setMsg(ExceptionUtil.UserExceptionEnum.USER_LOGIN_UNKNOWN.getMessage());
        }catch (UnauthenticatedException e){//用户没有被授权异常
            resultObject.setCode(ExceptionUtil.UserExceptionEnum.USER_LOGIN_UNAUTHENTICATED.getCode());
            resultObject.setMsg(ExceptionUtil.UserExceptionEnum.USER_LOGIN_UNAUTHENTICATED.getMessage());
        }catch (AuthenticationException e){
            //如果AuthenticationException异常抛出的时候我们自定义的异常，则直接转换为BusinessException异常
            if(e.getCause() instanceof  BusinessException){
                BusinessException businessException = (BusinessException)e.getCause();
                resultObject.setCode(businessException.getCode());
                resultObject.setMsg(businessException.getMessage());
            }
        }catch (BusinessException e){//用户没有被授权异常
            resultObject.setCode(e.getCode());
            resultObject.setMsg(e.getMessage());
        }
        catch (Exception e){//其他异常
            resultObject.setCode(ExceptionUtil.UserExceptionEnum.USER_LOGIN_FAIL.getCode());
            resultObject.setMsg(ExceptionUtil.UserExceptionEnum.USER_LOGIN_FAIL.getMessage());
        }
        return resultObject;
    }

    /**
     * 退出功能
     */
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public ResultObject logout(){
        logger.info("/api/login/logout 退出系统");
        SecurityUtils.getSubject().logout();;
        return new ResultObject(ExceptionUtil.UserExceptionEnum.USER_LOGOUT_SUCCESS.getMessage());
    }

}
