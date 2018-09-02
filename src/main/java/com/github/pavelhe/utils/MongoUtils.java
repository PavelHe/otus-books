package com.github.pavelhe.utils;

import java.lang.reflect.Field;
import java.util.*;

import com.github.pavelhe.model.mongodb.models.cascade.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mapping.*;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.util.*;

public class MongoUtils {

    public static Optional<Object> checkForSaving(java.lang.reflect.Field field, Object source) throws IllegalAccessException {
        Object fieldValue = null;
        if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadeSave.class)) {
            fieldValue = field.get(source);
            DbRefFieldCallback callback = new DbRefFieldCallback();
            ReflectionUtils.doWithFields(fieldValue.getClass(), callback);
            if (!callback.isIdFound()) {
                throw new MappingException("Cannot perform cascade save on child object without id set");
            }
        }
        return Optional.ofNullable(fieldValue);
    }

    private static class DbRefFieldCallback implements ReflectionUtils.FieldCallback {
        private boolean idFound;

        @Override
        public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
            ReflectionUtils.makeAccessible(field);
            if (field.isAnnotationPresent(Id.class)) {
                idFound = true;
            }
        }

        boolean isIdFound() {
            return idFound;
        }
    }
}
