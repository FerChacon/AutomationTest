package com.bebo.utils;

import com.bebo.config.BrowserType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class DriverFactory {
    
    public static final String USERNAME = "fernando_chacon";
    public static final String ACCESS_KEY = "44975c70-d113-4446-b48f-133f5801805f";
    public static final String URL = "http://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:80/wd/hub";

    private DriverFactory(){

    }

    private static DriverFactory instance = new DriverFactory();

    public static DriverFactory getInstance(){
        return instance;
    }

    ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>(){
        @Override
        protected WebDriver initialValue(){
            return null;
        }
    };

    public WebDriver getDriver(){
        return driver.get();
    }

    public WebDriver setDriver(BrowserType browser){
        String getOS = System.getProperty("os.name").toLowerCase();
        String osName = "";
        if(getOS.contains("mac")){
            osName = "mac";
        }else if(getOS.contains("win")){
            osName = "windows";
        }else if(getOS.contains("nix") || getOS.contains("nux") || getOS.contains("aix")){
            osName = "linux";
        }
        
        String driverPath = System.getProperty("user.dir") + "/drivers/";

        switch (browser.toString()){
            case "REMOTE":
                DesiredCapabilities caps = DesiredCapabilities.chrome();
                caps.setCapability("platform", "Windows 10");
                caps.setCapability("version", "65.0");
                try {
                    driver.set(new RemoteWebDriver(new URL(URL), caps));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            case "CHROME":
                if(osName.equals("windows")){
                    System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");
                }else{
                    System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver");
                }
                driver.set(new ChromeDriver());
                break;
            case  "IE":
                if(osName.equals("windows")){
                    System.setProperty("webdriver.ie.driver", driverPath + "IEDriverServer.exe");
                }
                driver.set(new InternetExplorerDriver());
                break;
            case "FIREFOX":
                if(osName.equals("windows")){
                    System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver.exe");
                }else{
                    System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver");
                }
                driver.set(new FirefoxDriver());
                break;
            case "SAFARI":
                if(osName.equals("mac")){driver.set(new SafariDriver());}
                break;
            case "EDGE":
                if(osName.equals("windows")){
                    System.setProperty("webdriver.edge.driver", driverPath + "MicrosoftWebDriver.exe");
                }
        }
        int i = 10;

        for (int j = 1; j <= i; i++){
            try {
                driver.get().manage().window().maximize();
                break;
            }catch (WebDriverException we){
                driver.set(new ChromeDriver());
                driver.get().manage().window().maximize();
            }
            if (i == j){
               Assert.fail("Failed to maximize window " + j + " times");
            }
        }
        driver.get().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        return driver.get();
    }


    public void removeDriver(){
        driver.get().quit();
        driver.remove();
    }
}
