package com.example.webdriver.test;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.Set;

/**
 * webDriver 常用函数
 */
public class T6 {

    public static void main(String[] args) {
        WebDriver webDriver = null;
        try {
            System.setProperty("webdriver.gecko.driver", "C:\\Users\\likun\\lk\\geckodriver.exe");
            FirefoxOptions options = new FirefoxOptions();
            webDriver = new FirefoxDriver(options);
            webDriver.manage().window().setSize(new Dimension(960,540));
            webDriver.manage().window().setPosition(new Point(326,40));

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
            System.out.println("cookies:"+JSON.toJSON(cookies));

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
