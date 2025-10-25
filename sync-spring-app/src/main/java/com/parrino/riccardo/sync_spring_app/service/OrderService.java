package com.parrino.riccardo.sync_spring_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parrino.riccardo.sync_spring_app.model.Order;
import com.parrino.riccardo.sync_spring_app.repository.OrderRespository;

@Service
public class OrderService {

    @Autowired
    private OrderRespository orderRespository;

    public List<Order> findAll() {
        return orderRespository.findAll();
    }

}
