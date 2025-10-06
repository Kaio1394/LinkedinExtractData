package com.kaiosantiago.linkedin.scraper.helpers.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SeleniumHelper extends BaseSelenium {
    private final String browser;
    private final Boolean headless;
    private final String userAgent;
    private final WebDriver driver;

    public SeleniumHelper(String browser, Boolean headless, String userAgent) {
        this.browser = browser;
        this.headless = headless;
        this.userAgent = userAgent;
        this.driver = createDriver(this.browser, this.userAgent, this.headless);
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

    public void openUrl(String url) {
        try {
            driver.get(url);
        } catch (WebDriverException e) {
            throw new RuntimeException("Erro ao abrir URL: " + url, e);
        }
    }

    public void setText(String by, String selector, String text, Boolean withDelayKeystrokes, int milliseconds){
        try{
            driver.findElement(this.getBy(by, selector)).clear();
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

    public void sendKeystrokes(String by, String selector, Keys key){
        driver.findElement(getBy(by, selector)).sendKeys(key);
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