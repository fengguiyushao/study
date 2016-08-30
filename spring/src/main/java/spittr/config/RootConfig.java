package spittr.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by robinzhou on 2016/8/30.
 */
@Configuration
@ComponentScan(basePackages = {"spittr"},
        excludeFilters = {@Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)})
public class RootConfig {
}
