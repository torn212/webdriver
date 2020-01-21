package com.example.webdriver;

import com.example.webdriver.test.T1;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebdriverApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(WebdriverApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
