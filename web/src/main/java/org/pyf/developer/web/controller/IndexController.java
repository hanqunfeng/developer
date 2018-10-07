/*******************************************************************************
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                      *
 *******************************************************************************
 * 源文件名: IndexController.java													       
 * 功能: 
 * 版本:	1.0	                                                                   
 * 编制日期: 2009-6-8
 * 说明:
 * 修改历史: (主要历史变动原因及说明)					      
 * YYMMDD      |     Author    |    Change Description 
 * 2014-03-15    sunchengqi         Created
 *******************************************************************************/
package org.pyf.developer.web.controller;


import lombok.extern.slf4j.Slf4j;
import org.pyf.developer.bean.one.model.auth.SystemRole;
import org.pyf.developer.bean.one.model.auth.SystemUser;
import org.pyf.developer.service.auth.ISystemRoleService;
import org.pyf.developer.service.auth.ISystemUserService;
import org.pyf.developer.utils.CP_IDUtil;
import org.pyf.developer.web.controller.base.CP_SimpleBaseController;
import org.pyf.developer.web.utils.log.CP_GlobalNamingConstant;
import org.pyf.developer.web.utils.log.CP_OperateLog;
import org.pyf.developer.web.utils.security.CP_AuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * 首页视图Controller
 *
 * @author sunchengqi
 * @version 1.0
 */
@Controller
@Slf4j
public class IndexController extends CP_SimpleBaseController {

    @Autowired
    protected ISystemUserService userService;
    @Autowired
    protected ISystemRoleService roleService;
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CookieLocaleResolver cookieLocaleResolver;

    @Autowired
    private PasswordEncoder passwordEncoder;





    /**
     * 展示首页面
     *
     * @param model 数据封装
     * @return 首页视图
     */
    @RequestMapping(value = {"/","/index.do"})
    public String handleIndex(Model model, HttpServletRequest request) {

        //更新系统语言，这里这么做是因为切换语言后LocaleContextHolder不能立刻更新locale，所以这里需要特别设置一下
        LocaleContextHolder.setLocale(cookieLocaleResolver.resolveLocale(request));

        String username = CP_AuthenticationUtils.getUsername();
        SystemUser user = userService.findById(
                username, "roles");

        List<SystemRole> roles = new ArrayList<SystemRole>();
        if (user != null) {
            for (SystemRole role : user.getRoles()) {
                roles.add((SystemRole) roleService.findById(role.getId(),
                        "authorities"));
            }
            model.addAttribute("roles", roles);
            model.addAttribute("user", user);
            //return "indexView";
            return getView("indexView");
        } else {// 虽然已经通过认证系统认证，但是系统用户不存在，提示用户申请开通权限
            // ladp用户信息
            final Object principal = SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            model.addAttribute("ldapuser", principal);
//			if (principal instanceof LdapUserDetailsImpl) {
//				model.addAttribute("isLdapUserDetails", Boolean.TRUE);
//			}
//			if (principal instanceof Person) {
//				model.addAttribute("isLdapPerson", Boolean.TRUE);
//			}
//			if (principal instanceof InetOrgPerson) {
//				model.addAttribute("isLdapInetOrgPerson", Boolean.TRUE);
//			}
            return "userNotFoundView";
        }

    }

    /**
     * 提示注册
     *
     * @param model 数据封装
     * @return 首页视图
     */
    @RequestMapping("/registrationOpenid.do")
    public String handleRegistrationOpenid(Model model,
                                           HttpServletRequest request) {
        String OpenId = (String) request.getSession(true).getAttribute(
                "USER_OPENID_CREDENTIAL");
        String email = (String) request.getSession(true).getAttribute(
                "USER_OPENID_EMAIL");
        model.addAttribute("OpenId", OpenId);
        model.addAttribute("email", email);

        return "userNotFoundView";

    }

