package co.in.kayak.base;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
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
		System.out.println("Inside beforeSuite method.");
		killDriverInstance();
	}
	
	@Parameters({ "browserName" })
	@BeforeMethod
	public void beforeMethod(String browserName) {
		System.out.println("Inside beforeMethod method.");
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
			System.out.println("Invalid browser name.");
			Assert.fail("Invalid browser name.");
		}
	}
	
	@AfterMethod
	public void afterMethod() {
		System.out.println("Inside After Method method.");
	}
	
	@AfterSuite
	public void afterSuite() {
		System.out.println("Inside After Suite Method.");
		if(driver != null) {
//			killDriverInstance();
			driver.quit();
		}
	}
	
	public void killDriverInstance() {
		try {
			Process process = Runtime.getRuntime().exec(System.getProperty("user.dir") +  "//KillDriver.bat");
			process.waitFor();
		} catch (IOException ex) {
			System.out.println("IOException in accessing KillDriver.bat file:" + ex.toString());
		} catch (Exception ex) {
			System.out.println("Exception in accessing KillDriver.bat file:" + ex.toString());
		}
	}

	public void  initChrome() {
		System.setProperty("webdriver.chrome.driver", CONSTANTS.CHROMEPATH);
		
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("incognito");
		capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		
		System.out.println("Launching Chrome browser.");
		driver = new ChromeDriver(capabilities);
		driver.manage().timeouts().implicitlyWait(30000, TimeUnit.MILLISECONDS);
		System.out.println("Chrome browser launched.");
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		
	}

	public void  initFirefox() {
//		System.setProperty("webdriver.gecko.driver", CONSTANTS.FIREFOXPATH);
		
		System.out.println("Launching Firefox browser.");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30000, TimeUnit.MILLISECONDS);
		System.out.println("Firefox browser launched successfully.");		
		
		driver.manage().window().maximize();
	}

	public void initInternetExplorer() {
		System.setProperty("webdriver.ie.driver", CONSTANTS.IEPATH);
		
		System.out.println("Launching Internet Explorer browser.");
		driver = new InternetExplorerDriver();
		driver.manage().timeouts().implicitlyWait(30000, TimeUnit.MILLISECONDS);
		System.out.println("Internet Explorer browser launched.");		
		
		driver.manage().window().maximize();
	}
}