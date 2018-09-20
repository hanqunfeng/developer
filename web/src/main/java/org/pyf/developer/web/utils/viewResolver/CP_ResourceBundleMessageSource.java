package org.pyf.developer.web.utils.viewResolver;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/9/18 19:44.
 */

@Slf4j
public class CP_ResourceBundleMessageSource extends ResourceBundleMessageSource implements InitializingBean {
    /**
     * @Fields REG_EXP : TODO(匹配正则)
     */
    public static final String REG_EXP = "(_)("
            + StringUtils.join(Locale.getAvailableLocales(), '|') + ")$";

    /**
     * @Fields packagename : TODO(查找路径)
     */
    private String packagename = "";

    public void setPackagename(String packagename) {
        packagename = StringUtils.trimToEmpty(packagename);
        if (!packagename.equals(""))
            this.packagename = packagename.replaceAll("\\.", "/");
        if (!this.packagename.endsWith("/"))
            this.packagename += "/";
    }


    /**
     * @Fields resourcePattern : TODO(资源查找的匹配正则)
     */
    private String resourcePattern;



    /**
     * @Fields urlPrefix : TODO(类路径下查找)
     */
    private String urlPrefix = "classpath:";

    /**
     * @Fields tempBaseNames : TODO(临时接收资源路径列表，用于合并自定义路径和原始路径)
     */
    private String[] tempBaseNames;



    public void afterPropertiesSet()  {
        StringBuilder sb = new StringBuilder();
        sb.append(urlPrefix).append(packagename).append("**/")
                .append("*.properties");
        resourcePattern = sb.toString();

        try {
            String[] all = resolveBaseNames();
            if (!ArrayUtils.isEmpty(all)) {
                super.setBasenames(all);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setBasenames(String... basenames) {
        tempBaseNames = basenames;
    }

    public String[] resolveBaseNames() throws URISyntaxException,
            IOException {
        List<String> namesList = new ArrayList<String>();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(
                new DefaultResourceLoader());
        Resource[] res = null;

        //String rootpath = resolver.getClassLoader().getResource("//").toURI() // jar包下不能访问，war包不受影响
        String rootpath = resolver.getClass().getResource("/").toURI() //这种方式jar和war都支持
                .toString();

        log.info("starting to find resource with pattern ["
                + resourcePattern + "]");

        try {
            res = resolver.getResources(resourcePattern);
        } catch (Exception e) {
            logger.error("Find resource by pattern [" + resourcePattern
                    + "] failed!!", e);
        }

        if (res != null) {
            for (Resource item : res) {
                String realName = item.getURI().toString();
                realName = realName.replaceAll(rootpath, "");
                realName = realName.replaceAll(REG_EXP, "");
                realName = realName.replaceAll("/", ".");
                realName = realName.replaceAll("\\.properties", "");
                if (!namesList.contains(realName))
                    namesList.add(realName);
            }
        }
        String[] resolvedBasenames = namesList.toArray(new String[] {});

        String[] all = org.springframework.util.StringUtils.mergeStringArrays(tempBaseNames,
                resolvedBasenames);
        return all;
    }


}
