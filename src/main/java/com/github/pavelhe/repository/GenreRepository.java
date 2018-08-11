package com.github.pavelhe.repository;


import java.util.*;

import com.github.pavelhe.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.*;

public interface GenreRepository extends CrudRepository<Genre, Long> {

    List<Genre> findAll();

    Optional<Genre> findByName(String name);

}
