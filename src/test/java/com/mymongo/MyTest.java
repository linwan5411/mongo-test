package com.mymongo;

import com.mymongo.entity.User;
import com.mymongo.mongo.service.BaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MyTest {

    @Resource
    private BaseService baseService;

    @Test
    public void testSave(){
        User u = new User();u.setName("张三11111");u.setId(UUID.randomUUID().toString());u.setCls("xssx");u.setAge(19);
        baseService.save(u);
    }

    @Test
    public void testgetSple(){

        baseService.getSple().forEach(e->{
            System.out.println(e.toString());
        });
    }

    @Test
    public void  testPage(){

        PageRequest pageable = new PageRequest(0,10, Sort.Direction.DESC,"age");
        Map<String,Object> map = new HashMap<>();map.put("cls","xxxxx");

        Page<User> p = baseService.selectByPage(pageable,map);
        System.out.println("getTotalPages = "+p.getTotalPages());
        System.out.println("getTotalElements = "+p.getTotalElements());
        System.out.println("getNumber = "+p.getNumber());
        System.out.println("getNumberOfElements = "+p.getNumberOfElements());
        System.out.println("getSize = "+p.getSize());
        p.getContent().forEach( e ->{
            System.out.println(e.toString()+"xxxxxxxxxxxxxx");
        });

    }

    @Test
    public void  test(){
        List<User> x = baseService.selectByLike();
        System.out.println("获取的大小:"+x.size());
        x.forEach(e ->{
            System.out.println(e.toString());
        });
    }

    @Test
    public void  pageTest(){
        System.out.println(baseService.pageTest());
    }

    @Test
    public void findByBasicQuery(){
        List<User> x = baseService.findByBasicQuery();
        System.out.println("获取的大小:"+x.size());
        x.forEach(e ->{
            System.out.println(e.toString());
        });
    }

    @Test
    public void findByCondition(){
        List<User> x = baseService.findByCondition();
        System.out.println("获取的大小:"+x.size());
        x.forEach(e ->{
            System.out.println(e.toString());
        });
    }

    @Test
    public void findByIn(){
        List<User> x = baseService.findByIn();
        System.out.println("获取的大小:"+x.size());
        x.forEach(e ->{
            System.out.println(e.toString());
        });
    }


    @Test
    public void groupBy(){
        List map = baseService.groupBy();
    }

    @Test
    public void sumAge(){
        baseService.sumAge();

    }

    @Test
    public void excuteCommnd(){
        baseService.excuteCommand();
    }

    @Test
    public void queryFiledResult(){
        baseService.queryFiledResult();
    }
    @Test
    public void update(){
        baseService.updateDBexsit();
    }

    @Test
    public void updateDBexsitMore(){
        baseService.updateDBexsitMore();
    }

    @Test
    public void oldColection(){
        baseService.oldColection();
    }
}
