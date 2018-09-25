/*******************************************************************************
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                      *
 *******************************************************************************
 * 源文件名: CascadeOptionView.java													       
 * 功能: 
 * 版本:	1.0	                                                                   
 * 编制日期: 2009-6-1
 * 说明:
 * 修改历史: (主要历史变动原因及说明)					      
 * YYMMDD      |     Author    |    Change Description 
 * 2014-03-15    sunchengqi         Created
 *******************************************************************************/
package org.pyf.developer.web.utils.view;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.spring.web.servlet.view.JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;

/**
 * 级联选择下拉框列表Option相关的JSON
 * View,生成诸如[{"key":"33","value":"防盗主业务"},{"key":"34","value"
 * :"防盗组件更新"},{"key":"35","value":"服务地址更新"}]的json view
 * 
 * @author sunchengqi
 * @version 2.0 增加fields 动态过滤不需要的字段 sunchengqi 
 */
public class CascadeOptionView extends JsonView {
	public static final String MODEL_KEY = "modelKey";
	public static String[] fields;
	private String modelKey = MODEL_KEY;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.json.spring.web.servlet.view.JsonView#createJSON(java.util.Map,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected JSON createJSON(Map model, HttpServletRequest request,
			HttpServletResponse response) {
		Object object = model.get(modelKey);
		object = object == null ? new ArrayList<Object>() : object;
		JsonConfig jsonConfig = new JsonConfig();  //建立配置文件
		jsonConfig.setIgnoreDefaultExcludes(false);  //设置默认忽略
		if(fields!=null&&fields.length>0){
			String[] newFields = new String[fields.length];
			System.arraycopy(fields, 0, newFields, 0, fields.length);
			fields = null;
		  	jsonConfig.setExcludes(newFields);
			return JSONSerializer.toJSON(object, jsonConfig);
		}
		JSON j = JSONSerializer.toJSON(object, jsonConfig);
		return j;
		
		
		
		
	}

	public String getModelKey() {
		return modelKey;
	}

	public void setModelKey(String modelKey) {
		this.modelKey = modelKey;
	}
}
