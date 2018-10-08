package org.pyf.developer.web.controller.auth;

import lombok.extern.slf4j.Slf4j;
import org.pyf.developer.bean.one.model.auth.SystemAccessType;
import org.pyf.developer.service.auth.ISystemAccessTypeService;
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

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * ${访问类型}
 * Created by hanqf on 2018/9/14 16:00.
 */

@Controller
@Slf4j
public class SystemAccessTypeController extends CP_SimpleBaseController {


    @Autowired
    ISystemAccessTypeService systemAccessTypeService;

    /**
     * 新建访问类型
     *
     * @param accessType
     *            访问类型
     * @param model
     *            数据封装
     * @return 视图名称
     */
    @RequestMapping(value = "/auth/accesstype/detail.do", params = { "modify=false" }, method = POST)
    @CP_OperateLog(value = "访问类型", type = CP_GlobalNamingConstant.OPERATE_ADD)
    public String handleCreate(SystemAccessType accessType, Model model) {
        systemAccessTypeService.create(accessType);
        model.addAttribute("dataObj", accessType);
        model.addAttribute("modify", true);
        model.addAttribute("message", "message.create.success");
        //return "accesstype_detail_view";
        return getView("accesstype_detail_view");
    }

    /**
     * 删除访问类型
     *
     * @param ids
     *            id数组
     * @param model
     *            数据封装
     * @return 视图名称
     */
    @RequestMapping("/auth/accesstype/delete.do")
    @CP_OperateLog(value = "访问类型", type = CP_GlobalNamingConstant.OPERATE_DELETE)
    public String handleDelete(Long[] ids,
                               @ModelAttribute("queryBean") SystemAccessType example,
                               Integer showExporter, @ModelAttribute("_pageBean") CP_Page page,
                               @ModelAttribute("sorter") CP_Sorter sorter, Model model) {
        systemAccessTypeService.delete(ids);
        model.addAttribute("message", "message.delete.success");
        return handleList(example, page, sorter, model);
    }

    /**
     * 查看访问类型详细信息
     *
     * @param id
     *            访问类型ID
     * @param model
     *            数据封装
     * @return 视图名称
     */
    @RequestMapping(value = "/auth/accesstype/detail.do", method = GET)
    @CP_OperateLog(value = "访问类型", type = CP_GlobalNamingConstant.OPERATE_EDIT)
    public String handleDetail(Long id, Model model) {
        SystemAccessType accessType = new SystemAccessType();
        boolean modify = false;
        if (id != null) {
            accessType = systemAccessTypeService.findById(id);
            modify = accessType != null ? true : false;
        }
        model.addAttribute("dataObj", accessType);
        model.addAttribute("modify", modify);
        //return "accesstype_detail_view";
        return getView("accesstype_detail_view");
    }




    /**
     * 查询访问类型列表
     *
     * @param example
     *            查询条件
     * @param sorter
     *            排序条件
     * @param model
     *            数据封装
     * @return 视图名称
     */
    @RequestMapping("/auth/accesstype/list.do")
    @CP_OperateLog(value = "访问类型", type = CP_GlobalNamingConstant.OPERATE_LIST)
    public String handleList(
            @ModelAttribute("queryBean") SystemAccessType example, @ModelAttribute("_pageBean") CP_Page page,
            @ModelAttribute("sorter") CP_Sorter sorter, Model model) {

        if ( sorter.getSortName() == null
                || "".equals(sorter.getSortName())) {
            sorter.setSortName("id");
            sorter.setSortType(CP_Sorter.DESC);
        }

        List<SystemAccessType> results = systemAccessTypeService.findByPage(example, sorter,
                page);
        model.addAttribute("results", results);
        //return "accesstype_list_view";
        return getView("accesstype_list_view");
    }

    /**
     * 修改访问类型
     *
     * @param accesstype
     *            访问类型
     * @param model
     *            数据封装
     * @return 视图名称
     */
    @RequestMapping(value = "/auth/accesstype/detail.do", params = { "modify=true" }, method = POST)
    @CP_OperateLog(value = "访问类型", type = CP_GlobalNamingConstant.OPERATE_MODIFY)
    public String handleUpdate(SystemAccessType accesstype, Model model) {
        systemAccessTypeService.update(accesstype);
        model.addAttribute("dataObj", accesstype);
        model.addAttribute("modify", true);
        model.addAttribute("message", "message.modify.success");
        //return "accesstype_detail_view";
        return getView("accesstype_detail_view");
    }


}
