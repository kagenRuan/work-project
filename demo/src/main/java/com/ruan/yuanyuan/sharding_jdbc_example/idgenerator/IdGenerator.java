package com.ruan.yuanyuan.sharding_jdbc_example.idgenerator;

import org.apache.shardingsphere.core.strategy.keygen.SnowflakeShardingKeyGenerator;
import org.apache.shardingsphere.spi.keygen.ShardingKeyGenerator;
import java.util.Properties;

/**
 * @ClassName Idgenerator
 * @Author ruanyuanyuan
 * @Date 2020/9/23-14:52
 * @Version 1.0
 * @Description TODO ID生成类（主要使用了雪花算法）
 **/
public class IdGenerator implements ShardingKeyGenerator {

    SnowflakeShardingKeyGenerator shardingKeyGenerator =new SnowflakeShardingKeyGenerator();
    //返回一个ID
    @Override
    public Comparable<?> generateKey() {
        System.err.println("使用了自定义的主键生成器");
        return shardingKeyGenerator.generateKey();
    }

    @Override
    public String getType() {
        return "IDGENERATOR";
    }

    @Override
    public Properties getProperties() {
        return null;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
