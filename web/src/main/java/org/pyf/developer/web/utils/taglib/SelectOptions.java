/*
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                   *
 ****************************************************************************
 * 源文件名: SelectOptions.java
 * 功能: （描述文件功能）
 * 版本:	@version 1.0
 * 编制日期: 2009-12-15
 * 说明: （描述使用文件功能时的制约条件）
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2009-12-15   |  hanqunfeng    |  Created
 */
package org.pyf.developer.web.utils.taglib;

import org.apache.commons.beanutils.BeanUtils;
import org.pyf.developer.service.IDaoService;
import org.pyf.developer.web.utils.context.ApplicationContextProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.BindStatus;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

/**
 * Description: select选择标签 .<br>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2009<br>
 * Company:www.netqin.com <br>
 * Makedate:2009-12-15 上午10:58:16<br>
 *
 * @author hanqunfeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class SelectOptions extends TagSupport {
    private static final Logger logger = LoggerFactory.getLogger(SelectOptions.class);

    private String po;//对象

    private String showtext;//显示值

    private String truevalue;//真实值

    private String filter;//过滤条件，别名用a表示

    private String order;//排序

    private String value;//默认值

    public int doEndTag() throws JspException {

        StringBuffer outStr = new StringBuffer();
        try {
//			Enumeration e = pageContext.getAttributeNamesInScope(pageContext.PAGE_SCOPE);
//			
//			while(e.hasMoreElements()){
//				Object el = e.nextElement();
//				System.out.println((String)el+":");
//				System.out.println(pageContext.getAttribute((String)el));
//				System.out.println("<br>");
//			}
//			

            Object selectTagValue = null;
            BindStatus bs = (BindStatus) pageContext.getAttribute("org.springframework.web.servlet.tags.form.SelectTag.listValue");
            if (bs != null) {
                selectTagValue = bs.getValue();
                if (selectTagValue != null) value = selectTagValue.toString();
            }


            IDaoService daoService = (IDaoService) ApplicationContextProvider.getBean(IDaoService.class);


            String hql = "";
            if (po.indexOf("from") != -1) {//自行编写hql
                hql += po;
                if (order != null && !order.equals("")) {
                    hql += " order by " + order;
                }
            } else {
                hql += "from " + po + " a ";

                if (filter != null && !filter.equals("")) {
                    hql += " where " + filter;
                }
                if (order != null && !order.equals("")) {
                    hql += " order by a." + order;
                }
            }


            logger.info(hql);
            List<?> list = null;
            try {
                list = daoService.getListByHql(hql);
            } catch (Exception e) {
                logger.info(e.getMessage());
                e.printStackTrace();
            }

            if (list != null) {
                logger.info("list.size===" + list.size());
                int num = list.size();
                for (int i = 0; i < num; i++) {
                    outStr.append("<option value=\"");
                    outStr.append(BeanUtils.getProperty(list.get(i), truevalue));
                    outStr.append("\"");
                    if (value != null) {
                        if (value.equals(BeanUtils.getProperty(list.get(i),
                                truevalue)))
                            outStr.append(" selected");
                    }
                    outStr.append(">");
                    outStr.append(BeanUtils.getProperty(list.get(i), showtext));
                    outStr.append("</option>\n");
                }
            }
            this.pageContext.getOut().print(outStr.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        value = null;
        return EVAL_PAGE;
    }

    /**
     * @return the po
     */
    public String getPo() {
        return po;
    }

    /**
     * @param po the po to set
     */
    public void setPo(String po) {
        this.po = po;
    }

    /**
     * @return the showtext
     */
    public String getShowtext() {
        return showtext;
    }

    /**
     * @param showtext the showtext to set
     */
    public void setShowtext(String showtext) {
        this.showtext = showtext;
    }

    /**
     * @return the truevalue
     */
    public String getTruevalue() {
        return truevalue;
    }

    /**
     * @param truevalue the truevalue to set
     */
    public void setTruevalue(String truevalue) {
        this.truevalue = truevalue;
    }

    /**
     * @return the filter
     */
    public String getFilter() {
        return filter;
    }

    /**
     * @param filter the filter to set
     */
    public void setFilter(String filter) {
        this.filter = filter;
    }

    /**
     * @return the order
     */
    public String getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(String order) {
        this.order = order;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

}
