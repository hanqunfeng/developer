/*
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                   *
 ****************************************************************************
 * 源文件名:  org.cpframework.cp_utils.UrlPathHelper.java 													       
 * 功能: url处理工具类 												   
 * 版本:	@version 1.0	                                                                   
 * 编制日期: 2014年1月6日 下午4:00:34 						    						                                        
 * 修改历史: (主要历史变动原因及说明)		
 * YYYY-MM-DD |    Author      |	 Change Description		      
 * 2014年1月6日    |    孙成启     |     Created 
 */
package org.pyf.developer.web.utils.url;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

/** 
 *Description: <url处理工具类>. <br>
 *<p>
	<使用说明>
 </p>
 *Makedate:2014年1月6日 下午4:00:34 
 * @author 孙成启  
 * @version V1.0                             
 */
public class CP_UrlPathHelper extends org.springframework.web.util.UrlPathHelper {
	/**
	 * 传入一段QueryString(例如abc=1&def=2),<br/>
	 * 用将其中的某个参数替换掉，如果没有就添加，有就替换。
	 * 
	 * @param queryString
	 *            查询参数字符串，如a=1&b=2
	 * @param paraName
	 *            参数名称
	 * @param paraValue
	 *            参数值
	 * @return 返回参数拼装之后的QueryString字符串
	 */
	public static String setQueryParameter(String queryString, String paraName,
			String paraValue) {
		String urlStr = StringUtils.hasLength(queryString) ? queryString : "";
		if (urlStr.length() == 0) {
			return paraName + "=" + paraValue;
		} else {
			if (urlStr.indexOf(paraName + "=") < 0) {
				return urlStr + "&" + paraName + "=" + paraValue;
			} else {
				return urlStr.replaceAll(paraName + "\\=[^\\\\&]*", paraName
						+ "=" + paraValue);
			}
		}
	}

	/**
	 * 将所有request中的参数(显式或者隐式)转换成URL字符串
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 转换之后的URL字符串
	 */
	public static String setQueryParameter(HttpServletRequest request) {

		String result = request.getQueryString();
		for (Enumeration<?> enums = request.getParameterNames(); enums
				.hasMoreElements();) {
			String key = (String) enums.nextElement();
			String obj = request.getParameter(key);
			obj = CP_UrlPathHelper.encode(obj, request.getCharacterEncoding());
			result = CP_UrlPathHelper.setQueryParameter(result, key, obj);
		}
		return result;
	}

	/**
	 * 向一个URL添加一个参数
	 * 
	 * @param url
	 *            初始URL,不可为空或者空串或者仅包含空格的字符串
	 * @param paras
	 *            参数Map
	 * @return 添加参数之后的URL
	 */
	public static String addParamToUrl(String url, Map<String, String> paras) {
		int index = url.indexOf("?");
		String queryString = "";
		String path = url;
		if (index >= 0) {
			path = url.substring(0, index);
			if (index < url.length() - 1) {
                queryString = url.substring(index + 1);
            }
		}
		for (Iterator<String> it = paras.keySet().iterator(); it.hasNext();) {
			String paraName = it.next();
			String paraValue = paras.get(paraName);
			queryString = CP_UrlPathHelper.setQueryParameter(queryString,
					paraName, paraValue);
		}

		return path + "?" + queryString;
	}

	/**
	 * 将URL字符串进行URL转码
	 * 
	 * @param source
	 *            源URL字符串
	 * @param encoding
	 *            编码格式
	 * @return 编码之后的URL字符串
	 */
	public static String encode(String source, String encoding) {
		try {
			return URLEncoder.encode(source, encoding);
		} catch (Exception ex) {
			return source;
		}
	}
}
