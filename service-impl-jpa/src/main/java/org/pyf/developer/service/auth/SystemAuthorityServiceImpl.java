package org.pyf.developer.service.auth;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pyf.developer.bean.one.model.auth.QSystemAuthority;
import org.pyf.developer.bean.one.model.auth.QSystemUrlResource;
import org.pyf.developer.bean.one.model.auth.SystemAuthority;
import org.pyf.developer.bean.one.model.auth.SystemUrlResource;
import org.pyf.developer.dao.jpa.one.auth.SystemAuthorityJpaRepository;
import org.pyf.developer.dao.jpa.one.auth.SystemUrlResourceJpaRepository;
import org.pyf.developer.service.IDaoService;
import org.pyf.developer.utils.page.CP_Page;
import org.pyf.developer.utils.page.CP_Sorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * 系统权限业务功能逻辑实现类
 */
@Service(value = "systemAuthorityService")
@Slf4j
@CacheConfig(cacheNames = "commonCache")
@Transactional
public class SystemAuthorityServiceImpl implements ISystemAuthorityService {

    private static final String AUTH_PREFIX = "AUTH_";

    @Autowired
    SystemAuthorityJpaRepository systemAuthorityJpaRepository;

    @Autowired
    SystemUrlResourceJpaRepository systemUrlResourceJpaRepository;

    @Autowired
    IDaoService daoService;

    @Override
    @CacheEvict(allEntries = true, beforeInvocation = true)
    public void create(SystemAuthority... auth) {

        if (!ArrayUtils.isEmpty(auth)) {
            for (SystemAuthority item : auth) {
                systemAuthorityJpaRepository.save(item);
            }
        }
    }

    @Override
    @CacheEvict(allEntries = true, beforeInvocation = true)
    public void delete(Long... ids) {
        if (ArrayUtils.isEmpty(ids))
            return;

        QSystemAuthority qSystemAuthority = QSystemAuthority.systemAuthority;
        //封装查询条件
        BooleanExpression booleanExpression = qSystemAuthority.id.in(ids).and(qSystemAuthority.reserved.eq(false));
        Iterator<SystemAuthority> iterator = systemAuthorityJpaRepository.findAll(booleanExpression).iterator();
        //如有需要，可以转成list
        List<SystemAuthority> results = IteratorUtils.toList(iterator);
        // 解除角色与权限映射关系
        if (CollectionUtils.isNotEmpty(results)) {
            for (Object obj : results) {
                SystemAuthority tmpAuth = (SystemAuthority) obj;
                if (CollectionUtils.isEmpty(tmpAuth.getRoles()))
                    continue;

                tmpAuth.setRoles(null);
            }
        }




        // 删除记录
        systemAuthorityJpaRepository.deleteAll(results);
    }

    @Override
    @Cacheable(key = "'SystemAuthorityServiceImpl.findAll'")
    public List<SystemAuthority> findAll() {
        log.info("Loading all the system authorities!");
        return systemAuthorityJpaRepository.findAll();
    }

    @Override
    @Cacheable(key = "#authId+'SystemAuthorityServiceImpl.findById'")
    public SystemAuthority findById(Long authId) {
        return systemAuthorityJpaRepository.findByIdNew(authId);
    }

    @Override
    @Cacheable(key = "#ids+'SystemAuthorityServiceImpl.findByIdArray'")
    public List<SystemAuthority> findByIdArray(Long... ids) {
        QSystemAuthority qSystemAuthority = QSystemAuthority.systemAuthority;
        //封装查询条件
        BooleanExpression booleanExpression = qSystemAuthority.id.in(ids);
        Iterator<SystemAuthority> iterator = systemAuthorityJpaRepository.findAll(booleanExpression).iterator();
        //如有需要，可以转成list
        List<SystemAuthority> results = IteratorUtils.toList(iterator);
        return results;
    }

    @Override
    public List<SystemAuthority> findByPage(SystemAuthority example, CP_Sorter sorter, CP_Page page) {
        page.setPageSize(25);
        //排序
        Sort sort = new Sort(sorter.getSortType().equals(CP_Sorter.DESC) ? Sort.Direction.DESC : Sort.Direction.ASC, sorter.getSortName());
        //分页
        Pageable pageable = PageRequest.of(page.getIndex(), page.getPageSize(), sort);

        QSystemAuthority qSystemAuthority = QSystemAuthority.systemAuthority;

        List<BooleanExpression> predicateList = new ArrayList<>();

        if (StringUtils.isNotBlank(example.getEntrance()))
            predicateList.add(qSystemAuthority.entrance.like("%" + example.getEntrance() + "%"));
        if (StringUtils.isNotBlank(example.getDescription()))
            predicateList.add(qSystemAuthority.description.like("%" + example
                    .getDescription() + "%"));
        if (StringUtils.isNotBlank(example.getName()))
            predicateList.add(qSystemAuthority.name.like("%" + example.getName() + "%"));


        Page<SystemAuthority> resultPage = systemAuthorityJpaRepository.findAll(predicateList, pageable);

        page.setTotal(resultPage.getTotalElements());

        //如有需要，可以转成list
        List<SystemAuthority> results = IteratorUtils.toList(resultPage.iterator());
        return results;
    }

    @Override
    @Cacheable(key = "'SystemAuthorityServiceImpl.getAuthoritiesAsPrefixAndId'")
    public Set<String> getAuthoritiesAsPrefixAndId() {

        List<SystemAuthority> auths = systemAuthorityJpaRepository.findAll();
        Set<String> results = null;
        if (auths != null && auths.size() > 0) {
            results = new HashSet<String>();
            for (SystemAuthority auth : auths) {
                results.add(AUTH_PREFIX + auth.getId());
            }
        }
        return results;
    }

    @Override
    public String getAuthorityPrefix() {
        return AUTH_PREFIX;
    }

    @Override
    @Cacheable(key = "'SystemAuthorityServiceImpl.getUrlAuthorities'")
    public Map<String, Set<String>> getUrlAuthorities() {

        List<SystemUrlResource> resources = systemUrlResourceJpaRepository.findAll();
        if (CollectionUtils.isEmpty(resources))
            return null;

        Map<String, Set<String>> results = new HashMap<String, Set<String>>();
        for (SystemUrlResource resource : resources) {
            Set<String> auths = new HashSet<String>();
            if (results.containsKey(resource.getUrlPattern()))
                auths = (Set<String>) results.get(resource.getUrlPattern());
            auths = auths == null ? new HashSet<String>() : auths;
            auths.add(AUTH_PREFIX + resource.getAuthority().getId());
            results.put(resource.getUrlPattern(), auths);
        }

        return results;
    }

    @Override
    @CacheEvict(allEntries=true,beforeInvocation=true)
    public void update(SystemAuthority auth) {
        QSystemUrlResource qSystemUrlResource = QSystemUrlResource.systemUrlResource;
        BooleanExpression booleanExpression = qSystemUrlResource.authority.id.eq(auth.getId());
        systemUrlResourceJpaRepository.deleteInBatch(systemUrlResourceJpaRepository.findAll(booleanExpression));
        daoService.merge(auth);
    }

    @Override
    @Cacheable(key="#userName+'SystemAuthorityServiceImpl.getAuthorityIdByUserId'")
    public List<Long> getAuthorityIdByUserId(String userName) {
        String s = "select  auth.id " +
                " from SystemAuthority auth join auth.roles as role join role.users as user " +
                " where user.userId='"+userName+"'";
        List<Long> l = (List<Long>) daoService.getListByHql(s);
        return l;
    }
}
