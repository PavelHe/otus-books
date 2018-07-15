package com.github.pavelhe.utils;


import java.util.*;

public interface MessageSourceWrapper {

    String getMessage(String value, Locale locale);

    String getMessage(String value, Object[] args, Locale locale);

    String getMessage(String value);

    String getMessage(String value, Object[] args);

}
