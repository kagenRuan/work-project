package com.ruan.yuanyuan.controller;

import com.ruan.yuanyuan.dto.RoleDto;
import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.entity.Role;
import com.ruan.yuanyuan.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Set;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-23
 * Time: 17:01
 * version:
 * Description:
 */
@Controller
@RequestMapping("/mall/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @RequestMapping("/index")
    public String index() {
        return "role/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject add(RoleDto roleDto) {
        roleService.add(roleDto);
        return new ResultObject();
    }

    /**
     * 根据id查询角色
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/findRoleById", method = RequestMethod.GET)
    @ResponseBody
    public ResultObject findRoleById(@RequestParam("id") String id) {
        Set<Role> role = roleService.findRoleById(id);
        return new ResultObject(role);
    }

    /**
     * 查询所有的角色
     *
     * @return
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public ResultObject findAll() {
        List<Role> roleList = roleService.findAll();
        return new ResultObject(roleList);
    }

    /**
     * 根据ID删除角色
     *
     * @param id
     */
    @RequestMapping(value = "/deleteRoleById", method = RequestMethod.GET)
    @ResponseBody
    public ResultObject deleteRoleById(@RequestParam("id") String id) {
        roleService.deleteRoleById(id);
        return new ResultObject();
    }


}
