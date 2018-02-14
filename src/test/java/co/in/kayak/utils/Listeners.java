package co.in.kayak.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class Listeners implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
		Reporter.log("Test Start" + result.getName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		Reporter.log("Test Success" + result.getName());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		Reporter.log("Test Failure" + result.getName());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		Reporter.log("Test Skipped" + result.getName());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		Reporter.log("Failed but with success percentage" + result.getName());
	}

	@Override
	public void onStart(ITestContext context) {
		Reporter.log("On Start" + context.getName());
	}

	@Override
	public void onFinish(ITestContext context) {
		Reporter.log("On Finish" + context.getName());
	}
}