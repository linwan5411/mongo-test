package com.mymongo.mongo.listener;

import com.mymongo.mongo.entity.Entity;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;

/**
 * 在执行之前进行监听器
 */
@Component
public class BeforeSaveListener extends AbstractMongoEventListener<Entity> {

    @Override
    public void onBeforeSave(BeforeSaveEvent<Entity> event) {
        System.out.println("在执行保存之前处理:");
        super.onBeforeSave(event);
    }
}
