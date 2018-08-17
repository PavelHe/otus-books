package com.github.pavelhe.web;


import com.github.pavelhe.config.*;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.boot.test.context.*;
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
@Transactional
public abstract class AbstractControllerTestClass {

    MockMvc mockMvc;
    InternalResourceViewResolver viewResolver;

    @Before
    public void setup() {
        viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".html");

        mockMvc = buildMockMvc();
    }

    protected abstract MockMvc buildMockMvc();

}
