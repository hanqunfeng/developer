/*
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                   *
 ****************************************************************************
 * 源文件名:  org.cpframework.dao.page.PageHolder.java 													       
 * 功能: cpframework框架													   
 * 版本:	@version 1.0	                                                                   
 * 编制日期: 2014-1-13 上午10:11:30 						    						                                        
 * 修改历史: (主要历史变动原因及说明)		
 * YYYY-MM-DD |    Author         |	 Change Description		      
 * 2014-1-13    |    gaowenming     |     Created 
 */
package org.pyf.developer.utils.page;

/**
 * Description: <分页信息线程安全持有类>. <br>
 * <p>
 * <使用说明>
 * </p>
 * Makedate:2014-1-13 上午10:11:30
 * 
 * @author gaowenming
 * @version V1.0
 */
public class CP_PageHolder {
	private static ThreadLocal<CP_Page> locale = new ThreadLocal<CP_Page>() {
		@Override
        protected CP_Page initialValue() {
			return new CP_Page(0, 15, 0);
		}
	};

	public static CP_Page getPage() {
		return locale.get();
	}

	public static void setPage(CP_Page target) {
		locale.set(target);
	}
}
