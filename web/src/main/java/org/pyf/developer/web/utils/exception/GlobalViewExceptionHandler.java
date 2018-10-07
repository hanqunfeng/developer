package org.pyf.developer.web.utils.exception;

import lombok.extern.slf4j.Slf4j;
import org.pyf.developer.web.utils.view.ViewProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/9/5 11:39.
 */

@ControllerAdvice(basePackages = "org.pyf.developer.web.controller")
@Slf4j
public class GlobalViewExceptionHandler {

    @Autowired(required = false)
    public ViewProperties viewProperties;


    protected String getView(String viewName){
        if(viewProperties != null){

            viewName =  viewProperties.getName().get(viewName);
        }

        return viewName;
    }

    //运行时异常
    @ExceptionHandler(Exception.class) //类似于@RequestMapping(value = "/ex/{number}")修饰的方法，方法参数也可以指定
    public String runtimeExceptionHandler(RuntimeException ex, Model model) {
        ex.printStackTrace();
        model.addAttribute("exception",ex);
        //return "common_error_view"; //返回视图
        return getView("common_error_view"); //返回视图
    }
}
