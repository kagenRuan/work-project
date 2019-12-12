package com.ruan.yuanyuan.shiro.matcher;

import com.ruan.yuanyuan.entity.User;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-16
 * Time: 16:42
 * version: 1.0
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    private static final Logger logger = LoggerFactory.getLogger(RetryLimitHashedCredentialsMatcher.class);


    /**
     * 密码验证
     *
     * @param token
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String password = new String(usernamePasswordToken.getPassword());
        User user = (User) info.getPrincipals().asSet().iterator().next();
        return password.equals(user.getPassword());
    }


}
