package com.mymongo.entity;

import com.example.demo1.mongo.entity.Entity;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "mytest")
public class User extends Entity {
    private static final long serialVersionUID = 2671960525046670185L;

    private String name;

    private int age;

    private String cls;

    //输出字段
    @Field("totalPop")
    private int totalPop;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", cls='" + cls + '\'' +
                '}';
    }

    public int getTotalPop() {
        return totalPop;
    }

    public void setTotalPop(int totalPop) {
        this.totalPop = totalPop;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }
}
