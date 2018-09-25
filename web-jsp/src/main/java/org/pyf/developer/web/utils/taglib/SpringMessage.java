package org.pyf.developer.web.utils.taglib;

import org.pyf.developer.web.utils.context.ApplicationContextProvider;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/8/16 16:48.
 */


public class SpringMessage extends TagSupport {

    private static final long serialVersionUID = 1L;


    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }



    @Override
    public int doEndTag() throws JspException {
        MessageSource messageSource = (MessageSource) ApplicationContextProvider.getBean("messageSource");

        String msg = messageSource.getMessage(code,null, LocaleContextHolder.getLocale());
        try {
            this.pageContext.getOut().print(msg);
        }catch (Exception e){
            e.printStackTrace();
        }

        return super.doEndTag();
    }
}
