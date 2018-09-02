package com.github.pavelhe.model.mongodb.models.cascade;


import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.data.mongodb.core.mapping.event.*;
import org.springframework.util.*;

import static com.github.pavelhe.utils.MongoUtils.checkForSaving;

public class CascadingMongoEventListener extends AbstractMongoEventListener {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void onBeforeConvert(BeforeConvertEvent event) {
        Object source = event.getSource();
        ReflectionUtils.doWithFields(source.getClass(), field -> {
            field.setAccessible(true);
            checkForSaving(field, source).ifPresent(mongoOperations::save);
        });
    }

}
