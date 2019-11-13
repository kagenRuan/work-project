package com.ruan.yuanyuan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruan.yuanyuan.dao.GrayReleaseConfigMapper;
import com.ruan.yuanyuan.entity.GrayReleaseConfig;
import com.ruan.yuanyuan.service.IGrayReleaseConfigService;
import org.springframework.stereotype.Service;

/**
 * User: ruanyuanyuan
 * Date: 2019-09-03
 * Time: 15:56
 * version:1.0
 * Description:灰度发布接口
 */
@Service
public class GrayReleaseConfigServiceImpl extends ServiceImpl<GrayReleaseConfigMapper, GrayReleaseConfig> implements IGrayReleaseConfigService {
}
