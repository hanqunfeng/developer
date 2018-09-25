/*
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                   *
 ****************************************************************************
 * 源文件名:  com.netqin.common.security.CP_SecurityUser.java 													       
 * 功能: cpframework框架													   
 * 版本:	@version 1.0	                                                                   
 * 编制日期: 2014年3月25日 下午2:39:32 						    						                                        
 * 修改历史: (主要历史变动原因及说明)		
 * YYYY-MM-DD |    Author      |	 Change Description		      
 * 2014年3月25日    |    hanqunfeng     |     Created 
 */
package org.pyf.developer.web.utils.security;


/**
 *Description: <类功能描述>. <br>
 *<p>
	<使用说明>
 </p>
 *Makedate:2014年3月25日 下午2:39:32 
 * @author hanqunfeng  
 * @version V1.0                             
 */
public class CP_SecurityUser implements CP_IUser {

	@Override
	public String getUserName(Object[] args) {
		// TODO Auto-generated method stub
		return CP_AuthenticationUtils.getUsername();
	}

	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return getUserName(new Object[]{});
	}

}


