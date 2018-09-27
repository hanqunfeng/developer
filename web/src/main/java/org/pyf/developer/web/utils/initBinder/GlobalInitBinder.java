package org.pyf.developer.web.utils.initBinder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/9/12 16:51.
 */

@ControllerAdvice(annotations = Controller.class)
@ResponseBody
@Slf4j
public class GlobalInitBinder {

    @InitBinder
     void initBinder(WebDataBinder binder) {
        // 1.将string类型的日期字符串初始化为date类型;
        binder.registerCustomEditor(Date.class, new MyDateEditor());
        // 2.去除参数两边的空格;
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));

    }


    private class MyDateEditor extends PropertyEditorSupport {
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            format.setLenient(false);
            Date date = null;

            try {
                date = format.parse(text);
            } catch (ParseException e) {
                format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = format.parse(text);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }

            setValue(date);
        }
    }

}
