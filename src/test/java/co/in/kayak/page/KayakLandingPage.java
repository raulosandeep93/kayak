package co.in.kayak.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import co.in.kayak.base.PageObject;
import co.in.kayak.utils.CONSTANTS;

public class KayakLandingPage extends PageObject {

	public KayakLandingPage() {
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@href='/flights']")
	public WebElement link_flights;

	public void selectFlight() {
		launchAUT(CONSTANTS.BASEURL);
		
		System.out.println("Clcking on Flights options.");
		elementClick(link_flights);
		System.out.println("Flights option clicked successfully.");
	}
}