package com.agungsetiawan.finalproject.controllerIT;

import com.agungsetiawan.finalproject.config.WebAppConfigTest;
import com.agungsetiawan.finalproject.service.BookService;
import com.agungsetiawan.finalproject.service.OrderService;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@WebAppConfiguration
@ContextConfiguration(classes=WebAppConfigTest.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionDbUnitTestExecutionListener.class})
public class CustomerOrderControllerIT {
    
    MockMvc mockMvc;
    
    @Autowired
    OrderService orderService;
    
    @Autowired
    UserDetailsService detailsService;
    
    @Autowired
    WebApplicationContext webApplicationContext;
    
    @Before
    public void setUp(){
        mockMvc= MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
    @DatabaseSetup("classpath:sampleData.xml")
    public void allOrderTest() throws Exception{
        
        UserDetails customer=detailsService.loadUserByUsername("agung");
        Authentication auth=new UsernamePasswordAuthenticationToken(customer.getUsername(), customer.getPassword(),customer.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        
        mockMvc.perform(get("/secured/my/order"))
                .andExpect(status().isOk())
                .andExpect(view().name("templateno"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/templateno.jsp"))
                .andExpect(model().attribute("page", "customerorder.jsp"))
                .andExpect(model().attribute("orders",hasItem(
                    allOf(
                        hasProperty("receiver",is("Hauril")),
                        hasProperty("shippingAddress",is("Boja"))
                    )
                ) ))
                .andExpect(model().attribute("orders",hasItem(
                    allOf(
                        hasProperty("receiver",is("Agung Setiawan")),
                        hasProperty("shippingAddress",is("Cihampelas"))
                    )
                ) ));
    }
    
    @Test
    @DatabaseSetup("classpath:sampleData.xml")
    public void detailOrderTest() throws Exception{
        
        UserDetails customer=detailsService.loadUserByUsername("agung");
        Authentication auth=new UsernamePasswordAuthenticationToken(customer.getUsername(), customer.getPassword(),customer.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        
        mockMvc.perform(get("/secured/my/order/detail/{orderId}",1L))
                .andExpect(status().isOk())
                .andExpect(view().name("templateno"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/templateno.jsp"))
                .andExpect(model().attribute("page", "customerorderdetail.jsp"))
                .andExpect(model().attribute("details",hasItem(
                    allOf(
                        hasProperty("amount",is(2))
                    )
                ) ))
                .andExpect(model().attribute("details",hasItem(
                    allOf(
                        hasProperty("amount",is(2))
                    )
                ) ));
    }
    
}
