package com.github.pavelhe.repository;


import java.util.*;

import com.github.pavelhe.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.*;

public interface CommentRepository extends CrudRepository<Comment, Long>{

    List<Comment> findAll();

    @Modifying
    @Query("DELETE FROM Comment c WHERE c.id=?1")
    int delete(Long id);

    List<Comment> findByName(String authorOfComment);

}
