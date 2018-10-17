package org.pyf.developer.web.mongo;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pyf.developer.bean.one.model.auth.SystemAccessType;
import org.pyf.developer.bean.one.model.auth.SystemLogger;
import org.pyf.developer.bean.one.model.auth.SystemUser;
import org.pyf.developer.dao.jpa.one.auth.SystemLoggerJpaRepository;
import org.pyf.developer.dao.mongo.SystemAccessTypeMongoRepository;
import org.pyf.developer.dao.mongo.SystemLoggerMongoRepository;
import org.pyf.developer.dao.mongo.SystemUserMongoRepository;
import org.pyf.developer.service.auth.ISystemAccessTypeService;
import org.pyf.developer.service.auth.ISystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    }
}
