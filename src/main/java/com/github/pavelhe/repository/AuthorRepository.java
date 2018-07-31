package com.github.pavelhe.repository;


import java.util.*;

import com.github.pavelhe.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.*;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    List<Author> findAll();

    @Modifying
    @Query("DELETE FROM Author a WHERE a.id=?1")
    int delete(Long id);

    Optional<Author> findByName(String name);

}
