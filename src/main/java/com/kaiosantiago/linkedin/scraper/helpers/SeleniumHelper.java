package com.kaiosantiago.linkedin.scraper.helpers;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import io.github.bonigarcia.wdm.managers.EdgeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeleniumHelper {
    private static final Logger logger = LoggerFactory.getLogger(SeleniumHelper.class);
    private String browser;
    private Boolean headless;
    private String userAgent;
    private final WebDriver driver;

    public SeleniumHelper(String browser, Boolean headless, String userAgent) {
        this.browser = browser;
        this.headless = headless;
        this.userAgent = userAgent;
        this.driver = this.createDriver();
    }

    private WebDriver createDriver()
    {
        try{
            switch (this.browser.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions opt = new ChromeOptions();
                    if (headless) opt.addArguments("--headless=new");
                    opt.addArguments("--window-size=1920,1080");
                    opt.addArguments("--disable-gpu");
                    opt.addArguments("--no-sandbox");
                    if(userAgent != null && !userAgent.isBlank()) opt.addArguments("user-agent=" + userAgent);
                    return new ChromeDriver(opt);
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions optFirefox = new FirefoxOptions();
                    if (headless) optFirefox.addArguments("--headless");
                    optFirefox.addArguments("--width=1920");
                    optFirefox.addArguments("--height=1080");
                    if(userAgent != null && !userAgent.isBlank())
                        optFirefox.addPreference("general.useragent.override", userAgent);
                    return new FirefoxDriver(optFirefox);
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions optEdge = new EdgeOptions();
                    if(headless) optEdge.addArguments("--headless=new");
                    optEdge.addArguments("--window-size=1920,1080");
                    optEdge.addArguments("--disable-gpu");
                    optEdge.addArguments("--no-sandbox");
                    if(userAgent != null && !userAgent.isBlank()) optEdge.addArguments("user-agent=" + userAgent);
                    return new EdgeDriver();
                default:
                    throw new IllegalArgumentException("Navegador não suportado: " + this.browser);
            }
        }
        catch (WebDriverException e){
            logger.error("Erro ao criar driver do navegador {}", browser, e);
            throw new RuntimeException("Erro ao criar driver do navegador " + browser, e);
        }
    }
    public void openUrl(String url){
        try{
            driver.get(url);
        }catch (WebDriverException e) {
            logger.error("Erro ao abrir URL: " + url);
        }
    }

    public void close(){
        try{
            if(driver != null){
                driver.close();
            }
        }catch (WebDriverException e){
            logger.error("Erro ao fechar o driver {}", browser, e);
        }
    }
}
