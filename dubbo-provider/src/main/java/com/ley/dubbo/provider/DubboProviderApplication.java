package com.ley.dubbo.provider;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.dubbo.container.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@EnableDubbo(scanBasePackages = "com.ley.dubbo.provider.service")
@EnableHystrix
//@ImportResource(locations = {"classpath:provider.xml"})
public class DubboProviderApplication {


    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(DubboProviderApplication.class);
        application.run(args);
        Main.main(args);
    }
}
