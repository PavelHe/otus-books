package com.github.pavelhe.config;

import java.util.*;

import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.context.support.*;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.i18n.*;

@Configuration
public class BaseConfiguration {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("classpath:locales/messages");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

}
