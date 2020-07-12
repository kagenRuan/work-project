package com.ruan.yuanyuan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruan.yuanyuan.entity.Permissions;
import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.entity.User;
import com.ruan.yuanyuan.enums.UserTypeEnum;
import com.ruan.yuanyuan.exception.BusinessAssert;
import com.ruan.yuanyuan.exception.ExceptionUtil;
import com.ruan.yuanyuan.service.IPermissionsService;
import com.ruan.yuanyuan.vo.PermissionsVo;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @ClassName: PermissionsController
 * @author: ruanyuanyuan
 * @date: 2020/7/6 10:16
 * @version: 1.0
 * @description: 资源控制器
 **/
@RequestMapping("/api/permissions")
@RestController
public class PermissionsController extends BaseController{

    @Autowired
    private IPermissionsService permissionsService;

    /**
     * 查询资源列表
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @RequiresPermissions("perm:list")
    public ResultObject list(){
        User user = getLoginUser();
        List<PermissionsVo>  permissionsVos = null;
        //管理员用户查询所有的资源信息
        if(UserTypeEnum.ADMIN.getCode().equals(user.getType())){
            permissionsVos = permissionsService.findAll(null);
        }else{
            //普通用户查询自己的资源
            permissionsVos = permissionsService.findPermissionsByUserId(user.getId(),null);
        }
        return new ResultObject(permissionsVos);
    }

    /**
     * 修改资源信息
     * @param permissionsVo 参数
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @RequiresPermissions("perm:update")
    public ResultObject update(@RequestBody PermissionsVo permissionsVo){
        BusinessAssert.notBlank(permissionsVo.getId(), ExceptionUtil.PermissionExceptionEnum.PERMISSION_ID_NOT_NULL);
        BusinessAssert.notBlank(permissionsVo.getTitle(), ExceptionUtil.PermissionExceptionEnum.PERMISSION_NAME_NOT_NULL);
        permissionsService.updateById(permissionsVo);
        return new ResultObject();
    }

    /**
     * 添加资源信息
     * @param permissionsVo 参数
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @RequiresPermissions("perm:add")
    public ResultObject add(@RequestBody PermissionsVo permissionsVo){
        BusinessAssert.notBlank(permissionsVo.getTitle(), ExceptionUtil.PermissionExceptionEnum.PERMISSION_NAME_NOT_NULL);
        QueryWrapper<Permissions> queryWrapper = new QueryWrapper();
        if(!StringUtils.isBlank(permissionsVo.getTitle())){
            queryWrapper.eq("name",permissionsVo.getTitle());
        }
        if(!StringUtils.isBlank(permissionsVo.getPermission())){
            queryWrapper.eq("code",permissionsVo.getPermission());
        }
        //验证添加的名称和权限资源是否已存在
        List<Permissions> permissions = permissionsService.list(queryWrapper);
        BusinessAssert.isTrue( ObjectUtils.isEmpty(permissions),ExceptionUtil.PermissionExceptionEnum.PERMISSION_PARENT_NAME_EXISTENT);
        permissionsService.add(permissionsVo);
        return new ResultObject();
    }

    /**
     * 删除资源信息,必须有权限以及要有战士角色才能使用此方法
     * @param id 资源ID
     * @return
     */
    @RequiresRoles("战士")
    @RequiresPermissions("perm:delete")
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public ResultObject delete(@RequestParam("id") String id){
        permissionsService.deleteById(id);
        return new ResultObject();
    }

}
