package com.ruan.yuanyuan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruan.yuanyuan.dto.UserDto;
import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.entity.UserRoleRef;
import com.ruan.yuanyuan.exception.BusinessAssert;
import com.ruan.yuanyuan.exception.ExceptionUtil;
import com.ruan.yuanyuan.service.IUserRoleService;
import com.ruan.yuanyuan.service.IUserService;
import com.ruan.yuanyuan.vo.UserVo;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: UserController
 * @author: ruanyuanyuan
 * @date: 2020/7/8 15:41
 * @version: 1.0
 * @description: 用户控制器
 **/
@RequestMapping("/api/user")
@RestController
public class UserController extends BaseController {


    @Autowired
    private IUserService userService;
    @Autowired
    private IUserRoleService userRoleService;

    /**
     * 用户列表
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @RequiresPermissions("user:list")
    public ResultObject list(@RequestParam("page") int page,@RequestParam("limit") int limit){
        Page<UserVo> roleVoPage = new Page<>(page,limit);
        List<UserVo> userVoList = userService.findAll(roleVoPage);
        ResultObject resultObject = new ResultObject();
        resultObject.setData(userVoList);
        resultObject.setCount(roleVoPage.getTotal());
        return resultObject;
    }

    /**
     * 添加用户
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @RequiresPermissions("user:add")
    public ResultObject add(@RequestBody UserDto userDto){
        BusinessAssert.notBlank(userDto.getUsername(), ExceptionUtil.UserExceptionEnum.POWER_SAVE_FAIL);
        BusinessAssert.notBlank(userDto.getPassword(), ExceptionUtil.UserExceptionEnum.USER_PASSWORD_NOT_NULL);
        BusinessAssert.notBlank(userDto.getStatus(), ExceptionUtil.UserExceptionEnum.USER_STATUS_NOT_NULL);
        BusinessAssert.notBlank(userDto.getType(), ExceptionUtil.UserExceptionEnum.USER_TYPE_NOT_NULL);
        userDto.setCreateBy(getLoginUser().getUsername());
        userService.add(userDto);
        return new ResultObject();
    }

    /**
     * 删除用户
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    @RequiresPermissions("user:delete")
    public ResultObject delete(@RequestParam("userId") String userId){
        BusinessAssert.notBlank(userId, ExceptionUtil.UserExceptionEnum.USER_DELETE_ID_NOT_NULL);
        List<String> userIds = Arrays.stream(userId.split(",")).collect(Collectors.toList());
        userService.delete(userIds);
        return new ResultObject();
    }

    /**
     * 修改用户
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @RequiresPermissions("user:update")
    public ResultObject update(@RequestBody UserDto userDto){
        BusinessAssert.notBlank(userDto.getType(), ExceptionUtil.UserExceptionEnum.USER_UPDATE_ID_NOT_NULL);
        userDto.setUpdateBy(getLoginUser().getUsername());
        userService.update(userDto);
        if(!StringUtils.isBlank(userDto.getRoleId())){
            UserRoleRef userRoleRef = Optional.ofNullable(userRoleService.getOne(new QueryWrapper<UserRoleRef>().eq("user_id",userDto.getId()))).orElse(new UserRoleRef());
            userRoleRef.setUserId(userDto.getId());
            userRoleRef.setRoleId(userDto.getRoleId());
            userRoleRef.initBean();
            userRoleService.saveOrUpdate(userRoleRef);
        }
        return new ResultObject();
    }



}
