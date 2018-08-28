package com.github.pavelhe.service.mongodb;

import java.util.*;

import com.github.pavelhe.model.mongodb.models.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.data.mongodb.core.query.*;

class QueryUtils {

    static Query findByValueQuery(String fieldName, Object value) {
        return Query.query(Criteria.where(fieldName).is(value));
    }

    static <T extends NamedModel> T findEntityBy(String fieldName, Object value, Class<T> type, MongoTemplate template) {
        Query byId = findByValueQuery(fieldName, value);
        List<T> entities = template.find(byId, type);
        if (entities.size() == 0)
            return null;
        return entities.get(0);
    }

    static Query emptyQuery() {
        return new Query();
    }

}
