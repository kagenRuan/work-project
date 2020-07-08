package com.ruan.yuanyuan.controller;

import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.entity.User;
import com.ruan.yuanyuan.enums.MenuEnum;
import com.ruan.yuanyuan.enums.UserTypeEnum;
import com.ruan.yuanyuan.service.IPermissionsService;
import com.ruan.yuanyuan.vo.PermissionsVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    @RequestMapping("/list")
    @RequiresPermissions("permission:list")
    public ResultObject list(){
        User user = getLoginUser();
        List<PermissionsVo>  permissionsVos = null;
        //管理员用户查询所有的资源信息
        if(UserTypeEnum.ADMIN.getCode().equals(user.getType())){
            permissionsVos = permissionsService.findPermissionsByUserId(null,null);
        }else{
            //普通用户查询自己的资源
            permissionsVos = permissionsService.findPermissionsByUserId(user.getId(),null);
        }
        //将所有的资源去掉parentID为0的资源
        permissionsVos = permissionsVos.stream().filter(obj -> !obj.getParentId().equals("0")).collect(Collectors.toList());
        return new ResultObject(permissionsVos);
    }



}
