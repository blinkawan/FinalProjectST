package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.domain.Category;
import com.agungsetiawan.finalproject.exception.NotFoundException;
import com.agungsetiawan.finalproject.service.BookService;
import com.agungsetiawan.finalproject.service.CategoryService;
import com.agungsetiawan.finalproject.util.BookBuilder;
import com.agungsetiawan.finalproject.util.CategoryBuilder;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import org.hamcrest.text.IsEmptyString;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.mock.web.MockMultipartFile;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 *
 * @author awanlabs
 */
public class AdminBukuControllerTest {
    
    AdminBukuController adminBukuController;
    BookService bookService;
    CategoryService categoryService;
    MockMvc mockMvc;
    View view;

    @Before
    public void setUp() {
        bookService= Mockito.mock(BookService.class);
        categoryService=Mockito.mock((CategoryService.class));
        adminBukuController=new AdminBukuController(bookService, categoryService);
        view=Mockito.mock(View.class);
        mockMvc= MockMvcBuilders.standaloneSetup(adminBukuController).setSingleView(view)
                .setViewResolvers(viewResolver()).setValidator(validator())
                .setHandlerExceptionResolvers(getSimpleMappingExceptionResolver()).build();
    }
    
    @Test
    public void indexTest() throws Exception{
         Book bookOne=new BookBuilder().id(1L).author("Agung Setiawan").title("Java in Nutshell")
                         .price(new BigDecimal(85000)).description("Java book for intermediate")
                         .image("java-in-nutshell").build();
        Book bookTwo=new BookBuilder().id(2L).author("Markosvey").title("PHP in Nutshell")
                         .price(new BigDecimal(65000)).description("PHP book for intermediate")
                         .image("php-in-nutshell").build();
        
        List<Book> books=new ArrayList<Book>();
        books.add(bookOne);
        books.add(bookTwo);
        
        Mockito.when(bookService.findAll()).thenReturn(books);
        
        mockMvc.perform(get("/admin/book"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/templateno"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/templateno.jsp"))
                .andExpect(model().attribute("books", books))
                .andExpect(model().attribute("page", "book.jsp"));
        
        Mockito.verify(bookService,Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(bookService);
        Mockito.verifyZeroInteractions(categoryService);
    }
    
    @Test
    public void addFormTest() throws Exception{
        Category categoryOne=new CategoryBuilder().id(1L).name("Java").description("Java Books Category").build();
        Category categoryTwo=new CategoryBuilder().id(2L).name(".NET").description(".NET Books Category").build();
        List<Category> categories=new ArrayList<Category>();
        categories.add(categoryOne);
        categories.add(categoryTwo);
        
        Mockito.when(categoryService.findAll()).thenReturn(categories);
        
        mockMvc.perform(get("/admin/book/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/templateno"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/templateno.jsp"))
                .andExpect(model().attribute("page", "bookForm.jsp"))
                .andExpect(model().attribute("categories", categories))
                .andExpect(model().attribute("book", hasProperty("id", nullValue())))
                .andExpect(model().attribute("book", hasProperty("title", IsEmptyString.isEmptyOrNullString())))
                .andExpect(model().attribute("book", hasProperty("author", IsEmptyString.isEmptyOrNullString())))
                .andExpect(model().attribute("book", hasProperty("description", IsEmptyString.isEmptyOrNullString())))
                .andExpect(model().attribute("book", hasProperty("price", nullValue())))
                .andExpect(model().attribute("book", hasProperty("image", IsEmptyString.isEmptyOrNullString())))
                .andExpect(model().attribute("book", hasProperty("category", nullValue())));
        
        Mockito.verify(categoryService,Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(categoryService);
        Mockito.verifyZeroInteractions(bookService);
    }
    
    @Test
    public void addFailTest()throws Exception{        
        
        MockMultipartFile file = new MockMultipartFile("file", "orig", null, "bar".getBytes());
        Book bookToSave=new BookBuilder().author("Agung Setiawan")
                         .price(new BigDecimal(85000)).description("Java book for intermediate")
                         .image("java-in-nutshell").build();
        
        mockMvc.perform(fileUpload("/admin/book/add").file(file)
//                .param("title", "Java in Nutshell")
                .param("author", "Agung Setiawan")
                .param("description", "Java book for intermediate")
                .param("price", "85000")
                .param("category", "1")
                .param("image", "java-in-nutshell")
                .param("fileUpload", "image")
                .sessionAttr("book", bookToSave))
                .andExpect(status().is(200))
                .andExpect(view().name("admin/templateno"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/templateno.jsp"))
                .andExpect(model().attributeHasFieldErrors("book", "title"));
    }
    
    @Test
    public void addTest() throws Exception{
        
        Book bookToSave=new BookBuilder().author("Agung Setiawan").title("Java in Nutshell")
                         .price(new BigDecimal(85000)).description("Java book for intermediate")
                         .image("java-in-nutshell").build();
        
        Book bookSaved=new BookBuilder().id(1L).author("Agung Setiawan").title("Java in Nutshell")
                         .price(new BigDecimal(85000)).description("Java book for intermediate")
                         .image("java-in-nutshell").build();
        
        MockMultipartFile file = new MockMultipartFile("file", "orig", "jpg", "bar".getBytes());
        
        Mockito.when(bookService.save(bookToSave)).thenReturn(bookSaved);
        
        mockMvc.perform(fileUpload("/admin/book/add").file(file)
                .param("title", "Java in Nutshell")
                .param("author", "Agung Setiawan")
                .param("description", "Java book for intermediate")
                .param("price", "85000")
                .param("category", "1")
                .param("image", "java-in-nutshell")
                .sessionAttr("book", bookToSave))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/admin/book?save"))
                .andExpect(redirectedUrl("/admin/book?save"));
//                .andExpect(flash().attribute("title", "Java in Nutshell"));
        
//        Mockito.verify(bookService,Mockito.times(1)).save(bookToSave);
        Mockito.verifyNoMoreInteractions(bookService);
    }
    
    @Test
    public void editFormNotFound() throws Exception{
        Mockito.when(bookService.findOne(1L)).thenReturn(null);
        mockMvc.perform(get("/admin/book/edit/{id}",1L))
                .andExpect(view().name("404s"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/404s.jsp"));
    }
    
    @Test
    public void editFormTest() throws Exception{
        Book book=new BookBuilder().id(1L).author("Agung Setiawan").title("Java in Nutshell")
                         .price(new BigDecimal(85000)).description("Java book for intermediate")
                         .image("java-in-nutshell").build();
        
        Mockito.when(bookService.findOne(1L)).thenReturn(book);
        
         mockMvc.perform(get("/admin/book/edit/{id}",1L))
                .andExpect(view().name("admin/templateno"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/templateno.jsp"))
                .andExpect(model().attribute("book", book))
                .andExpect(model().attribute("page", "bookEditForm.jsp"))
                .andExpect(model().attribute("book", hasProperty("id", is(1L))))
                .andExpect(model().attribute("book", hasProperty("author", is("Agung Setiawan"))))
                .andExpect(model().attribute("book", hasProperty("title", is("Java in Nutshell"))))
                .andExpect(model().attribute("book", hasProperty("price", is(new BigDecimal(85000)))))
                .andExpect(model().attribute("book", hasProperty("description", is("Java book for intermediate"))))
                .andExpect(model().attribute("book", hasProperty("image", is("java-in-nutshell"))));
         
         Mockito.verify(bookService,Mockito.times(1)).findOne(1L);
         Mockito.verifyNoMoreInteractions(bookService);
    }
    
    @Test
    public void editFailTest()throws Exception{        
        
        MockMultipartFile file = new MockMultipartFile("file", "orig", null, "bar".getBytes());
        Book bookToSave=new BookBuilder().id(1L).author("Agung Setiawan")
                         .price(new BigDecimal(85000)).description("Java book for intermediate")
                         .image("java-in-nutshell").build();
        
        mockMvc.perform(fileUpload("/admin/book/edit").file(file)
//                .param("title", "Java in Nutshell")
                .param("author", "Agung Setiawan")
                .param("description", "Java book for intermediate")
                .param("price", "85000")
                .param("category", "1")
                .param("image", "java-in-nutshell")
                .param("fileUpload", "image")
                .sessionAttr("book", bookToSave))
                .andExpect(status().is(200))
                .andExpect(view().name("admin/templateno"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/templateno.jsp"))
                .andExpect(model().attributeHasFieldErrors("book", "title"))
                .andExpect(model().attribute("book", hasProperty("id",is(1L))));
    }
    
    @Test
    public void editTest(){
        
    }
    
    @Test
    public void deleteNotFoundTest() throws Exception{
        Mockito.when(bookService.findOne(1L)).thenReturn(null);
        mockMvc.perform(get("/admin/book/delete/{id}",1L))
                .andExpect(view().name("404s"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/404s.jsp"));
    }
    
    @Test
    public void deleteTest()throws Exception{
        Book bookDelete=new BookBuilder().id(1L).author("Agung Setiawan").title("Java in Nutshell")
                         .price(new BigDecimal(85000)).description("Java book for intermediate")
                         .image("java-in-nutshell").build();
        
        Book bookDeleted=new BookBuilder().id(1L).author("Agung Setiawan").title("Java in Nutshell")
                         .price(new BigDecimal(85000)).description("Java book for intermediate")
                         .image("java-in-nutshell").build();
        
        Mockito.when(bookService.findOne(1L)).thenReturn(bookDelete);
        Mockito.when(bookService.delete(bookDelete)).thenReturn(bookDeleted);
        
        mockMvc.perform(get("/admin/book/delete/{id}",1L))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/admin/book?delete"))
                .andExpect(redirectedUrl("/admin/book?delete"))
                .andExpect(flash().attribute("title", "Java in Nutshell"));
        
        Mockito.verify(bookService, Mockito.times(1)).findOne(1L);
        Mockito.verify(bookService,Mockito.times(1)).delete(bookDelete);
        Mockito.verifyNoMoreInteractions(bookService);
    }
    
    private LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }
    
    private ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
    
    public SimpleMappingExceptionResolver getSimpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();

        Properties exceptionMappings = new Properties();

        exceptionMappings.put(NotFoundException.class.getName(), "404s");
        exceptionMappings.put(ConstraintViolationException.class.getName(), "409");
//        exceptionMappings.put("java.lang.Exception", "404");
//        exceptionMappings.put("java.lang.RuntimeException", "404");

        exceptionResolver.setExceptionMappings(exceptionMappings);
//        exceptionResolver.setDefaultErrorView("404s");
        exceptionResolver.setOrder(0);
//        Properties statusCodes = new Properties();
//
//        statusCodes.put("404", "404");
//        statusCodes.put("error/error", "500");
//
//        exceptionResolver.setStatusCodes(statusCodes);

        return exceptionResolver;
    }
}