package com.ruan.yuanyuan.webconfig;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.mengyun.tcctransaction.spring.recover.DefaultRecoverConfig;
import org.mengyun.tcctransaction.spring.repository.SpringJdbcTransactionRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.IOException;

/**
 * @ClassName: OrderConfig
 * @author: ruanyuanyuan
 * @date: 2019/12/25 18:42
 * @version: 1.0
 * @description: 配置文件
 *tcc-transaction.xml Tcc配置文件
 **/
@ImportResource(locations = "classpath:tcc-transaction.xml")
@Configuration
public class OrderConfig {

    @Value("${order.jdbc.driver}")
    private String driver;
    //TCC数据库url
    @Value("${order.jdbc.tcc.url}")
    private String tccUrl;
    //业务数据库
    @Value("${order.jdbc.url}")
    private String jdbcUrl;
    //数据库名称
    @Value("${order.jdbc.username}")
    private String userName;
    //数据库密码
    @Value("${order.jdbc.password}")
    private String passWord;
    //数据库初始化物理连接数
    @Value("${order.jdbc.initialSize}")
    private int initialSize;
    //数据库最小连接数量
    @Value("${order.jdbc.minDle}")
    private int minDle;
    //数据库最大连接数量
    @Value("${order.jdbc.maxActive}")
    private int maxActive;
    //数据库连接等待时间
    @Value("${order.jdbc.maxWit}")
    private long maxWait;

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
        druidDataSource.setDriverClassName(driver);
        druidDataSource.setUrl(jdbcUrl);
        druidDataSource.setUsername(userName);
        druidDataSource.setPassword(passWord);
        druidDataSource.setInitialSize(initialSize);
        druidDataSource.setMinIdle(minDle);
        druidDataSource.setMaxActive(maxActive);
        druidDataSource.setMaxWait(maxWait);
        return druidDataSource;
    }

    /**
     * MyBatis Plus 全局变量配置
     */
    @Bean("dbConfig")
    public GlobalConfig.DbConfig dbConfig(){
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setIdType(IdType.UUID);
        dbConfig.setUpdateStrategy(FieldStrategy.NOT_EMPTY);
        return dbConfig;
    }

    /**
     * MyBatis Plus 全局变量
     * @param dbConfig
     */
    @Bean("globalConfig")
    public GlobalConfig globalConfig(@Qualifier("dbConfig")GlobalConfig.DbConfig dbConfig){
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setDbConfig(dbConfig);
        return globalConfig;
    }

    /**
     * MyBatis Plus 全局变量
     */
    @Bean("configuration")
    public MybatisConfiguration configuration(){
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.setMapUnderscoreToCamelCase(true);
        mybatisConfiguration.setCallSettersOnNulls(true);
        return mybatisConfiguration;
    }

    /**
     * Mybatis plus插件sqlSessionFactory
     * @param configuration
     * @param globalConfig
     * @param druidDataSource
     * @return
     * @throws IOException
     */
    @Bean("sqlSessionFactory")
    public MybatisSqlSessionFactoryBean sqlSessionFactory(@Qualifier("configuration") MybatisConfiguration configuration,
                                                          @Qualifier("globalConfig") GlobalConfig globalConfig,
                                                          @Qualifier("druidDataSource") DruidDataSource druidDataSource
    ) throws IOException {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(druidDataSource);
        factoryBean.setConfiguration(configuration);
        factoryBean.setGlobalConfig(globalConfig);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
        factoryBean.setTypeAliasesPackage("com.ruan.yuanyuan.entity");
        return factoryBean;
    }

    /**===============================================TCC事务配置==================================
     * TCC 事务恢复策略
     * @return DefaultRecoverConfig
     */
    @Bean("defaultRecoverConfig")
    public DefaultRecoverConfig defaultRecoverConfig(){
        DefaultRecoverConfig config = new DefaultRecoverConfig();
        //一个事务最多恢复次数30次
        config.setMaxRetryCount(30);
        //当一个事务在一定的时间内没有更新则认为这个事务发生异常需要恢复，恢复JOB将扫描超过这个时间间隔依旧没有更新的事务日志，并对这些事务日志进行恢复
        config.setRecoverDuration(120);
        //出发恢复JOB的时间间隔设置
        config.setCronExpression("0 */1 * * * ?");
        return config;
    }

    /**
     * 这个是TCC事务日志数据源
     * @return com.alibaba.druid.pool.DruidDataSource
     */
    @Bean(value = "tccDruidDataSource",initMethod = "init",destroyMethod = "clone")
    public DruidDataSource tccDruidDataSource(){
        DruidDataSource tccDruidDataSource = new DruidDataSource();
        tccDruidDataSource.setDriverClassName(driver);
        tccDruidDataSource.setUrl(tccUrl);
        tccDruidDataSource.setUsername(userName);
        tccDruidDataSource.setPassword(passWord);
        tccDruidDataSource.setInitialSize(initialSize);
        tccDruidDataSource.setMinIdle(minDle);
        tccDruidDataSource.setMaxActive(maxActive);
        tccDruidDataSource.setMaxWait(maxWait);
        return tccDruidDataSource;
    }

    /**
     * TCC与Spring集成的数据库事务操作类
     * @return SpringJdbcTransactionRepository
     */
    @Bean("transactionRepository")
    public SpringJdbcTransactionRepository transactionRepository(){
        SpringJdbcTransactionRepository springJdbcTransactionRepository = new SpringJdbcTransactionRepository();
        springJdbcTransactionRepository.setDataSource(tccDruidDataSource());
        springJdbcTransactionRepository.setDomain("ORDER");
        springJdbcTransactionRepository.setTbSuffix("_ORDER");
        return springJdbcTransactionRepository;
    }
}
