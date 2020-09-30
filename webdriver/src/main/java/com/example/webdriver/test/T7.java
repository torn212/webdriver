package com.example.webdriver.test;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * webDriver dom元素定位
 */
public class T7 {

    public static void main(String[] args) {
        WebDriver webDriver = null;
        try {
            System.setProperty("webdriver.gecko.driver", "C:\\Users\\likun\\lk\\geckodriver.exe");
            FirefoxOptions options = new FirefoxOptions();
            webDriver = new FirefoxDriver(options);
            webDriver.manage().window().setSize(new Dimension(960,540));
            webDriver.manage().window().setPosition(new Point(326,40));

            webDriver.get("http://localhost:8080/a.html");

            /**
             *      xpath 方式
             *      一个/表示其子元素查找,//表示模糊匹配查找
             *      //*　　获取页面所有元素
             *      //*[@id='xxxx']　　@表示以id属性定位
             *      //input[@id='xxx']　　input表示以input标签名进行定位
             *      //input[1]　　如果出现不唯一的请求可以通过角标的形式取,注意角标从1开始
             *      //input[@id='xxx' and @class='xxx']　　#逻辑定位方法,通过多种属性确立唯一
             */
            TimeUnit.SECONDS.sleep(2);
            System.out.println("-----By.xpath(\"/html/body/div/div/ul/li\")------");
            List<WebElement> elements = webDriver.findElements(By.xpath("/html/body/div/div/ul/li"));
            for (WebElement element : elements) {
                String text = element.getText();
                System.out.println(text);
            }

            System.out.println("------By.xpath(\"/html/body/div/div/ul/li[1]\"-----");
            WebElement element = webDriver.findElement(By.xpath("/html/body/div/div/ul/li[1]"));
            System.out.println(element.getText());

            System.out.println("------By.xpath(\"/html/body/input[1]\")-----");
            WebElement element1 = webDriver.findElement(By.xpath("/html/body/input[1]"));
            System.out.println(element1.getAttribute("value"));

            System.out.println("--------By.xpath(\"//input[1]\")---------");
            WebElement element2 = webDriver.findElement(By.xpath("//input[1]"));
            System.out.println(element2.getAttribute("value"));

            System.out.println("--------By.xpath(\"//input[@type='text' and @name='input2']\")---------");
            WebElement element3 = webDriver.findElement(By.xpath("//input[@type='text' and @name='input2']"));
            System.out.println(element3.getAttribute("value"));


            /**
             *    #i1　　表示查找id属性为i1的元素
             * 　　.i1　　表示查找class属性为i1的属性
             * 　　[name='xxx']　　表示查找name属性定位
             * 　　input[name='xxx']　　可以通过标签名进行检索来缩小范围
             * 　　input#name　　查找input标签中id为name的元素
             * 　　input[name='xxx'][id='xxx'][class='xxx']　　input标签下多属性确立唯一
             * 　　input[name^='xxx']　　^支持前者模糊匹配
             * 　　input[name$='xxx']　　$支持后者模糊匹配
             * 　　input[name*='xxx']　　*表示包含,只要包含字段中的内容都可能会被匹配上
             * 　　css selector不支持角标取值,所以我们要灵活运用xpath和css selector结合
             *    详细可访问次文档：https://www.w3school.com.cn/cssref/css_selectors.asp
             */
            System.out.println("#########################");

            System.out.println("-----------By.cssSelector(\"#input3\")---------------");
            WebElement element4 = webDriver.findElement(By.cssSelector("#input3"));
            System.out.println(element4.getAttribute("value"));

            System.out.println("-----------By.cssSelector(\".text-input\")---------------");
            List<WebElement> elements1 = webDriver.findElements(By.cssSelector(".text-input"));
            System.out.println(elements1.size());

            System.out.println("-----------By.cssSelector(\"input.text-input\")---------------");
            List<WebElement> elements2 = webDriver.findElements(By.cssSelector("input.text-input"));
            System.out.println(elements2.size());

            System.out.println("-----------By.cssSelector(\"[name='input4']\")---------------");
            WebElement element5 = webDriver.findElement(By.cssSelector("[name='input4']"));
            System.out.println(element5.getAttribute("value"));

            System.out.println("-----------By.cssSelector(\"[name^='input']\")---------------");
            List<WebElement> elements3 = webDriver.findElements(By.cssSelector("[name^='input']"));
            for (WebElement webElement : elements3) {
                System.out.println(webElement.getAttribute("value"));
            }

            System.out.println("-----------By.cssSelector(\"[name$='4']\")---------------");
            elements3 = webDriver.findElements(By.cssSelector("[name$='4']"));
            for (WebElement webElement : elements3) {
                System.out.println(webElement.getAttribute("value"));
            }

            System.out.println("-----------By.cssSelector(\"[name*='put']\")---------------");
            elements3 = webDriver.findElements(By.cssSelector("[name*='put']"));
            for (WebElement webElement : elements3) {
                System.out.println(webElement.getAttribute("value"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            webDriver.close();
        }
    }

}
