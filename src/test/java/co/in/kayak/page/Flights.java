package co.in.kayak.page;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

import co.in.kayak.base.PageObject;
import co.in.kayak.utils.CONSTANTS;

public class Flights extends PageObject {

	public Flights() {
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//label[contains(@id,'-oneway-label')]")
	public WebElement link_oneway;

	@FindBy(xpath = "//input[contains(@id,'-origin-airport')]")
	public WebElement ddl_from;

	@FindBy(xpath = "//input[contains(@id,'-destination-airport')]")
	public WebElement ddl_to;

	@FindBy(xpath = "//div[contains(@id,'-origin-airport-smartbox-dropdown')]/ul")
	public WebElement ddl_fromAutoSuggestion;

	@FindBy(xpath = "//div[contains(@id,'-destination-airport-smartbox-dropdown')]/ul")
	public WebElement ddl_toAutoSuggestion;

	@FindBy(xpath = "//div[contains(@id,'-depart-input')]")
	public WebElement txt_departDate;

	@FindBy(xpath = "//div[@class='innerWrapper']//div[@class='contentContainer']//div[contains(@id,'-prev')]")
	public WebElement link_previousOption;

	@FindBy(xpath = "//div[@class='innerWrapper']//div[@class='contentContainer']//div[contains(@id,'-next')]")
	public WebElement link_nextOption;

	@FindBy(xpath = "//div[@class='innerWrapper']")
	public WebElement divCalenderInstance;

	@FindBy(xpath = "//div[contains(@id,'-201') and @aria-hidden='false']/div[@class='month']/div[@class='monthDisplay'][1]")
	public WebElement cal_currentMonth;

	@FindBy(xpath = "//div[@class='errorContent']/ul/li/ul/li")
	public WebElement txtErrorMessage;

	@FindBy(xpath = "//button[contains(@id,'-close')]")
	public WebElement btnOk;

	@FindBy(xpath = "(//button[contains(@id,'-submit')])[2]")
	public WebElement btnSearch;

	@FindBy(xpath="//a[@aria-label='KAYAK logo']")
	public WebElement kayakLogo;
	
	public void selectOneWay() {
		Reporter.log("Clicking on One Way link.");
		elementClick(link_oneway);
		Reporter.log("Clicked on One Way link.");
	}

	public void selectOrigin() {
		enterValue(ddl_from, CONSTANTS.ORIGINLOCATION);
		selectValueFromDropdown(ddl_fromAutoSuggestion, CONSTANTS.ORIGINLOCATION);
	}

	public void selectDestination() {
		enterValue(ddl_to, CONSTANTS.DESTINATIONLOCATION);
		selectValueFromDropdown(ddl_toAutoSuggestion, CONSTANTS.DESTINATIONLOCATION);
	}

	public void selectOriginDate() {
		String calTodaysDate = null;
		
		// Click on Depart textbox.
		elementClick(txt_departDate);
		
		// Wait for Calender instance to get pop-up.
		elementToBeVisible(cal_currentMonth);
		
		// Fetch all the cell values from the current calender instance month.
		List<WebElement> currentMonth = driver.findElements(By.xpath("//div[contains(@id,'-201') and @aria-hidden='false']//div[contains(@class,'col-day')]"));
		System.out.println("Number of days:" + currentMonth.size());
		
		// Iterate through all the cell values and fetch the current date.
		for (WebElement day : currentMonth) {
			if(day.getAttribute("class").contains("today")) {
				calTodaysDate = convertTodaysDate(day.getText(), getAttribute(cal_currentMonth,"text"));
				break;
			}
		}		
		
		if(validateTravelDate(calTodaysDate)) {
			List<WebElement> allDays = driver.findElements(By.xpath("//div[contains(@id,'-201') and @aria-hidden='false']//div[contains(@class,'col-day')]"));
			System.out.println("Number of days:" + allDays.size());
			
			for (WebElement day : allDays) {
				if(CONSTANTS.ORIGINDATE.split("-")[2].equals(day.getText())) {
					if(day.getAttribute("class").contains("empty") || day.getAttribute("class").contains("disabled")) {
						System.out.println("Booking for required date:" + CONSTANTS.ORIGINDATE + " is either a past date or not available.");
					} else {
						day.click();
						break;
					}
				}
			}
		}
}
	
	public boolean validateTravelDate(String CurrentDate) {
		// Split the origin date for validaiton purpose.
		String[] splittedDate = CONSTANTS.ORIGINDATE.split("-");
		
		// Expected month - fetched form origin date.
		String currentExpectedMonth = getMonth(CONSTANTS.ORIGINDATE) + " " + splittedDate[0];
		
		boolean monthFound = false;
		
		Date sysDate = null;
		Date oriDate = null;
		
		// Format the current date(from calender instance) and origin date(passed by user)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
    		sysDate = sdf.parse(CurrentDate);
            oriDate = sdf.parse(CONSTANTS.ORIGINDATE);
        } catch(ParseException ex) {
        	System.out.println("Unable to parse the date:" + ex.toString());
        	Assert.fail("Unable to parse the date:" + ex.toString());
        }
        
        // Validate if origin date falls before current date.
        if (oriDate.compareTo(sysDate) < 0) {
//            System.out.println("You are trying to booking a flight for a past date.");
            Assert.fail("You are trying to booking a flight for a past date.");
            monthFound = false;
        } 
        
        // Check for expected month and current month is same.
		if (currentExpectedMonth.equals(getAttribute(cal_currentMonth,"text"))) {
			monthFound = true;
		}

		// Check for expected month falls after current month
		// If Yes, Keep clicking on the next button till the calender instance is set to the desired month.
//		 if(!currentExpectedMonth.equals(getAttributeText(cal_currentMonth))) {
	    if (oriDate.compareTo(sysDate) > 0) {
        		do {
        			System.out.println("Current Month:Before Clicking:" + getAttribute(cal_currentMonth,"text"));
        			elementClick(link_nextOption);
        			System.out.println("Current Month:After Clicking:" + getAttribute(cal_currentMonth,"text"));
        			
        			// Check for current month after clicking on next button equals to the expected month.
        			if(getAttribute(cal_currentMonth,"text").equals(currentExpectedMonth)) {
        				monthFound = true;
        				break;
        			} 
        		} while(!getAttribute(link_nextOption,"class").contains("disabled"));
        	} 
   		
        	if(!monthFound) {
        		System.out.println("The month for booking needs to be done is either incorrect or the booking has not yet started.");
        		Assert.fail("The month for booking needs to be done is either incorrect or the booking has not yet started.");
        	}
        	
        	System.out.println("Month in focus:" + getAttribute(cal_currentMonth,"text"));
    		return monthFound;
        }

	public void searchFlights() {
		takeScreenshot("IternaryDetails");
		
		elementClick(btnSearch);
		elementToBeVisible(kayakLogo);
		
		getWindowHandles(CONSTANTS.ORIGINDATE.split("-")[2] + "/" + CONSTANTS.ORIGINDATE.split("-")[1].replaceAll("0", ""));
		takeScreenshot("FlightDetails");
	}
}