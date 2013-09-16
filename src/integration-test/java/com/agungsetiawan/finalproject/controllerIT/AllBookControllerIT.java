package com.agungsetiawan.finalproject.controllerIT;

import com.agungsetiawan.finalproject.config.WebAppConfigTest;
import com.agungsetiawan.finalproject.service.BookService;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author awanlabs
 */
//@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@WebAppConfiguration
@ContextConfiguration(classes=WebAppConfigTest.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionDbUnitTestExecutionListener.class})
public class AllBookControllerIT {
    
    MockMvc mockMvc;
    
    @Autowired
    BookService bookService;
    
    @Autowired
    WebApplicationContext webApplicationContext;
    
    @Before
    public void setUp(){
        mockMvc= MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
//    @Test
    @DatabaseSetup("classpath:sampleData.xml")
    public void allBookTest() throws Exception{
        mockMvc.perform(get("/public/book/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("templateno"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/templateno.jsp"))
                .andExpect(model().attribute("page", "grid.jsp"))
                .andExpect(model().attribute("books",hasItem(
                    allOf(
                        hasProperty("title",is("Spring MVC")),
                        hasProperty("description",is("Good Java Book"))
                    )
                ) ))
                .andExpect(model().attribute("books",hasItem(
                    allOf(
                        hasProperty("title",is("ASP.NET MVC")),
                        hasProperty("description",is("Good .NET Book"))
                    )
                ) ))
                .andExpect(model().attribute("books",hasItem(
                    allOf(
                        hasProperty("title",is("Java Super")),
                        hasProperty("description",is("Good Java Book"))
                    )
                ) ));
    }
    
}
