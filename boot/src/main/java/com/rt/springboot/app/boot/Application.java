package com.rt.springboot.app.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.rt.springboot.app")
@EnableJpaRepositories("com.rt.springboot.app.adapter.driven")
@EntityScan("com.rt.springboot.app.adapter.driven")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
