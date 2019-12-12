package com.ruan.yuanyuan.controller;

import com.ruan.yuanyuan.dto.UserDto;
import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.entity.User;
import com.ruan.yuanyuan.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-22
 * Time: 17:44
 * version:
 * Description:
 */
@Controller
@RequestMapping("/mall/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/list")
    public String list() {
        return "user/list";
    }

    @RequestMapping("/index")
    public String add() {
        return "user/index";
    }

    /**
     * 查询所有的用户信息
     *
     * @return
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public ResultObject findAll() {
        List<User> userList = userService.findAllUser();
        return new ResultObject(userList);
    }

    /**
     * 添加用户
     *
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject add(UserDto userDto) {
        userService.addUser(userDto);
        return new ResultObject();
    }

    /**
     * 根据ID删除用户
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteUserById", method = RequestMethod.GET)
    @ResponseBody
    public ResultObject deleteUserById(@RequestParam("id") String id) {
        userService.deleteUser(id);
        return new ResultObject();
    }


    @RequestMapping("/update")
    public String update() {
        return "user/update";
    }
}
