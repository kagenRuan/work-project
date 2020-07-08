package com.ruan.yuanyuan.shiro;

import com.ruan.yuanyuan.entity.User;
import com.ruan.yuanyuan.exception.BusinessAssert;
import com.ruan.yuanyuan.exception.ExceptionUtil;
import com.ruan.yuanyuan.service.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: MyShiroRealm
 * @author: ruanyuanyuan
 * @date: 2020/7/7 20:10
 * @version: 1.0
 * @description:
 **/
public class MyShiroRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    @Autowired
    private IUserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        logger.info("《《《《《《《《执行授权》》》》》》");
        User principal = (User) arg0.getPrimaryPrincipal();
        Set<String> pers = new HashSet<String>();
        pers.add("login:test");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(pers);
        return info;

    }
    //认证 从loginController 中的login方法调过来
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("《《《《《《《《执行认证逻辑》》》》》》");
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        String username = token.getUsername();
        User user = userService.findUserByName(username);
        BusinessAssert.notNull(user, ExceptionUtil.UserExceptionEnum.USER_NOT_EXISTENT);

        Object principal = user;//user用户
        Object credentials = user.getPassword();//数据库密码

        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getId());//盐值
        String realmName = this.getName();
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), realmName);
        return info;
    }
}
