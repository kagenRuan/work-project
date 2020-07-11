package com.ruan.yuanyuan.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.exception.BusinessAssert;
import com.ruan.yuanyuan.exception.ExceptionUtil;
import com.ruan.yuanyuan.service.IRoleService;
import com.ruan.yuanyuan.vo.RoleVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: RoleController
 * @author: ruanyuanyuan
 * @date: 2020/7/10 14:59
 * @version: 1.0
 * @description: 角色控制器
 **/
@RestController
@RequestMapping("/api/role")
public class RoleController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private IRoleService roleService;

    /**
     * 查询角色列表
     * @param page 页码
     * @param limit 每页数量
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions(("user:list"))
    public ResultObject listPage(@RequestParam("page") int page,@RequestParam("limit") int limit) {
        Page<RoleVo> roleVoPage = new Page<>(page,limit);
        ResultObject resultObject = new ResultObject();
        List<RoleVo> roleVoList = roleService.findAllRolePage(roleVoPage);;
        resultObject.setData(roleVoList);
        resultObject.setCount(roleVoPage.getTotal());
        return resultObject;
    }

    /**
     * 查所有的用户LIst
     * @return ResultObject
     */
    @RequestMapping(value = "/findAllRoleList", method = RequestMethod.GET)
    public ResultObject findAllRoleList() {
        List<RoleVo> roleVoList = roleService.findAllRoleList();;
        return new ResultObject(roleVoList);
    }


    /**
     * 添加角色
     * @param  roleVo 参数
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @RequiresPermissions("role:add")
    public ResultObject add(@RequestBody RoleVo roleVo){
        BusinessAssert.notBlank(roleVo.getName(), ExceptionUtil.RoleExceptionEnum.ROLE_NAME_NOT_NULL);
        BusinessAssert.notBlank(roleVo.getCode(), ExceptionUtil.RoleExceptionEnum.ROLE_CODE_NOT_NULL);
        roleService.add(roleVo);
        return new ResultObject();
    }

    /**
     * 删除角色
     * @param  roleIds 参数
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @RequiresPermissions("role:delete")
    public ResultObject add(@RequestParam("roleIds") String roleIds){
        BusinessAssert.notBlank(roleIds, ExceptionUtil.RoleExceptionEnum.ROLE_DELETE_ID_NOT_NULL);
        List<String> roleList = Arrays.stream(roleIds.split(",")).collect(Collectors.toList());
        roleService.delete(roleList);
        return new ResultObject();
    }

    /**
     * 修改角色
     * @param  roleVo 参数
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @RequiresPermissions("role:update")
    public ResultObject update(@RequestBody RoleVo roleVo){
        BusinessAssert.notBlank(roleVo.getName(), ExceptionUtil.RoleExceptionEnum.ROLE_NAME_NOT_NULL);
        BusinessAssert.notBlank(roleVo.getCode(), ExceptionUtil.RoleExceptionEnum.ROLE_CODE_NOT_NULL);
        BusinessAssert.notBlank(roleVo.getId(), ExceptionUtil.RoleExceptionEnum.ROLE_UPDATE_ID_NOT_NULL);
        roleService.update(roleVo);
        return new ResultObject();
    }
}
