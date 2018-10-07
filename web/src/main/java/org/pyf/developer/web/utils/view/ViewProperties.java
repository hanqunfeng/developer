package org.pyf.developer.web.utils.view;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/9/26 17:56.
 */

@Data
@Component
@ConditionalOnProperty(prefix="spring.thymeleaf",name = "enabled",havingValue = "true")
@PropertySource({"classpath:/config/properties/view-name.properties"})//通过代码生成器搞定吧
@ConfigurationProperties(prefix = "view")
public class ViewProperties {
    private Map<String, String> name = new HashMap<String, String>();

}
