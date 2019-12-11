/*
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                   *
 ****************************************************************************
 * 源文件名:  org.cpframework.dao.page.Sorter.java 													       
 * 功能: cpframework框架													   
 * 版本:	@version 1.0	                                                                   
 * 编制日期: 2014-1-13 上午10:14:53 						    						                                        
 * 修改历史: (主要历史变动原因及说明)		
 * YYYY-MM-DD |    Author         |	 Change Description		      
 * 2014-1-13    |    gaowenming     |     Created 
 */
package org.pyf.developer.utils.page;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;

/**
 * Description: <排序类>. <br>
 * <p>
 * <使用说明>
 * </p>
 * Makedate:2014-1-13 上午10:14:53
 * 
 * @author gaowenming
 * @version V1.0
 */
public class CP_Sorter {
	public static final String DESC = "desc";

	public static final String ASC = "asc";

	/**
	 * @Fields sortName : TODO(排序的列名称)
	 */
	private String sortName;

	/**
	 * @Fields sortType : TODO(排序类型：升序、降序)
	 */
	private String sortType;

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public Order getOrder() {
		if (StringUtils.isBlank(sortName)) {
            return null;
        }
		if (DESC.equalsIgnoreCase(sortType)) {
            return Order.desc(sortName);
        } else {
            return Order.asc(sortName);
        }
	}

}
