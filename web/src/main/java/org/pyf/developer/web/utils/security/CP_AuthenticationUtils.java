/*******************************************************************************
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                      *
 *******************************************************************************
 * 源文件名: AuthenticationUtils.java													       
 * 功能: 用户认证授权工具类
 * 版本:	1.0	                                                                   
 * 编制日期: 2008-12-17
 * 说明:  用户认证授权工具类                                      
 * 修改历史: 				      
 * YYMMDD |  Author  |  Change Description 
 *
 *******************************************************************************/
package org.pyf.developer.web.utils.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户认证授权工具类
 * 
 * @author sunchengqi
 * @version 1.0
 * 
 */
public class CP_AuthenticationUtils {
	/**
	 * 取得当前用户名
	 * 
	 * @return java.lang.String 当前用户名
	 */
	public static String getUsername() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
		SecurityContext context = SecurityContextHolder.getContext();
		if (context != null) {
			if (context instanceof SecurityContext) {
				SecurityContext sc = (SecurityContext) context;
				Authentication auth = sc.getAuthentication();
				if (auth != null) {
					Object principal = auth.getPrincipal();
					if (principal instanceof UserDetails) {
						return ((UserDetails) principal).getUsername();
					} else {
						return principal.toString();
					}
				}
			}
		}
		return null;
	}

	/**
	 * 判断用户是否已经通过认证。
	 * 
	 * @return boolean 是否通过认证标记
	 */
	public static boolean isAuthenticated() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context != null) {
			if (context instanceof SecurityContext) {
				SecurityContext sc = (SecurityContext) context;
				Authentication auth = sc.getAuthentication();
				if (auth != null) {
					return auth.isAuthenticated();
				}
			}
		}
		return false;
	}
}
