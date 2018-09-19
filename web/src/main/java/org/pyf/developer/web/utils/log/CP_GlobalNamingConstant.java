/*******************************************************************************
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                      *
 *******************************************************************************
 * 源文件名: CP_GlobalNamingConstant.java
 * 功能: 
 * 版本:	1.0	                                                                   
 * 编制日期: 2009-4-22
 * 说明:
 * 修改历史: (主要历史变动原因及说明)					      
 * YYMMDD      |     Author    |    Change Description 
 * 2009-4-22    Liujiajun         Created
 *******************************************************************************/
package org.pyf.developer.web.utils.log;


public  enum CP_GlobalNamingConstant {


	/**
	 * ****************************************************
	 * 用户操作日志记录类型定义<br/> 
	 * ****************************************************
	 */

	/**
	 * 用户操作类型：登录
	 */
	 OPERATE_LOGIN("登录"),

	/**
	 * 用户操作类型：获取列表
	 */
	OPERATE_LIST("获取列表"),

	/**
	 * 用户操作类型：添加记录
	 */
	OPERATE_ADD("添加记录"),

	/**
	 * 用户操作类型：修改记录
	 */
	OPERATE_MODIFY("修改记录"),

	/**
	 * 用户操作类型：删除记录
	 */
	OPERATE_DELETE("删除记录"),

	/**
	 * 用户操作类型：编辑记录
	 */
	OPERATE_EDIT("编辑记录"),

	/**
	 * 用户操作类型：数据详细信息
	 */
	OPERATE_DETAIL_MODIFY("修改记录"),

	/**
	 * 用户操作类型：上传
	 */
	OPERATE_UPLOAD("上传"),

	/**
	 * 用户操作类型：刷新缓存
	 */
	OPERATE_CACHE("刷新缓存"),
	
	/**
	 * 用户操作类型：默认
	 */
	OPERATE_DEFAULT("默认");

	private String desc;//中文描述

	/**
	 * 私有构造,防止被外部调用
	 * @param desc
	 */
	private CP_GlobalNamingConstant(String desc){
		this.desc=desc;
	}

	/**
	 * 定义方法,返回描述,跟常规类的定义没区别
	 * @return
	 */
	public String getDesc(){
		return desc;
	}

	/**
	 * 覆盖
	 * @return
	 */
	@Override
	public String toString() {
		return desc;
	}
}
