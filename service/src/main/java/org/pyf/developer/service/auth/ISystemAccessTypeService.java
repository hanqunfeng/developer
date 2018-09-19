package org.pyf.developer.service.auth;

import org.pyf.developer.bean.one.model.auth.SystemAccessType;
import org.pyf.developer.utils.page.CP_Page;
import org.pyf.developer.utils.page.CP_Sorter;

import java.util.List;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/9/14 15:29.
 */


public interface ISystemAccessTypeService {

    /**
     * 创建访问类型
     *
     * @param type
     *            访问类型列表
     */
    public void create(SystemAccessType... type);

    /**
     * 更新访问类型
     *
     * @param type
     *            更新访问类型
     */
    public void update(SystemAccessType type);

    /**
     * 删除访问类型
     *
     * @param ids
     *            访问类型ID
     */
    public void delete(Long... ids);

    /**
     * 查询所有访问类型
     *
     * @return 所有访问类型列表
     */
    public List<SystemAccessType> findAll();

    /**
     * 根据访问类型ID获取访问类型详细信息
     *
     * @param typeId
     *            访问类型ID
     * @return 访问类型详细信息
     */
    public SystemAccessType findById(Long typeId);

    /**
     * 根据访问类型ID数据获取相应的访问类型列表
     *
     * @param ids
     *            访问类型数组
     * @return 访问类型列表
     */
    public List<SystemAccessType> findByIdArray(Long... ids);

    /**
     * 分页查询
     *
     * @param example
     *            查询条件
     * @param sorter
     *            排序对象
     * @param page
     *            分页对象
     * @return 访问类型列表
     */
    public List<SystemAccessType> findByPage(SystemAccessType example,
                                            CP_Sorter sorter, CP_Page page);
}