    /**
     * 展示首页面
     *
     * @param model 数据封装
     * @return 首页视图
     */
    @RequestMapping("/welcome.do")
    public String handleWelcome(Model model) {

        //获取用户security角色
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Set<GrantedAuthority> authorities = (Set<GrantedAuthority>) userDetails.getAuthorities();
        if (!CollectionUtils.isEmpty(authorities)) {
            for (GrantedAuthority item : authorities) {
                log.info(item.getAuthority());
            }
        }
        //return "welcomeView";
        return getView("welcomeView");
    }

    /**
     * 登录页面
     *
     * @param model 数据封装
     * @return 登录视图
     */
    @RequestMapping("/login.do")
    @CP_OperateLog(value = "登录页面")
    public String handleLogin(Model model, String login_error) {
        if (StringUtils.hasText(login_error)) {
            model.addAttribute("sErrorCause", login_error);
        }

        //return "loginView";
        return getView("loginView");
    }

    /**
     * 登录页面
     *
     * @param model 数据封装
     * @return 登录视图
     */
    @RequestMapping("/loginByOpenId.do")
    @CP_OperateLog(value = "登录页面")
    public String handleLoginByOpenId(Model model, String login_error) {
        if (StringUtils.hasText(login_error)) {
            model.addAttribute("sErrorCause", login_error);
        }
        return "loginByOpenIdView";
    }

    @RequestMapping("/access/denied.do")
    @CP_OperateLog(value = "访问被拒绝页面")
    public String handleAccessDenied(Model model, HttpServletRequest request) {
        AccessDeniedException ex = (AccessDeniedException) request
                .getAttribute(WebAttributes.ACCESS_DENIED_403);
        StringWriter sw = new StringWriter();
        model.addAttribute("errorDetails", ex.getMessage());
        ex.printStackTrace(new PrintWriter(sw));
        model.addAttribute("errorTrace", sw.toString());
        //return "accessDeniedView";
        return getView("accessDeniedView");
    }

    @RequestMapping("/access/sameLogin.do")
    @CP_OperateLog(value = "访问被拒绝页面")
    public String handleAccessSameLogin(Model model) {

        //return "accessSameLoginView";
        return getView("accessSameLoginView");
    }

    /**
     * 不作任何动作，只是通过AOP框架记录用户登录
     */
    @CP_OperateLog(value = "用户登录", type = CP_GlobalNamingConstant.OPERATE_LOGIN)
    public void handleLoginRecord() {
    }


    /**
     * 登录页面
     *
     * @param model 数据封装
     * @return 登录视图
     */
    @RequestMapping("/elfinder.do")
    @CP_OperateLog(value = "登录页面")
    public String handleElFinder(Model model) {

        return "elfinder_view";
    }


    /**
     * 登录页面
     *
     * @param model
     *            数据封装
     * @return 登录视图
     */
    @RequestMapping(value="/forgotPassword.do")
    @CP_OperateLog(value = "忘记密码页面")
    public String handleForgotPasswordGet(Model model, String error) {
        if (StringUtils.hasText(error)) {
            model.addAttribute("result", error);
        }
        return "forgotPasswordView";
    }
    /**
     * 登录页面
     *
     * @param model
     *            数据封装
     * @return 登录视图
     */
    @RequestMapping(value="/forgotPasswordEmail.do")
    @CP_OperateLog(value = "忘记密码确认邮件")
    public String handleResetPasswordEmail(SystemUser su,Model model) {

        int result = userService.handleForgotPasswordEmail(su);

        model.addAttribute("result", result);
        return "forgotPasswordView";
    }
    /**
     * 登录页面
     *
     * @param model
     *            数据封装
     * @return 登录视图
     */
    @RequestMapping(value="/resetPassword.do")
    @CP_OperateLog(value = "重置密码")
    public String handleResetPasswordEmail(String encode,Model model) {
        String newPassword = CP_IDUtil.genRandomPassword(10);
        String encodePassword = passwordEncoder.encode(newPassword);
        Integer result = userService.handleResetPassword(encode,newPassword,encodePassword);
        model.addAttribute("result", result);
        return "resetPasswordView";
    }

}
