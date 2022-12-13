package com.best.electronics.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class FilterRegistrationConfig {

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(AuthenticationFilter());
        registration.addUrlPatterns("/*");

        String excludedUrls = getExcludedUrls();
        registration.addInitParameter("excluded_urls", excludedUrls);
        registration.setName("filter");
        registration.setOrder(1);
        return registration;
    }

    public Filter AuthenticationFilter() {
        return new AuthenticationFilter();
    }

    private String getExcludedUrls() {
        FilterProperties filterProperties = new FilterProperties();
        return filterProperties.getCommonExcludedUrls() + "," + filterProperties.getUserExcludedUrls() + "," + filterProperties.getAdminExcludedUrls();
    }


}
