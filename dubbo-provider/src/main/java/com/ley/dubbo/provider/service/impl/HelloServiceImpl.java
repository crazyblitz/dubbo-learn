package com.ley.dubbo.provider.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.ley.dubbo.provider.service.HelloService;
import org.springframework.beans.factory.annotation.Value;

/**
 * @see Service
 **/
@Service(interfaceClass = HelloService.class, version = "${hello.service.version}", loadbalance = "roundrobin",retries = 3)
public class HelloServiceImpl implements HelloService {


    @Value("${dubbo.protocol.port}")
    private String serverPort;



    @Override
    public String sayHello(String name) { return "Hello: " + name + "\n I am from port " + serverPort;
    }

}
