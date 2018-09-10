package com.github.pavelhe.web;


import com.github.pavelhe.config.*;
import com.github.pavelhe.repository.*;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.boot.test.context.*;
import org.springframework.data.jpa.repository.config.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;
import org.springframework.test.context.web.*;
import org.springframework.test.web.servlet.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.servlet.view.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestBasicConfiguration.class, TestJPAConfiguration.class})
@WebAppConfiguration
@EnableJpaRepositories(basePackageClasses = {
        GenreRepository.class,
        BookRepository.class,
        CommentRepository.class,
        AuthorRepository.class,
        UserController.class
},
        entityManagerFactoryRef = "entityManagerFactoryBean",
        transactionManagerRef = "testJpaTransactionManager")
@Transactional(value = "testJpaTransactionManager")
public abstract class AbstractControllerTestClass {

    protected MockMvc mockMvc;
    protected InternalResourceViewResolver viewResolver;

    @Before
    public void setup() {
        viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".html");

        mockMvc = buildMockMvc();
    }

    protected abstract MockMvc buildMockMvc();

}
