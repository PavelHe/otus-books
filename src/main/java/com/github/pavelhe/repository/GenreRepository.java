package com.github.pavelhe.repository;


import java.util.*;

import com.github.pavelhe.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.*;

public interface GenreRepository extends CrudRepository<Genre, Long> {

    List<Genre> findAll();

    @Modifying
    @Query("DELETE FROM Genre g WHERE g.id=?1")
    int delete(Long id);

    Optional<Genre> findByName(String name);

}
