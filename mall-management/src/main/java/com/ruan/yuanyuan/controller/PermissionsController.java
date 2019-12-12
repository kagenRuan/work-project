package com.ruan.yuanyuan.controller;

import com.alibaba.fastjson.JSON;
import com.ruan.yuanyuan.cache.UserCacheUtil;
import com.ruan.yuanyuan.dto.PermissionsDto;
import com.ruan.yuanyuan.entity.Permissions;
import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.service.IPermissionsService;
import com.ruan.yuanyuan.shiro.realm.ShiroRealm;
import com.ruan.yuanyuan.vo.PermissionsVo;
import com.ruan.yuanyuan.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-23
 * Time: 17:01
 * version:
 * Description: 资源
 */
@Controller
@RequestMapping("/mall/permissions")
public class PermissionsController {

    @Autowired
    private IPermissionsService permissionsService;
    @Autowired
    private HashOperations hashOperations;

    @RequestMapping("/index")
    public String index() {
        return "permissions/index";
    }

    /**
     * 添加资源
     *
     * @param permissionsDto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject add(PermissionsDto permissionsDto) {
        permissionsService.add(permissionsDto);
        UserVo userVo = UserCacheUtil.getUser();
        List<PermissionsVo> permissionsVoList = permissionsService.findPermissionsByUserId(userVo.getId());
        hashOperations.put(UserCacheUtil.PERMISSION_CACHE_KET, userVo.getId(), JSON.toJSON(permissionsVoList));
        return new ResultObject();
    }


    /**
     * 根据ID查询资源
     *
     * @param id
     * @return
     */
    @RequestMapping("/findPermissionsById")
    @ResponseBody
    public ResultObject findPermissionsById(String id) {
        Permissions permissions = permissionsService.findPermissionsById(id);
        return new ResultObject(permissions);
    }

    /**
     * 查询所有的资源
     *
     * @return
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public ResultObject findAll() {
        List<Permissions> permissionsList = permissionsService.findAll();
        return new ResultObject(permissionsList);
    }

    /**
     * 根据ID删除资源
     *
     * @param id
     */
    @RequestMapping("/deletePermissionsById")
    @ResponseBody
    public ResultObject deletePermissionsById(String id) {
        permissionsService.deletePermissionsById(id);
        return new ResultObject();
    }

    /**
     * 根据父级id查询资源
     *
     * @return
     */
    @RequestMapping("/findAllPermissionsByParentId")
    @ResponseBody
    public ResultObject findAllPermissionsByParentId() {
        List<Permissions> permissionsList = permissionsService.findAllPermissionsByParentId("0");
        return new ResultObject(permissionsList);
    }


    /**
     * 根据用户id查询角色，并查询角色对应的资源
     */
    @RequestMapping("/findPermissionsByUserId")
    @ResponseBody
    public ResultObject findPermissionsByUserId() {
        List<PermissionsVo> permissionsVoList = permissionsService.findPermissionsByUserId("16");
        return new ResultObject(permissionsVoList);
    }
}
