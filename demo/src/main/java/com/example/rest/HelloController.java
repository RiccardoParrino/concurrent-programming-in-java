package demo.src.main.java.com.example.rest;

import io.micronaut.http.annotation.*;

@Controller("/hello")
public class HelloController {
    
    @Get
    public String sayHello(String name) {
        return "Hello " + name + "!"; 
    }

}
