/*******************************************************************************
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                      *
 *******************************************************************************
 * 源文件名: UserRoleController.java													       
 * 功能: 
 * 版本:	1.0	                                                                   
 * 编制日期: 2009-5-25
 * 说明:
 * 修改历史: (主要历史变动原因及说明)					      
 * YYMMDD      |     Author    |    Change Description 
 * 2014-03-15    sunchengqi        Created
 *******************************************************************************/
package org.pyf.developer.web.controller.auth;


import org.apache.commons.lang3.StringUtils;
import org.pyf.developer.bean.one.model.auth.SystemRole;
import org.pyf.developer.bean.one.model.auth.SystemUser;
import org.pyf.developer.service.auth.ISystemRoleService;
import org.pyf.developer.service.auth.ISystemUserService;
import org.pyf.developer.utils.CP_BusinessException;
import org.pyf.developer.utils.page.CP_Page;
import org.pyf.developer.utils.page.CP_Sorter;
import org.pyf.developer.web.controller.base.CP_SimpleBaseController;
import org.pyf.developer.web.utils.initBinder.CustomDataEditor;
import org.pyf.developer.web.utils.log.CP_OperateLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.pyf.developer.web.utils.log.CP_GlobalNamingConstant.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * 用户角色配置Controller
 * 
 * @author sunchengqi
 * @version 1.0
 */
@Controller
@SessionAttributes({"message","messageStatus"})
public class UserRoleController extends CP_SimpleBaseController {

	@Resource(name = "systemRoleService")
	private ISystemRoleService roleService;

	@Resource(name = "systemUserService")
	private ISystemUserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Set.class, "roles",
				new CustomDataEditor(SystemRole.class,"id",Long.class));
	}

	/**
	 * 查看系统用户对应角色详细信息
	 * 
     * 用户操作类型，默认类型为0<br/> 
     * 0 - 其他操作 <br/> 
     * 1 - 查询 <br/> 
     * 2 - 新增 <br/> 
     * 3 - 修改 <br/> 
     * 4 - 删除 
     * @return 用户操作类型 
	 * @param userId
	 *            用户ID
	 * @param model
	 *            数据封装
	 * @return 视图名称
	 */
	@RequestMapping(value = "/auth/user/detail.do", method = GET)
	@CP_OperateLog(value = "用户角色配置", type = OPERATE_EDIT)
	public String handleDetail(String userId, Model model) {
		SystemUser user =  (SystemUser) userService.findById(userId,"roles");
		if (user == null) {
			model.addAttribute("message", "error.user.invalid");
			return handleList(null,new CP_Page(),new CP_Sorter(), model);
		}
		List<SystemRole> roles = roleService.findAll();
		boolean reserved = userService.isReserved(userId);
		if (reserved) {
			user.setRoles(new HashSet<SystemRole>(roles));
		}
		model.addAttribute("dataObj", user);
		model.addAttribute("roles", roles);
		model.addAttribute("reserved", reserved);
		return "user_userdetail_view";
	}

	/**
	 * 修改用户对应角色
	 * 
	 * @param user
	 *            系统用户
	 * @param model
	 *            数据封装
	 * @return 视图名称
	 */
	@RequestMapping(value = "/auth/user/detail.do", method = POST)
	@CP_OperateLog(value = "用户角色配置", type = OPERATE_MODIFY)
	//@CacheFlush(modelId="findUser")
	public String handleUpdate(SystemUser user, Model model) {
		try {
			userService.modifyUserRole(user);
			model.addAttribute("message", getMessage("message.modify.success",
					null));
		} catch (CP_BusinessException e) {
			model.addAttribute("message", getMessage(e.getKey(), e
					.getMessageArguments()));
		}
		return handleDetail(user == null ? null : user.getUserId(), model);
	}

	/**
	 * 查询系统用户列表
	 *
	 * @param user
	 *            查询条件
	 * @param sorter
	 *            排序条件
	 * @param model
	 *            数据封装
	 * @return 视图名称
	 */
	@RequestMapping("/auth/user/list.do")
	@CP_OperateLog(value = "用户角色配置", type = OPERATE_LIST)
	public String handleList(@ModelAttribute("queryBean") SystemUser user,@ModelAttribute("_pageBean") CP_Page page,
			@ModelAttribute("sorter") CP_Sorter sorter, Model model) {
		if ( sorter.getSortName() == null
				|| "".equals(sorter.getSortName())) {
			sorter.setSortName("userId");
			sorter.setSortType(CP_Sorter.DESC);
		}
		
		List<SystemUser> results = userService.findByPage(user, sorter,
				page,"roles");
		
		model.addAttribute("results", results);
		return "user_list_view";
	}
	
	/**                                                          
	* 描述 : <新增页面>. <br>
	*<p>                                                 
	                                                                                                                                                                                                      
	* @param model
	* @return                                                                                    			   
	*/
	@RequestMapping("/auth/user/userAdddetail.do")
	@CP_OperateLog(value = "用户新增", type = OPERATE_ADD)
	public String handleAddDetail(Model model) {
		
		model.addAttribute("dataObj", new SystemUser());
		model.addAttribute("modify", "add");
		return "user_userdetail_view";
	}
	
	/**                                                          
	* 描述 : <用户新增/修改保存>. <br>
	*<p>                                                 
	                                                                                                                                                                                                      
	* @param user
	* @param model
	* @return                                                                                    			   
	*/
