package com.example.webdriver.test;

import com.alibaba.fastjson.JSON;
import com.example.webdriver.vo.Ssq;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * webDriver dom元素定位
 */
public class Anjuke2 {
    static List<Ssq> all = new ArrayList<>();

    public static void main(String[] args) {
        WebDriver webDriver = null;
        FirefoxDriver webDriver2 = null;
        try {
            System.setProperty("webdriver.gecko.driver", "/Users/likun/Downloads/geckodriver");
            FirefoxOptions options = new FirefoxOptions();
            webDriver = new FirefoxDriver(options);
            webDriver2 = new FirefoxDriver(options);

            LineIterator lineIterator1 = FileUtils.lineIterator(new File("/Users/likun/a.txt"));
            while (lineIterator1.hasNext()) {
                String s = lineIterator1.nextLine();
                if (StringUtils.isBlank(s)) {
                    continue;
                }
                webDriver.get(s);
                waitPageLoad(webDriver, ".main-content");
                try {
                    List<WebElement> a = webDriver.findElement(By.cssSelector(".sub-items")).findElements(By.tagName("a"));
                    for (WebElement webElement : a) {
                        String shiUrl = webElement.getAttribute("href");
                        String shi = webElement.getText();
                        String sheng = webDriver.findElement(By.cssSelector("span.selected-item")).getText();
                        queryQu(webDriver2, shiUrl, sheng, shi);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                TimeUnit.SECONDS.sleep(1 + new Random().nextInt(4));
            }
            queryPrice(webDriver, all);
            FileUtils.writeLines(new File("/Users/likun/call.txt"), all);

            try {
                Runtime.getRuntime().exec("shutdown /s /t  60");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            webDriver.close();
            webDriver2.close();
        }
    }

    private static void queryPrice(WebDriver webDriver, List<Ssq> all) {
        for (Ssq ssq : all) {
            String url = ssq.getQuUrl();
            if (StringUtils.isBlank(url)) {
                url = ssq.getShiUrl();
            }
            if (StringUtils.isBlank(url)) {
                continue;
            }
            webDriver.get(url);
            waitPageLoad(webDriver, ".main-content");
            List<WebElement> a = webDriver.findElement(By.cssSelector("div.fjlist-box")).findElements(By.tagName("a"));
            for (WebElement webElement : a) {
                WebElement b1 = null;
                WebElement b2 = null;
                try {
                    b1 = webElement.findElement(By.tagName("b"));
                    b2 = webElement.findElement(By.tagName("span"));
                } catch (Exception e) {
                    continue;
                }
                ssq.getPrice().put(b1.getText(), b2.getText());
            }
        }
    }

    private static void queryQu(FirefoxDriver webDriver2, String shiUrl, String sheng, String shi) {
        try {
            Ssq shiSsq = new Ssq();
            shiSsq.setSheng(sheng);
            shiSsq.setShi(shi);
            shiSsq.setShiUrl(shiUrl);
            shiSsq.setQu("全部");
            shiSsq.setQuUrl("");
            all.add(shiSsq);

            webDriver2.get(shiUrl);
            waitPageLoad(webDriver2, ".main-content");
            List<WebElement> a = webDriver2.findElement(By.cssSelector(".elem-l")).findElements(By.tagName("a"));
            for (WebElement webElement : a) {
                String quUrl = webElement.getAttribute("href");
                String qu = webElement.getText();
                Ssq ssq = new Ssq();
                ssq.setQu(qu);
                ssq.setQuUrl(quUrl);
                ssq.setSheng(sheng);
                ssq.setShi(shi);
                ssq.setShiUrl(shiUrl);
                System.out.println(JSON.toJSON(ssq));
                all.add(ssq);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
