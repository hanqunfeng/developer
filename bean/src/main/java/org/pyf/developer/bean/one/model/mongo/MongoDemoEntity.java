package org.pyf.developer.bean.one.model.mongo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/10/17 18:15.
 */
@Entity
@Data
public class MongoDemoEntity {
    @Id
    private String id;

    private String name;

    private Integer age;
}
