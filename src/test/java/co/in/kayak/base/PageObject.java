package co.in.kayak.base;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import co.in.kayak.utils.CONSTANTS;
import junit.framework.Assert;

public class PageObject extends PageTest {
	
	public WebDriver driver = PageTest.driver;
	
	/**
	 * 
	 * To launch the applicataion under test.	 * 
	 * @param url - It defines the URL which needs to be triggered.
	 * 
	 */
	public void launchAUT(String url) {
		System.out.println("Launching application under test.");
		driver.get(url);
		System.out.println("Launched application under test.");
	}

	/**
	 * 
	 * Generic method to click an element.
	 * @param element - It takes an element as an parameter.
	 * 
	 */
	public void elementClick(WebElement element) {
		elementToBeClickable(element);
		element.click();
	}

	/**
	 * 
	 * Generic method to send values to input elements.
	 * @param element - The element with which action needs to be performed.
	 * @param value - The value which is supposed to be passed to the element.
	 * 
	 */
	public void enterValue(WebElement element, String value) {
		elementToBeClickable(element);
		element.clear();
		element.sendKeys(value);
	}

	/**
	 * 
	 * Generic method for implementing explicit wait for an element to be clickable.
	 * @param element - The element for which explicit wait needs to get implemented.
	 * 
	 */
	public void elementToBeClickable(WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (StaleElementReferenceException ex) {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			
			Assert.fail("Stale Element exception:" + ex.getMessage());
		} catch (NoSuchElementException ex) {
			System.out.println("No such element exception:" + ex.toString());
			Assert.fail("No such element exception:" + ex.toString());
		} catch (Exception ex) {
			System.out.println("Exception in elementToBeClickable method:" + ex.toString());
			Assert.fail("Exception in elementToBeClickable method:" + ex.toString());
		}
	}

	/**
	 * 
	 * Generic method for implementing explicit wait for an element to be visible.
	 * @param element - The element for which explicit wait needs to get implemented.
	 * 
	 */
	public void elementToBeVisible(WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 120);
			wait.until(ExpectedConditions.visibilityOf (element));
		} catch (StaleElementReferenceException ex) {
			System.out.println("Stale Element exception:" + ex.toString());
			Assert.fail("Stale Element exception:" + ex.toString());
		} catch (NoSuchElementException ex) {
			System.out.println("No such element exception:" + ex.toString());
			Assert.fail("No such element exception:" + ex.toString());
		} catch (Exception ex) {
			System.out.println("Exception in elementToBeVisible method:" + ex.toString());
			Assert.fail("Exception in elementToBeVisible method:" + ex.toString());
		}
	}

	/**
	 * 
	 * Generic method to explicitly wait for page title to change.
	 * @param pageTitle - It takes page title as a parameter.
	 */
	public void waitForPageTitle(String pageTitle) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.titleContains(pageTitle.split(",")[0]));
		} catch (Exception ex) {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.titleContains(pageTitle.split(",")[1]));
		}
	}
	
	/**
	 * 
	 * Generic method to select a value from auto-suggestion dropdown list.
	 * @param element - The element from which a value needs to get selected.
	 * @param value - The value which needs to get selected.
	 * @return - The method returns TRUE if the value is available in the dropdown list
	 * 					else the method returns FALSE if the value is not available in the dropdown list.
	 */
	public boolean selectValueFromDropdown(WebElement element, String value) {
		boolean cityFound = false;

		elementToBeVisible(element);

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
			System.out.println("Expected value:" + value + " is present in the dropdown list.");
//			System.out.println("Expected value:" + value + " is present in the dropdown list.");
		} else {
			System.out.println("Expected value:" + value + " is not present in the dropdown list.");
//			System.out.println("Expected value:" + value + " is not present in the dropdown list.");
			Assert.fail("Expected value:" + value + " is not present in the dropdown list.");
		}

		return cityFound;
	}

	/**
	 * 
	 * Generic method to format the date as per required by AUT.
	 * @param date - The date mentioned by the user to book the ticket.
	 * @param monthYear
	 * @return - It returns the formatted date as per required by AUT.
	 */
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

	/**
	 * 
	 * Generic method to get the attribute of an element.
	 * @param element - Element for which attribute needs to get fetched. 
	 * @return - It returns the required attribute value for the given element.
	 */
	public String getAttribute(WebElement element, String attribute) {
		elementToBeVisible(element);

		switch (attribute) {
		case "text":
			element.getText();
			break;
		case "class":
			element.getAttribute("class");
			break;
		default:
			System.out.println("Invalid attribute name.");
			Assert.fail("Invalid attribute name.");
		}
		return element.getText();
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
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(CONSTANTS.EXECUTIONSCREENSHOTS + fileName + "_" + currentDateTime() + ".jpg"));
		} catch (IOException ex) {
			System.out.println("IOException while storing screenshot." + ex.toString());
		} catch (Exception ex) {
			System.out.println("Exception while taking screenshot." + ex.getMessage());
		}
	}

	public void getWindowHandles(String title) {
		Set<String> allWindows = driver.getWindowHandles();
		System.out.println("Number of windows:" + allWindows.size());
		
		boolean windowFound = false;

		for (String window : allWindows) {
			driver.switchTo().window(window);
			System.out.println("Page Title:" + driver.getTitle());
			
			if (driver.getTitle().contains(title) || driver.getTitle().equalsIgnoreCase(title)) {
				windowFound = true;
				break;
			} else {
				windowFound = false;
			}
		}

		if (windowFound) {
			System.out.println("Window switched successfully.");
		} else {
			System.out.println("Unable to find the desired window.");
			Assert.fail("Unable to find the desired window.");
		}
	}

	public void pressEscapeButton() {
		try {
			Actions action = new Actions(driver);
			action.sendKeys(Keys.ESCAPE).build().perform();
		} catch (Exception ex) {
			System.out.println("Exception in pressEscapeButton method:" + ex.toString());
		}
	}
	
	public String currentDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
}