package com.bebo.pages;

import com.bebo.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    WebDriver driver;

    @FindBy(linkText = "Get Started")
    WebElement lnkGetStarted;

    @FindBy(linkText = "Login")
    WebElement lnkLogin;

    public HomePage(){
        //This initElements method will create all WebElements
        PageFactory.initElements(DriverFactory.getInstance().getDriver(),this);
    }

    //Click on Get Started Link
    public void clickGetStarted(){
        this.lnkGetStarted.click();
    }

    //Click on Login Link
    public void clickLogin(){
        this.lnkLogin.click();
    }
}
