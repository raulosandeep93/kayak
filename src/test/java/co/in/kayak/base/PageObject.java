package co.in.kayak.base;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import co.in.kayak.utils.CONSTANTS;

public class PageObject extends PageTest {
	public static WebDriver driver = PageTest.driver;

	public void launchAUT(String url) {
		Reporter.log("Launching application under test..");
		driver.get(url);
		Reporter.log("Launched application under test..");
	}
	
	public void elementClick(WebElement element) {
		elementToBeClickable(element);
		element.click();
	}

	public void enterValue(WebElement element, String value) {
		elementToBeClickable(element);
		element.clear();
		element.sendKeys(value);
	}

	public void elementToBeClickable(WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (StaleElementReferenceException ex) {
			System.out.println("Stale Element exception:" + ex.toString());
		} catch (NoSuchElementException ex) {
			System.out.println("No such element exception:" + ex.toString());
		} catch (Exception ex) {
			System.out.println("Exception in elementToBeClickable method:" + ex.toString());
		}
	}

	public void elementToBeVisible(By by) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(by)));
		} catch (StaleElementReferenceException ex) {
			System.out.println("Stale Element exception:" + ex.toString());
		} catch (NoSuchElementException ex) {
			System.out.println("No such element exception:" + ex.toString());
		} catch (Exception ex) {
			System.out.println("Exception in elementToBeVisible method:" + ex.toString());
		}
	}

	public void waitForPageTitle(String pageTitle) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.titleContains(pageTitle));
		} catch (Exception ex) {
			System.out.println("Exception in elementToBeVisible method:" + ex.toString());
		}
	}

	public boolean selectValueFromDropdown(WebElement element, String value) {
		boolean cityFound = false;

		elementToBeClickable(element);

		List<WebElement> options = element.findElements(By.tagName("li"));
		System.out.println("Number of matching options: " + options.size());

		for (WebElement option : options) {
			String origin = option.getText();
			if (origin.equalsIgnoreCase(value) || origin.contains(value)) {
				cityFound = true;
				option.click();
				break;
			}
		}

		if (cityFound == true) {
			System.out.println("Expected value:" + value + " has been selected from the availabel list.");
		} else {
			System.out.println("Expected value:" + value + " is not present in the available list.");
		}
		
		return cityFound;
	}

	public String convertTodaysDate(String date, String monthYear) {
		String todaysDate = null;
		String currentMonth = null;
		String month = monthYear.split(" ")[0];
		switch (month) {
		case "January":
			currentMonth = "01";
			break;
		case "February":
			currentMonth = "02";
			break;
		case "March":
			currentMonth = "03";
			break;
		case "April":
			currentMonth = "04";
			break;
		case "May":
			currentMonth = "05";
			break;
		case "June":
			currentMonth = "06";
			break;
		case "July":
			currentMonth = "07";
			break;
		case "August":
			currentMonth = "08";
			break;
		case "September":
			currentMonth = "09";
			break;
		case "October":
			currentMonth = "10";
			break;
		case "November":
			currentMonth = "11";
			break;
		case "December":
			currentMonth = "12";
			break;
		}

		todaysDate = monthYear.split(" ")[1] + "-" + currentMonth + "-" + date;
		return todaysDate;
	}

	public String getAttributeText(By by) {
		elementToBeVisible(by);
		return driver.findElement(by).getText();
	}

	public String getMonth(String date) {
		String month = null;
		String splitMonth = date.split("-")[1];

		switch (splitMonth) {
		case "01":
			month = "January";
			break;
		case "02":
			month = "February";
			break;
		case "03":
			month = "March";
			break;
		case "04":
			month = "April";
			break;
		case "05":
			month = "May";
			break;
		case "06":
			month = "June";
			break;
		case "07":
			month = "July";
			break;
		case "08":
			month = "August";
			break;
		case "09":
			month = "September";
			break;
		case "10":
			month = "October";
			break;
		case "11":
			month = "November";
			break;
		case "12":
			month = "December";
			break;
		}
		return month;
	}

	public void takeScreenshot(String fileName) {
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(CONSTANTS.PROJECTLOCATION + fileName + ".jpg"));
		} catch (IOException ex) {
			System.out.println("IOException while storing screenshot." + ex.toString());
		} catch (Exception ex) {
			System.out.println("Exception while taking screenshot." + ex.toString());
		}
	}

	public void getWindowHandles(String title) {
		Set<String> allWindows = driver.getWindowHandles();

		boolean windowFound = false;
		
		for (String window : allWindows) {
			driver.switchTo().window(window);
			System.out.println("Page Title:" + driver.getTitle());
			if (driver.getTitle().contains(title) || driver.getTitle().equalsIgnoreCase(title)) {
				windowFound = true;
				break;
			} else {
				windowFound= false;
			}
		}
		
		if (windowFound) {
			Reporter.log("Window switched successfully.");
			System.out.println("Window switched successfully.");
		} else {
			Reporter.log("Unable to find the desired window.");
			System.out.println("Unable to find the desired window.");
		}
	}
}