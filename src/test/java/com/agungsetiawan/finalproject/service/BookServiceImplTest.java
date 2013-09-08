package com.agungsetiawan.finalproject.service;

import com.agungsetiawan.finalproject.dao.BookDao;
import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.domain.Category;
import com.agungsetiawan.finalproject.util.BookBuilder;
import com.agungsetiawan.finalproject.util.CategoryBuilder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mockito;

/**
 *
 * @author awanlabs
 */
public class BookServiceImplTest {
    
    BookServiceImpl bookServiceImpl;
    BookDao bookDao;
    
    @Before
    public void setUp(){
        bookDao= Mockito.mock(BookDao.class);
        bookServiceImpl=new BookServiceImpl(bookDao);
    }
    
    @Test
    public void saveTest() {
        Book bookToSave=new BookBuilder().title("Java Testing").author("Agung Setiawan")
                            .price(new BigDecimal(70000)).description("Good book about Java Testing Technique")
                            .image("java-testing.jpg").category(new CategoryBuilder().build())
                            .build();
        
        Book bookSaved=new BookBuilder().id(1L).title(bookToSave.getTitle())
                           .author(bookToSave.getAuthor()).price(bookToSave.getPrice())
                           .description(bookToSave.getDescription())
                           .image(bookToSave.getImage()).category(bookToSave.getCategory())
                           .build();
        
        Mockito.when(bookDao.save(bookToSave)).thenReturn(bookSaved);
        
        Book actual=bookServiceImpl.save(bookToSave);
        
        Mockito.verify(bookDao, Mockito.times(1)).save(bookToSave);
        Mockito.verifyNoMoreInteractions(bookDao);

        Assert.assertNotNull(actual);
        Assert.assertEquals(1L, actual.getId(),0);
        Assert.assertEquals("Java Testing",actual.getTitle()); 
        Assert.assertEquals("Agung Setiawan", actual.getAuthor()); 
        Assert.assertEquals(new BigDecimal(70000), actual.getPrice()); 
        Assert.assertEquals("Good book about Java Testing Technique", actual.getDescription());
        Assert.assertEquals("java-testing.jpg", actual.getImage()); 
    }
    
    @Test
    public void editTest(){
        Book bookToUpdate=new BookBuilder().id(1L).title("Java Testing").author("Agung Setiawan")
                            .price(new BigDecimal(70000)).description("Good book about Java Testing Technique")
                            .image("java-testing.jpg").category(new CategoryBuilder().build()).build();
        Book bookUpdated=new BookBuilder().id(bookToUpdate.getId()).title("Java Testing 2nd Edition")
                            .author(bookToUpdate.getAuthor())
                           .price(bookToUpdate.getPrice()).description(bookToUpdate.getDescription())
                           .image(bookToUpdate.getImage()).category(bookToUpdate.getCategory()).build();
        
        Mockito.when(bookDao.edit(bookToUpdate)).thenReturn(bookUpdated);
        
        Book actual=bookServiceImpl.edit(bookToUpdate);
        
        Mockito.verify(bookDao,Mockito.times(1)).edit(bookToUpdate);
        Mockito.verifyNoMoreInteractions(bookDao);
        
        Assert.assertNotNull(actual);
        Assert.assertEquals(1L, actual.getId(),0);
        Assert.assertEquals("Java Testing 2nd Edition",actual.getTitle()); 
        Assert.assertEquals("Agung Setiawan", actual.getAuthor()); 
        Assert.assertEquals(new BigDecimal(70000), actual.getPrice()); 
        Assert.assertEquals("Good book about Java Testing Technique", actual.getDescription());
        Assert.assertEquals("java-testing.jpg", actual.getImage()); 
    }
    
    @Test
    public void deleteTest(){
        Book bookToDelete=new BookBuilder().id(1L).title("Java Testing").author("Agung Setiawan")
                            .price(new BigDecimal(70000)).description("Good book about Java Testing Technique")
                            .image("java-testing.jpg").category(new CategoryBuilder().build()).build();
        
        Mockito.when(bookDao.delete(bookToDelete)).thenReturn(bookToDelete);
        
        Book actual=bookServiceImpl.delete(bookToDelete);
        
        Mockito.verify(bookDao,Mockito.times(1)).delete(bookToDelete);
        Mockito.verifyNoMoreInteractions(bookDao);
        
        Assert.assertNotNull(actual);
        Assert.assertEquals(1L, actual.getId(),0);
        Assert.assertEquals("Java Testing",actual.getTitle()); 
        Assert.assertEquals("Agung Setiawan", actual.getAuthor()); 
        Assert.assertEquals(new BigDecimal(70000), actual.getPrice()); 
        Assert.assertEquals("Good book about Java Testing Technique", actual.getDescription());
        Assert.assertEquals("java-testing.jpg", actual.getImage()); 
    }
    
