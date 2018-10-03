package com.github.pavelhe.batch;

import com.github.pavelhe.config.*;
import com.github.pavelhe.config.utils.*;
import com.github.pavelhe.dao.*;
import com.github.pavelhe.service.batch.*;
import com.github.pavelhe.service.mongodb.interfaces.*;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;


import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
        TestBasicConfiguration.class,
        TestJPAConfiguration.class,
        TestMongoConfiguration.class,
        BatchConfiguration.class,
        TestBatchConfiguration.class})
public class TestBatchImport {

    @Autowired
    @Qualifier("testGenreDaoJpa")
    private GenreDao genreDaoJpa;

    @Autowired
    private GenreService genreServiceMongo;

    @Autowired
    @Qualifier("testImportService")
    private ImportService importService;

    @Test
    public void testImportFromSqlToNoSql() throws Exception {
        assertTrue(genreDaoJpa.count() > 0);
        assertTrue(genreServiceMongo.count() == 0);

        importService.start();

        assertTrue(genreDaoJpa.count() > 0);
        assertTrue(genreServiceMongo.count() > 0);
    }

}
