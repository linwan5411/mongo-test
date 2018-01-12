package com.mymongo.mongo.base;

import com.mymongo.mongo.entity.Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Map;

/**
 * spring 不扫描该mongo的值
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public interface CommonRepository<T extends Entity,ID extends Serializable> extends MongoRepository<T,ID>{

    /**
     * 分页查询单个document
     * @param pageable 分页排序对象
     * @param param 分页条件 and 类型
     * @return
     */
    Page<T> findByPage(PageRequest pageable, Map<String, Object> param);
}
