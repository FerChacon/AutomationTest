package com.bebo.pages;

import com.bebo.utils.DriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    WebDriverWait wait;

    @FindBy(name = "login")
    WebElement txtEmailAddress;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement btnNextLogin;

    @FindBy(name = "password")
    WebElement txtPassword;

    @FindBy(xpath = "//div[@class='form-error']")
    WebElement lblInvalidCredentials;

    public LoginPage(){
        PageFactory.initElements(DriverFactory.getInstance().getDriver(),this);
    }

    public void setEmailAddress(String email){
        txtEmailAddress.sendKeys(email);
    }

    public void clickNext(){
        btnNextLogin.click();
    }

    public void setPassword(String password){
        txtPassword.sendKeys(password);
    }

    public void clickLogIn(){
        btnNextLogin.click();
    }

    public String getInvalidLogin(){
        return lblInvalidCredentials.getText();
    }

    public boolean invalidLoginExist(){
        wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), 15);
        wait.until(ExpectedConditions.visibilityOf(lblInvalidCredentials));
        return true;
    }

    public void login(String email, String password){
        this.setEmailAddress(email);
        this.clickNext();

        wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), 15);
        wait.until(ExpectedConditions.visibilityOf(txtPassword));

        this.setPassword(password);
        this.clickLogIn();
    }
}
