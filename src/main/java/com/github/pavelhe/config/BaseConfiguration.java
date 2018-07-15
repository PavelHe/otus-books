package com.github.pavelhe.config;

import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.context.support.*;

@Configuration
public class BaseConfiguration {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("locales/messages");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

}
