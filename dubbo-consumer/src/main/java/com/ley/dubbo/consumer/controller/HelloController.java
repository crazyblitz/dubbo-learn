package com.ley.dubbo.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ley.dubbo.provider.service.HelloService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

    @Reference(version = "${hello.service.version}")
    private HelloService helloService;

    //客户端使用熔断器
    @HystrixCommand(fallbackMethod = "sayHelloError")
    @GetMapping("/sayHello/{name}")
    public String sayHello(@PathVariable String name) {
        return helloService.sayHello(name);
    }

    public String sayHelloError(String name) {
        return "error..." + name;
    }

}
