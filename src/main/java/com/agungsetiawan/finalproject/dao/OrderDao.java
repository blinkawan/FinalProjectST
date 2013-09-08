package com.agungsetiawan.finalproject.dao;

import com.agungsetiawan.finalproject.domain.Order;
import java.util.List;

/**
 *
 * @author awanlabs
 */
public interface OrderDao {
    public Order save(Order order);    
    public Order findOne(Long id);
    public List<Order> findByCustomer(String username);
    public List<Order> findAll();
    public Order edit(Order order);
}
