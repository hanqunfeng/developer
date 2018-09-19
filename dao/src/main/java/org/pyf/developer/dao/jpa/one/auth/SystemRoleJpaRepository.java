package org.pyf.developer.dao.jpa.one.auth;

import org.pyf.developer.bean.one.model.auth.SystemRole;
import org.pyf.developer.dao.jpa.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/9/11 17:18.
 */


public interface SystemRoleJpaRepository extends BaseJpaRepository<SystemRole, Long> {

    @Query(value = "delete from tbl_cp_auth_access where ROLE_ID = ?1", nativeQuery = true)
    @Modifying
    int deleteAccessTypeByRoleId(Long roleId);


    @Query(value = "insert into tbl_cp_auth_access(ROLE_ID,AUTH_ID,ACTY_ID) value(?1,?2,?3)", nativeQuery = true)
    @Modifying
    int insertAuthAccess(Long roleId, Long authId, Long actyId);

    @Query(value = "select concat(AUTH_ID,'_',ACTY_ID) from tbl_cp_auth_access where ROLE_ID = ?1", nativeQuery = true)
    List<String> getAccessAuthShowIds(Long roleId);


}
