/*******************************************************************************
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                      *
 *******************************************************************************
 * 源文件名: SystemAuthorityController.java													       
 * 功能: 
 * 版本:	1.0	                                                                   
 * 编制日期: 2009-5-21
 * 说明:
 * 修改历史: (主要历史变动原因及说明)					      
 * YYMMDD      |     Author    |    Change Description 
 * 2014-03-15    sunchengqi         Created
 *******************************************************************************/
package org.pyf.developer.web.controller.auth;


import org.pyf.developer.bean.one.model.auth.SystemAuthority;
import org.pyf.developer.service.auth.ISystemAuthorityService;
import org.pyf.developer.utils.page.CP_Page;
import org.pyf.developer.utils.page.CP_Sorter;
import org.pyf.developer.web.controller.base.CP_SimpleBaseController;
import org.pyf.developer.web.utils.log.CP_GlobalNamingConstant;
import org.pyf.developer.web.utils.log.CP_OperateLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


/**
 * 系统权限管理Controller
 *
 */
@Controller
public class AuthorityController extends CP_SimpleBaseController {

	@Autowired
	private ISystemAuthorityService authService;


	/**
	 * 新建系统权限
	 * 
	 * @param authority
	 *            系统权限
	 * @param model
	 *            数据封装
	 * @return 视图名称
	 */
	@RequestMapping(value = "/auth/authority/detail.do", params = { "modify=false" }, method = POST)
	@CP_OperateLog(value = "功能权限", type = CP_GlobalNamingConstant.OPERATE_ADD)
	public String handleCreate(SystemAuthority authority, Model model) {
		authority.setLastupdate(new Date()) ;
		authService.create(authority);
		model.addAttribute("dataObj", authority);
		model.addAttribute("modify", true);
		model.addAttribute("message", "message.create.success");
		//return "authority_detail_view";
		return getView("authority_detail_view");
	}

	/**
	 * 删除功能权限
	 * 
	 * @param ids
	 *            id数组
	 * @param model
	 *            数据封装
	 * @return 视图名称
	 */
	@RequestMapping("/auth/authority/delete.do")
	@CP_OperateLog(value = "功能权限", type = CP_GlobalNamingConstant.OPERATE_DELETE)
	public String handleDelete(Long[] ids,
							   @ModelAttribute("queryBean") SystemAuthority example,
							   Integer showExporter, @ModelAttribute("_pageBean") CP_Page page,
							   @ModelAttribute("sorter") CP_Sorter sorter, Model model) {
		authService.delete(ids);
		model.addAttribute("message", "message.delete.success");
		return handleList(example, showExporter, page, sorter, model);
	}

	/**
	 * 查看系统权限详细信息
	 * 
	 * @param id
	 *            系统权限ID
	 * @param model
	 *            数据封装
	 * @return 视图名称
	 */
	@RequestMapping(value = "/auth/authority/detail.do", method = GET)
	@CP_OperateLog(value = "功能权限", type = CP_GlobalNamingConstant.OPERATE_EDIT)
	public String handleDetail(Long id, Model model) {
		SystemAuthority auth = new SystemAuthority();
		boolean modify = false;
		if (id != null) {
			auth = authService.findById(id);
			modify = auth != null ? true : false;
		}
		model.addAttribute("dataObj", auth);
		model.addAttribute("modify", modify);
		//return "authority_detail_view";
		return getView("authority_detail_view");
	}




	/**
	 * 查询系统功能权限列表
	 * 
	 * @param example
	 *            查询条件
	 * @param sorter
	 *            排序条件
	 * @param model
	 *            数据封装
	 * @return 视图名称
	 */
	@RequestMapping("/auth/authority/list.do")
	@CP_OperateLog(value = "功能权限", type = CP_GlobalNamingConstant.OPERATE_LIST)
	public String handleList(
			@ModelAttribute("queryBean") SystemAuthority example,
			Integer showExporter, @ModelAttribute("_pageBean") CP_Page page,
			@ModelAttribute("sorter") CP_Sorter sorter, Model model) {

		if ( sorter.getSortName() == null
				|| "".equals(sorter.getSortName())) {
			sorter.setSortName("id");
			sorter.setSortType(CP_Sorter.DESC);
		}

		List<SystemAuthority> results = authService.findByPage(example, sorter,
				page);
		model.addAttribute("results", results);
		model.addAttribute("showExporter", showExporter);


		//return "authority_list_view";
		return getView("authority_list_view");
	}

	/**
	 * 修改系统权限
	 * 
	 * @param authority
	 *            系统权限
	 * @param model
	 *            数据封装
	 * @return 视图名称
	 */
	@RequestMapping(value = "/auth/authority/detail.do", params = { "modify=true" }, method = POST)
	@CP_OperateLog(value = "功能权限", type = CP_GlobalNamingConstant.OPERATE_MODIFY)
	public String handleUpdate(SystemAuthority authority, Model model) {
		authority.setLastupdate(new Date()) ;
		authService.update(authority);
		model.addAttribute("dataObj", authority);
		model.addAttribute("modify", true);
		model.addAttribute("message", "message.modify.success");
		//return "authority_detail_view";
		return getView("authority_detail_view");
	}




	
}
