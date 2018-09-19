/*******************************************************************************
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                      *
 *******************************************************************************
 * 源文件名: ISystemUserService.java													       
 * 功能: 
 * 版本:	1.0	                                                                   
 * 编制日期: 2009-5-19
 * 说明:
 * 修改历史: (主要历史变动原因及说明)					      
 * YYMMDD      |     Author    |    Change Description 
 * 2014-03-15    sunchengqi         Created
 *******************************************************************************/
package org.pyf.developer.service.auth;


import org.pyf.developer.bean.one.model.auth.SystemUser;
import org.pyf.developer.utils.page.CP_Page;
import org.pyf.developer.utils.page.CP_Sorter;

import java.util.List;



/**
 * 系统用户业务信息接口
 * 
 * @author sunchengqi
 * @version 1.0
 */

/**
 * Title: project_name Description: XXXX Copyright: Copyright (c) 2011
 * Company:www.netqin.com Makedate:2012-10-17 下午2:42:53
 * 
 * @author sunchengqi
 * @version %I%, %G%
 * @since 1.0
 * 
 */
public interface ISystemUserService {

	/**
	 * 查询所有系统用户
	 * 
	 * @return 所有系统用户列表
	 */
	public List<SystemUser> findAll();

	/**
	 * 分页查询
	 * 
	 * @param example
	 *            查询条件
	 * @param sorter
	 *            排序对象
	 * @param page
	 *            分页对象
	 * @return 系统用户列表
	 */
	public List<SystemUser> findByPage(SystemUser example, CP_Sorter sorter,
                                       CP_Page page);
	
	/**
	 * 分页查询
	 * 
	 * @param example
	 *            查询条件
	 * @param sorter
	 *            排序对象
	 * @param page
	 *            分页对象
	 * @param str
	 *            预封装子集合名称列表
	 * @return 系统用户列表
	 */
	public List<SystemUser> findByPage(SystemUser example, CP_Sorter sorter,
                                       CP_Page page, String... str);

	/**
	 * 根据用户ID获取用户详细信息
	 * 
	 * @param userId
	 *            用户ID
	 * @return 用户详细信息
	 */
	public SystemUser findById(String userId, String... fields);

	/**
	 * 设置用户角色
	 * 
	 * @param user
	 */
	public void modifyUserRole(SystemUser user);

	/**
	 * 判断某个系统用户是否为超级用户
	 * 
	 * @param userId
	 *            用户user id
	 * @return true-用户为超级用户,false-用户不是超级用户
	 */
	public boolean isReserved(String userId);

	/**
	 * 描述 : <新增>. <br>
	 * <p>
	 * 
	 * @param user
	 * @return
	 */
	public int insert(SystemUser user);

	/**
	 * 描述 : <修改>. <br>
	 * <p>
	 * 
	 * @param user
	 * @return
	 */
	public int update(SystemUser user);

	/**
	 * 描述 : <修改个人密码>. <br>
	 * <p>
	 * 
	 * @param user
	 * @param newPassword
	 * @return
	 */
	public SystemUser updatePS(SystemUser user, String newPassword);
	
	/**                                                          
	* 描述 : <删除用户>. <br> 
	*<p> 
		<使用方法说明>  
	 </p>                                                                                                                                                                                                                                                
	* @param ids                                                                                                      
	*/  
	public void delete(String... ids);

	/**
	 * 获取当前用户的所有访问类型权限
	 * @param userId
	 * @return
	 */
	public List<String> getSecurityAuthorize(String userId);


	/**
	 * 重置用户名密码
	 * @param encode userid加密数据
	 */
	public Integer handleResetPassword(String encode,String newpassword,String encodePassword );

	/**
	 * 忘记密码 邮件功能
	 * @param su
	 */
	public Integer handleForgotPasswordEmail( SystemUser su );


}
