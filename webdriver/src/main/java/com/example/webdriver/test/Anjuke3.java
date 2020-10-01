package com.example.webdriver.test;

import com.alibaba.fastjson.JSON;
import com.example.webdriver.vo.Ssq;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * webDriver dom元素定位
 */
@Slf4j
public class Anjuke3 {
    static Queue<Ssq> all = new ConcurrentLinkedQueue<Ssq>();


    public static void start(String sourceFile, String jsonFile, String targetFile, int min, int max, String fireFoxFile) throws IOException {
        WebDriver webDriver = null;
        try {
            Anjuke4.init(jsonFile);
            System.setProperty("webdriver.gecko.driver", fireFoxFile);
            FirefoxOptions options = new FirefoxOptions();
            webDriver = new FirefoxDriver(options);

            LineIterator lineIterator1 = FileUtils.lineIterator(new File(sourceFile));
            while (lineIterator1.hasNext()) {
                try {
                    String s = lineIterator1.nextLine();
                    if (StringUtils.isBlank(s)) {
                        continue;
                    }
                    Ssq ssq = JSON.parseObject(s, Ssq.class);
                    if (ssq == null) {
                        continue;
                    }
                    String quUrl = ssq.getQuUrl();
                    String shiUrl = ssq.getShiUrl();
                    if (!quUrl.startsWith(shiUrl)) {
                        continue;
                    }
                    if ("全部".equals(ssq.getQu())) {
                        quUrl = shiUrl;
                    }

                    if (StringUtils.isBlank(quUrl)) {
                        continue;
                    }
                    if (Anjuke4.allKey.contains(ssq.getSheng() + ssq.getShi() + ssq.getQu())) {
                        continue;
                    }

                    for (int i = 2011; i <= 2020; i++) {
                        sleep(min, max);
                        String baseUrl = quUrl.replace("2020", i + "");
                        webDriver.get(baseUrl);
                        waitPageLoad(webDriver, ".main-content");
                        try {
                            List<WebElement> elements = webDriver.findElement(By.cssSelector(".fjlist-box")).findElements(By.cssSelector("a.nostyle"));
                            if (elements == null) {
                                continue;
                            }
                            for (WebElement element : elements) {
                                if (element == null) {
                                    continue;
                                }
                                String text = element.getText();
                                if (StringUtils.isBlank(text)) {
                                    continue;
                                }
                                String[] split = text.split("\n");
                                if (split.length != 3) {
                                    continue;
                                }
                                String date = split[0].replace("年", "").replace("月房价", "");
                                String price = split[1].replace("元/㎡", "");
                                ssq.getPrice().put(date, price);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            continue;
                        }
                    }
                    System.out.println(JSON.toJSON(ssq));
                    all.add(ssq);
                    FileUtils.writeLines(new File(jsonFile), all, true);
                    all.clear();
                } catch (Exception e) {
                    FileUtils.writeLines(new File(jsonFile), all, true);
                    all.clear();
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            FileUtils.writeLines(new File(jsonFile), all, true);
            all.clear();
        } finally {
            webDriver.close();
        }
        FileUtils.writeLines(new File(jsonFile), all, true);
        all.clear();
        Anjuke4.writeExcel(targetFile);
    }

    private static void waitPageLoad(WebDriver webDriver, String css) {
        WebDriverWait wait = new WebDriverWait(webDriver, 30);
        wait.until(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver d) {
                return d.findElement(By.cssSelector(css));
            }
        });
    }

    public static void sleep(int min, int max) {
        int bound = max - min;
        if (bound < 1) {
            return;
        }
        int i = new Random().nextInt(bound);
        try {
            int timeout = min + i;
            if (timeout < 1) {
                return;
            }
            TimeUnit.MILLISECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
