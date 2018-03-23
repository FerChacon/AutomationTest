package com.bebo.pages;

import com.bebo.utils.DriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignUpPage {

    WebDriverWait wait;

    @FindBy(name = "userName")
    WebElement txtFullName;

    @FindBy(name = "userEmail")
    WebElement txtEmailAddress;

    @FindBy(name = "password")
    WebElement txtPassword;

    @FindBy(name = "userPhoneNumber")
    WebElement txtPhoneNumber;

    @FindBy(name = "userCountry")
    WebElement ddlUserCountry;

    @FindBy(xpath = "//div[@class='recaptcha-checkbox-checkmark']")
    WebElement dvRecaptcha;

    @FindBy(xpath = "(//button[contains(text(),' Continue')])[1]")
    WebElement btnContinue;

    @FindBy(xpath = "(//input[@name='bundle'])[2]")
    WebElement rdbBoxIndividual;

    @FindBy(xpath = "(//input[@name='bundle'])[2]")
    WebElement rdbBoxStarter;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement btnSubmit;

    @FindBy(xpath = "(//input[@name='currencyCode'])[1]")
    WebElement rdbCurrencyPesos;

    @FindBy(xpath = "(//input[@name='currencyCode'])[2]")
    WebElement rdbCurrencyDolars;

    @FindBy(xpath = "//h2[contains(text(),'We’ve Sent You a Confirmation Email')]")
    WebElement hdrConfirmation;

    public SignUpPage(){
        PageFactory.initElements(DriverFactory.getInstance().getDriver(),this);
    }

    public void setFullName(String fullName){
        txtFullName.sendKeys(fullName);
    }

    public void setEmailAddress(String email){
        txtEmailAddress.sendKeys(email);
    }

    public void setPassword(String password){
        txtPassword.sendKeys(password);
    }

    public void setPhoneNumber(String phone){
        txtPhoneNumber.sendKeys(phone);
    }

    public void selectCountry(String country){
        new Select(ddlUserCountry).selectByVisibleText(country);
    }

    public void clickRecaptcha(){
        dvRecaptcha.click();
    }

    public void clickContinue(){
        btnContinue.click();
    }

    public void selectBoxIndividual(){
        if(!rdbBoxIndividual.isSelected()) {rdbBoxIndividual.click();}
    }

    public void selectBoxStarter(){
        if(!rdbBoxStarter.isSelected()) {rdbBoxStarter.click();}
    }

    public void selectCurrencyPesos(){
        if(!rdbCurrencyPesos.isSelected()){rdbCurrencyPesos.click();}
    }

    public void selectCurrencyDolars(){
        if(!rdbCurrencyDolars.isSelected()){rdbCurrencyDolars.click();}
    }

    public void clickSubmit(){
        btnSubmit.click();
    }

    public String getConfirmation(){
        return hdrConfirmation.getText();
    }

    /**
     * This POM will be exposed in Test Case to sign up
     */
    public void userSignUp(String ... userData) throws InterruptedException {
        this.setFullName(userData[0]);
        this.setEmailAddress(userData[1]);
        this.setPassword(userData[2]);
        this.setPhoneNumber(userData[3]);
        this.selectCountry(userData[4]);
        this.clickRecaptcha();
        Thread.sleep(3000);
        this.clickContinue();
    }

    /**
     * This POM will be exposed in Test Case to submit
     */
    public String submit(String plan, String currency){
        if (plan.equalsIgnoreCase("box individual")) {
            this.selectBoxIndividual();
        } else {
            this.selectBoxStarter();
        }

        if(currency.equalsIgnoreCase("MXN")){
            this.selectCurrencyPesos();
        }else {
            this.selectCurrencyDolars();
        }

        this.clickSubmit();

        wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), 15);
        wait.until(ExpectedConditions.visibilityOf(hdrConfirmation));
        return this.getConfirmation();
    }
}
