package co.in.kayak.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import co.in.kayak.base.PageTest;
import co.in.kayak.page.Flights;
import co.in.kayak.page.KayakLandingPage;

public class FlightBooking extends PageTest {

	public KayakLandingPage kayakLandingPage;
	public Flights flights;

	@BeforeMethod
	public void beforeMethod() {
		initializePageObjects();
	}

	public void initializePageObjects() {
		kayakLandingPage = new KayakLandingPage();
		flights = new Flights();
	}

	@Test
	public void flightBooking() {
		kayakLandingPage.selectFlight();
		flights.selectOneWay();
		flights.selectOrigin();
		flights.selectDestination();
		flights.selectOriginDate();
		flights.searchFlights();
	}
}