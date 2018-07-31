package com.github.pavelhe.repository;


import java.util.*;

import com.github.pavelhe.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.*;

public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findAll();

    @Modifying
    @Query("DELETE FROM Book b WHERE b.id=?1")
    int delete(Long id);

    Optional<Book> findByName(String name);

}
