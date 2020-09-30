package com.example.webdriver.test;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * 分页列表内容读取
 */
public class T5 {

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


            webDriver.findElement(By.id("TAB_QueryConditionItem270")).click();
            WebElement startInput = webDriver.findElement(By.id("TAB_queryDateItem_270_1"));
            WebElement endInput = webDriver.findElement(By.id("TAB_queryDateItem_270_2"));
            WebElement searchButton = webDriver.findElement(By.id("TAB_QueryButtonControl"));

            if (startInput != null) {
                startInput.clear();
                startInput.sendKeys("2019-10-01");
            }
            if (endInput != null) {
                endInput.clear();
                endInput.sendKeys("2019-10-01");
            }
            if (searchButton != null) {
                searchButton.click();
            }
            waitPageLoad(webDriver);

            List<WebElement> elements = webDriver.findElements(By.cssSelector("td.queryCellBordy>a"));
            for (WebElement element : elements) {
                System.out.println(element.getText() + "\t" + element.getAttribute("href"));
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            //webDriver.close();
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
