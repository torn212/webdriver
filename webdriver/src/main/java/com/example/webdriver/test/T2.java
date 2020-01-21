package com.example.webdriver.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.concurrent.TimeUnit;

/**
 * WebDriver 爬取网页内容
 */
public class T2 {
    public static void main(String[] args) {
        WebDriver webDriver = null;
        try {
            System.setProperty("webdriver.gecko.driver", "/Users/likun/Downloads/geckodriver");
            FirefoxOptions options = new FirefoxOptions();
            webDriver = new FirefoxDriver(options);
            webDriver.get("https://www.landchina.com/default.aspx");
            TimeUnit.SECONDS.sleep(5);
            String title = webDriver.getTitle();
            String body = webDriver.findElement(By.cssSelector("body")).getAttribute("innerHTML");
            System.out.println(body);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            webDriver.close();
        }
    }
}
