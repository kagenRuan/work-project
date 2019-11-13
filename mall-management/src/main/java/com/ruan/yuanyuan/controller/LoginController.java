package com.ruan.yuanyuan.controller;

import com.alibaba.fastjson.JSON;
import com.ruan.yuanyuan.cache.UserCacheUtil;
import com.ruan.yuanyuan.dto.UserDto;
import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.entity.User;
import com.ruan.yuanyuan.service.IPermissionsService;
import com.ruan.yuanyuan.vo.PermissionsVo;
import com.ruan.yuanyuan.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-22
 * Time: 16:40
 * version:
 * Description: 登录页面
 */
@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private IPermissionsService permissionsService;
    @Autowired
    private HashOperations hashOperations;


    @RequestMapping("/")
    public String  root(){
        Subject subject = SecurityUtils.getSubject();
        User user=(User) subject.getPrincipal();
        if (user == null){
            return "redirect:/login";
        }else{
            return "redirect:/index";
        }
    }

    /**
     * 登录页面
     * @return
     */
    @RequestMapping(value = "login",method = RequestMethod.GET)
    public String login(){
        return "login";
    }


    /**
     * 登录
     * 1.登录成功后需要把用户的数据保存到缓存，并返回token
     * @return
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public ResultObject doLongin(@RequestBody UserDto userDto,HttpSession session){
        ResultObject resultObject = new ResultObject();
        /**
         * 1.获取subject
         */
        Subject subject = SecurityUtils.getSubject();
        /**
         * 2.封装用户数据
         */
        UsernamePasswordToken token = new UsernamePasswordToken(userDto.getUsername(),userDto.getPassword(),userDto.isRememberMe());
        /**
         * 3.执行登录方法后会把信息交给自定义的ShiroRealm进行用户名和密码的验证
         */
        try{
            subject.login(token);
            User user=(User) subject.getPrincipal();
            session.setAttribute("user",user);
        }catch (UnknownAccountException e){
            resultObject.setCode(1000);
            resultObject.setMsg("用户不存在");
        }catch (IncorrectCredentialsException e){
            resultObject.setCode(1001);
            resultObject.setMsg("密码或者用户名错误");
        }catch (Exception e){
            e.printStackTrace();
            resultObject.setCode(900);
            resultObject.setMsg("系统错误");
        }
        return resultObject;
    }

    /**
     * 登录成功后跳转页面
     */
    @RequestMapping("index")
    public String menuNav(Model model){
          UserVo userVo = UserCacheUtil.getUser();
          List<PermissionsVo> permissionsVoList =userVo.getPermissionsVoList();
          if(ObjectUtils.isEmpty(permissionsVoList)){
              permissionsVoList = permissionsService.findPermissionsByUserId(userVo.getId());
              hashOperations.put(UserCacheUtil.PERMISSION_CACHE_KET,userVo.getId(), JSON.toJSON(permissionsVoList));
          }
        model.addAttribute("permissions",permissionsVoList);
        return "index/index";
    }


    @RequestMapping("content")
    public String content(){
        return "index/content";
    }



}
