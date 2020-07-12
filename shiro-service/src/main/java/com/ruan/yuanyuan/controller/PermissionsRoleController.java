package com.ruan.yuanyuan.controller;

import com.ruan.yuanyuan.entity.PermissionsRoleRef;
import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.service.IPermissionsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: PermissionsRoleController
 * @author: ruanyuanyuan
 * @date: 2020/7/12 15:53
 * @version: 1.0
 * @description: 资源信息与角色中间表
 **/
@RestController
@RequestMapping("/api/permissions/role")
public class PermissionsRoleController extends BaseController {

    @Autowired
    IPermissionsRoleService permissionsRoleService;

    /**
     * 根据角色ID查询资源信息ID
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ResultObject list(@RequestParam("roleId")String roleId){
       List<PermissionsRoleRef> permissionsRoleRefList = permissionsRoleService.findPermissionByRoleId(roleId);
       List<String> permissionList = permissionsRoleRefList.stream().map(obj ->obj.getPermissionsId()).collect(Collectors.toList());
       return new ResultObject(permissionList);
    }

}
