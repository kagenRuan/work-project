package com.ruan.yuanyuan.cache;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.ruan.yuanyuan.entity.User;
import com.ruan.yuanyuan.util.SpringBeanUtil;
import com.ruan.yuanyuan.vo.PermissionsVo;
import com.ruan.yuanyuan.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-29
 * Time: 13:43
 * version:1.0
 * Description:获取用户缓存工具类
 */
public class UserCacheUtil {


    public static final String PERMISSION_CACHE_KET = "shiro:cache:authorizationCache:";

    public static UserVo getUser() {
        HashOperations hashOperations = SpringBeanUtil.getBean(HashOperations.class);
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        Object permissions = hashOperations.get(PERMISSION_CACHE_KET, user.getUsername());
        if (!ObjectUtils.isEmpty(permissions)) {
            List<PermissionsVo> permissionsVoList = JSON.parseArray(permissions.toString(), PermissionsVo.class);
            userVo.setPermissionsVoList(permissionsVoList);
        }
        return userVo;
    }


}
