package com.ruan.yuanyuan.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 订单数据源配置
 *  主要用于在当前项目中出现了多数据源
 *  @TODO  basePackages 表示 当前数据源只会映射到com.ruan.yuanyuan.mapper.order 目录中的Mapper
 */
@Configuration
@MapperScan(basePackages = "com.ruan.yuanyuan.mapper.order",sqlSessionFactoryRef = "orderSqlSessionFactory")
public class OrderDataSourcesConfig {

    @Value("${order.datasource.url}")
    private String url;
    @Value("${order.datasource.username}")
    private String username;
    @Value("${order.datasource.password}")
    private String password;
    @Value("${order.datasource.driverClassName}")
    private String driverClassName;
    @Value("${order.datasource.type}")
    private String type;
    @Value("${order.datasource.initialSize}")
    private int initialSize;
    @Value("${order.datasource.minIdle}")
    private int minIdle;
    @Value("${order.datasource.maxActive}")
    private int maxActive;
    @Value("${order.datasource.maxWait}")
    private int maxWait;
    @Value("${order.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${order.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;
    @Value("${order.datasource.validationQuery}")
    private String validationQuery;
    @Value("${order.datasource.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${order.datasource.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${order.datasource.testOnReturn}")
    private boolean testOnReturn;
    @Value("${order.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;
    @Value("${order.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;
//    @Value("${order.datasource.filters}")
//    private String filters;
    @Value("${order.datasource.connectionProperties}")
    private String connectionProperties;

    @Bean("orderDataSource")
    @Primary  //主要是用于优先加载实例化
    public DataSource orderDataSource() throws SQLException {
        DruidDataSource orderDruidDataSource = new DruidDataSource();
        orderDruidDataSource.setUrl(url);
        orderDruidDataSource.setDriverClassName(driverClassName);
        orderDruidDataSource.setUsername(username);
        orderDruidDataSource.setPassword(password);
        orderDruidDataSource.setDbType(type);
        orderDruidDataSource.setInitialSize(initialSize);
        orderDruidDataSource.setMinIdle(minIdle);
        orderDruidDataSource.setMaxActive(maxActive);
        orderDruidDataSource.setMaxWait(maxWait);
        orderDruidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        orderDruidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        orderDruidDataSource.setValidationQuery(validationQuery);
        orderDruidDataSource.setTestWhileIdle(testWhileIdle);
        orderDruidDataSource.setTestOnBorrow(testOnBorrow);
        orderDruidDataSource.setTestOnReturn(testOnReturn);
        orderDruidDataSource.setPoolPreparedStatements(poolPreparedStatements);
        orderDruidDataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
//        orderDruidDataSource.setFilters(filters);
        orderDruidDataSource.setConnectionProperties(connectionProperties);
        return orderDruidDataSource;
    }

    //设置数据源管理器
    @Bean("orderPlatformTransactionManager")
    @Primary
    public PlatformTransactionManager orderPlatformTransactionManager(@Qualifier("orderDataSource") DataSource orderDataSource) throws SQLException {
        return new DataSourceTransactionManager(orderDataSource);
    }

//    /**
//     * MyBatis Plus 全局变量配置
//     */
//    @Bean("dbConfig")
//    public GlobalConfig.DbConfig dbConfig(){
//        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
//        dbConfig.setIdType(IdType.INPUT);
//        dbConfig.setUpdateStrategy(FieldStrategy.NOT_EMPTY);
//        return dbConfig;
//    }
//
//    /**
//     * MyBatis Plus 全局变量
//     * @param dbConfig
//     */
//    @Bean("globalConfig")
//    public GlobalConfig globalConfig(@Qualifier("dbConfig")GlobalConfig.DbConfig dbConfig){
//        GlobalConfig globalConfig = new GlobalConfig();
//        globalConfig.setDbConfig(dbConfig);
//        return globalConfig;
//    }

    /**
     * MyBatis Plus 全局变量
     */
    @Bean("orderMybatisConfiguration")
    public MybatisConfiguration orderMybatisConfiguration(){
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.setMapUnderscoreToCamelCase(true);
        mybatisConfiguration.setCallSettersOnNulls(true);
        return mybatisConfiguration;
    }


    //将数据源交给SqlSessionFactory管理
    @Bean("orderSqlSessionFactory")
    @Primary
    public SqlSessionFactory orderSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(orderDataSource());
        sqlSessionFactoryBean.setConfiguration(orderMybatisConfiguration());
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/order/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.ruan.yuanyuan.entity");
        return sqlSessionFactoryBean.getObject();
    }

    @Bean("orderSqlSession")
    public SqlSessionTemplate orderSqlSession() throws Exception{
        SqlSessionTemplate sqlSessionTemplate=new SqlSessionTemplate(orderSqlSessionFactory());
        return sqlSessionTemplate;
    }

}
