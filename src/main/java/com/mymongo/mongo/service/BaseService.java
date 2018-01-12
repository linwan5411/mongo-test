package com.mymongo.mongo.service;

import com.mymongo.entity.User;
import com.mymongo.mongo.dao.UserDao;
import com.mymongo.mongo.entity.Entity;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 使用业务层直接调用
 * @param <T>
 */
@Service
public class BaseService<T extends Entity> {

    @Resource
    private UserDao userDao;

    /***使用template,而且这个还是一个线程安全的类**/
    @Resource
    private MongoTemplate mongoTemplate;


    public List<User> getSple(){
        //return userDao.findAllByNameXX("张三放");
        return userDao.findAllByName("张三放");
    }


    public void save(User u) {
        mongoTemplate.save(u);
    }


    public List<User> findAll(){
        return userDao.findAll();
    }

    /**分页查询**/
    public Page<User> selectByPage(PageRequest pageable,Map<String,Object> param){
        return userDao.findByPage(pageable,param);
    }

    /***模糊查询几种方式*/
    public List<User> selectByLike(){
        //Query q = new Query(Criteria.where("name").regex(Pattern.compile("^.*网.*$", Pattern.CASE_INSENSITIVE)));
        Query q = new Query(Criteria.where("cls").regex("xx"));
        return  mongoTemplate.find(q,User.class);
    }

    /**分页查询*/
    public Page<User> pageTest(){
        Query q = new Query();
        Criteria criteria = new Criteria();
        criteria.and("name").is("张三");
        Pageable pageable = new PageRequest(0,1);

        q.addCriteria(criteria);

        Sort sort = new Sort(new Sort.Order[]{new Sort.Order(Sort.Direction.ASC,"xxx")});
        //添加分页参数
        q.with(pageable);
        //排序
        q.with(sort);

        long count = mongoTemplate.count(q,User.class);

        List<User> list = mongoTemplate.find(q,User.class,"mytest");

        Page<User> pages = new PageImpl<User>(list, pageable, count);

        return pages;
    }

    /***使用原始的basicQuery进行查询***/
    public List<User> findByBasicQuery(){
        BasicQuery query = new BasicQuery("{'cls': {$regex : 'xxx'} }");
        query.limit(10);
        return mongoTemplate.find(query,User.class);
    }

    /**比较查询 > ,< ...**/
    public List<User> findByCondition(){
        // age > 10 and age < 18 and name = '张三'
        //Query query = new Query(Criteria.where("age").gt(10).lt(18));
        Query query = new Query();
        query.addCriteria(Criteria.where("age").gt(10).lt(18));
        query.addCriteria(new Criteria().and("name").is("张三"));

        //BasicQuery query = new BasicQuery("{'age': {$gt:10,$lt:18},;; }");
        return mongoTemplate.find(query,User.class);
    }

    /**in 查询**/
    public List<User> findByIn(){
        // age in(15,18,20)
        Query query = new Query();
        query.addCriteria(Criteria.where("age").in(15,18,20));
        return mongoTemplate.find(query,User.class);
    }

    /**分组查询**/
    public List<HashMap> groupBy(){
        //$match： 就是where查询
        MatchOperation match = Aggregation.match(new Criteria("cls").is("xxxxx"));
        //$group : 就是group by
        GroupOperation group  = group("name").count().as("total");

        // 注group key name，所以要利用project阶段映射回name
        ProjectionOperation project =  Aggregation.project("total").and("_id").as("myname");

        Aggregation aggregation = newAggregation(match,group,project);

        //return 字段 {'total','myname','_id'}
        AggregationResults<HashMap> results = mongoTemplate.aggregate(aggregation,"mytest",HashMap.class);

        /****都是分组统计***/
        /*TypedAggregation<User> aggregation = newAggregation(User.class, group("id").sum("age").as("totalPop"));
        AggregationResults<User> x = mongoTemplate.aggregate(aggregation,User.class);
        List<User> y  = x.getMappedResults();*/

        return results.getMappedResults();
    }

    /**统计查询:sum.avg，min等直接用mapReduce进行处理Map-Reduce Operations**/
    public void sumAge(){

        //创建一个执行每行的map
        String map = "function(){emit(1,this.age)}";

        //创建一个每行计算的reduce
        String reduce = "function(key,values){var sum = 0;for(var i = 0;i<values.length;i++){ sum += values[i];} return sum;}";

        //求和
        MapReduceResults<A> test =  mongoTemplate.mapReduce("mytest",map,reduce,A.class);

        //回的结果集
        for (A valueObject : test) {
            System.out.println(valueObject);
        }
    }

    /***采用命令行进行执行的方式**/
    public void excuteCommand(){
        DBObject dbObject = new BasicDBObject();
        //执行去重复的命令:distinct
        dbObject.put("distinct","mytest");
        //去重复的字段
        dbObject.put("key", "cls");
        CommandResult result = mongoTemplate.executeCommand(dbObject);
        System.out.println(result.toString());

        //采用json的命令进行输出
        String json = "{'$group':{_id:'max',sum_value:{'$sum':'$age'}}}";
        CommandResult jsonResult = mongoTemplate.executeCommand(json);
        System.out.println(jsonResult.toString());


    }

}
