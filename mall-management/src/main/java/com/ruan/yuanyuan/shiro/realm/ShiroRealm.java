package com.ruan.yuanyuan.shiro.realm;

import com.ruan.yuanyuan.entity.Role;
import com.ruan.yuanyuan.entity.User;
import com.ruan.yuanyuan.service.IPermissionsService;
import com.ruan.yuanyuan.service.IRoleService;
import com.ruan.yuanyuan.service.IUserService;
import com.ruan.yuanyuan.shiro.matcher.MyByteSource;
import com.ruan.yuanyuan.vo.PermissionsVo;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-22
 * Time: 15:58
 * version: 1.0.0
 * Description: 该类的作用主要是用于shiro连接数据库同时拿到数据认证以及授权
 */
public class ShiroRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IPermissionsService permissionsService;

    /**
     * 执行授权逻辑
     * TODO 只有当shiro和页面结合页面上有shiro标签或者使用@RequiresPermissions注解时才会调用此方法，否则这个方法时无效的
     *      如果前后端分离可以直接把权限信息存入到redis中，不走这个方法
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("《《《《《《《执行授权逻辑》》》》》》》》");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        User user = (User)principalCollection.getPrimaryPrincipal();
        //1、查询用户角色
        Set<Role> roleSet = roleService.findRoleById(user.getId());
        //添加角色
        SimpleAuthorizationInfo authorizationInfo =  new SimpleAuthorizationInfo();
        for (Role role : roleSet) {
            authorizationInfo.addRole(role.getCode());
        }
        //查询用户权限
        Set<PermissionsVo> permissionsVos = permissionsService.findPermissionsByRoleId(roleSet);
        //添加权限
        for (PermissionsVo permission:permissionsVos) {
            authorizationInfo.addStringPermission(permission.getPermission());
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 执行认证逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("《《《《《《《《执行认证逻辑》》》》》》");
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        String username = token.getUsername();

        /**
         * 判断用户名
         */
        if(StringUtils.isEmpty(username)){
            return null;
        }
        User user = userService.findUserByName(username);
        if(ObjectUtils.isEmpty(user)){
            return null;
        }
        /**
         * 2.判断密码，直接返回其子类
         */
        return new SimpleAuthenticationInfo(user,user.getPassword(),new MyByteSource(user.getUsername()),getName());
    }

}
