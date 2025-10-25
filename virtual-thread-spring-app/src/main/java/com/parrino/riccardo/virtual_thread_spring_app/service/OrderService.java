package com.parrino.riccardo.virtual_thread_spring_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parrino.riccardo.virtual_thread_spring_app.model.Order;
import com.parrino.riccardo.virtual_thread_spring_app.repository.OrderRepository;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

}
