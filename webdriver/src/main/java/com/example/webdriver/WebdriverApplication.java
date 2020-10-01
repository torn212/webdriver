package com.example.webdriver;

import com.example.webdriver.test.Anjuke3;
import com.example.webdriver.test.Anjuke4;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class WebdriverApplication implements CommandLineRunner {

    @Value("${source.file}")
    private String sourceFile;

    @Value("${json.file}")
    private String jsonFile;

    @Value("${target.file}")
    private String targetFile;

    @Value("${firefox.file}")
    private String fireFoxFile;

    @Value("${sleep.min}")
    private int min;

    @Value("${sleep.max}")
    private int max;

    @Value("${type}")
    private String type;


    public static void main(String[] args) {
        SpringApplication.run(WebdriverApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("执行类型：{}", type);
        if ("excel".equals(type)) {
            Anjuke4.init(jsonFile);
            Anjuke4.writeExcel(targetFile);
        } else {
            Anjuke3.start(sourceFile, jsonFile, targetFile, min, max, fireFoxFile);
        }
    }
}