//	@RequestMapping("/auth/user/userSave.do")
//	@CP_OperateLog(value = "用户新增/修改保存", type = OPERATE_MODIFY)
//	public ModelAndView handleAddSave(SystemUser user,Model model,String modify,RedirectAttributes redirectAttributes) {
//		ModelAndView v = null;
//		if(modify.equals("add")){//新增
//			userService.insert(user);
//			model.addAttribute("message","message.create.success");
//			v = new ModelAndView(new RedirectView("detail.do?userId="+user.getUserId()));
//		}else{//修改
//			userService.update(user);
//			model.addAttribute("message","message.modify.success");
////			v = new ModelAndView(new RedirectView("list.do"));
//			v = new ModelAndView(new RedirectView("detail.do?userId="+user.getUserId()));
//		}
//		 
//		return v;
//	}
	@RequestMapping("/auth/user/userSave.do")
	@CP_OperateLog(value = "用户新增/修改保存", type = OPERATE_MODIFY)
	public String handleAddSave(SystemUser user,Model model,String modify,RedirectAttributes redirectAttributes) {
		if (user.getPassword() != null && !"".equals(user.getPassword())) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}


		if(modify.equals("add")){//新增
			userService.insert(user);
			redirectAttributes.addFlashAttribute("message","message.create.success");
		}else{//修改
			userService.update(user);
			redirectAttributes.addFlashAttribute("message", "message.modify.success");//放到redirect后的model中
		}
		return "redirect:detail.do?userId="+user.getUserId();
	}
	@RequestMapping("/auth/user/userUpdatePS.do")
	@CP_OperateLog(value = "个人密码修改页面", type = OPERATE_MODIFY)
	public String handleUpdatePS(SystemUser user,@RequestParam(required=false)String newPassword ,Model model){
		SystemUser dataObj = null;

		dataObj = userService.findById(user.getUserId());
		
		if(StringUtils.isNotBlank(newPassword)){//修改密码

			if (passwordEncoder.encode(user.getPassword())
					.equals(dataObj.getPassword())) {// 旧密码正确
                newPassword = passwordEncoder.encode(newPassword);
				dataObj = userService.updatePS(user, newPassword);
				model.addAttribute("message", "message.user.password.success");//修改成功
			}else {
				model.addAttribute("message", "message.user.password.failed");//旧密码错误
			}
			
		}
		
		model.addAttribute("dataObj", dataObj);
		
		return "user_updatePS_view";
	}
	
	
	
	/**                                                          
	* 描述 : <修改页面>. <br>
	*<p>                                                 
	                                                                                                                                                                                                      
	* @param userId
	* @param model
	* @return                                                                                    			   
	*/
	@RequestMapping("/auth/user/userdetail.do")
	@CP_OperateLog(value = "用户修改", type = OPERATE_ADD)
	public String handleUserDetail(String userId,Model model) {
		SystemUser user = userService.findById(userId,"roles");
		if (user == null) {
			model.addAttribute("message", "error.user.invalid");
			return handleList(null,new CP_Page(),new CP_Sorter(),model);
		}
		model.addAttribute("dataObj", user);
		model.addAttribute("modify", "update");
		return "user_userdetail_view";
	}
	

	/**                                                          
	* 描述 : <用户删除>. <br> 
	*<p> 
		<使用方法说明>  
	 </p>                                                                                                                                                                                                                                                
	* @param ids
	* @param user
	* @param page
	* @param sorter
	* @param model
	* @return                                                                                                      
	*/  
	@RequestMapping(value = "/auth/user/delete.do")
	@CP_OperateLog(value = "系统用户配置", type = OPERATE_DELETE)
	public String handleDelete(
			String[] ids,
			@ModelAttribute("queryBean") SystemUser user,
			@ModelAttribute("_pageBean") CP_Page page,
			@ModelAttribute("sorter") CP_Sorter sorter, Model model) {
		userService.delete(ids);
		model.addAttribute("message", "message.delete.success");
		return handleList(user, page, sorter, model);
	}
}
