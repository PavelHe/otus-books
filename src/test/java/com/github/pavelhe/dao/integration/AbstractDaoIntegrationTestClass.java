package com.github.pavelhe.dao.integration;

import com.github.pavelhe.config.TestConfiguration;
import org.junit.runner.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;
import org.springframework.transaction.annotation.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ContextConfiguration(classes = TestConfiguration.class)
public abstract class AbstractDaoIntegrationTestClass {

    public abstract void testCount() throws Exception;

    public abstract void testGetAll() throws Exception;

    public abstract void testGetById() throws Exception;

    public abstract void testCreate() throws Exception;

    public abstract void testRemove() throws Exception;

    public abstract void testUpdate() throws Exception;

}
