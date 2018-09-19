package org.pyf.developer.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.pyf.developer.web.utils.interceptor.CP_InitializingInterceptor;
import org.pyf.developer.web.utils.log.CP_OperateLogInterceptor;
import org.pyf.developer.web.utils.security.CP_IUser;
import org.pyf.developer.web.utils.security.CP_SecurityUser;
import org.pyf.developer.web.utils.viewResolver.CP_ResourceBundleMessageSource;
import org.pyf.developer.web.utils.viewResolver.CP_ResourceBundleViewResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.SimpleServletHandlerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.List;

/**
 * 这里要实现WebMvcConfigurer接口，并实现相应的方法，，由于WebMvcConfigurer的方法都是default的（jdk1.8特性：缺省实现方法），所以并不强制实现所有方法。
 * 另外这样做的好处是不会破坏springboot的自动配置就可以改变自动配置的默认规则。
 * 如果继承WebMvcConfigurationSupport类，可能会导致错误。
 * Created by hanqf on 2018/8/24 17:32.
 */
@Slf4j
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${spring.datasource.druid.web-stat-filter.exclusions}")
    private String exclusions;


    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {


        CP_ResourceBundleViewResolver viewResolver = new CP_ResourceBundleViewResolver();
        //viewResolver.setBasenames("config.views.views");
        viewResolver.setPackagename("config.views");
        viewResolver.setDefaultParentView("modelView");
        viewResolver.setOrder(1);

        registry.viewResolver(viewResolver);

        log.info("ViewResolver");
    }


    /**
     * 描述 : <注册消息资源处理器>. <br>
     *<p>
     <使用方法说明>
     </p>
     * @return
     */
    @Primary
    @Bean("messageSource")
    public MessageSource messaeSource() {
        log.info("MessageSource");

        CP_ResourceBundleMessageSource messageSource = new CP_ResourceBundleMessageSource();
        //messageSource.setBasename("config.messages.messages");
        messageSource.setPackagename("config.messages");
        return messageSource;
    }



    /**
     * 配置json转换为ali的FastJson
     * @param converters
     *
     * 可以对输出格式进行配置
     * FastJson SerializerFeatures
     *
     * WriteNullListAsEmpty  ：List字段如果为null,输出为[],而非null
     * WriteNullStringAsEmpty ： 字符类型字段如果为null,输出为"",而非null
     * DisableCircularReferenceDetect ：消除对同一对象循环引用的问题，默认为false（如果不配置有可能会进入死循环）
     * WriteNullBooleanAsFalse：Boolean字段如果为null,输出为false,而非null
     * WriteMapNullValue：是否输出值为null的字段,默认为false。
     *
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty
        );
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(fastJsonHttpMessageConverter);
        log.info("FastJsonHttpMessageConverter");
    }



    /**
     * 描述 : <注册servlet适配器>. <br>
     *<p>
     <只需要在自定义的servlet上用@Controller("映射路径")标注即可>
     </p>
     * @return
     */
    @Bean
    public HandlerAdapter servletHandlerAdapter(){
        log.info("HandlerAdapter");
        return new SimpleServletHandlerAdapter();
    }

    /**
     * 描述 : <本地化拦截器>. <br>
     *<p>
     <使用方法说明>
     </p>
     * @return
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        return new LocaleChangeInterceptor();
    }

    /**
     * 描述 : <基于cookie的本地化资源处理器>. <br>
     *<p>
     <使用方法说明>
     </p>
     * @return
     */
    @Bean(name="localeResolver")
    public CookieLocaleResolver cookieLocaleResolver(){
        log.info("CookieLocaleResolver");
        return new CookieLocaleResolver();
    }


    @Bean(name = "userName")
    public CP_IUser getSecurityUser(){
        return new CP_SecurityUser();

    }

    /**
     * 描述 : <注册自定义拦截器>. <br>
     *<p>
     <使用方法说明>
     </p>
     * @return
     */
    @Bean
    public CP_InitializingInterceptor initializingInterceptor(){
        log.info("CP_InitializingInterceptor");
        CP_InitializingInterceptor cp_initializingInterceptor =  new CP_InitializingInterceptor();
        cp_initializingInterceptor.setUserName(getSecurityUser());
        return cp_initializingInterceptor;
    }

    @Bean
    public CP_OperateLogInterceptor operateLogInterceptor(){
        CP_OperateLogInterceptor operateLogInterceptor = new CP_OperateLogInterceptor();
        return operateLogInterceptor;
    }


    /**
     * 添加拦截器，拦截器可以执行拦截哪些url或者不拦截哪些url
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(initializingInterceptor()).excludePathPatterns(exclusions).addPathPatterns("/**/*.do*");
        log.info("CP_InitializingInterceptor=="+exclusions);
        registry.addInterceptor(localeChangeInterceptor()).addPathPatterns("/**");
        log.info("localeChangeInterceptor");
        registry.addInterceptor(operateLogInterceptor()).addPathPatterns("/**/*.do*");
    }

    /**
     * 配置路劲匹配规则，以下是默认规则，可以不重写该方法
     * @param configurer
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(false) //设置是否是后缀模式匹配，如“/user”是否匹配/user.*，默认false即不匹配；
                .setUseTrailingSlashMatch(true);  //设置是否自动后缀路径模式匹配，如“/user”是否匹配“/user/”，默认true即匹配；
        log.info("configurePathMatch");
    }

    /**
     * 静态资源访问路径映射
     * 类路径下要加上classpath:前缀
     * @param registry
     * 示例：
     *  <script src="${pageContext.request.contextPath}/static/js/jquery-1.11.0.min.js"></script>
     *  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/font-awesome.min.css" />
     *  <img src="${pageContext.request.contextPath}/static/img/1.jpg">
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resource/**").addResourceLocations("/resource/").addResourceLocations("classpath:/static/");
    }



    /**
     * 类似于OpenSessionInViewFilter
     *
     * spring 中 正常的防止session失效的方法是:OpenSessionInViewFilter
     * 在 spring data jpa 中 用的是:OpenEntityManagerInViewFilter
     * 在application.yml中声明spring.jpa.open-in-view=true 作用相同
     *
     * 但是这里有一个问题，如果对象是从缓存中获取，由于使用了延迟加载，所以在序列化到缓存中时不会保存级联对象，所以也就无法通过openEntityManagerInViewFilter的方式获取到
     * @return
     */
    //@Bean
    //public FilterRegistrationBean openEntityManagerInViewFilter(){
    //    OpenEntityManagerInViewFilter openEntityManagerInViewFilter = new OpenEntityManagerInViewFilter();
    //    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    //    filterRegistrationBean.setFilter(openEntityManagerInViewFilter);
    //    filterRegistrationBean.setName("OpenEntityManagerInViewFilter");
    //    filterRegistrationBean.addUrlPatterns("/*");
    //    return filterRegistrationBean;
    //}

    //@Bean
    //public FilterRegistrationBean characterEncodingFilter(){
    //    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
    //    characterEncodingFilter.setEncoding("UTF-8");
    //    characterEncodingFilter.setForceEncoding(true);
    //    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    //    filterRegistrationBean.setFilter(characterEncodingFilter);
    //    filterRegistrationBean.setName("CharacterEncodingFilter");
    //    filterRegistrationBean.addUrlPatterns("/*");
    //    return filterRegistrationBean;
    //}

    /**
     * 路径直接映射到视图，不需要创建controller和映射方法
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.jsp");

    }

    /**
     * 跨域访问控制
     * @param registry
     *
     *
     * import org.springframework.context.annotation.Bean;
     * import org.springframework.context.annotation.Configuration;
     * import org.springframework.web.cors.CorsConfiguration;
     * import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
     * import org.springframework.web.filter.CorsFilter;
     *
     * @Configuration
     * public class CorsConfig {
     *     private CorsConfiguration buildConfig() {
     *         CorsConfiguration corsConfiguration = new CorsConfiguration();
     *         corsConfiguration.addAllowedOrigin("*"); // 1
     *         corsConfiguration.addAllowedHeader("*"); // 2
     *         corsConfiguration.addAllowedMethod("*"); // 3
     *         return corsConfiguration;
     *     }
     *
     *     @Bean
     *     public CorsFilter corsFilter() {
     *         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
     *         source.registerCorsConfiguration("/**", buildConfig()); // 4
     *         return new CorsFilter(source);
     *     }
     * }
     *
     *
     *
     * 也可以在controller的类或方法上加上@CrossOrigin注解，来设置某个controller的全部方法或某个方法可以呗跨域访问，参数与下面的配置类似
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //配置可以被跨域的路径，可以任意配置，可以具体到直接请求路径，这里表示全部路径
                .allowedMethods("*")   //允许所有的请求方法访问该跨域资源服务器，如：POST、GET、PUT、DELETE等。
                .allowedOrigins("*")//允许所有的请求域名访问我们的跨域资源，可以固定单条或者多条内容，如："http://www.baidu.com"，只有百度可以访问我们的跨域资源。
                .allowedHeaders("*")//允许所有的请求header访问，可以自定义设置任意请求头信息，如："X-YAUTH-TOKEN"
                .allowCredentials(true) //是否允许用户发送、处理 cookie
                .maxAge(3600); //预检请求的有效期，单位为秒。有效期内，不会重复发送预检请求
    }



}
