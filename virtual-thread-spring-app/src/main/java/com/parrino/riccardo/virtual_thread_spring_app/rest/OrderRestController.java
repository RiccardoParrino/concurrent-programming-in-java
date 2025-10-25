package com.parrino.riccardo.virtual_thread_spring_app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.parrino.riccardo.virtual_thread_spring_app.model.Order;
import com.parrino.riccardo.virtual_thread_spring_app.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class OrderRestController {
    
    @Autowired
    private OrderService orderService;

    @GetMapping("/api/order/findall")
    public List<Order> findAll() {
        return orderService.findAll();
    }

}
