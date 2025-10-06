package com.kaiosantiago.linkedin.scraper.helpers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SeleniumHelper {
    private final String browser;
    private final Boolean headless;
    private final String userAgent;
    private final WebDriver driver;

    public SeleniumHelper(String browser, Boolean headless, String userAgent) {
        this.browser = browser;
        this.headless = headless;
        this.userAgent = userAgent;
        this.driver = this.createDriver();
    }

    private By getBy(String by, String selector){
        switch (by.toLowerCase()){
            case "xpath":
                return By.xpath(selector);
            case "id":
                return By.id(selector);
            case "name":
                return By.name(selector);
            case "tag_name":
                return By.tagName(selector);
            case "class":
                return By.className(selector);
            default:
                throw new IllegalArgumentException("Tipo de argumento BY não suportado: " + by);
        }
    }

    private WebDriver createDriver() {
        try {
            switch (this.browser.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions opt = new ChromeOptions();
                    if (headless) opt.addArguments("--headless=new");
                    opt.addArguments("--window-size=1920,1080");
                    opt.addArguments("--disable-gpu");
                    opt.addArguments("--no-sandbox");
                    if (userAgent != null && !userAgent.isBlank()) opt.addArguments("user-agent=" + userAgent);
                    return new ChromeDriver(opt);

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions optFirefox = new FirefoxOptions();
                    if (headless) optFirefox.addArguments("--headless");
                    optFirefox.addArguments("--width=1920");
                    optFirefox.addArguments("--height=1080");
                    if (userAgent != null && !userAgent.isBlank())
                        optFirefox.addPreference("general.useragent.override", userAgent);
                    return new FirefoxDriver(optFirefox);

                case "edge":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions optEdge = new EdgeOptions();
                    if (headless) optEdge.addArguments("--headless=new");
                    optEdge.addArguments("--window-size=1920,1080");
                    optEdge.addArguments("--disable-gpu");
                    optEdge.addArguments("--no-sandbox");
                    if (userAgent != null && !userAgent.isBlank()) optEdge.addArguments("user-agent=" + userAgent);
                    return new EdgeDriver();

                default:
                    throw new IllegalArgumentException("Navegador não suportado: " + this.browser);
            }
        } catch (WebDriverException e) {
            throw new RuntimeException("Erro ao criar driver do navegador " + browser, e);
        }
    }

    public void openUrl(String url) {
        try {
            driver.get(url);
        } catch (WebDriverException e) {
            throw new RuntimeException("Erro ao abrir URL: " + url, e);
        }
    }

    public void setText(String by, String selector, String text, Boolean withDelayKeystrokes, int milliseconds){
        try{
            if(!withDelayKeystrokes){
                driver.findElement(this.getBy(by, selector)).sendKeys(text);
            }else{
                for(char c: text.toCharArray()){
                    driver.findElement(this.getBy(by, selector)).sendKeys(String.valueOf(c));
                    Thread.sleep(milliseconds);
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean elementExists(String by, String selector, int timeout){
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(this.getBy(by, selector)));
            return element != null;
        }catch (TimeoutException | NoSuchElementException e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void clickElement(String by, String selector){
        driver.findElement(this.getBy(by, selector)).click();
    }

    public void close() {
        try {
            if (driver != null) {
                driver.close();
            }
        } catch (WebDriverException e) {
            throw new RuntimeException("Erro ao fechar o driver " + browser, e);
        }
    }
}