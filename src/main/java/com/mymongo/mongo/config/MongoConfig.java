package com.mymongo.mongo.config;

import com.mymongo.mongo.base.CommonRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * 改用通用的接口来注册启动类,CommonRepositoryImpl替换原来的MongoRepository来实现注册扫描
 */
@Configuration
@EnableMongoRepositories(
        basePackages = {"com.mymongo.mongo"},
        repositoryBaseClass  = CommonRepositoryImpl.class
)
public class MongoConfig{

    /***实现手动配置**/
    /*extends MongoAutoConfiguration
    public MongoConfig(MongoProperties properties, ObjectProvider<MongoClientOptions> options, Environment environment) {
        super(properties, options, environment);
    }*/
}
