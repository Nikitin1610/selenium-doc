package com.hanoi1610.tests;

import com.google.common.util.concurrent.Uninterruptibles;
import com.hanoi1610.listener.TestListener;
import com.hanoi1610.util.Config;
import com.hanoi1610.util.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
@Listeners({TestListener.class})
public abstract class AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(AbstractTest.class);
    protected WebDriver driver;

    @BeforeSuite
    public void setupConfig(){
        Config.initialize();
    }

    @BeforeTest
//    @Parameters({"browser"})
    public void setDriver(ITestContext ctx) throws MalformedURLException {
//        System.getProperty("selenium.grid.enabled")
        this.driver = Boolean.parseBoolean(Config.get(Constants.GRID_ENABLED)) ?
                getRemoteDriver() : getLocalDriver();
        ctx.setAttribute(Constants.DRIVER,this.driver);
    }

//        if(Boolean.getBoolean("selenium.grid.enabled")){
//            this.driver = getRemoteDriver(browser);
//        }else {
//            this.driver = getLocalDriver();
//        }
//        this.driver = new ChromeDriver();

    private WebDriver getRemoteDriver() throws MalformedURLException {
        Capabilities capabilities = new ChromeOptions();
        if(Constants.FIREFOX.equalsIgnoreCase(Config.get(Constants.BROWSER))){
            capabilities = new FirefoxOptions();
        }

//        if(System.getProperty("browser").equalsIgnoreCase("chrome")){
//        if(browser.equalsIgnoreCase("chrome")){
//            capabilities = new ChromeOptions();
//        }else {
//            capabilities = new FirefoxOptions();
//        }

        String urlFormat = Config.get(Constants.GRID_URL_FORMAT);
        String hubHost = Config.get(Constants.GRID_HUB_HOST);
        String url = String.format(urlFormat, hubHost);
        log.info("grid url: {}", url);
        return new RemoteWebDriver(new URL(url), capabilities);
    }


    private WebDriver getLocalDriver(){
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }

    @AfterTest
    public void quitDriver(){
        this.driver.quit();
    }

//    @AfterMethod
//    public void sleep(){
//        Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(5));
//    }


}