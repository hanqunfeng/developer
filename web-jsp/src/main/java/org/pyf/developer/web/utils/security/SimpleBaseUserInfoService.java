/*******************************************************************************
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                      *
 *******************************************************************************
 * 源文件名: SimpleBaseUserInfoService.java													       
 * 功能: 
 * 版本:	1.0	                                                                   
 * 编制日期: 2009-6-8
 * 说明:
 * 修改历史: (主要历史变动原因及说明)					      
 * YYMMDD      |     Author    |    Change Description 
 * 2014-03-14    sunchengqi         Created
 *******************************************************************************/
package org.pyf.developer.web.utils.security;


import org.pyf.developer.bean.one.model.auth.SystemUser;
import org.pyf.developer.service.auth.ISystemAuthorityService;
import org.pyf.developer.service.auth.ISystemUserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * 用户安全相关基本信息获取类
 * 
 * @author sunchengqi
 * @version 1.0
 */
//@Service
public class SimpleBaseUserInfoService {
	@Resource(name = "systemAuthorityService")
	protected ISystemAuthorityService authService;

	@Resource(name = "systemUserService")
	protected ISystemUserService userService;

	/**
	 * 获取所有的权限
	 * 
	 * @return 所有权限
	 */
	protected Set<SimpleGrantedAuthority> loadAllAuthorities() {
		Set<String> auths = authService.getAuthoritiesAsPrefixAndId();
		if (auths == null)
			return null;
		Set<SimpleGrantedAuthority> results = new HashSet<SimpleGrantedAuthority>();
		for (Iterator<String> it = auths.iterator(); it.hasNext();) {
			results.add(new SimpleGrantedAuthority(it.next()));
		}
		return results;
	}

	/**
	 * 根据用户获取其相应的权限列表
	 * 
	 * @param user
	 *            用户信息
	 * @return 权限列表
	 */
	protected Set<SimpleGrantedAuthority> loadUserAuthorities(SystemUser user) {
		return loadUserAuthorities(user.getUserId());
	}
	/**
	 * 根据用户获取其相应的权限列表
	 * 
	 * @param userId
	 *            用户信息
	 * @return 权限列表
	 */
	protected Set<SimpleGrantedAuthority> loadUserAuthorities(String userId) {
		
		List<Long> authIds = authService.getAuthorityIdByUserId(userId);
		Set<SimpleGrantedAuthority> results = new HashSet<SimpleGrantedAuthority>();
		String prefix = authService.getAuthorityPrefix();
		for (Long id:authIds) {
			results.add(new SimpleGrantedAuthority(prefix
					+ id));
		}

		//第一、在html页面中如果要使用hasRole去设置权限，那么必须放入是以ROLE_开头的角色。而hasAnyAuthority则可以放入任意字符串。
		//第二、在html页面中使用hasRole时可以省略掉ROLE_部分，只在后端放入角色的时候前面加上ROLE_即可，而hasAnyAuthority不支持这么做。
		//results.add(new SimpleGrantedAuthority("ROLE_FILE_ADD"));  //hasRole('FILE_ADD') 和 hasRole('ROLE_FILE_ADD') 都可以
		//results.add(new SimpleGrantedAuthority("FILE_MODIFY"));  //只能使用 hasAnyAuthority('FILE_MODIFY')

		//这个地方加入访问类型的权限
        List<String> auths = userService.getSecurityAuthorize(userId);
		for(String auth : auths ){
            results.add(new SimpleGrantedAuthority("ROLE_"+auth));
        }

		return results;
	}

	public void setAuthService(ISystemAuthorityService authService) {
		this.authService = authService;
	}

	public void setUserService(ISystemUserService userService) {
		this.userService = userService;
	}
}
