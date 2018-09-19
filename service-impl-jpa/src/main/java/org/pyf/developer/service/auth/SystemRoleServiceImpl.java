package org.pyf.developer.service.auth;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pyf.developer.bean.one.model.auth.QSystemRole;
import org.pyf.developer.bean.one.model.auth.SystemRole;
import org.pyf.developer.bean.one.model.auth.SystemUser;
import org.pyf.developer.dao.jpa.one.auth.SystemRoleJpaRepository;
import org.pyf.developer.service.IDaoService;
import org.pyf.developer.utils.CP_BusinessException;
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
import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/9/12 18:22.
 */

@Service(value = "systemRoleService")
@Slf4j
@CacheConfig(cacheNames = "commonCache")
@Transactional
public class SystemRoleServiceImpl implements ISystemRoleService {

    @Autowired
    SystemRoleJpaRepository systemRoleJpaRepository;

    @Autowired
    IDaoService daoService;

    @Override
    @Cacheable(key = "'SystemRoleServiceImpl.findAll'")
    public List<SystemRole> findAll() {

        Sort sort = new Sort(Sort.Direction.ASC, "id");
        return systemRoleJpaRepository.findAll(sort);
    }

    @Override
    public List<SystemRole> findAll(String... str) {
        return findAll();
    }

    @Override
    public List<SystemRole> findByPage(SystemRole example, String authname, CP_Sorter sorter, CP_Page page) {
        page.setPageSize(25);
        //排序
        Sort sort = new Sort(sorter.getSortType().equals(CP_Sorter.DESC) ? Sort.Direction.DESC : Sort.Direction.ASC, sorter.getSortName());
        //分页
        Pageable pageable = PageRequest.of(page.getIndex(), page.getPageSize(), sort);

        QSystemRole qSystemRole = QSystemRole.systemRole;


        List<BooleanExpression> predicateList = new ArrayList<>();

        if (StringUtils.isNotBlank(example.getDescription()))
            predicateList.add(qSystemRole.description.like("%" + example.getDescription() + "%"));
        if (StringUtils.isNotBlank(example.getName()))
            predicateList.add(qSystemRole.name.like("%" + example.getName() + "%"));
        if (StringUtils.isNotBlank(authname)) {
            predicateList.add(qSystemRole.authorities.any().name.like("%" + authname + "%"));
        }


        Page<SystemRole> resultPage = systemRoleJpaRepository.findAll(predicateList, pageable);

        page.setTotal(resultPage.getTotalElements());

        //如有需要，可以转成list
        List<SystemRole> results = IteratorUtils.toList(resultPage.iterator());
        return results;
    }

    @Override
    @Cacheable(key = "#roleId+''+#fields+'SystemRoleServiceImpl.findById'")
    public SystemRole findById(Long roleId, String... fields) {
        SystemRole role = systemRoleJpaRepository.findByIdNew(roleId);
        //role.getAuthorities().size();
        return role;
    }

    @Override
    public void create(SystemRole role) {

        systemRoleJpaRepository.save(role);

        String[] accessTypes = role.getAccessTypes();
        this.saveAccessTypeByRoleId(role.getId(),accessTypes);
    }

    @Override
    @CacheEvict(allEntries = true, beforeInvocation = true)
    public void update(SystemRole role) {
        SystemRole dbrole = (SystemRole) daoService.merge(role);
        if (dbrole.isReserved())
            throw new CP_BusinessException("error.role.reserved", null,
                    "The reserved role can not be edited!");
        systemRoleJpaRepository.save(dbrole);


        String[] accessTypes = role.getAccessTypes();
        this.saveAccessTypeByRoleId(role.getId(),accessTypes);

    }

    private void saveAccessTypeByRoleId(Long roleId,String[] accessTypes){
        if (accessTypes != null && accessTypes.length > 0) {
            systemRoleJpaRepository.deleteAccessTypeByRoleId(roleId);
            String[] sp = null;
            for (int i = 0;i<accessTypes.length;i++){
                sp = accessTypes[i].split("_");
                systemRoleJpaRepository.insertAuthAccess(roleId,Long.valueOf(sp[0]),Long.valueOf(sp[1]));
            }
        }
    }

    @Override
    public void delete(Long... ids) {
        if (ArrayUtils.isEmpty(ids))
            return;

        QSystemRole qSystemRole = QSystemRole.systemRole;


        List<BooleanExpression> predicateList = new ArrayList<>();
        predicateList.add(qSystemRole.id.in(ids));
        predicateList.add(qSystemRole.reserved.eq(false));

        List<SystemRole> results = systemRoleJpaRepository.findAll(predicateList);

        if (CollectionUtils.isNotEmpty(results))
            for (Object obj : results) {
                SystemRole tmpRole = (SystemRole) obj;
                if (CollectionUtils.isEmpty(tmpRole.getUsers()))
                    continue;
                for (SystemUser user : tmpRole.getUsers()) {
                    user.getRoles().remove(tmpRole);
                }
            }

        systemRoleJpaRepository.deleteAll(results);
    }


    @Override
    public String getAccessAuthShowIds(Long roleId) {
        StringBuffer sb = new StringBuffer();
        List<String> list = systemRoleJpaRepository.getAccessAuthShowIds(roleId);
        if(list!=null&&list.size()>0){
            for(String s:list){
                sb.append(s+",");
            }
        }
        return sb.toString();
    }
}
