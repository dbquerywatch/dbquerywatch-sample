package com.parolisoft.dbquerywatch.sample.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.parolisoft.dbquerywatch.sample")
@EnableJpaRepositories(basePackages = "com.parolisoft.dbquerywatch.sample.adapters.db")
@EntityScan(basePackages = "com.parolisoft.dbquerywatch.sample.adapters.db")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
