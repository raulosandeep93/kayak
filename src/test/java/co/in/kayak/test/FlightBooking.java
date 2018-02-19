package co.in.kayak.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import co.in.kayak.base.PageObject;
import co.in.kayak.base.PageTest;
import co.in.kayak.page.Flights;
import co.in.kayak.page.IternaryDetails;
import co.in.kayak.page.KayakLandingPage;
import co.in.kayak.utils.CONSTANTS;

public class FlightBooking extends PageTest {

	public KayakLandingPage kayakLandingPage;
	public Flights flights;
	public IternaryDetails iternaryDetails;	
	public PageObject pageObject;

	@BeforeMethod
	public void beforeMethod() {
		initializePageObjects();
	}

	public void initializePageObjects() {
		kayakLandingPage = new KayakLandingPage();
		flights = new Flights();
		pageObject = new PageObject();
		iternaryDetails = new IternaryDetails();
	}

	@Test
	public void flightBooking() {
		kayakLandingPage.selectFlight();
		
		flights.selectOneWay();
		flights.selectOrigin(CONSTANTS.ORIGINLOCATION);
		flights.selectDestination(CONSTANTS.DESTINATIONLOCATION);
		
		flights.selectOriginDate(CONSTANTS.ORIGINDATE);
		
		pageObject.takeScreenshot("Booking Details.");		
		
		flights.searchFlights();		
		iternaryDetails.closeOfferAlertWindow();
		
		pageObject.takeScreenshot("Iternary Details.");
		
		iternaryDetails.validateSearchResults();
	}
}