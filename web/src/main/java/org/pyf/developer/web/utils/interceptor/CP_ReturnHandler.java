package org.pyf.developer.web.utils.interceptor;

import org.pyf.developer.web.utils.view.ViewProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/9/27 11:23.
 */


public class CP_ReturnHandler implements HandlerMethodReturnValueHandler {

    @Autowired(required = false)
    public ViewProperties viewProperties;


    private String getView(String viewName) {
        if (viewProperties != null) {

            viewName = viewProperties.getName().get(viewName);
        }

        return viewName;
    }

    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        if (methodParameter.getParameterType() == String.class) {
            return true;
        }
        return false;
    }

    @Override
    public void handleReturnValue(Object o, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception {

        Assert.isInstanceOf(String.class, o);
        String returnValue = (String) o;
        returnValue = getView(returnValue);

        modelAndViewContainer.setViewName(returnValue);


        //如果希望立刻结束梳理，可以通过调用 ModelAndViewContainer.setRequestHandled(true) 来标识响应已经被直接处理(不再调用视图解析器)
        //比如：
        //modelAndViewContainer.setRequestHandled(true);
        //HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        //response.setContentType("text/plain;charset=UTF-8");
        //response.getWriter().append(JSON.toJSONString(o)).flush();


    }
}
