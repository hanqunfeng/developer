/*******************************************************************************
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                      *
 *******************************************************************************
 * 源文件名: SystemRolesDataEditor.java													       
 * 功能: 
 * 版本:	1.0	                                                                   
 * 编制日期: 2009-5-26
 * 说明:
 * 修改历史: (主要历史变动原因及说明)					      
 * YYMMDD      |     Author    |    Change Description 
 * 2014-03-15    sunchengqi         Created
 *******************************************************************************/
package org.pyf.developer.web.utils.initBinder;


import org.apache.commons.lang3.ArrayUtils;

import java.beans.PropertyEditorSupport;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * 角色列表绑定处理类
 *
 * @author sunchengqi
 * @version 1.0
 */
public class CustomDataEditor extends PropertyEditorSupport {
    /*
     * (non-Javadoc)
     *
     * @see java.beans.PropertyEditorSupport#setValue(java.lang.Object)
     */
    private Class<?> clzz;
    private Class<?> attrClass;
    private String attr;

    /**
     * @param clzz      对象类型
     * @param attr      属性名字
     * @param attrClass 属性的类型
     */
    public CustomDataEditor(Class<?> clzz, String attr, Class<?> attrClass) {
        this.clzz = clzz;
        this.attrClass = attrClass;
        this.attr = attr;
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof String[]) {
            String[] roleids = (String[]) value;
            Set set = null;
            if (!ArrayUtils.isEmpty(roleids)) {
                set = new HashSet();
                for (String id : roleids) {
                    try {
                        Object obj = clzz.newInstance();
                        String targetMethod = "set" + upperFirstWord(attr);
                        Method method;

                        method = clzz.getDeclaredMethod(targetMethod, attrClass);

                        if (attrClass.getName().endsWith("java.lang.String")) {
                            method.invoke(obj, id);
                        } else if (attrClass.getName().endsWith("java.lang.Long")) {
                            method.invoke(obj, Long.valueOf(id));
                        } else if (attrClass.getName().endsWith("java.lang.Integer")) {
                            method.invoke(obj, Integer.valueOf(id));
                        }

                        set.add(obj);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                }
            }
            super.setValue(set);
        } else if (value instanceof String) {
            Set set = new HashSet();
            try {
                Object obj = clzz.newInstance();
                String targetMethod = "set" + upperFirstWord(attr);
                Method method;

                method = clzz.getDeclaredMethod(targetMethod, attrClass);
                if (attrClass.getName().endsWith("java.lang.String")) {
                    method.invoke(obj, value);
                } else if (attrClass.getName().endsWith("java.lang.Long")) {
                    method.invoke(obj, Long.valueOf((String) value));
                } else if (attrClass.getName().endsWith("java.lang.Integer")) {
                    method.invoke(obj, Integer.valueOf((String) value));
                }
                set.add(obj);
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            super.setValue(set);
        } else {
            super.setValue(value);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
     */
    @Override
    @SuppressWarnings("rawtypes")
    public void setAsText(String text) {
        Set set = new HashSet();
        try {
            Object obj = clzz.newInstance();
            String targetMethod = "set" + upperFirstWord(attr);
            Method method;

            method = clzz.getDeclaredMethod(targetMethod, attrClass);
            if (attrClass.getName().endsWith("java.lang.String")) {
                method.invoke(obj, text);
            } else if (attrClass.getName().endsWith("java.lang.Long")) {
                method.invoke(obj, Long.valueOf((String) text));
            } else if (attrClass.getName().endsWith("java.lang.Integer")) {
                method.invoke(obj, Integer.valueOf((String) text));
            }
            set.add(obj);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        super.setValue(set);
    }

    private String upperFirstWord(String str) {
        StringBuffer sb = new StringBuffer(str);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        return sb.toString();
    }
}
