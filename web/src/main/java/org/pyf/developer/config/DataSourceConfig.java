package org.pyf.developer.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/9/5 16:18.
 */

@Configuration
@Slf4j
public class DataSourceConfig {

    @Primary
    @Bean(name = "oneDataSource")
    @ConfigurationProperties("spring.datasource.druid.one")
    public DataSource dataSourceOne(){
        log.info("spring.datasource.druid.one init");
        return DruidDataSourceBuilder.create().build();
    }


}
