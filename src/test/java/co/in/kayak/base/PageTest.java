package co.in.kayak.base;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import co.in.kayak.utils.CONSTANTS;

public class PageTest {
	public static WebDriver driver;
	
	@BeforeSuite
	public void beforeSuite() {
		Reporter.log("Inside Before Suite method.");
		killDriverInstance();
	}
	
	@Parameters({ "browserName" })
	@BeforeMethod
	public void beforeMethod(String browserName) {
		Reporter.log("Inside Before Method method.");
		switch (browserName) {
		case "Chrome":
			initChrome();
			break;
		case "Firefox":
			initFirefox();
			break;
		case "IE":
			initInternetExplorer();
			break;
		default:
			Reporter.log("Invalid browser name.");
			Assert.fail("Invalid browser name.");
		}
	}
	
	@AfterMethod
	public void afterMethod() {
		Reporter.log("Inside After Method method.");
	}
	
	@AfterSuite
	public void afterSuite() {
		Reporter.log("Inside After Suite Method.");
		if (driver != null) {
			killDriverInstance();
		}
	}
	
	public void killDriverInstance() {
		try {
			Process process = Runtime.getRuntime().exec(".\\KillDriver.bat");
			process.waitFor();
		} catch (IOException ex) {
			Reporter.log("IOException in accessing KillDriver.bat file:" + ex.toString());
		} catch (Exception ex) {
			Reporter.log("Exception in accessing KillDriver.bat file:" + ex.toString());
		}
	}

	public WebDriver  initChrome() {
		System.setProperty("webdriver.chrome.driver", CONSTANTS.CHROMEPATH);
		
		Reporter.log("Launching Chrome browser.");
		driver = new ChromeDriver();
		Reporter.log("Chrome browser launched.");
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30000, TimeUnit.MILLISECONDS);
		
		return driver;
	}

	public WebDriver  initFirefox() {
		System.setProperty("webdriver.gecko.driver", CONSTANTS.FIREFOXPATH);
		
		Reporter.log("Launching Firefox browser.");
		driver = new FirefoxDriver();
		Reporter.log("Firefox browser launched.");		
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30000, TimeUnit.MILLISECONDS);
		
		return driver;
	}

	public WebDriver initInternetExplorer() {
		System.setProperty("webdriver.ie.driver", CONSTANTS.IEPATH);
		
		Reporter.log("Launching Internet Explorer browser.");
		driver = new InternetExplorerDriver();
		Reporter.log("Internet Explorer browser launched.");		
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30000, TimeUnit.MILLISECONDS);
		
		return driver;
	}
}