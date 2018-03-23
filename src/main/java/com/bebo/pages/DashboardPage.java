package com.bebo.pages;

import com.bebo.utils.DriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage {

    WebDriverWait wait;

    @FindBy(xpath = "(//button[@data-type='menu-toggle-button'])[3]")
    WebElement btnUser;

    @FindBy(partialLinkText = "Log Out")
    WebElement lnkLogOut;

    public DashboardPage(){
        PageFactory.initElements(DriverFactory.getInstance().getDriver(),this);
    }

    public void clickUserName(){
        btnUser.click();
    }

    public void clickLogOut(){
        lnkLogOut.click();
    }

    public boolean waitForUserAvatar(){
        wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), 15);
        wait.until(ExpectedConditions.visibilityOf(btnUser));
        return true;
    }
    public boolean logOut(){
        this.clickUserName();
        this.clickLogOut();
        return true;
    }

}
