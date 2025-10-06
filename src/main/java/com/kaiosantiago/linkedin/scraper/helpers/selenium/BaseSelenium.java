package com.kaiosantiago.linkedin.scraper.helpers.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public abstract class BaseSelenium {

    protected WebDriver createDriver(String browser, String userAgent, Boolean headless) {
        try {
            switch (browser.toLowerCase()) {
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
                    throw new IllegalArgumentException("Navegador não suportado: " + browser);
            }
        } catch (WebDriverException e) {
            throw new RuntimeException("Erro ao criar driver do navegador " + browser, e);
        }
    }
}
