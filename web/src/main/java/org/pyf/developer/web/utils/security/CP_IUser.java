/*
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                   *
 ****************************************************************************
 * 源文件名:  CP_IUser.java
 * 功能: cpframework框架													   
 * 版本:	@version 1.0	                                                                   
 * 编制日期: 2013年12月30日 下午1:16:06 						    						                                        
 * 修改历史: (主要历史变动原因及说明)		
 * YYYY-MM-DD |    Author      |	 Change Description		      
 * 2013年12月30日    |    hanqunfeng     |     Created 
 */
package org.pyf.developer.web.utils.security;
/** 
 *Description: <系统用户类>. <br>
 *<p>
	<使用说明>
 </p>
 *Makedate:2013年12月30日 下午1:16:06 
 * @author hanqunfeng  
 * @version V1.0                             
 */
public interface CP_IUser {
	
	/**                                                          
	* 描述 : <获得用户名称>. <br> 
	*<p> 
		<获得用户名称>  
	 </p>                                                                                                                                                                                                                                                
	* @param args 方法参数
	* @return    用户名称                                                                                                  
	*/  
	public String getUserName(Object[] args);
	
	/**                                                          
	* 描述 : <获得用户名称>. <br> 
	*<p> 
		<获得用户名称>  
	 </p>                                                                                                                                                                                                                                                
	* @return                                                                                                      
	*/  
	public String getUserName();
}


