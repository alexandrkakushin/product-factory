package ru.pf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.pf.controller.ControllerInterceptor;

/**
 * @author a.kakushin
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/login").setViewName("login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/webjars/**")) {
            registry
                    .addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }

        registry.
                addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");

        registry.
                addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");

        registry.
                addResourceHandler("/fonts/**")
                .addResourceLocations("classpath:/static/fonts/");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ControllerInterceptor())
                .addPathPatterns("/development/**")
                .addPathPatterns("/infrastructure/**")
                .addPathPatterns("/vcs/**")
                .addPathPatterns("/tools/**");
    }
}