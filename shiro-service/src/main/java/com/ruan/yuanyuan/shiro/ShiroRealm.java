package com.ruan.yuanyuan.shiro;

import com.ruan.yuanyuan.entity.User;
import com.ruan.yuanyuan.exception.BusinessAssert;
import com.ruan.yuanyuan.exception.ExceptionUtil;
import com.ruan.yuanyuan.service.IPermissionsService;
import com.ruan.yuanyuan.service.IRoleService;
import com.ruan.yuanyuan.service.IUserService;
import com.ruan.yuanyuan.vo.PermissionsVo;
import com.ruan.yuanyuan.vo.RoleVo;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-22
 * Time: 15:58
 * version: 1.0.0
 * Description: 该类的作用主要是用于shiro认证以及授权
 */
@Component
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
     * 如果前后端分离可以直接把权限信息存入到redis中，不走这个方法
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("《《《《《《《执行授权逻辑》》》》》》》》");
        //获取登录用户
        User shiroUser = (User) principalCollection.getPrimaryPrincipal();
        //通过登录用户名称到数据库中查询用户
        User user = userService.findUserByName(shiroUser.getUsername());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //根据用户ID查询用户角色
        List<RoleVo> roleSet = roleService.findRoleByUserId(user.getId());
        //获取到所有的role名称
        Set<String> roleNames = roleSet.stream().map(obj -> obj.getName()).collect(Collectors.toSet());
        Set<String> roleIds = roleSet.stream().map(obj -> obj.getId()).collect(Collectors.toSet());
        //获取到资源
        Set<PermissionsVo> permissionsVos = permissionsService.findPermissionsByRoleId(roleIds);
        Set<String> permissionsNameVos = permissionsVos.stream().map(obj ->obj.getTitle()).collect(Collectors.toSet());
        //然后将角色和权限放入到shiro中
        simpleAuthorizationInfo.setRoles(roleNames);
        simpleAuthorizationInfo.setStringPermissions(permissionsNameVos);
        return simpleAuthorizationInfo;
    }

    /**
     * 执行认证逻辑
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("《《《《《《《《执行认证逻辑》》》》》》");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        User user = userService.findUserByName(username);
        BusinessAssert.notNull(user, ExceptionUtil.UserExceptionEnum.USER_NOT_EXISTENT);
        /**
         * 2.判断密码，直接返回其子类
         */
        return new SimpleAuthenticationInfo(user, user.getPassword(),getName());
    }

    /**
     * 清除授权信息
     * @param username
     */
    public void  removeUserAuthorizationInfoCache(String username){
        SimplePrincipalCollection principalCollection = new SimplePrincipalCollection();
        principalCollection.add(username,super.getName());
        super.clearCachedAuthorizationInfo(principalCollection);
    }

}
