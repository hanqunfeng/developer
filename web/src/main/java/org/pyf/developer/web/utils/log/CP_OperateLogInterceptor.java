package org.pyf.developer.web.utils.log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.pyf.developer.bean.one.model.auth.SystemLogger;
import org.pyf.developer.service.auth.ISystemLoggerService;
import org.pyf.developer.web.utils.security.CP_IUser;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/9/13 18:45.
 */

@Slf4j
public class CP_OperateLogInterceptor implements HandlerInterceptor {

    //请求开始时间标识
    private static final String LOGGER_SEND_TIME = "_send_time";
    //请求日志实体标识
    private static final String LOGGER_ENTITY = "_logger_entity";

    @Autowired
    MessageSource messageSource;

    @Autowired
    private CP_IUser userName;


    /**
     * 根据传入的类型获取spring管理的对应dao
     * @param clazz 类型
     * @param request 请求对象
     * @param <T>
     * @return
     */
    private <T> T getDAO(Class<T> clazz,HttpServletRequest request)
    {
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        return factory.getBean(clazz);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //创建日志实体
        SystemLogger logger = new SystemLogger();
        //获取请求sessionId
        String sessionId = request.getRequestedSessionId();
        //请求路径
        String url = request.getRequestURI();
        //获取请求参数信息
        String paramData = JSON.toJSONString(request.getParameterMap(),
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue);
        //设置客户端ip
        logger.setLogClientIp(CP_LoggerUtils.getCliectIp(request));
        //设置请求方法
        logger.setLogMethod(request.getMethod());
        //设置请求类型（json|普通请求）
        logger.setLogType(CP_LoggerUtils.getRequestType(request));
        //设置请求参数内容json字符串
        logger.setLogParamData(paramData);
        //设置请求地址
        logger.setLogUri(url);
        //设置sessionId
        logger.setLogSessionId(sessionId);

        //设置请求开始时间
        Date date = new Date();
        logger.setLogTime(date);

        request.setAttribute(LOGGER_SEND_TIME,date.getTime());
        //设置请求实体到request内，方面afterCompletion方法调用
        request.setAttribute(LOGGER_ENTITY,logger);

        if (getCP_OperateLogAnnotation(handler)==null) {
            // 如果注解为null, 说明不需要拦截, 直接放过
            return true;
        }


        log.info(getLog(request,handler)+"  start");




        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        //获取请求错误码
        int status = response.getStatus();
        //当前时间
        long currentTime = System.currentTimeMillis();
        //请求开始时间
        long time = Long.valueOf(request.getAttribute(LOGGER_SEND_TIME).toString());
        //获取本次请求日志实体
        SystemLogger logger = (SystemLogger) request.getAttribute(LOGGER_ENTITY);
        //设置请求时间差
        logger.setLogTimeConsuming(Integer.valueOf((currentTime - time)+""));
        //设置返回时间
        logger.setLogReturmTime(currentTime + "");
        //设置返回错误码
        logger.setLogHttpStatusCode(status+"");
        //设置返回值
        logger.setLogReturnData(JSON.toJSONString(request.getAttribute(CP_LoggerUtils.LOGGER_RETURN),
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue));
        //执行将日志写入数据库
        ISystemLoggerService service = getDAO(ISystemLoggerService.class,request);

        CP_OperateLog operateLog = getCP_OperateLogAnnotation(handler);

        if (operateLog!=null) {
            log.info(getLog(request,handler)+"  end");

            String defaultMessage = operateLog.value();
            String type = operateLog.type().getDesc();

            logger.setLogDesc(defaultMessage+"--"+type);


        }

        logger.setLogUser(userName.getUserName());
        if(userName.getUserName()!=null && !"anonymousUser".equals(userName.getUserName())) {
            service.add(logger);
        }
    }

    /**
     * 验证是否存在@CP_OperateLog注解
     * @param handler
     * @return
     */
    private CP_OperateLog getCP_OperateLogAnnotation(Object handler){
        // 将handler强转为HandlerMethod, 前面已经证实这个handler就是HandlerMethod
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 从方法处理器中获取出要调用的方法
        Method method = handlerMethod.getMethod();
        // 获取出方法上的CP_OperateLog注解
        CP_OperateLog operateLog = method.getAnnotation(CP_OperateLog.class);
        if(operateLog!=null) {
            return operateLog;
        }
        return null;
    }

    /**
     * 构建日志内容
     * @param request
     * @param handler
     * @return
     */
    private String  getLog(HttpServletRequest request,Object handler){
        String username = userName.getUserName();
        // 将handler强转为HandlerMethod, 前面已经证实这个handler就是HandlerMethod
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 从方法处理器中获取出要调用的方法
        Method method = handlerMethod.getMethod();
        // 获取出方法上的CP_OperateLog注解
        CP_OperateLog operateLog = method.getAnnotation(CP_OperateLog.class);
        String defaultMessage = operateLog.value();
        String methodName = handlerMethod.getBean().getClass().getName() + "."
                + method.getName();



        String desc = this.handleDescription(operateLog.key(), StringUtils
                .hasText(defaultMessage) ? defaultMessage : methodName);
        // 装配日志信息
        String logline = this.buildLogLine(username, operateLog.type().getDesc(), desc);


        return logline;
    }

    /**
     * 描述 : <构建日志行>. <br>
     * <p>
     * <使用方法说明>
     * </p>
     *
     * @param username
     *            用户名称
     * @param operateType
     *            操作类型
     * @param description
     *            操作描述
     * @return 日志行： username - operateType - description
     */
    protected String buildLogLine(String username, String operateType,
                                  String description) {
        StringBuilder sb = new StringBuilder();
        sb.append(username).append(" - ").append(operateType).append(" - ")
                .append(description);
        return sb.toString();
    }

    /**
     * 描述 : <获取日志内容描述，可以从消息配置文件中找到对应的信息 >. <br>
     * <p>
     * <使用方法说明>
     * </p>
     *
     * @param key
     *            日志内容key
     * @param defaultMessage
     *            默认的描述信息
     * @return 描述信息
     */
    protected String handleDescription(String key, String defaultMessage) {
        if (messageSource == null) {
            return defaultMessage;
        }
        if (!StringUtils.hasText(key)) {
            return defaultMessage;
        }
        String message = messageSource.getMessage(key, new Object[] {}, defaultMessage, LocaleContextHolder.getLocale());
        if (!StringUtils.hasText(message)) {
            return defaultMessage;
        } else {
            return message;
        }
    }

}
