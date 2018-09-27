package org.pyf.developer.web.thymeleafController.auth;

import org.pyf.developer.bean.one.model.auth.SystemUser;
import org.pyf.developer.service.auth.ISystemUserService;
import org.pyf.developer.web.controller.base.CP_SimpleBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/9/26 14:12.
 */

@Controller
public class DemoController extends CP_SimpleBaseController {

    @Resource(name = "systemUserService")
    private ISystemUserService userService;

    @RequestMapping("/demo/getuser/{userId}")
    public String demo(@PathVariable("userId")String userId, Model model){
        SystemUser user = userService.findById(userId);
        model.addAttribute("user",user);
        //return "auth/demo";
        return getView("authDemo");
    }


    @RequestMapping("/demo/login.do")
    public String login( Model model){
        return "login";
    }
}
