package co.in.kayak.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import co.in.kayak.base.PageTest;
import co.in.kayak.pages.KayakLandingPage;

public class FlightBooking extends PageTest {

	public KayakLandingPage kayakLandingPage;

	@BeforeMethod
	public void beforeMethod() {
		initializePageObjects();
	}

	public void initializePageObjects() {
		kayakLandingPage = new KayakLandingPage();
	}

	@Test
	public void flightBooking() {
		kayakLandingPage.selectFlight();
	}
}