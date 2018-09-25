package org.pyf.developer.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/9/5 16:18.
 */

@Configuration
@Slf4j
public class DataSourceConfig {

    @Value("${spring.datasource.jndi-name}")
    String jndiName;


    /**
     * druid参考资料：https://blog.csdn.net/u012562943/article/details/54407307
     * @return
     */
    @Primary
    @Bean(name = "oneDataSource")
    @ConfigurationProperties("spring.datasource.druid.one")
    public DataSource dataSourceOne(){
        log.info("spring.datasource.druid.one init");
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 其实druid的性能更好一些，druid支持jndi，具体参考：https://github.com/alibaba/druid/wiki/配置_JNDI_Tomcat，但是过滤器不能自定义了
     * 在项目下的webapp/META-INF的context.xml中配置数据源
     * 在tomcat中使用jndi的方式获取数据源，需要在Resource中增加如下任意一种配置：
     * 1.singleton="false"，此时默认pool为org.apache.tomcat.dbcp.dbcp2.BasicDataSourceFactory的dbcppool
     * 2.factory="org.apache.tomcat.jdbc.pool.DataSourceFactory" ，也就是使用tomcat的jdbcpool
     * @return
     */
    //@Primary
    @Bean(name = "dataSource")
    public DataSource JNDIDataSource(){
        log.info("JNDIDataSource");
        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        DataSource dataSource = dataSourceLookup.getDataSource(jndiName);
        return dataSource;

    }


}
