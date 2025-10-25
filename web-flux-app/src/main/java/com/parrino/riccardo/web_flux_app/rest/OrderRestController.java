package com.parrino.riccardo.web_flux_app.rest;

import org.springframework.web.bind.annotation.RestController;

import com.parrino.riccardo.web_flux_app.model.Order;
import com.parrino.riccardo.web_flux_app.service.OrderService;

import reactor.core.publisher.Flux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class OrderRestController {

    @Autowired
    private OrderService orderService;
    
    @GetMapping("api/order/findall")
    public Flux<Order> findAll() {
        return orderService.findAll();
    }
    

}
