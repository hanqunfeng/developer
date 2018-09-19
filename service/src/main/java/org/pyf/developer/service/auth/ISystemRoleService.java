/*******************************************************************************
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                      *
 *******************************************************************************
 * 源文件名: ISystemRoleService.java													       
 * 功能: 
 * 版本:	1.0	                                                                   
 * 编制日期: 2009-5-21
 * 说明:
 * 修改历史: (主要历史变动原因及说明)					      
 * YYMMDD      |     Author    |    Change Description 
 * 2014-03-15    sunchengqi         Created
 *******************************************************************************/
package org.pyf.developer.service.auth;


import org.pyf.developer.bean.one.model.auth.SystemRole;
import org.pyf.developer.utils.page.CP_Page;
import org.pyf.developer.utils.page.CP_Sorter;

import java.util.List;


/**
 * 系统角色维护业务接口
 * 
 * @author sunchengqi
 * @version 1.0
 */
public interface ISystemRoleService {

	/**
	 * 查询所有系统角色
	 * 
	 * @return 所有系统角色列表
	 */
	public List<SystemRole> findAll();
	public List<SystemRole> findAll(String... str);

	/**
	 * 分页查询
	 * 
	 * @param example
	 *            查询条件
	 * @param authname
	 *            权限名称
	 * @param sorter
	 *            排序对象
	 * @param page
	 *            分页对象
	 * @return 系统角色列表
	 */
	public List<SystemRole> findByPage(SystemRole example, String authname,
									   CP_Sorter sorter, CP_Page page);

	/**
	 * 根据用户ID获取系统角色详细信息
	 * 
	 * @param roleId
	 *            系统角色ID
	 * @return 系统角色详细信息
	 */
	public SystemRole findById(Long roleId, String... fields);

	/**
	 * 创建系统角色
	 * 
	 * @param role
	 *            系统角色
	 */
	public void create(SystemRole role);

	/**
	 * 更新系统角色
	 * 
	 * @param role
	 *            系统角色
	 */
	public void update(SystemRole role);

	/**
	 * 删除系统角色
	 * 
	 * @param ids
	 *            系统角色ID
	 */
	public void delete(Long... ids);

	/**
	 * 获取对应角色的权限的访问类型
	 * @param roleId
	 * @return
	 */
	public String getAccessAuthShowIds(Long roleId);
}
