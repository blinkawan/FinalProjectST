package com.agungsetiawan.finalproject.dao;

import com.agungsetiawan.finalproject.domain.Book;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author awanlabs
 */
@Repository
public class BookDaoHibernateImpl implements BookDao{
    
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Book save(Book book) {
        sessionFactory.getCurrentSession().save(book);
        return book;
    }

    @Override
    public Book edit(Book book) {
        sessionFactory.getCurrentSession().merge(book);
        return book;
    }

    @Override
    public Book delete(Book book) {
        sessionFactory.getCurrentSession().delete(book);
        return book;
    }

    @Override
    public Book findOne(Long id) {
        return (Book) sessionFactory.getCurrentSession().get(Book.class, id);
    }

    @Override
    public List<Book> findAll() {
        return sessionFactory.getCurrentSession().createQuery("select b from Book b").list();
    }

    @Override
    public List<Book> findByCategory(Long id) {
        return sessionFactory.getCurrentSession().createQuery("select b from Book b where b.category.id=:id")
                .setParameter("id", id).list();
    }

    @Override
    public List<Book> findByTitle(String title) {
        return sessionFactory.getCurrentSession().createQuery("select b from Book b where b.title like :title")
                .setParameter("title", "%"+title+"%").list();
    }

    @Override
    public List<Book> findRandom() {
        return sessionFactory.getCurrentSession().createQuery("SELECT b from Book b order by rand()").list();
    }

    @Override
    public List<Book> findRandomFive() {
        return sessionFactory.getCurrentSession().createQuery("SELECT b from Book b order by rand()")
                .setMaxResults(5).list();
    }
    
}
