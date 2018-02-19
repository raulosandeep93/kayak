package co.in.kayak.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import co.in.kayak.base.PageObject;
import co.in.kayak.utils.CONSTANTS;
import junit.framework.Assert;

public class IternaryDetails extends PageObject {
	public IternaryDetails() {
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "")
	public WebElement txtEmailId;

	@FindBy(xpath = "//div[contains(@id,'-dialog-close')]")
	public WebElement closeButton;
	
	@FindBy(xpath="//div[@class='Flights-Results-BestFlights withChrome']//div[@class='Base-Results-HorizonResult Flights-Results-FlightResultItem ']")
	public List<WebElement> flightSearchResults;
	
	public void closeOfferAlertWindow() {
		String pageTitle = CONSTANTS.ORIGINDATE.split("-")[2] + "/" + CONSTANTS.ORIGINDATE.split("-")[1].replaceAll("0", "");

		String pageTitleWhileRender = "Compare vs KAYAK," + pageTitle;

		waitForPageTitle(pageTitleWhileRender);
		getWindowHandles(pageTitle);

		System.out.println("Closing the offer alert window.");
		pressEscapeButton();
		System.out.println("Offer alert window has been closed.");
	}
	
	public void validateSearchResults() {
		String originText = null;
		String destinationText = null;
		
		for (WebElement searchResult : flightSearchResults) {
			originText = searchResult.findElement(By.xpath("//div[contains(@class,'airport-codes')]/span[@class='origin']/span")).getAttribute("innerHTML");
			destinationText = searchResult.findElement(By.xpath("//div[contains(@class,'airport-codes')]/span[@class='destination']/span")).getAttribute("innerHTML");
			
			if(originText.contains(CONSTANTS.ORIGINLOCATION) && destinationText.contains(CONSTANTS.DESTINATIONLOCATION)) {
				System.out.println("The ORIGIN and DESTINATION location are as per the business requirements.");
			} else {
				System.out.println("There is some mis-match in the ORIGIN LOCATION:" + originText + " and/or in the DESTINATION LOCATION:" + destinationText);
				Assert.fail("There is some mis-match in the ORIGIN LOCATION:" + originText + " and/or in the DESTINATION LOCATION:" + destinationText);
			}
		}
	}
}