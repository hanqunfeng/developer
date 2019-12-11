package org.pyf.developer.service.auth;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pyf.developer.bean.one.model.auth.QSystemUser;
import org.pyf.developer.bean.one.model.auth.SystemUser;
import org.pyf.developer.dao.jpa.one.auth.SystemUserJpaRepository;
import org.pyf.developer.utils.*;
import org.pyf.developer.utils.page.CP_Page;
import org.pyf.developer.utils.page.CP_Sorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/9/12 18:23.
 */

@Service(value = "systemUserService")
@Slf4j
@CacheConfig(cacheNames = "commonCache")
@Transactional
public class SystemUserServiceImpl implements ISystemUserService{


    @Autowired
    private MessageSource messageSource;

    @Autowired
    SystemUserJpaRepository systemUserJpaRepository;

    private String[] reservedUsers;

    private static final Map<String, String> m = new ConcurrentHashMap<String, String>();

    public final void setReservedUsers(String[] reservedUsers) {
        if (this.reservedUsers != null) {
            throw new IllegalArgumentException(
                    "The reserved users can not be changed!");
        } else {
            String[] tmp = new String[]{""};
            if (!ArrayUtils.isEmpty(reservedUsers)) {
                for (String item : reservedUsers) {
                    tmp = (String[]) ArrayUtils.add(tmp,
                            CP_CryptUtils.encryptToMD5(item));
                }
            }
            this.reservedUsers = tmp;
        }
    }


    @Override
    @Cacheable(key = "SystemUserServiceImpl.findAll")
    public List<SystemUser> findAll() {
        return systemUserJpaRepository.findAll();
    }

    @Override
    public List<SystemUser> findByPage(SystemUser user, CP_Sorter sorter, CP_Page page, String... str) {

        page.setPageSize(25);
        //排序
        Sort sort = new Sort(sorter.getSortType().equals(CP_Sorter.DESC) ? Sort.Direction.DESC : Sort.Direction.ASC, sorter.getSortName());
        //分页
        Pageable pageable = PageRequest.of(page.getIndex(), page.getPageSize(), sort);

        QSystemUser qSystemUser = QSystemUser.systemUser;

        List<BooleanExpression> predicateList = new ArrayList<>();
        if (StringUtils.isNotBlank(user.getAddress())) {
            predicateList.add(qSystemUser.address.like("%" + user.getAddress() + "%"));
        }
        if (StringUtils.isNotBlank(user.getEmail())) {
            predicateList.add(qSystemUser.email.like("%" + user.getEmail() + "%"));
        }
        if (StringUtils.isNotBlank(user.getMobileNumber())) {
            predicateList.add(qSystemUser.mobileNumber.like("%" + user.getMobileNumber() + "%"));
        }
        if (StringUtils.isNotBlank(user.getName())) {
            predicateList.add(qSystemUser.name.like("%" + user.getName() + "%"));
        }
        if (StringUtils.isNotBlank(user.getPhoneNumber())) {
            predicateList.add(qSystemUser.phoneNumber.like("%" + user.getPhoneNumber() + "%"));
        }
        if (StringUtils.isNotBlank(user.getUserId())) {
            predicateList.add(qSystemUser.userId.like("%" + user.getUserId() + "%"));
        }

        Page<SystemUser> resultPage = systemUserJpaRepository.findAll(predicateList, pageable);

        page.setTotal(resultPage.getTotalElements());

        //如有需要，可以转成list
        List<SystemUser> results = IteratorUtils.toList(resultPage.iterator());
        systemUserJpaRepository.lazyInitialize(SystemUser.class,results,str);
        return results;
    }

    @Override
    public List<SystemUser> findByPage(SystemUser example, CP_Sorter sorter, CP_Page page) {
        return this.findByPage(example, sorter, page,new String[]{});
    }

    @Override
    @Cacheable(key = "#userId+''+#fields+'SystemUserServiceImpl.findById'")
    public SystemUser findById(String userId, String... fields) {
        SystemUser user = systemUserJpaRepository.findByIdNew(userId);
        systemUserJpaRepository.lazyInitialize(user,fields);
        return user;
    }

    @Override
    @CacheEvict(allEntries = true, beforeInvocation = true)
    public void modifyUserRole(SystemUser user) {
        if (user == null || StringUtils.isBlank(user.getUserId())) {
            throw new CP_BusinessException("error.nouser.choosed", null,
                    "The system user is null or the user id is a blank string!");
        }

        SystemUser dbuser = systemUserJpaRepository.findByIdNew(user.getUserId());
        if (dbuser == null) {
            throw new CP_BusinessException("error.user.invalid",
                    new Object[]{user.getUserId()}, "The user id "
                    + user.getUserId() + " is invalid!");
        }
        dbuser.setRoles(user.getRoles());
        systemUserJpaRepository.save(dbuser);
    }

    @Override
    public boolean isReserved(String userId) {

        if (StringUtils.isNotBlank(userId)) {
            log.error("The parameter of this method:isReserved, can not be a empty string!!");
            return ArrayUtils.contains(reservedUsers,
                    CP_CryptUtils.encryptToMD5(userId));
        } else {
            return false;
        }
    }

    @Override
    @CacheEvict(allEntries = true, beforeInvocation = true)
    public int insert(SystemUser user) {

        systemUserJpaRepository.save(user);
        return 0;
    }

