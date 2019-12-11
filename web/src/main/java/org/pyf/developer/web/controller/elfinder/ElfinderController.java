package org.pyf.developer.web.controller.elfinder;


import org.apache.commons.fileupload.FileItemHeaders;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.pyf.developer.elfinder.ElFinderConstants;
import org.pyf.developer.elfinder.command.ElfinderCommand;
import org.pyf.developer.elfinder.command.ElfinderCommandFactory;
import org.pyf.developer.elfinder.core.ElfinderContext;
import org.pyf.developer.elfinder.service.ElfinderStorageFactory;
import org.pyf.developer.web.controller.base.CP_SimpleBaseController;
import org.pyf.developer.web.service.elfinder.ElfinderService;
import org.pyf.developer.web.utils.security.CP_IUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.AbstractMultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/elfinder")
public class ElfinderController extends CP_SimpleBaseController {

    private static final Logger logger = LoggerFactory.getLogger(ElfinderController.class);

    public static final String OPEN_STREAM = "openStream";
    public static final String GET_PARAMETER = "getParameter";

    @Autowired
    private CP_IUser user;

    @Resource(name = "commandFactory")
    private ElfinderCommandFactory elfinderCommandFactory;


    @Autowired
    private ElfinderService elfinderService;


    @RequestMapping("/view.do")
    public String homeView(Model model){
        model.addAttribute("flag","home");
        //return "elfinder_view";
        return getView("elfinder_view");
    }

    @RequestMapping("/shareview.do")
    public String showView(Model model){
        model.addAttribute("flag","share");
        //return "elfinder_view";
        return getView("elfinder_view");
    }





    @RequestMapping("/connector.do")
    public void connector(HttpServletRequest request, final HttpServletResponse response,final String flag) throws IOException {
        try {
            request = processMultipartContent(request);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }


        String cmd = request.getParameter(ElFinderConstants.ELFINDER_PARAMETER_COMMAND);
        ElfinderCommand elfinderCommand = elfinderCommandFactory.get(cmd);

        try {
            final HttpServletRequest protectedRequest = request;
            elfinderCommand.execute(new ElfinderContext() {
                @Override
                public ElfinderStorageFactory getVolumeSourceFactory() {
                    return elfinderService.getElfinderStorageFactory(user.getUserName(),flag);
                }

                @Override
                public HttpServletRequest getRequest() {
                    return protectedRequest;
                }

                @Override
                public HttpServletResponse getResponse() {
                    return response;
                }
            });
        } catch (Exception e) {
            logger.error("Unknown error", e);
        }
    }

    private HttpServletRequest processMultipartContent(final HttpServletRequest request) throws Exception {
        if (!ServletFileUpload.isMultipartContent(request)) {
            return request;
        }

        Map<String,String[]> map = request.getParameterMap();

        final Map<String, Object> requestParams = new HashMap<>();

        for(String key : map.keySet()){
            String[] obj = map.get(key);
            if(obj.length == 1){
                requestParams.put(key,obj[0]);
            }else{
                requestParams.put(key,obj);
            }

        }

        AbstractMultipartHttpServletRequest multipartHttpServletRequest = (AbstractMultipartHttpServletRequest)request;

        ServletFileUpload servletFileUpload = new ServletFileUpload();
        String characterEncoding = request.getCharacterEncoding();
        if (characterEncoding == null) {
            characterEncoding = "UTF-8";
        }
        servletFileUpload.setHeaderEncoding(characterEncoding);

        List<MultipartFile> fileList = multipartHttpServletRequest.getFiles("upload[]");

        List<FileItemStream> listFiles = new ArrayList<>();

        for(MultipartFile file : fileList){

            FileItemStream item = createFileItemStream(file);
            InputStream stream = item.openStream();
            String fileName = item.getName();
            if (fileName != null && !fileName.trim().isEmpty()) {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                IOUtils.copy(stream, os);
                final byte[] bs = os.toByteArray();
                stream.close();

                listFiles.add((FileItemStream) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                        new Class[]{FileItemStream.class}, new InvocationHandler() {
                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                if (OPEN_STREAM.equals(method.getName())) {
                                    return new ByteArrayInputStream(bs);
                                }

                                return method.invoke(item, args);
                            }
                        }));
            }

        }

        request.setAttribute(FileItemStream.class.getName(), listFiles);
        return (HttpServletRequest) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{HttpServletRequest.class}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object arg0, Method arg1, Object[] arg2) throws Throwable {
                        if (GET_PARAMETER.equals(arg1.getName())) {
                            return requestParams.get(arg2[0]);
                        }

                        return arg1.invoke(request, arg2);
                    }
                });
    }


    private FileItemStream createFileItemStream(MultipartFile file){
        return new FileItemStream() {
            @Override
            public InputStream openStream() throws IOException {
                return file.getInputStream();
            }

            @Override
            public String getContentType() {
                return file.getContentType();
            }

            @Override
            public String getName() {
                return file.getOriginalFilename();
            }

            @Override
            public String getFieldName() {
                return file.getName();
            }

            @Override
            public boolean isFormField() {
                return false;
            }

            @Override
            public FileItemHeaders getHeaders() {
                return null;
            }

            @Override
            public void setHeaders(FileItemHeaders fileItemHeaders) {

            }
        };
    }
}
