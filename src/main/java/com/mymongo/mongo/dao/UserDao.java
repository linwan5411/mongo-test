package com.mymongo.mongo.dao;

import com.mymongo.entity.User;
import com.mymongo.mongo.base.CommonRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lyj
 */
@Repository
public interface UserDao extends CommonRepository<User,String> {

    /**
     * 直接采用注解的方式进行对参数映射值
     * @param name
     * @return
     */
    @Query(value = "{'name': ?#{[0]} }")
    public List<User> findAllByNameXX(String name);

    /**
     * 采用隐士的方式进行查询
     * @param name
     * @return
     */
    public List<User> findAllByName(String name);

}
