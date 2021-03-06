package com.agungsetiawan.finalproject.service;

import com.agungsetiawan.finalproject.dao.BookDao;
import com.agungsetiawan.finalproject.domain.Book;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author awanlabs
 */
@Service
@Transactional
public class BookServiceImpl implements BookService{
    
    private BookDao bookDao;
    
    @Autowired
    public BookServiceImpl(BookDao bookDao){
        this.bookDao=bookDao;
    }

    @Override
    public Book save(Book book) {
        return bookDao.save(book);
    }

    @Override
    public Book edit(Book book) {
        return bookDao.edit(book);
    }

    @Override
    public Book delete(Book book) {
        return bookDao.delete(book);
    }

    @Override
    public Book findOne(Long id) {
        Book bookFound=bookDao.findOne(id);
//        bookFound.setTitle(bookFound.getTitle()+" NEW");
        return bookFound;
    }

    @Override
    public List<Book> findAll() {
//        List<Book> books=bookDao.findAll();
//        books.add(new Book());
//        return books;
        return bookDao.findAll();
    }

    @Override
    public List<Book> findByCategory(Long id) {
        return bookDao.findByCategory(id);
    }

    @Override
    public List<Book> findByTitle(String title) {
        return bookDao.findByTitle(title);
    }

    @Override
    public List<Book> findRandom() {
        return bookDao.findRandom();
    }

    @Override
    public List<Book> findRandomFive() {
        return bookDao.findRandomFive();
    }
    
}
