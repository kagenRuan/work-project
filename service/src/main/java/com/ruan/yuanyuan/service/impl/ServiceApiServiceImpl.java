package com.ruan.yuanyuan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruan.yuanyuan.dao.ServiceApiMapper;
import com.ruan.yuanyuan.entity.ServiceApi;
import com.ruan.yuanyuan.service.IServiceApiService;
import org.springframework.stereotype.Service;

/**
 * User: ruanyuanyuan
 * Date: 2019-09-03
 * Time: 15:50
 * version:1.0
 * Description:服务API接口
 */
@Service
public class ServiceApiServiceImpl extends ServiceImpl<ServiceApiMapper, ServiceApi> implements IServiceApiService {
}
