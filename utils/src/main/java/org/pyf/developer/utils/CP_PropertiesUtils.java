/*
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                   *
 ****************************************************************************
 * 源文件名:  CP_PropertiesUtils.java
 * 功能: cpframework框架													   
 * 版本:	@version 1.0	                                                                   
 * 编制日期: 2013年12月26日 下午6:23:04 						    						                                        
 * 修改历史: (主要历史变动原因及说明)		
 * YYYY-MM-DD |    Author      |	 Change Description		      
 * 2013年12月26日    |    hanqunfeng     |     Created 
 */
package org.pyf.developer.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description: <系统全局属性初始化工具类>. <br>
 * <p>
 * <声明在：/config/properties/config.properties中，使用方法：CP_PropertiesUtils..get(key)
 * 
 * </p>
 * Makedate:2013年12月30日 上午10:48:06
 * 
 * @author hanqunfeng
 * @version V1.0
 */
public class CP_PropertiesUtils {
	
	private static Logger logger = LoggerFactory.getLogger(CP_PropertiesUtils.class);
	
	private static Map<String,String> map= new ConcurrentHashMap<String, String>();
	
	static {
		CP_PropertiesUtils.initProperties();
	}

	/**
	 * 描述 : <读取配置文件初始化>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 */
	public static void initProperties() {
		Properties properties = new Properties();
		try {
			InputStream in = CP_PropertiesUtils.class.getResourceAsStream(
					"/config/properties/config.properties");
			properties.load(in);
			in.close();
			Set<Object> entry = properties.keySet();
			for(Object key:entry){
				map.put((String)key, properties.getProperty((String)key));
			}
			logger.info("init config successful!!");
		} catch (IOException e) {
			logger.error("init config failed!!", e);
			// System.out.println(e.getMessage());
		}

	}
	
	
	
	/**                                                          
	* 描述 : <读取配置文件中的实时属性>. <br> 
	*<p> 
		<使用方法说明>  
	 </p>                                                                                                                                                                                                                                                
	* @param key
	* @return                                                                                                      
	*/  
	public static String get(String key){
		return map.get(key);
	}

	//public static void main(String[] args) {
	//	System.out.println(CP_PropertiesUtils.get("test"));
	//}
}
