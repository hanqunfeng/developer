package org.pyf.developer.config;


import org.pyf.developer.web.filter.CP_ImageFilter;
import org.pyf.developer.web.utils.security.CP_UserDetailsService;
import org.pyf.developer.web.utils.security.DynamicRoleVoter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/8/15 15:28.
 */


@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory
            .getLogger(SpringSecurityConfig.class);




    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置不拦截规则
        web.ignoring().antMatchers("/checkcode/**","/resource/**","/**/*.jsp", "/login.do", "/access/sameLogin.do", "/**/*.json*", "/**/*.xml*", "/druid/**","/forgotPassword.do","/forgotPasswordEmail.do","/resetPassword.do");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //解决不允许显示在iframe的问题
        http.headers().frameOptions().disable();

        // 设置拦截规则
        // 自定义accessDecisionManager访问控制器,并开启表达式语言
        http.authorizeRequests()
                .accessDecisionManager(accessDecisionManager())
                .expressionHandler(webSecurityExpressionHandler())
                .antMatchers("/").authenticated()
                .antMatchers("/index.do*").authenticated()
                .antMatchers("/welcome.do*").authenticated()
                .antMatchers("/**/*.do*").hasRole("HOLDER")
                //.antMatchers("/**/*.do*").authenticated()
                //.anyRequest().authenticated()
                .and().exceptionHandling().accessDeniedPage("/access/denied.do");


        // 开启默认登录页面
        // http.formLogin();

        // 自定义登录页面
        http.csrf().disable().formLogin().loginPage("/login.do")
                .failureUrl("/login.do?login_error=1").defaultSuccessUrl("/index.do", true)
                .loginProcessingUrl("/j_spring_security_check")
                .usernameParameter("j_username")
                .passwordParameter("j_password").permitAll();

        // 自定义注销
        http.logout().logoutUrl("/logout.do").logoutSuccessUrl("/login.do")
                .invalidateHttpSession(true);


        // session管理
        http.sessionManagement()
                .sessionFixation().migrateSession().sessionAuthenticationStrategy(concurrentSessionControlStrategy())
                .maximumSessions(1).expiredUrl("/access/sameLogin.do");

        // RemeberMe
        http.rememberMe().key("webmvc#FD637E6D9C0F1A5A67082AF56CE32485");

        http.addFilterBefore(new CP_ImageFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlStrategy(){
        ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy = new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());
        concurrentSessionControlAuthenticationStrategy.setMaximumSessions(1);
        return concurrentSessionControlAuthenticationStrategy;
    }

    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {

        // 自定义UserDetailsService,并对密码加密
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());


    }

    @Bean
    public CP_UserDetailsService userDetailsService() {
        logger.info("CP_UserDetailsService");
        CP_UserDetailsService userDetailsService = new CP_UserDetailsService();
        return userDetailsService;
    }

    @Bean
    public org.springframework.security.authentication.event.LoggerListener loggerListener() {
        logger.info("org.springframework.security.authentication.event.LoggerListener");
        org.springframework.security.authentication.event.LoggerListener loggerListener = new org.springframework.security.authentication.event.LoggerListener();

        return loggerListener;
    }

    @Bean
    public org.springframework.security.access.event.LoggerListener eventLoggerListener() {
        logger.info("org.springframework.security.access.event.LoggerListener");
        org.springframework.security.access.event.LoggerListener eventLoggerListener = new org.springframework.security.access.event.LoggerListener();

        return eventLoggerListener;
    }

    /*
     *
     * 这里可以增加自定义的投票器
     */
    @SuppressWarnings("rawtypes")
    @Bean(name = "accessDecisionManager")
    public AccessDecisionManager accessDecisionManager() {
        logger.info("AccessDecisionManager");
        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList();
        decisionVoters.add(new RoleVoter());
        decisionVoters.add(new AuthenticatedVoter());
        decisionVoters.add(dynamicRoleVoter());
        decisionVoters.add(webExpressionVoter());// 启用表达式投票器

        AffirmativeBased accessDecisionManager = new AffirmativeBased(decisionVoters);

        return accessDecisionManager;
    }

    /*
     * 表达式控制器
     */
    @Bean(name = "expressionHandler")
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        logger.info("DefaultWebSecurityExpressionHandler");
        DefaultWebSecurityExpressionHandler webSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
        return webSecurityExpressionHandler;
    }

    /*
     * 表达式投票器
     */
    @Bean(name = "expressionVoter")
    public WebExpressionVoter webExpressionVoter() {
        logger.info("WebExpressionVoter");
        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
        webExpressionVoter.setExpressionHandler(webSecurityExpressionHandler());
        return webExpressionVoter;
    }

    @Bean
    public DynamicRoleVoter dynamicRoleVoter(){
        return new DynamicRoleVoter();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

