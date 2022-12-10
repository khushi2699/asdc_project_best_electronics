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
        registration.addInitParameter("excluded_urls", "/welcome,/user/login,/user/process_login," +
                "/user/register,/user/process_registration,/user/forgotPassword, /user/getCode," +
                "/admin/login,/admin/process_login,/admin,/user,/user/resetPassword," +
                "/user/checkValidToken,/user/enterNewPassword,/user/userLogin");
        registration.setName("filter");
        registration.setOrder(1);
        return registration;
    }

    public Filter AuthenticationFilter() {
        return new AuthenticationFilter();
    }
}
