package com.ruan.yuanyuan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruan.yuanyuan.dao.ZuulApiRouteMapper;
import com.ruan.yuanyuan.entity.ZuulApiRoute;
import com.ruan.yuanyuan.service.IZuulApiRouteService;
import org.springframework.stereotype.Service;

/**
 * User: ruanyuanyuan
 * Date: 2019-09-03
 * Time: 15:52
 * version:1.0
 * Description:网关路由api服务
 */
@Service
public class ZuulApiRouteServiceImpl extends ServiceImpl<ZuulApiRouteMapper, ZuulApiRoute> implements IZuulApiRouteService {
}
