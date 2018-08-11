package com.github.pavelhe.repository;


import java.util.*;

import com.github.pavelhe.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.*;

public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findAll();

    Optional<Book> findByName(String name);

}
