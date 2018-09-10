package com.github.pavelhe.dao.jpa;


import java.util.*;
import javax.persistence.*;

import com.github.pavelhe.model.*;

public class QueryUtils {

    static <T extends NamedModel> T getEntityFromQuery(String query, Map<String, Object> params, Class<T> type,
                                                       EntityManager entityManager) {
        TypedQuery<T> byNameQuery = entityManager.createQuery(query, type);
        params.forEach(byNameQuery::setParameter);
        return byNameQuery.getSingleResult();
    }

}
