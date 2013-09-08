package com.agungsetiawan.finalproject.dao;

import com.agungsetiawan.finalproject.domain.Category;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author awanlabs
 */
@Repository
public class CategoryDaoHibernateImpl implements CategoryDao{

    @Autowired
    SessionFactory sessionFactory;
    
    @Override
    public Category save(Category category) {
        sessionFactory.getCurrentSession().save(category);
        return category;
    }

    @Override
    public Category edit(Category category) {
        sessionFactory.getCurrentSession().merge(category);
        return category;
    }

    @Override
    public Category delete(Category category) {
        sessionFactory.getCurrentSession().delete(category);
        return category;
    }

    @Override
    public Category findOne(Long id) {
        return (Category) sessionFactory.getCurrentSession().get(Category.class, id);
    }

    @Override
    public List<Category> findAll() {
        return sessionFactory.getCurrentSession().createQuery("select c from Category c").list();
    }
    
}
