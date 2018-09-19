package org.pyf.developer.bean.one.model.auth;

import lombok.Data;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/9/14 15:24.
 */

@Entity
@Table(name ="TBL_CP_ACCESS_TYPE", schema = "cp2015db")
@Lazy(value=true)
@Data
public class SystemAccessType {


    @Id
    @Column(name="ACTY_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name="ACTY_NAME")
    private String name;

    @Column(name="ACTY_CODE")
    private String code;


    @Column(name="ACTY_RESERVED")
    private boolean reserved;


}
