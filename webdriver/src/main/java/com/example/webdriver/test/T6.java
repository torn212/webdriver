package com.example.webdriver.test;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * webDriver 常用函数和dom元素选择搜索
 */
public class T6 {

    public static void main(String[] args) {
        WebDriver webDriver = null;
        try {
            System.setProperty("webdriver.gecko.driver", "/Users/likun/Downloads/geckodriver");
            FirefoxOptions options = new FirefoxOptions();
            webDriver = new FirefoxDriver(options);

            webDriver.get("http://www.landchina.com/default.aspx?tabid=263");

            waitPageLoad(webDriver);

            webDriver.get("https://www.jd.com");
            String currentUrl = webDriver.getCurrentUrl();
            System.out.println(currentUrl);
            webDriver.navigate().back();
            webDriver.navigate().forward();
            webDriver.navigate().refresh();

            webDriver.manage().addCookie(new Cookie("111", "222"));
            webDriver.manage().deleteCookieNamed("111");
            Set<Cookie> cookies = webDriver.manage().getCookies();

            webDriver.manage().window().fullscreen();

            snapshot((TakesScreenshot) webDriver, "open_baidu.png");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            //webDriver.close();
        }
    }


    public static void snapshot(TakesScreenshot drivername, String filename) {
        try {
            File scrFile = drivername.getScreenshotAs(OutputType.FILE);
            File destFile = new File(filename);
            FileUtils.copyFile(scrFile, destFile);
            System.out.println("====snapshot===" + destFile.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void waitPageLoad(WebDriver webDriver) {
        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        wait.until(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver d) {
                return d.findElement(By.id("TAB_QueryButtonControl"));
            }
        });
    }
}
