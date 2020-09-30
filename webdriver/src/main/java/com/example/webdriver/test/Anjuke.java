package com.example.webdriver.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * webDriver dom元素定位
 */
public class Anjuke {

    public static void main(String[] args) {
        WebDriver webDriver = null;
        try {
            System.setProperty("webdriver.gecko.driver", "/Users/likun/Downloads/geckodriver");
            FirefoxOptions options = new FirefoxOptions();
            webDriver = new FirefoxDriver(options);

            webDriver.get("https://www.anjuke.com/fangjia/quanguo2020/");

            waitPageLoad(webDriver,".main-content");

            List<WebElement> a = webDriver.findElement(By.cssSelector(".elem-l")).findElements(By.tagName("a"));
            for (WebElement webElement : a) {
                System.out.println(webElement.getAttribute("href"));
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            webDriver.close();
        }
    }


    private static void waitPageLoad(WebDriver webDriver, String css) {
        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        wait.until(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver d) {
                return d.findElement(By.cssSelector(css));
            }
        });
    }
}
