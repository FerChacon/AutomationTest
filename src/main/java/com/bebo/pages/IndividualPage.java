package com.bebo.pages;

import com.bebo.utils.DriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class IndividualPage {

    @FindBy(xpath = "//h2[contains(text(),'Individual Plans')]")
    WebElement lnkIndividualPlan;

    @FindBy(linkText = "Sign Up")
    WebElement lnkSignUp;

    public IndividualPage(){
        PageFactory.initElements(DriverFactory.getInstance().getDriver(), this);
    }

    public void clickIndividualPlans(){
        lnkIndividualPlan.click();
    }

    public void clickSignUp(){
        lnkSignUp.click();
    }
}