    @Test
    public void findOneTest(){
        Book bookToFind=new BookBuilder().id(1L).title("Java Testing").author("Agung Setiawan")
                            .price(new BigDecimal(70000)).description("Good book about Java Testing Technique")
                            .image("java-testing.jpg").category(new CategoryBuilder().build()).build();
        
        Mockito.when(bookDao.findOne(1L)).thenReturn(bookToFind);
        
        Book actual=bookServiceImpl.findOne(1L);
        
        Mockito.verify(bookDao,Mockito.times(1)).findOne(1L);
        Mockito.verifyNoMoreInteractions(bookDao);
       
        Assert.assertNotNull(actual);
        Assert.assertEquals(1L, actual.getId(),0);
        Assert.assertEquals("Java Testing",actual.getTitle()); 
        Assert.assertEquals("Agung Setiawan", actual.getAuthor()); 
        Assert.assertEquals(new BigDecimal(70000), actual.getPrice()); 
        Assert.assertEquals("Good book about Java Testing Technique", actual.getDescription());
        Assert.assertEquals("java-testing.jpg", actual.getImage()); 
    }
    
    @Test
    public void findAllTest(){
        Book bookOne=new BookBuilder().id(1L).title("Java Testing").author("Agung Setiawan")
                            .price(new BigDecimal(70000)).description("Good book about Java Testing Technique")
                            .image("java-testing.jpg").category(new CategoryBuilder().build()).build();
        
        Book bookTwo=new BookBuilder().id(2L).title("Java Security").author("Agung Setiawan")
                            .price(new BigDecimal(80000)).description("Good book about Java Security Technique")
                            .image("java-security.jpg").category(new CategoryBuilder().build()).build();
        List<Book> books=new ArrayList<Book>();
        books.add(bookOne);
        books.add(bookTwo);
        
        Mockito.when(bookDao.findAll()).thenReturn(books);
        
        List<Book> actual=bookServiceImpl.findAll();
        
        Mockito.verify(bookDao,Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(bookDao);
        
        Assert.assertNotNull(actual);
        Assert.assertEquals(2, actual.size());
        
        Assert.assertEquals(1L, actual.get(0).getId(),0);
        Assert.assertEquals("Java Testing",actual.get(0).getTitle()); 
        Assert.assertEquals("Agung Setiawan", actual.get(0).getAuthor()); 
        Assert.assertEquals(new BigDecimal(70000), actual.get(0).getPrice()); 
        Assert.assertEquals("Good book about Java Testing Technique", actual.get(0).getDescription());
        Assert.assertEquals("java-testing.jpg", actual.get(0).getImage()); 
        
        Assert.assertEquals(2L, actual.get(1).getId(),0);
        Assert.assertEquals("Java Security",actual.get(1).getTitle()); 
        Assert.assertEquals("Agung Setiawan", actual.get(1).getAuthor()); 
        Assert.assertEquals(new BigDecimal(80000), actual.get(1).getPrice()); 
        Assert.assertEquals("Good book about Java Security Technique", actual.get(1).getDescription());
        Assert.assertEquals("java-security.jpg", actual.get(1).getImage()); 
    }
    
    @Test
    public void findByCategoryTest(){
        Category javaCategory=new CategoryBuilder().id(1L).name("Java")
                                  .description("Java book description").build();
        
        Book bookOne=new BookBuilder().id(1L).title("Java Testing").author("Agung Setiawan")
                            .price(new BigDecimal(70000)).description("Good book about Java Testing Technique")
                            .image("java-testing.jpg").category(javaCategory).build();
        
        Book bookTwo=new BookBuilder().id(2L).title("Java Security").author("Agung Setiawan")
                            .price(new BigDecimal(80000)).description("Good book about Java Security Technique")
                            .image("java-security.jpg").category(javaCategory).build();
        
        List<Book> books=new ArrayList<Book>();
        books.add(bookOne);
        books.add(bookTwo);
        
        Mockito.when(bookDao.findByCategory(1L)).thenReturn(books);
        
        List<Book> actual=bookServiceImpl.findByCategory(1L);
        
        Mockito.verify(bookDao,Mockito.times(1)).findByCategory(1L);
        Mockito.verifyNoMoreInteractions(bookDao);
        
        Assert.assertNotNull(actual);
        Assert.assertEquals(2, actual.size());
        
        Assert.assertEquals(1L, actual.get(0).getId(),0);
        Assert.assertEquals("Java Testing",actual.get(0).getTitle()); 
        Assert.assertEquals("Agung Setiawan", actual.get(0).getAuthor()); 
        Assert.assertEquals(new BigDecimal(70000), actual.get(0).getPrice()); 
        Assert.assertEquals("Good book about Java Testing Technique", actual.get(0).getDescription());
        Assert.assertEquals("java-testing.jpg", actual.get(0).getImage()); 
        
        Assert.assertEquals(2L, actual.get(1).getId(),0);
        Assert.assertEquals("Java Security",actual.get(1).getTitle()); 
        Assert.assertEquals("Agung Setiawan", actual.get(1).getAuthor()); 
        Assert.assertEquals(new BigDecimal(80000), actual.get(1).getPrice()); 
        Assert.assertEquals("Good book about Java Security Technique", actual.get(1).getDescription());
        Assert.assertEquals("java-security.jpg", actual.get(1).getImage()); 
    }
    
    @Test
    public void findByTitleTest(){
        Book bookOne=new BookBuilder().id(1L).title("Java Testing").author("Agung Setiawan")
                            .price(new BigDecimal(70000)).description("Good book about Java Testing Technique")
                            .image("java-testing.jpg").build();
        
        Book bookTwo=new BookBuilder().id(2L).title("Java Security").author("Agung Setiawan")
                            .price(new BigDecimal(80000)).description("Good book about Java Security Technique")
                            .image("java-security.jpg").build();
        
        List<Book> books=new ArrayList<Book>();
        books.add(bookOne);
        books.add(bookTwo);
        
        Mockito.when(bookDao.findByTitle("java")).thenReturn(books);
        
        List<Book> actual=bookServiceImpl.findByTitle("java");
        
        Mockito.verify(bookDao,Mockito.times(1)).findByTitle("java");
        Mockito.verifyNoMoreInteractions(bookDao);
        
        Assert.assertNotNull(actual);
        Assert.assertEquals(2, actual.size());
        
        Assert.assertEquals(1L, actual.get(0).getId(),0);
        Assert.assertEquals("Java Testing",actual.get(0).getTitle()); 
        Assert.assertEquals("Agung Setiawan", actual.get(0).getAuthor()); 
        Assert.assertEquals(new BigDecimal(70000), actual.get(0).getPrice()); 
        Assert.assertEquals("Good book about Java Testing Technique", actual.get(0).getDescription());
        Assert.assertEquals("java-testing.jpg", actual.get(0).getImage()); 
        
        Assert.assertEquals(2L, actual.get(1).getId(),0);
        Assert.assertEquals("Java Security",actual.get(1).getTitle()); 
        Assert.assertEquals("Agung Setiawan", actual.get(1).getAuthor()); 
        Assert.assertEquals(new BigDecimal(80000), actual.get(1).getPrice()); 
        Assert.assertEquals("Good book about Java Security Technique", actual.get(1).getDescription());
        Assert.assertEquals("java-security.jpg", actual.get(1).getImage()); 
    }
    
    @Test
    public void findRandomTest(){
        Book bookOne=new BookBuilder().id(1L).title("Java Testing").author("Agung Setiawan")
                            .price(new BigDecimal(70000)).description("Good book about Java Testing Technique")
                            .image("java-testing.jpg").build();
        
        Book bookTwo=new BookBuilder().id(2L).title("Java Security").author("Agung Setiawan")
                            .price(new BigDecimal(80000)).description("Good book about Java Security Technique")
                            .image("java-security.jpg").build();
        
        Book bookThree=new BookBuilder().id(3L).title("Java Rest").author("Agung Setiawan")
                            .price(new BigDecimal(85000)).description("Good book about Java Rest Web Service")
                            .image("java-rest.jpg").build();
        
        List<Book> books=new ArrayList<Book>();
        books.add(bookOne);
        books.add(bookTwo);
        books.add(bookThree);
        
        Mockito.when(bookDao.findRandom()).thenReturn(books);
        
        List<Book> actual=bookServiceImpl.findRandom();
        
        Mockito.verify(bookDao, Mockito.times(1)).findRandom();
        Mockito.verifyNoMoreInteractions(bookDao);
        
        Assert.assertNotNull(actual);
        Assert.assertEquals(3, actual.size());
        
        Assert.assertEquals(1L, actual.get(0).getId(),0);
        Assert.assertEquals("Java Testing",actual.get(0).getTitle()); 
        Assert.assertEquals("Agung Setiawan", actual.get(0).getAuthor()); 
        Assert.assertEquals(new BigDecimal(70000), actual.get(0).getPrice()); 
        Assert.assertEquals("Good book about Java Testing Technique", actual.get(0).getDescription());
        Assert.assertEquals("java-testing.jpg", actual.get(0).getImage()); 
        
        Assert.assertEquals(2L, actual.get(1).getId(),0);
        Assert.assertEquals("Java Security",actual.get(1).getTitle()); 
        Assert.assertEquals("Agung Setiawan", actual.get(1).getAuthor()); 
        Assert.assertEquals(new BigDecimal(80000), actual.get(1).getPrice()); 
        Assert.assertEquals("Good book about Java Security Technique", actual.get(1).getDescription());
        Assert.assertEquals("java-security.jpg", actual.get(1).getImage()); 
    }
}