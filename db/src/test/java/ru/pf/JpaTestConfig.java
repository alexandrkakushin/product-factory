package ru.pf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@EntityScan({"ru.pf.entity"})
public class JpaTestConfig {

    public static void main(String[] args) {
        SpringApplication.run(JpaTestConfig.class, args);
    }
}
