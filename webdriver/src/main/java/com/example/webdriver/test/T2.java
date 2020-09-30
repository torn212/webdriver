package com.example.webdriver.test;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
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
            System.setProperty("webdriver.gecko.driver", "C:\\Users\\likun\\lk\\geckodriver.exe");
            FirefoxOptions options = new FirefoxOptions();
            webDriver = new FirefoxDriver(options);
            webDriver.manage().window().setSize(new Dimension(960,540));
            webDriver.manage().window().setPosition(new Point(326,40));

            webDriver.get("https://www.landchina.com/default.aspx");
            TimeUnit.SECONDS.sleep(10);
            String title = webDriver.getTitle();
            String body = webDriver.getPageSource();
            System.out.println("title:"+title);
            System.out.println("body:"+body);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           // webDriver.close();
        }
    }
}
