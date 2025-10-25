package com.parrino.riccardo.web_flux_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parrino.riccardo.web_flux_app.model.Order;
import com.parrino.riccardo.web_flux_app.repository.OrderRepository;

import reactor.core.publisher.Flux;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    
    public Flux<Order> findAll() {
        return orderRepository.findAll();
    }

}
