/*
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                   *
 ****************************************************************************
 * 源文件名:messageOptions.java 														       
 * 功能: （描述文件功能）													   
 * 版本:	@version 1.0	                                                                   
 * 编制日期: 2012-11-02							    						   
 * 说明: （描述使用文件功能时的制约条件）                                       
 * 修改历史: (主要历史变动原因及说明)		
 * YYYY-MM-DD |    Author      |	 Change Description		      
 * 2012-11-02 |  sunchengqi    |     Created 
 */
package org.pyf.developer.web.utils.taglib;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.taglibs.standard.tag.common.fmt.BundleSupport;
import org.springframework.web.servlet.support.BindStatus;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


/**
 * 
 * @author sunchengqi
 *
 */

public class MessageOptions extends TagSupport {

	private static final long serialVersionUID = 1L;


	public static final String UNDEFINED_KEY = "???";


    private String key;       // 'key' attribute value
    
    private String selected; //默认选中的值
    
    private String exclude; //显示时需要排除的值,逗号分割多个值
    
    public String getExclude() {
		return exclude;
	}

	public void setExclude(String exclude) {
		this.exclude = exclude;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	private String value; //上次选中的值,用于查询参数的回传
    public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

    @Override
	public int doEndTag() throws JspException {

	LocalizationContext locCtxt = null;
	value = null;

	if ((key == null) || "".equals(key)) {
	    try {
		pageContext.getOut().print("??????");
	    } catch (IOException ioe) {
		throw new JspTagException(ioe.toString(), ioe);
	    }
	    return EVAL_PAGE;
	}

	String prefix = null;
    Tag t = findAncestorWithClass(this, BundleSupport.class);
    if (t != null) {
	// use resource bundle from parent <bundle> tag
	BundleSupport parent = (BundleSupport) t;
	locCtxt = parent.getLocalizationContext();
	prefix = parent.getPrefix();
    } else {
	locCtxt = BundleSupport.getLocalizationContext(pageContext);
    }
        
    
    Object selectTagValue = null;
	BindStatus bs = (BindStatus)pageContext.getAttribute("org.springframework.web.servlet.tags.form.SelectTag.listValue");
	if(bs!=null){
		selectTagValue = bs.getValue();
	if(selectTagValue!=null) {
        value = selectTagValue.toString();
    }
	}
    
 	String message = UNDEFINED_KEY + key + UNDEFINED_KEY;
 	 ResourceBundle bundle = null;
	if (locCtxt != null) {
	    bundle = locCtxt.getResourceBundle();
	    if (bundle != null) {
		try {
		    // prepend 'prefix' attribute from parent bundle
		    if (prefix != null) {
                key = prefix + key;
            }
		    message = bundle.getString(key);
		    // Perform parametric replacement if required
		} catch (MissingResourceException mre) {
		    message = UNDEFINED_KEY + key + UNDEFINED_KEY;
		}
	    }
	}

	String[] options = message.split(",");
	String[] excludes = exclude==null?null:exclude.split(",");
	StringBuffer results = new StringBuffer();
	String keyPrefix = getKeyPrefix(key);
	for(String option:options){
		if(ArrayUtils.contains(excludes, option)) {
            continue;
        }
		String newKey = keyPrefix+option;
		if(value!=null&&value.equals(option)){
			results.append("<option value='"+option+"' selected >"+bundle.getString(newKey)+"</option>");
		}else if(selected!=null&&selected.equals(option)){
			results.append("<option value='"+option+"' selected >"+bundle.getString(newKey)+"</option>");
		}else{
			results.append("<option value='"+option+"'>"+bundle.getString(newKey)+"</option>");
		}
	}
    try {
	pageContext.getOut().print(results);
    } catch (IOException ioe) {
	throw new JspTagException(ioe.toString(), ioe);
    }
    key =null;
	return EVAL_PAGE;
    }
    
    private String getKeyPrefix(String key){
    	return key.substring(0,key.length()-14);
    }

}
