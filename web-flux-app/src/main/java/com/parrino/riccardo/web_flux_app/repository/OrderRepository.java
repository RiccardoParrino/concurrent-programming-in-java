package com.parrino.riccardo.web_flux_app.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.parrino.riccardo.web_flux_app.model.Order;

import reactor.core.publisher.Flux;

@Repository
public class OrderRepository {
    
    private List<Order> orderList = List.of(
        new Order(1L, 1L, 1, 10.00),
        new Order(2L, 2L, 2, 100.00),
        new Order(3L, 3L, 10, 50.00),
        new Order(4L, 4L, 7, 70.00),
        new Order(5L, 4L, 12, 30.00),
        new Order(6L, 5L, 5, 20.00),
        new Order(7L, 1L, 4, 15.00)
    );

    public Flux<Order> findAll() {
        try{
            Thread.sleep(1000*10); // simulating heavy db ops
            return Flux.fromIterable(orderList);
        } catch (Exception e) {
            return null;
        }
    }
    
}
