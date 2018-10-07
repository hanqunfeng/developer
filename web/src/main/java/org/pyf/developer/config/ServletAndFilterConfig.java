package org.pyf.developer.config;

import lombok.extern.slf4j.Slf4j;
import org.pyf.developer.web.filter.CP_ImageFilter;
import org.pyf.developer.web.servlet.CP_BufferImageServlet;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/10/7 13:09.
 */

@Slf4j
@Configuration
public class ServletAndFilterConfig {

    @Bean
    public ServletRegistrationBean BufferImageServlet(){
        return new ServletRegistrationBean(new CP_BufferImageServlet(),"/checkcode/bimage");
    }


    @Bean
    public FilterRegistrationBean ImageFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new CP_ImageFilter());
        filterRegistrationBean.addUrlPatterns("/forgotPasswordEmail.do");
        return filterRegistrationBean;
    }
}
