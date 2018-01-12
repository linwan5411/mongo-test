package com.mymongo.mongo.base;

import com.mymongo.mongo.entity.Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 定义一个实现类
 */
public class CommonRepositoryImpl<T extends Entity,ID extends Serializable> extends SimpleMongoRepository<T,ID> implements CommonRepository<T,ID>{


    protected final MongoOperations mongoTemplate;

    protected final MongoEntityInformation<T, ID> entityInformation;

    /**
     * Creates a new {@link SimpleMongoRepository} for the given {@link MongoEntityInformation} and {@link MongoTemplate}.
     *
     * @param metadata        must not be {@literal null}.
     * @param mongoOperations must not be {@literal null}.
     */
    public CommonRepositoryImpl(MongoEntityInformation<T, ID> metadata, MongoOperations mongoOperations) {
        super(metadata, mongoOperations);
        this.mongoTemplate=mongoOperations;
        this.entityInformation = metadata;
    }

    protected Class<T> getEntityClass(){
        return entityInformation.getJavaType();
    }


    @Override
    public Page<T> findByPage(PageRequest pageable,Map<String,Object> param) {
        Query query = new Query();
        if(param != null && param.size() > 0){
            param.forEach((k,v)->{
                query.addCriteria(new Criteria().and(k).is(v));
            });
        }
        long count = mongoTemplate.count(query,getEntityClass());

        List<T> list = new ArrayList<>();
        if(count > 0){
            query.with(pageable);
            list = mongoTemplate.find(query,getEntityClass());
        }
        return new PageImpl<>(list, pageable, count);
    }
}
