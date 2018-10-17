package org.pyf.developer.web.mongo;

import com.alibaba.fastjson.JSON;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pyf.developer.bean.one.model.auth.QSystemLogger;
import org.pyf.developer.bean.one.model.auth.SystemAccessType;
import org.pyf.developer.bean.one.model.auth.SystemLogger;
import org.pyf.developer.bean.one.model.auth.SystemUser;
import org.pyf.developer.bean.one.model.mongo.MongoDemoEntity;
import org.pyf.developer.bean.one.model.mongo.QMongoDemoEntity;
import org.pyf.developer.dao.jpa.one.auth.SystemLoggerJpaRepository;
import org.pyf.developer.dao.mongo.auth.SystemAccessTypeMongoRepository;
import org.pyf.developer.dao.mongo.auth.SystemLoggerMongoRepository;
import org.pyf.developer.dao.mongo.auth.SystemUserMongoRepository;
import org.pyf.developer.dao.mongo.mongo.MongoDemoEntityRepository;
import org.pyf.developer.service.auth.ISystemAccessTypeService;
import org.pyf.developer.service.auth.ISystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/10/17 12:30.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MongoTests {

    @Autowired
    private ISystemUserService systemUserService;

    @Autowired
    private SystemUserMongoRepository systemUserMongoRepository;

    @Autowired
    private ISystemAccessTypeService systemAccessTypeService;

    @Autowired
    private SystemAccessTypeMongoRepository systemAccessTypeMongoRepository;

    @Autowired
    private SystemLoggerJpaRepository systemLoggerJpaRepository;

    @Autowired
    private SystemLoggerMongoRepository systemLoggerMongoRepository;

    @Autowired
    private MongoDemoEntityRepository mongoDemoEntityRepository;

    @Test
    public void test1(){
        SystemUser user = systemUserService.findById("admin","roles");
        user.setRoles(null);
        systemUserMongoRepository.deleteAll();
        systemUserMongoRepository.save(user);
        // 查询全部
        System.out.println(JSON.toJSONString(systemUserMongoRepository.findAll()));
    }


    @Test
    public void test2(){
        List<SystemAccessType> list = systemAccessTypeService.findAll();
        systemAccessTypeMongoRepository.deleteAll();
        for(SystemAccessType systemAccessType : list){
            systemAccessTypeMongoRepository.save(systemAccessType);
        }
        // 查询全部
        System.out.println(JSON.toJSONString(systemAccessTypeMongoRepository.findAll()));
    }


    @Test
    public void test3(){
        List<SystemLogger> list = systemLoggerJpaRepository.findAll();
        systemLoggerMongoRepository.deleteAll();
        int i = 0;
        for(SystemLogger systemLogger : list){
            i++;
            systemLoggerMongoRepository.save(systemLogger);
            if(i==200) break;
        }
        //排序
        Sort sort = new Sort(Sort.Direction.DESC , "id");
        //分页
        Pageable pageable = PageRequest.of(0, 10, sort);
        System.out.println(JSON.toJSONString(systemLoggerMongoRepository.findAll(pageable)));

    }

    @Test
    public void test4(){
        QSystemLogger logger = QSystemLogger.systemLogger;
        BooleanExpression booleanExpression = logger.id.between(1360l,1370l);
        System.out.println(JSON.toJSONString(systemLoggerMongoRepository.findAll(booleanExpression)));
    }

    @Test
    public void test5(){
        MongoDemoEntity entity = new MongoDemoEntity();
        entity.setName("test");
        entity.setAge(10);
        mongoDemoEntityRepository.save(entity);

        QMongoDemoEntity qMongoDemoEntity = QMongoDemoEntity.mongoDemoEntity;
        BooleanExpression booleanExpression = qMongoDemoEntity.name.eq("test");
        System.out.println(JSON.toJSONString(mongoDemoEntityRepository.findAll(booleanExpression)));
    }



}
