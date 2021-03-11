package com.ruan.yuanyuan.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
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
import java.sql.SQLException;

/**
 * 订单数据源配置
 *  主要用于在当前项目中出现了多数据源
 * @TODO  basePackages 表示 当前数据源只会映射到com.ruan.yuanyuan.mapper.coupon 目录中的Mapper
 */
@Configuration
@MapperScan(basePackages = "com.ruan.yuanyuan.mapper.coupon",sqlSessionFactoryRef = "couponSqlSessionFactory")
public class CouponDataSourcesConfig {


    @Value("${coupon.datasource.url}")
    private String url;
    @Value("${coupon.datasource.username}")
    private String username;
    @Value("${coupon.datasource.password}")
    private String password;
    @Value("${coupon.datasource.driverClassName}")
    private String driverClassName;
    @Value("${coupon.datasource.type}")
    private String type;
    @Value("${coupon.datasource.initialSize}")
    private int initialSize;
    @Value("${coupon.datasource.minIdle}")
    private int minIdle;
    @Value("${coupon.datasource.maxActive}")
    private int maxActive;
    @Value("${coupon.datasource.maxWait}")
    private int maxWait;
    @Value("${coupon.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${coupon.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;
    @Value("${coupon.datasource.validationQuery}")
    private String validationQuery;
    @Value("${coupon.datasource.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${coupon.datasource.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${coupon.datasource.testOnReturn}")
    private boolean testOnReturn;
    @Value("${coupon.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;
    @Value("${coupon.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;
//    @Value("${coupon.datasource.filters}")
//    private String filters;
    @Value("${coupon.datasource.connectionProperties}")
    private String connectionProperties;

    @Bean("couponDataSource")
    public DataSource couponDataSource() throws SQLException {
        DruidDataSource couponDruidDataSource = new DruidDataSource();
        couponDruidDataSource.setUrl(url);
        couponDruidDataSource.setDriverClassName(driverClassName);
        couponDruidDataSource.setUsername(username);
        couponDruidDataSource.setPassword(password);
        couponDruidDataSource.setDbType(type);
        couponDruidDataSource.setInitialSize(initialSize);
        couponDruidDataSource.setMinIdle(minIdle);
        couponDruidDataSource.setMaxActive(maxActive);
        couponDruidDataSource.setMaxWait(maxWait);
        couponDruidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        couponDruidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        couponDruidDataSource.setValidationQuery(validationQuery);
        couponDruidDataSource.setTestWhileIdle(testWhileIdle);
        couponDruidDataSource.setTestOnBorrow(testOnBorrow);
        couponDruidDataSource.setTestOnReturn(testOnReturn);
        couponDruidDataSource.setPoolPreparedStatements(poolPreparedStatements);
        couponDruidDataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
//        couponDruidDataSource.setFilters(filters);
        couponDruidDataSource.setConnectionProperties(connectionProperties);
        return couponDruidDataSource;
    }

    //设置数据源管理器
    @Bean("couponPlatformTransactionManager")
    public PlatformTransactionManager couponPlatformTransactionManager(@Qualifier("couponDataSource") DataSource couponDataSource) throws SQLException {
        return new DataSourceTransactionManager(couponDataSource);
    }

    /**
     * MyBatis Plus 全局变量
     */
    @Bean("couponMybatisConfiguration")
    public MybatisConfiguration couponMybatisConfiguration(){
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.setMapUnderscoreToCamelCase(true);
        mybatisConfiguration.setCallSettersOnNulls(true);
        return mybatisConfiguration;
    }


    //将数据源交给SqlSessionFactory管理
    @Bean("couponSqlSessionFactory")
    public SqlSessionFactory couponSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(couponDataSource());
        sqlSessionFactoryBean.setConfiguration(couponMybatisConfiguration());
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/coupon/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.ruan.yuanyuan.entity");
        return sqlSessionFactoryBean.getObject();
    }

    @Bean("couponSqlSession")
    public SqlSessionTemplate couponSqlSession() throws Exception{
        SqlSessionTemplate sqlSessionTemplate=new SqlSessionTemplate(couponSqlSessionFactory());
        return sqlSessionTemplate;
    }
}
