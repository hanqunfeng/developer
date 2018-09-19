/*
* COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                   *
****************************************************************************
* 源文件名: Write.java 														       
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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;


@SuppressWarnings("serial")
public class Write extends TagSupport {
	private String po;//对象

	private String showtext;//显示值属属性名称
	
	private String truevalue;//真是值属性名称
	
	private String value;//真实值
	
	private String type = "string";//显示类型
	

	



	@SuppressWarnings("rawtypes")
	public int doEndTag() throws JspException {

		
		try {
			if(value==null || value.equals("")) return EVAL_PAGE ;
			
			
			
			String hql="from "+po+" a ";
			
	        if(type!=null&&!type.equals(""))
	            {
	                hql+=" where a."+truevalue+"="+value;
	            }
			
			//数据字典
			String str = null;


			IDaoService daoService = (IDaoService) ApplicationContextProvider.getBean(IDaoService.class);

			List list = daoService.getListByHql(hql);
			
			
			if(list!=null&&list.size()>0){
				if(list.size()==1){
					str = BeanUtils.getProperty(list.get(0), showtext);
				}else{
					for(int i =0;i<list.size();i++){
						str += BeanUtils.getProperty(list.get(i), showtext)+",";
					}
				}
			}
			

			if(str!=null){
				
				
                if(type!=null&&(type.equalsIgnoreCase("html")))
                {
                    str=str.replace("<","&lt;");
                    str=str.replace(">","&gt;");
                    str=str.replace("\n","<br>");
                    str=str.replace(" ","&nbsp;");
                    str=str.replace("\"","&quot;");
                    str=str.replace((char)0x0D,' ');
                }
                else if(type!=null&&(type.equalsIgnoreCase("string")))
                {
                    //str=str.replace("","\\\"");
                    str=str.replace((char)0x0D,' ');
                    str=str.replace("\n"," ");
                }

                this.pageContext.getOut().print(str);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
		
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




	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}


	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	




	



}