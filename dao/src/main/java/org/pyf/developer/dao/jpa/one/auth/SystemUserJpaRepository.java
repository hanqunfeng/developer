package org.pyf.developer.dao.jpa.one.auth;


import org.pyf.developer.bean.one.model.auth.SystemUser;
import org.pyf.developer.dao.jpa.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/8/31 15:18.
 */


public interface SystemUserJpaRepository extends BaseJpaRepository<SystemUser,String> {

    @Query(value = "select concat(a.auth_code,'_',s.acty_code) from tbl_cp_auth_access t,tbl_cp_authority a,tbl_cp_access_type s,tbl_cp_userrole u where t.ROLE_ID = u.USRO_ROLEID_FK and t.AUTH_ID = a.auth_id and t.ACTY_ID = s.acty_id and u.USRO_USERID_FK = :userId", nativeQuery = true)
    List<String> getAccessAuthtype(@Param("userId") String  userId);
}
