package com.best.electronics.properties;

import java.util.Properties;

public class FilterProperties {

    String userExcludedUrls;

    String adminExcludedUrls;

    String commonExcludedUrls;

    public FilterProperties() {
        PropertiesLoader propertyLoader = PropertiesLoader.getInstance();
        Properties properties = propertyLoader.getProperties();
        userExcludedUrls = properties.getProperty("app.filter.excluded.urls.user");
        adminExcludedUrls = properties.getProperty("app.filter.excluded.urls.admin");
        commonExcludedUrls = properties.getProperty("app.filter.excluded.urls.common");
    }

    public String getUserExcludedUrls() {
        return userExcludedUrls;
    }

    public String getAdminExcludedUrls() {
        return adminExcludedUrls;
    }

    public String getCommonExcludedUrls() {
        return commonExcludedUrls;
    }
}
