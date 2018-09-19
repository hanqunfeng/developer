/*******************************************************************************
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                      *
 *******************************************************************************
 * 源文件名: SystemAuthorityList.java													       
 * 功能: 
 * 版本:	1.0	                                                                   
 * 编制日期: 2009-7-6
 * 说明:
 * 修改历史: (主要历史变动原因及说明)					      
 * YYMMDD      |     Author    |    Change Description 
 * 2009-7-6    Liujiajun         Created
 *******************************************************************************/
package org.pyf.developer.bean.one.model.auth;

import java.util.List;

/**
 * 系统权限列表封装
 * 
 * @author Liu jiajun
 * @version 1.0
 */
public class SystemAuthorityList {

	private List<SystemAuthority> authorityList;

	public List<SystemAuthority> getAuthorityList() {
		return authorityList;
	}

	public void setAuthorityList(List<SystemAuthority> authorityList) {
		this.authorityList = authorityList;
	}

}
