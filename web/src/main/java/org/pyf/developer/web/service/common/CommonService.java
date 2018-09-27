package org.pyf.developer.web.service.common;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/9/27 19:10.
 */

@Service("commonService")
public class CommonService {

    public String clearSessionMessage(HttpServletRequest request){
        request.getSession().removeAttribute("message");
        request.getSession().removeAttribute("messageStatus");
        return "ok";
    }

    public String clearSessionMessage(HttpSession session){
        session.removeAttribute("message");
        session.removeAttribute("messageStatus");
        return "ok";
    }
}
