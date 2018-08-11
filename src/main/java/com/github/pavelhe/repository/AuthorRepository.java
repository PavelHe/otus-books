package com.github.pavelhe.repository;


import java.util.*;

import com.github.pavelhe.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.*;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    List<Author> findAll();

    Optional<Author> findByName(String name);

}
