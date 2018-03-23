package com.bebo.config;

import com.bebo.pages.*;
import com.bebo.utils.DriverFactory;
import com.bebo.utils.ExtentProperties;
import com.bebo.utils.SeleniumUtils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BaseClass {
    public WebDriver driver;
    public ExtentProperties exProp = new ExtentProperties();
    String extentReportFile;
    public AllFilesPage allFiles;
    public DashboardPage dashboard;
    public HomePage home;
    public IndividualPage individual;
    public LoginPage login;
    public SignUpPage signUp;

    @AfterMethod
    public void getResult(ITestResult result){
        switch (result.getStatus()){
            case ITestResult.FAILURE:
                // In case you want to attach screenshot then use below method
                // We used a random image but you've to take screenshot at run-time
                // and specify the error image path.
                exProp.getExtentTest().log(LogStatus.FAIL, result.getName() + " Failed due to below issue : " +
                        exProp.getExtentTest().addScreenCapture(SeleniumUtils.takeSnapShot(DriverFactory.getInstance().getDriver(), "error_")));
                break;
            case ITestResult.SUCCESS:
                exProp.getExtentTest().log(LogStatus.PASS, result.getName() + " Passed");
                break;
            case ITestResult.SKIP:
                 exProp.getExtentTest().log(LogStatus.SKIP, result.getName() + " Skipped");
                 break;
        }
    }

    @BeforeTest
    public void setUpTest(){
        extentReportFile = Constants.HTML_REPORT;

        // Create object of extent report and specify the report file path.
        exProp.setExtent(new ExtentReports(extentReportFile, true));

        exProp.getExtent().loadConfig(new File(Constants.EXTENT_CONFIG));
        // Start the test using the ExtentTest class object.
        DriverFactory.getInstance().setDriver(BrowserType.REMOTE);
        driver = DriverFactory.getInstance().getDriver();

        allFiles = new AllFilesPage();
        home = new HomePage();
        login = new LoginPage();
        dashboard = new DashboardPage();
        individual = new IndividualPage();
        signUp = new SignUpPage();
    }

    @AfterTest
    public void tearDown(){
        DriverFactory.getInstance().removeDriver();
        exProp.getExtentTest().log(LogStatus.INFO, "Browser closed");

        // close report.
        exProp.getExtent().endTest(exProp.getExtentTest());

        // writing everything to document.
        exProp.getExtent().flush();
    }

    @AfterSuite
    public void openReport() throws IOException {
        Desktop.getDesktop().open(new File(Constants.HTML_REPORT));
    }

    public static void waitTime(int time){
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
