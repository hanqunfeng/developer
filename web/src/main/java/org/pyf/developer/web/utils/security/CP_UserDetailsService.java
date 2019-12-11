/*******************************************************************************
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                      *
 *******************************************************************************
 * 源文件名: CP_UserDetailsService.java
 * 功能: 
 * 版本:	1.0	                                                                   
 * 编制日期: 2009-6-5
 * 说明:
 * 修改历史: (主要历史变动原因及说明)					      
 * YYMMDD      |     Author    |    Change Description 
 * 2014-03-18    sunchengqi         Created
 *******************************************************************************/
package org.pyf.developer.web.utils.security;


import org.pyf.developer.bean.one.model.auth.SystemUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * 网秦用户详细信息服务实现类，实现{@link UserDetailsService}接口.
 * 接口获取用户详细信息，并生成Spring Security需要的UserDetails对象
 *
 * @author sunchengqi
 * @version 1.0
 */
public class CP_UserDetailsService extends SimpleBaseUserInfoService implements UserDetailsService {

    protected MessageSourceAccessor messages = SpringSecurityMessageSource
            .getAccessor();
    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
     * (non-Javadoc)
     *
     * @seeorg.springframework.security.userdetails.UserDetailsService#
     * loadUserByUsername(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {
        //https://www.google.com/accounts/o8/id?id=AItOawlZ1qx1wGyqWdJKBAs7JxXuyE8CgjoC454
        //hanqunfeng@nq.com  google openId认证账号

        SystemUser user = userService.findById(username);
        //userService.findById(username);
        if (user == null) {
            throw new UsernameNotFoundException("The user name " + username
                    + " can not be found!");
        }
        Set<SimpleGrantedAuthority> auths = null;
        if (userService.isReserved(username)) {
            auths = this.loadAllAuthorities();
        } else {
            auths = this.loadUserAuthorities(username);
        }

        @SuppressWarnings("rawtypes")
        List<SimpleGrantedAuthority> resultAuths = auths == null ? new ArrayList()
                : new ArrayList(auths);

        //addCustomAuthorities(username, resultAuths);

        if (resultAuths == null || resultAuths.size() == 0) {
            throw new UsernameNotFoundException(messages.getMessage(
                    "JdbcDaoImpl.noAuthority", new Object[]{username},
                    "User {0} has no GrantedAuthority"));
        }
        String password = user.getPassword();
        //password = "123456";
        //password = passwordEncoder.encode(password);
        return new User(username, password, user.isStatus(), true,
                true, true, resultAuths);
    }

    //这个方法没用，要是配置角色，应该配置『ROLE_HOLDER』,就是要加上前缀，实际上是通过投票器决定的
    protected void addCustomAuthorities(String username,
                                        List<SimpleGrantedAuthority> authorities) {
        if (authorities != null) {
            authorities.add(new SimpleGrantedAuthority("HOLDER"));
        }
    }
}
