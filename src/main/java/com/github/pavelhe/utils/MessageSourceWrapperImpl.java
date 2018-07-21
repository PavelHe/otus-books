package com.github.pavelhe.utils;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.stereotype.*;

@Component
public class MessageSourceWrapperImpl implements MessageSourceWrapper {

    private @Value("#{environment['defaultLocale']}")
    String defaultLocale;
    private MessageSource messageSource;

    public MessageSourceWrapperImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getMessage(String value, Locale locale) {
        validateLocale(locale);
        return messageSource.getMessage(value, null, locale);
    }

    @Override
    public String getMessage(String value, Object[] args, Locale locale) {
        validateLocale(locale);
        return messageSource.getMessage(value, args, locale);
    }

    @Override
    public String getMessage(String value) {
        return getMessage(value, validateLocale(null));
    }

    @Override
    public String getMessage(String value, Object... args) {
        return getMessage(value, args, validateLocale(null));
    }

    private Locale validateLocale(Locale locale) {
        return locale = locale == null ? Locale.forLanguageTag(defaultLocale) : locale;
    }
}
