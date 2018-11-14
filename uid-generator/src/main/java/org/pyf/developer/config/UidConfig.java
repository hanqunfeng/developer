package org.pyf.developer.config;

import com.baidu.fsg.uid.UidGenerator;
import com.baidu.fsg.uid.impl.CachedUidGenerator;
import com.baidu.fsg.uid.worker.DisposableWorkerIdAssigner;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/11/14 17:53.
 */

@Configuration
//@ImportResource(locations = { "classpath:uid-generator/cached-uid-spring.xml" })
@MapperScan("com.baidu.fsg.uid")
public class UidConfig {

    @Bean
    public DisposableWorkerIdAssigner disposableWorkerIdAssigner(){
        return new DisposableWorkerIdAssigner();
    }

    @Bean
    public UidGenerator uidGenerator(){
        CachedUidGenerator uidGenerator = new CachedUidGenerator();
        uidGenerator.setWorkerIdAssigner(disposableWorkerIdAssigner());

        return uidGenerator;

    }
}
