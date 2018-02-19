package co.in.kayak.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import co.in.kayak.base.PageObject;
import co.in.kayak.base.PageTest;

public class Listeners extends PageObject implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
		// System.out.println("Test Start" + result.getName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("ExecutionStatus::" + result.getStatus());
		getTestResults(result);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("ExecutionStatus::FAIL::" + result.getName());
		getTestResults(result);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// System.out.println("Test Skipped" + result.getName());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// System.out.println("Failed but with success percentage" + result.getName());
	}

	@Override
	public void onStart(ITestContext context) {
		System.out.println("Execution has been started for :::" + context.getName() + "::: test case.");
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("Execution has been completed for :::" + context.getName() + "::: test case.");
	}
	
	public void takeScreenshot(String fileName, String status) {
		File scrFile = ((TakesScreenshot) PageTest.driver).getScreenshotAs(OutputType.FILE);
		try {
			if(status.equalsIgnoreCase("pass")) {
				FileUtils.copyFile(scrFile, new File(CONSTANTS.PASSSCREENSHOTS + fileName + ".jpg"));
			} else if(status.equalsIgnoreCase("fail")) {
				FileUtils.copyFile(scrFile, new File(CONSTANTS.FAILURESCREENSHOTS + fileName + ".jpg"));
			} else {
				//FileUtils.copyFile(scrFile, new File(CONSTANTS.+ fileName + ".jpg"));
			}
		} catch (IOException ex) {
			System.out.println("IOException while storing screenshot." + ex.toString());
		} catch (Exception ex) {
			System.out.println("Exception while taking screenshot." + ex.getMessage());
		}
	}
	
	public void getTestResults(ITestResult result) {
		switch (result.getStatus()) {
		case ITestResult.SUCCESS:
			takeScreenshot("ExecutionStatus_PASS_"+result.getMethod().getMethodName() + "_" + currentDateTime(), "PASS");
			break;
		case ITestResult.FAILURE:
			takeScreenshot("ExecutionStatus_FAIL_" +result.getMethod().getMethodName() + "_" + currentDateTime(), "FAIL");
			break;
		case ITestResult.SKIP:
			takeScreenshot("ExecutionStatus_SKIP_" +result.getMethod().getMethodName() + "_" + currentDateTime(), "SKIP");
		}
	}
}