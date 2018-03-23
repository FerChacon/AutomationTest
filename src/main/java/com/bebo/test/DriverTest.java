package com.bebo.test;

import com.bebo.config.BaseClass;
import com.bebo.config.Constants;
import com.bebo.config.TestData;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DriverTest extends BaseClass {

    WebElement folderCreatedName;

    @Test(priority = 0, enabled = true, dataProvider = "BoxData", dataProviderClass = TestData.class)
    public void loginToBoxWithInvalidCredentials(String userName, String password) {
        exProp.setExtentTest(exProp.getExtent().startTest("Login With Valid Credentials",
                "Verify user is not able to login with incorrect password."));
        driver.get(Constants.URL);
        home.clickLogin();
        exProp.getExtentTest().log(LogStatus.INFO, "Clicking on Login Button");
        login.login(userName,password);
        exProp.getExtentTest().log(LogStatus.INFO, "Login using User: " + userName + " and Password: " + password);
        Assert.assertTrue(login.invalidLoginExist());
        exProp.getExtentTest().log(LogStatus.PASS, "Invalid Login text displayed " + login.getInvalidLogin());
    }

    @Test(priority = 1, enabled = true, dataProvider = "BoxData", dataProviderClass = TestData.class)
    public void loginToBoxWithValidCredentials(String userName, String password){
        exProp.setExtentTest(exProp.getExtent().startTest("Login With Valid Credentials",
                "Verify that user is able login to box with valid user account credentials."));
        driver.get(Constants.URL);
        home.clickLogin();
        exProp.getExtentTest().log(LogStatus.INFO, "Clicking on Login Button");
        login.login(userName,password);
        exProp.getExtentTest().log(LogStatus.INFO, "Login using User: " + userName + " and Password: " + password);
        Assert.assertTrue(dashboard.waitForUserAvatar());
        exProp.getExtentTest().log(LogStatus.PASS, "Accessing to Dashboard");
    }

    @Test(dependsOnMethods = {"loginToBoxWithValidCredentials"}, priority = 2, enabled = true,
            dataProvider = "BoxData", dataProviderClass = TestData.class)
    public void createNewFolder(String folderName, String emailInvite, String perrmission){
        exProp.setExtentTest(exProp.getExtent().startTest("Create New Folder",
                "Verify user is able to create a new folder in box account."));
        int startNumber = allFiles.getFoldersNumber();
        exProp.getExtentTest().log(LogStatus.INFO, "Number of folders is: " + startNumber);
        allFiles.createNewFolder(folderName, emailInvite, perrmission);
        waitTime(2);
        int finalNumber =  allFiles.getFoldersNumber();
        Assert.assertTrue(startNumber<finalNumber);
        exProp.getExtentTest().log(LogStatus.PASS, "Number of folders increased to: " + finalNumber);
        folderCreatedName = allFiles.getListFolders().get(0);
        String newFolderName = folderCreatedName.getText();
        exProp.getExtentTest().log(LogStatus.PASS, "Name of folder created is: " + newFolderName);
    }

    @Test(dependsOnMethods = {"loginToBoxWithValidCredentials"}, priority = 3, enabled = true,
            dataProvider = "BoxData", dataProviderClass = TestData.class)
    public void renameCreatedFolder(String newFolderName){
        exProp.setExtentTest(exProp.getExtent().startTest("Rename Folder",
                "Verify the user is able to rename the folder."));
        exProp.getExtentTest().log(LogStatus.INFO, "Renaming folder as: " + newFolderName);
        Assert.assertTrue(allFiles.renameFolderDirect(newFolderName));
        folderCreatedName = allFiles.getListFolders().get(0);
        waitTime(3);
        String renamedFolder = folderCreatedName.getText();
        exProp.getExtentTest().log(LogStatus.PASS, "New name of folder is: " + renamedFolder);
    }

    @Test(dependsOnMethods = {"loginToBoxWithValidCredentials"}, priority = 4, enabled = true)
    public void logoutFromBox(){
        exProp.setExtentTest(exProp.getExtent().startTest("LogOut from Box",
                "Verify user is successfully able to logout from the application."));
        Assert.assertTrue(dashboard.logOut());
        exProp.getExtentTest().log(LogStatus.PASS, "User logout successfully");
    }

    @Test(priority = 5, enabled = true, dataProvider = "BoxData", dataProviderClass = TestData.class)
    public void faledTestScreenShot(String userName, String password){
        exProp.setExtentTest(exProp.getExtent().startTest("Scenario to take screenshot",
                "Any failed test case should capture the screenshot of the page."));
        driver.get(Constants.URL);
        home.clickLogin();
        exProp.getExtentTest().log(LogStatus.INFO, "Clicking on Login Button");
        login.login(userName,password);
        exProp.getExtentTest().log(LogStatus.INFO, "Login using User: " + userName + " and Password: " + password);
        Assert.assertTrue(dashboard.waitForUserAvatar());
    }
}
