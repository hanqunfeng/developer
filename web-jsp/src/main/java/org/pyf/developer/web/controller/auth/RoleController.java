/*******************************************************************************
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                      *
 *******************************************************************************
 * 源文件名: RoleController.java													       
 * 功能: 
 * 版本:	1.0	                                                                   
 * 编制日期: 2009-5-23
 * 说明:
 * 修改历史: (主要历史变动原因及说明)					      
 * YYMMDD      |     Author    |    Change Description 
 * 2014-03-15    sunchengqi         Created
 *******************************************************************************/
package org.pyf.developer.web.controller.auth;


import org.pyf.developer.bean.one.model.auth.SystemAccessType;
import org.pyf.developer.bean.one.model.auth.SystemAuthority;
import org.pyf.developer.bean.one.model.auth.SystemRole;
import org.pyf.developer.service.auth.ISystemAccessTypeService;
import org.pyf.developer.service.auth.ISystemAuthorityService;
import org.pyf.developer.service.auth.ISystemRoleService;
import org.pyf.developer.utils.CP_BusinessException;
import org.pyf.developer.utils.page.CP_Page;
import org.pyf.developer.utils.page.CP_Sorter;
import org.pyf.developer.web.controller.base.CP_SimpleBaseController;
import org.pyf.developer.web.utils.initBinder.SystemAuthoritiesDataEditor;
import org.pyf.developer.web.utils.log.CP_OperateLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.pyf.developer.web.utils.log.CP_GlobalNamingConstant.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * 角色管理Controller
 *
 * @author sunchengqi
 * @version 1.0
 */
@Controller
public class RoleController extends CP_SimpleBaseController {

    @Autowired
    private ISystemRoleService roleService;

    @Autowired
    private ISystemAuthorityService authService;

    @Autowired
    private ISystemAccessTypeService accessTypeService;

    @ModelAttribute("authorities")
    public List<SystemAuthority> getAuthorities() {
        return authService.findAll();
    }

    @ModelAttribute("accessTypes")
    public List<SystemAccessType> getAccessTypes() {
        return accessTypeService.findAll();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Set.class, "authorities",
                new SystemAuthoritiesDataEditor());
    }

    /**
     * 查询系统角色列表
     *
     * @param example 查询条件
     * @param sorter  排序条件
     * @param model   数据封装
     * @return 视图名称
     */
    @RequestMapping("/auth/role/list.do")
    @CP_OperateLog(value = "系统角色配置", type = OPERATE_LIST)
    public String handleList(
            @ModelAttribute("queryBean") SystemRole example,
            @RequestParam(value = "authname", required = false) String authname,
            @ModelAttribute("_pageBean") CP_Page page,
            @ModelAttribute("sorter") CP_Sorter sorter, Model model) {

        if (sorter.getSortName() == null
                || "".equals(sorter.getSortName())) {
            sorter.setSortName("id");
            sorter.setSortType(CP_Sorter.DESC);
        }

        List<SystemRole> results = roleService.findByPage(example, authname,
                sorter, page);
        model.addAttribute("results", results);
        model.addAttribute("authname", authname);
        return "role_list_view";
    }

    /**
     * 查看系统角色详细信息
     *
     * @param id    系统角色ID
     * @param model 数据封装
     * @return 视图名称
     */
    @RequestMapping(value = "/auth/role/detail.do", method = GET)
    @CP_OperateLog(value = "系统角色配置", type = OPERATE_EDIT)
    public String handleDetail(Long id, Model model) {
        SystemRole role = new SystemRole();
        boolean modify = false;
        if (id != null) {
            role = roleService.findById(id, "authorities");
            modify = role != null ? true : false;

            model.addAttribute("accessAuthShowIds",roleService.getAccessAuthShowIds(id));
        }
        model.addAttribute("dataObj", role);
        model.addAttribute("modify", modify);
        return "role_detail_view";
    }

    /**
     * 新建系统角色
     *
     * @param role  系统角色
     * @param model 数据封装
     * @return 视图名称
     */
    @RequestMapping(value = "/auth/role/detail.do", params = {"modify=false"}, method = POST)
    @CP_OperateLog(value = "系统角色配置", type = OPERATE_ADD)
    public String handleCreate(SystemRole role, Model model, String[] accessTypes) {
        role.setLastupdate(new Date());
        roleService.create(role);
        model.addAttribute("dataObj", roleService.findById(role.getId(), "authorities"));
        model.addAttribute("modify", true);
        model.addAttribute("message", "message.create.success");
        return "role_detail_view";
    }

    /**
     * 修改系统角色
     *
     * @param role  系统角色
     * @param model 数据封装
     * @return 视图名称
     */
    @RequestMapping(value = "/auth/role/detail.do", params = {"modify=true"}, method = POST)
    @CP_OperateLog(value = "系统角色配置", type = OPERATE_MODIFY)
    public String handleUpdate(SystemRole role, Model model) {
        try {
            role.setLastupdate(new Date());
            roleService.update(role);
            model.addAttribute("message", "message.modify.success");
        } catch (CP_BusinessException e) {
            model.addAttribute("message",
                    getMessage(e.getKey(), e.getMessageArguments()));
        }

        model.addAttribute("accessAuthShowIds",roleService.getAccessAuthShowIds(role.getId()));
        model.addAttribute("dataObj", roleService.findById(role.getId(), "authorities"));
        model.addAttribute("modify", true);

        return "role_detail_view";
    }

    /**
     * 删除功能权限
     *
     * @param ids   id数组
     * @param model 数据封装
     * @return 视图名称
     */
    @RequestMapping(value = "/auth/role/delete.do")
    @CP_OperateLog(value = "系统角色配置", type = OPERATE_DELETE)
    public String handleDelete(
            Long[] ids,
            @ModelAttribute("queryBean") SystemRole example,
            @RequestParam(value = "authname", required = false) String authname,
            @ModelAttribute("_pageBean") CP_Page page,
            @ModelAttribute("sorter") CP_Sorter sorter, Model model) {
        roleService.delete(ids);
        model.addAttribute("message", "message.delete.success");
        return handleList(example, authname, page, sorter, model);
    }

}
