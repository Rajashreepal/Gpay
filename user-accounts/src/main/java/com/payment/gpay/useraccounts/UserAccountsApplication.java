package com.payment.gpay.useraccounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.payment.gpay.transactions.repository",
        "com.payment.gpay.useraccounts.repository"})
@EntityScan(basePackages = "com.payment.gpay.common.models")
@ComponentScan(basePackages = {
        "com.payment.gpay.useraccounts",
        "com.payment.gpay.common",
        "com.payment.gpay.transactions"
})
public class UserAccountsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserAccountsApplication.class, args);
    }
}