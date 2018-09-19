/*******************************************************************************
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                      *
 *******************************************************************************
 * 源文件名: ISystemAuthorityService.java													       
 * 功能: 
 * 版本:	1.0	                                                                   
 * 编制日期: 2009-5-20
 * 说明:
 * 修改历史: (主要历史变动原因及说明)					      
 * YYMMDD      |     Author    |    Change Description 
 * 2014-03-15    sunchengqi         Created
 *******************************************************************************/
package org.pyf.developer.service.auth;


import org.pyf.developer.bean.one.model.auth.SystemAuthority;
import org.pyf.developer.utils.page.CP_Page;
import org.pyf.developer.utils.page.CP_Sorter;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 系统权限业务接口
 * 
 * @author sunchengqi
 * @version 1.0
 */
public interface ISystemAuthorityService {

	/**
	 * 创建系统权限
	 * 
	 * @param auth
	 *            系统权限列表
	 */
	public void create(SystemAuthority... auth);

	/**
	 * 删除系统权限
	 * 
	 * @param ids
	 *            系统权限ID
	 */
	public void delete(Long... ids);

	/**
	 * 查询所有系统权限
	 * 
	 * @return 所有系统权限列表
	 */
	public List<SystemAuthority> findAll();

	/**
	 * 根据系统权限ID获取系统权限详细信息
	 * 
	 * @param authId
	 *            系统权限ID
	 * @return 系统权限详细信息
	 */
	public SystemAuthority findById(Long authId);

	/**
	 * 根据系统权限ID数据获取相应的系统权限列表
	 * 
	 * @param ids
	 *            系统权限数组
	 * @return 系统权限列表
	 */
	public List<SystemAuthority> findByIdArray(Long... ids);

	/**
	 * 分页查询
	 * 
	 * @param example
	 *            查询条件
	 * @param sorter
	 *            排序对象
	 * @param page
	 *            分页对象
	 * @return 系统权限列表
	 */
	public List<SystemAuthority> findByPage(SystemAuthority example,
											CP_Sorter sorter, CP_Page page);

	/**
	 * 获取所有的权限字符串，该权限字符串按照Prefix_id的形式组织
	 * 
	 * @return 权限字符串列表
	 */
	public Set<String> getAuthoritiesAsPrefixAndId();

	/**
	 * 获取Spring Security所需Role name前缀信息
	 * 
	 * @return 前缀信息
	 */
	public String getAuthorityPrefix();

	/**
	 * 获取URL表达式对应的权限，权限以AUTH_${ID}表示
	 * 
	 * @return URL表达式对应权限Map
	 */
	public Map<String, Set<String>> getUrlAuthorities();

	/**
	 * 更新系统权限
	 * 
	 * @param auth
	 *            更新系统权限
	 */
	public void update(SystemAuthority auth);
	
	/**
	 * 根据用户名查询出所有权限
	* 描述 : <描述函数实现的功能>. <br>  
	*<p>                                                   
	                                                                                                                                                                                                        
	* @param userName
	* @return
	 */
	public List<Long> getAuthorityIdByUserId(String userName);
}
