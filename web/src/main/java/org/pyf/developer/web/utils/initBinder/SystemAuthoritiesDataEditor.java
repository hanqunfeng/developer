/*******************************************************************************
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                      *
 *******************************************************************************
 * 源文件名: SystemAuthorityDataEditor.java													       
 * 功能: 
 * 版本:	1.0	                                                                   
 * 编制日期: 2009-5-25
 * 说明:
 * 修改历史: (主要历史变动原因及说明)					      
 * YYMMDD      |     Author    |    Change Description 
 * 2014-03-15    sunchengqi         Created
 *******************************************************************************/
package org.pyf.developer.web.utils.initBinder;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.pyf.developer.bean.one.model.auth.SystemAuthority;

import java.beans.PropertyEditorSupport;
import java.util.HashSet;
import java.util.Set;


/**
 * 功能权限列表绑定处理类
 * 
 * @author sunchengqi
 * @version 1.0
 */
public class SystemAuthoritiesDataEditor extends PropertyEditorSupport {
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.beans.PropertyEditorSupport#setValue(java.lang.Object)
	 */
	public void setValue(Object value) {
		if (value instanceof String[]) {
			String[] authids = (String[]) value;
			Set<SystemAuthority> auths = null;
			if (!ArrayUtils.isEmpty(authids)) {
				auths = new HashSet<SystemAuthority>();
				for (String id : authids) {
					SystemAuthority auth = new SystemAuthority();
					auth.setId(NumberUtils.toLong(id));
					auths.add(auth);
				}
			}
			super.setValue(auths);
		} else if (value instanceof String) {
			Set<SystemAuthority> auths = new HashSet<SystemAuthority>();
			SystemAuthority auth = new SystemAuthority();
			auth.setId(NumberUtils.toLong((String) value));
			auths.add(auth);
			super.setValue(auths);
		} else
			super.setValue(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
	 */
	public void setAsText(String text) {
		SystemAuthority auth = new SystemAuthority();
		auth.setId(NumberUtils.toLong(text));
		Set<SystemAuthority> auths = new HashSet<SystemAuthority>();
		auths.add(auth);
		super.setValue(auths);
	}

}
