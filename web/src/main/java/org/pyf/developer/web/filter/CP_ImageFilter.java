/*
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                   *
 ****************************************************************************
 * 源文件名:  com.netqin.common.filter.CP_ImageFilter.java
 * 功能: cpframework框架
 * 版本:	@version 1.0
 * 编制日期: 2014年3月27日 上午11:00:33
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2014年3月27日    |    hanqunfeng     |     Created
 */
package org.pyf.developer.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * Description: <类功能描述>. <br>
 * <p>
 * <使用说明>
 * </p>
 * Makedate:2014年3月27日 上午11:00:33
 *
 * @author hanqunfeng
 * @version V1.0
 */
//@WebFilter(filterName="imageFilter",urlPatterns="/forgotPasswordEmail.do")
public class CP_ImageFilter implements Filter {
    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1,
                         FilterChain arg2) throws IOException, ServletException {
        // TODO Auto-generated method stub
        HttpServletRequest request = (HttpServletRequest) arg0;
        HttpServletResponse response = (HttpServletResponse) arg1;

        String uri = request.getRequestURI();
        String[] checkUri = {request.getContextPath()+"/j_spring_security_check",request.getContextPath()+"/forgotPasswordEmail.do"};

        if(Arrays.asList(checkUri).contains(uri)) {

            String yanzhengm = request.getParameter("j_code");
            String sessionyanz = (String) request.getSession(true).getAttribute("checkcode");
            if (yanzhengm.equalsIgnoreCase(sessionyanz)) {
                arg2.doFilter(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/login.do?login_error=2");
            }
        }else {
            arg2.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }
}


