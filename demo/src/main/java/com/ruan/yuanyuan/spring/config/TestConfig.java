package com.ruan.yuanyuan.spring.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.ruan.yuanyuan.spring.aop.TestAop;
import com.ruan.yuanyuan.spring.entity.TestSay;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName TestConfig
 * @Author ruanyuanyuan
 * @Date 2020/8/28-15:10
 * @Version 1.0
 * @Description TODO
 **/
@Configuration
@ComponentScan(basePackages = "com.ruan.yuanyuan.spring")
//@EnableAspectJAutoProxy
@EnableTransactionManagement
public class TestConfig {


    @Bean
    public TestSay testSay(){
       return new TestSay();
   }

    /**
     * 配置事务管理器
     * @param druidDataSource
     */
    @Bean
    public PlatformTransactionManager platformTransactionManager(@Qualifier("druidDataSource") DruidDataSource druidDataSource){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(druidDataSource);
        return dataSourceTransactionManager;
    }

    /**
     * 业务数据源
     * @return
     */
    @Bean(value = "druidDataSource",initMethod = "init",destroyMethod = "clone")
    public DruidDataSource druidDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/cloudproject?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("123456");
        druidDataSource.setInitialSize(5);
        druidDataSource.setMinIdle(5);
        druidDataSource.setMaxActive(5);
        druidDataSource.setMaxWait(60000);
        return druidDataSource;
    }

}
