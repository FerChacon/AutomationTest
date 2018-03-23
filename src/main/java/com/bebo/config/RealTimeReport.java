package com.bebo.config;

import com.bebo.utils.DriverFactory;
import com.bebo.utils.ExtentProperties;
import com.bebo.utils.SeleniumUtils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class RealTimeReport implements ITestListener {

    ExtentProperties exProp = new ExtentProperties();
    String extentReportFile = Constants.HTML_REPORT;;

    @Override
    public void onTestStart(ITestResult iTestResult) {
        exProp.setExtentTest(exProp.getExtent().startTest(iTestResult.getName(), iTestResult.getTestName()));
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        exProp.getExtentTest().log(LogStatus.PASS, iTestResult.getTestName() + " Passed");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        // In case you want to attach screenshot then use below method
        // We used a random image but you've to take screenshot at run-time
        // and specify the error image path.
        exProp.getExtentTest().log(LogStatus.FAIL, iTestResult.getTestName() + " Failed due to below issue : " +
                exProp.getExtentTest().addScreenCapture(SeleniumUtils.takeSnapShot(DriverFactory.getInstance().getDriver(), "error_")));
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        exProp.getExtentTest().log(LogStatus.SKIP, iTestResult.getTestName() + " Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {
        exProp.setExtent(new ExtentReports(extentReportFile, false));
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        exProp.getExtent().endTest(exProp.getExtentTest());
        exProp.getExtent().flush();
    }
}
