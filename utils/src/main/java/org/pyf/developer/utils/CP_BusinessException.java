/*
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                   *
 ****************************************************************************
 * 源文件名:  CP_BusinessException.java
 * 功能: cpframework框架													   
 * 版本:	@version 1.0	                                                                   
 * 编制日期: 2013年12月26日 下午6:23:04 						    						                                        
 * 修改历史: (主要历史变动原因及说明)		
 * YYYY-MM-DD |    Author      |	 Change Description		      
 * 2013年12月26日    |    hanqunfeng     |     Created 
 */
package org.pyf.developer.utils;

/**
 * Description: <业务异常类>. <br>
 * <p>
 * <但需要对外主动抛出异常时，可以抛出该异常>
 * </p>
 * Makedate:2013年12月30日 上午10:22:15
 * 
 * @author hanqunfeng
 * @version V1.0
 */
public class CP_BusinessException extends RuntimeException {
	private static final long serialVersionUID = -5005501451149627815L;

	/**
	 * @Fields key : TODO(国际化消息资源文件中对应的message key值)
	 */
	private String key;

	/**
	 * @Fields messageArguments : TODO(国际化资源需要的参数数组)
	 */
	private Object[] messageArguments;

	public CP_BusinessException(String defaultMessage) {
		super(defaultMessage);
	}

	/**
	 * 构造方法. <br>
	 * 
	 * @param key
	 *            国际化资源message key
	 * @param messageArguments
	 *            国际化资源需要的参数数组
	 * @param defaultMessage
	 *            默认异常描述信息
	 */
	public CP_BusinessException(String key, Object[] messageArguments,
                                String defaultMessage) {
		super(defaultMessage);
		this.key = key;
		this.messageArguments = messageArguments;
	}

	/**
	 * 描述 : <获取国际化资源message key>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @return 国际化资源message key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * 描述 : <获取国际化资源所需要的参数数组>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @return 国际化资源所需要的参数数组
	 */
	public Object[] getMessageArguments() {
		return messageArguments;
	}
}
