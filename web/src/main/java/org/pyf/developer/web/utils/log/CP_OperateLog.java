/*
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                   *
 ****************************************************************************
 * 源文件名:  CP_OperateLog.java
 * 功能: cpframework框架													   
 * 版本:	@version 1.0	                                                                   
 * 编制日期: 2013年12月30日 上午10:58:09 						    						                                        
 * 修改历史: (主要历史变动原因及说明)		
 * YYYY-MM-DD |    Author      |	 Change Description		      
 * 2013年12月30日    |    hanqunfeng     |     Created 
 */
package org.pyf.developer.web.utils.log;

import java.lang.annotation.*;

/** 
 *Description: <功能操作日志注解>. <br>
 *<p>
	<声明在被spring管理的类的方法上，会记录下当前的操作日志类型>
	比如：@CP_OperateLog(value="注解日志",type=1,key="test")
 </p>
 *Makedate:2013年12月30日 上午10:55:27 
 * @author hanqunfeng  
 * @version V1.0                             
 */
@Target({ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME)  
@Documented 
public @interface CP_OperateLog {




	/** 
     * 用户操作名称 
     * @return 用户操作名称，默认为空串 
     */  
    String value() default "";  
      
    /** 
     * 用户操作类型，默认类型为0<br/> 
     * 0 - 其他操作 <br/> 
     * 1 - 查询 <br/> 
     * 2 - 新增 <br/> 
     * 3 - 修改 <br/> 
     * 4 - 删除 
     * @return 用户操作类型 
     */
    CP_GlobalNamingConstant type() default CP_GlobalNamingConstant.OPERATE_DEFAULT;
      
    /** 
     * 用户操作名称对应的key,可以通过该key值在属性文件中查找对应的value 
     * @return key 
     */  
    String key() default "";  
}


