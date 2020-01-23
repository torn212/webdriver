package com.example.webdriver.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * 监听页面加载是否完成
 */
public class T3 {
    public static void main(String[] args) {
        WebDriver webDriver = null;
        try {
            System.setProperty("webdriver.gecko.driver", "/Users/likun/Downloads/geckodriver");
            FirefoxOptions options = new FirefoxOptions();
            webDriver = new FirefoxDriver(options);

            long start = System.currentTimeMillis();
            webDriver.get("https://www.landchina.com/default.aspx");

            WebDriverWait wait = new WebDriverWait(webDriver, 10);
            wait.until(new ExpectedCondition<WebElement>() {
                @Override
                public WebElement apply(WebDriver d) {
                    return d.findElement(By.id("mainModuleContainer_1843_1844_1524_1784_1527_ctl00_popButton"));
                }
            });

            String body = webDriver.getPageSource();
            System.out.println(body);

            long end = System.currentTimeMillis();
            System.out.println(end - start);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            webDriver.close();
        }
    }
}