    @Override
    @CacheEvict(allEntries = true, beforeInvocation = true)
    public int update(SystemUser user) {
        SystemUser dbuser = systemUserJpaRepository.findByIdNew(user.getUserId());
        if (dbuser == null) {
            throw new CP_BusinessException("error.user.invalid",
                    new Object[]{user.getUserId()}, "The user id "
                    + user.getUserId() + " is invalid!");
        }

        dbuser.setAddress(user.getAddress());
        dbuser.setEmail(user.getEmail());
        dbuser.setMobileNumber(user.getMobileNumber());
        dbuser.setName(user.getName());
        if (user.getPassword() != null && !"".equals(user.getPassword())) {
            dbuser.setPassword(user.getPassword());
        }
        dbuser.setPhoneNumber(user.getPhoneNumber());
        dbuser.setStatus(user.getStatus());


        dbuser.setRoles(user.getRoles());

        systemUserJpaRepository.save(dbuser);
        return 0;
    }

    @Override
    @CacheEvict(allEntries = true, beforeInvocation = true)
    public SystemUser updatePS(SystemUser user, String newPassword) {
        SystemUser dbuser = systemUserJpaRepository.findByIdNew(user.getUserId());
        dbuser.setPassword(newPassword);
        systemUserJpaRepository.save(dbuser);

        return dbuser;

    }

    @Override
    @CacheEvict(allEntries = true, beforeInvocation = true)
    public void delete(String... ids) {
        if (ArrayUtils.isEmpty(ids)) {
            return;
        }

        QSystemUser qSystemUser = QSystemUser.systemUser;
        systemUserJpaRepository.deleteAll(systemUserJpaRepository.findAll(qSystemUser.userId.in(ids)));


    }

    @Override
    @Cacheable(key = "#userId+'SystemUserServiceImpl.getSecurityAuthorize'")
    public List<String> getSecurityAuthorize(String userId) {
        return systemUserJpaRepository.getAccessAuthtype(userId);
    }


    @Override
    @CacheEvict(allEntries = true, beforeInvocation = true)
    public Integer handleResetPassword(String encode,String newpassword,String encodePassword) {
        String data = m.remove(encode);
        if(data==null){
            return 5;
        }else if((System.currentTimeMillis()-Long.valueOf(data.split("_")[1]))>1000*60*10){
            return 6;
        }
        String su;
        try {
            su = CP_CryptUtils_DES.decrypt(encode,data.split("_")[0]);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            log.error("",e);
            return 9;
        }
        if(su==null){
            return 7;
        }
        boolean b = resetPassword(su,newpassword,encodePassword);
        if(!b){
            log.info("忘记密码功能,新密码邮件发送失败:"+su);
            return 8;
        }
        return 101;
    }


    private boolean resetPassword(String id,String newpassword,String encodePassword){
        String newPassword = newpassword;
        SystemUser dbuser = systemUserJpaRepository.findByIdNew(id);

        dbuser.setPassword(encodePassword);


        systemUserJpaRepository.save(dbuser);

        String content = getMessage("email.reset.password",new Object[]{dbuser.getName(),dbuser.getUserId(),
                newPassword,
                CP_PropertiesUtils.get("systemAddress"),
                CP_PropertiesUtils.get("maintainer")});
        CP_SendMailManager sm = new CP_SendMailManager();
        sm.setSubject(getMessage("email.checker.reminder.subject"));
        sm.setBody(content);
        sm.setTo(dbuser.getEmail());
        return sm.sendout();
    }

    @Override
    public Integer handleForgotPasswordEmail(SystemUser su) {
        log.info("SystemUserServiceImpl handleForgotPassword begin");
        SystemUser user =systemUserJpaRepository.findByIdNew(su.getUserId());

        if(user==null){return 1;}
        if(!su.getName().equals(user.getName())){
            return 2;
        }
        su = user;
        String encode=null;
        String uuid = CP_IDUtil.getUUID();
        try {
            encode= CP_CryptUtils_DES.encrypt(su.getUserId(),uuid);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            log.error("",e);
            return 5;
        }
        String data  = m.get(encode);
        if(null == data){
            m.put(encode, uuid+"_"+System.currentTimeMillis());
        }else{
            if((System.currentTimeMillis()-Long.valueOf(data.split("_")[1]))>1000*60*10){
                return 3;
            }else{
                m.put(encode, uuid+"_"+System.currentTimeMillis());
            }
        }

        String content = null;
        try {
            content = getMessage("email.reset.forgotpassword",new Object[]{su.getName(), CP_DateUtils.formatDate(new Date(),CP_DateUtils.DEFAULT_TIME_FORMAT), CP_PropertiesUtils.get("systemAddress")+"/resetPassword.do?userId="+ URLEncoder.encode(encode,"utf-8"),
                    CP_PropertiesUtils.get("maintainer")});
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        CP_SendMailManager sm = new CP_SendMailManager();
        sm.setSubject(getMessage("email.checker.reminder.subject"));
        sm.setBody(content);
        sm.setTo(user.getEmail());

        Integer result = 0;
        if(sm.sendout()){
            result = 100;
        }else{
            m.remove(encode);
            result=4;
        }
        log.info("SystemUserServiceImpl handleForgotPassword end");
        return result;
    }

    private  String getMessage(String key){
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    private  String getMessage(String key,Object[] args){
        return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }


}
