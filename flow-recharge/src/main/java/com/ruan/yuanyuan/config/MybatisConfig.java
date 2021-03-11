package com.ruan.yuanyuan.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class MybatisConfig {

    /**
     * MyBatis Plus 全局变量
     */
    @Bean("mybatisConfiguration")
    public MybatisConfiguration mybatisConfiguration(){
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.setMapUnderscoreToCamelCase(true);
        mybatisConfiguration.setCallSettersOnNulls(true);
        return mybatisConfiguration;
    }
}
