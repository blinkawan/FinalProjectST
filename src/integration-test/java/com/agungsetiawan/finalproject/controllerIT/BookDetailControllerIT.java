package com.agungsetiawan.finalproject.controllerIT;

import com.agungsetiawan.finalproject.config.WebAppConfigTest;
import com.agungsetiawan.finalproject.service.BookService;
import com.agungsetiawan.finalproject.type.IntegrationTest;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import java.math.BigDecimal;
import static org.hamcrest.Matchers.hasProperty;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author awanlabs
 */
@org.junit.experimental.categories.Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@WebAppConfiguration
@ContextConfiguration(classes=WebAppConfigTest.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionDbUnitTestExecutionListener.class})
public class BookDetailControllerIT {
    
    MockMvc mockMvc;
    
    @Autowired
    BookService bookService;
    
    @Autowired
    WebApplicationContext webApplicationContext;
    
    @Before
    public void setUp(){
        mockMvc= MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
    @DatabaseSetup("classpath:sampleData.xml")
    public void bookDetailTest() throws Exception{
        mockMvc.perform(get("/public/book/detail/{bookId}",1L))
                .andExpect(status().isOk())
                .andExpect(view().name("template"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/template.jsp"))
                .andExpect(model().attribute("page", "detail.jsp"))
                .andExpect(model().attribute("book", hasProperty("title", is("Java Super"))))
                .andExpect(model().attribute("book", hasProperty("author", is("Agung Setiawan"))))
                .andExpect(model().attribute("book", hasProperty("description", is("Good Java Book"))))
//                .andExpect(model().attribute("book", hasProperty("price", is(new BigDecimal(30)))))
                .andExpect(model().attribute("book", hasProperty("image", is("image-java.jpg"))));
        
    }
}
