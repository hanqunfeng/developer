package org.pyf.developer.service.auth;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pyf.developer.bean.one.model.auth.QSystemAccessType;
import org.pyf.developer.bean.one.model.auth.SystemAccessType;
import org.pyf.developer.dao.jpa.one.auth.SystemAccessTypeJpaRepository;
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
import java.util.Iterator;
import java.util.List;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/9/14 15:33.
 */

@Service(value = "systemAccessTypeService")
@Slf4j
@CacheConfig(cacheNames = "commonCache")
@Transactional
public class SystemAccessTypeServiceImpl implements ISystemAccessTypeService {

    @Autowired
    SystemAccessTypeJpaRepository systemAccessTypeJpaRepository;

    @Override
    @CacheEvict(allEntries = true, beforeInvocation = true)
    public void create(SystemAccessType... type) {
        if (!ArrayUtils.isEmpty(type)) {
            for (SystemAccessType item : type) {
                systemAccessTypeJpaRepository.save(item);
            }
        }
    }

    @Override
    @CacheEvict(allEntries = true, beforeInvocation = true)
    public void update(SystemAccessType type) {
        systemAccessTypeJpaRepository.save(type);
    }

    @Override
    @CacheEvict(allEntries = true, beforeInvocation = true)
    public void delete(Long... ids) {
        if (ArrayUtils.isEmpty(ids)) {
            return;
        }

        QSystemAccessType qSystemAccessType = QSystemAccessType.systemAccessType;
        //封装查询条件
        BooleanExpression booleanExpression = qSystemAccessType.id.in(ids).and(qSystemAccessType.reserved.eq(false));
        Iterator<SystemAccessType> iterator = systemAccessTypeJpaRepository.findAll(booleanExpression).iterator();
        //如有需要，可以转成list
        List<SystemAccessType> results = IteratorUtils.toList(iterator);


        // 删除记录
        systemAccessTypeJpaRepository.deleteAll(results);
    }

    @Override
    @Cacheable(key = "'SystemAccessTypeServiceImpl.findAll'")
    public List<SystemAccessType> findAll() {
        return systemAccessTypeJpaRepository.findAll();
    }

    @Override
    @Cacheable(key = "#typeId+'SystemAccessTypeServiceImpl.findById'")
    public SystemAccessType findById(Long typeId) {
        return systemAccessTypeJpaRepository.findByIdNew(typeId);
    }

    @Override
    @Cacheable(key = "#ids+'SystemAccessTypeServiceImpl.findByIdArray'")
    public List<SystemAccessType> findByIdArray(Long... ids) {
        QSystemAccessType qSystemAccessType = QSystemAccessType.systemAccessType;
        //封装查询条件
        BooleanExpression booleanExpression = qSystemAccessType.id.in(ids);
        Iterator<SystemAccessType> iterator = systemAccessTypeJpaRepository.findAll(booleanExpression).iterator();
        //如有需要，可以转成list
        List<SystemAccessType> results = IteratorUtils.toList(iterator);
        return results;
    }

    @Override
    public List<SystemAccessType> findByPage(SystemAccessType example, CP_Sorter sorter, CP_Page page) {
        page.setPageSize(25);
        //排序
        Sort sort = new Sort(sorter.getSortType().equals(CP_Sorter.DESC) ? Sort.Direction.DESC : Sort.Direction.ASC, sorter.getSortName());
        //分页
        Pageable pageable = PageRequest.of(page.getIndex(), page.getPageSize(), sort);

        QSystemAccessType qSystemAccessType = QSystemAccessType.systemAccessType;

        List<BooleanExpression> predicateList = new ArrayList<>();

        if (StringUtils.isNotBlank(example.getName())) {
            predicateList.add(qSystemAccessType.name.like("%" + example.getName() + "%"));
        }
        if (StringUtils.isNotBlank(example.getCode())) {
            predicateList.add(qSystemAccessType.code.like("%" + example
                    .getCode() + "%"));
        }


        Page<SystemAccessType> resultPage = systemAccessTypeJpaRepository.findAll(predicateList, pageable);

        page.setTotal(resultPage.getTotalElements());

        //如有需要，可以转成list
        List<SystemAccessType> results = IteratorUtils.toList(resultPage.iterator());
        return results;
    }
}
