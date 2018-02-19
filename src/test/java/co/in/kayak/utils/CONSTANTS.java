package co.in.kayak.utils;

public class CONSTANTS {
	public static String BASEURL = "https://www.kayak.co.in/";

	public static String CHROMEPATH = "./browserDriver/chromedriver.exe";
	public static String FIREFOXPATH = "./browserDriver/geckodriver.exe";
	public static String IEPATH = "./browserDriver/IEDriverServer.exe";

	public static String SCREENSHOTS = System.getProperty("user.dir") + "//Screenshots//";
	public static String PASSSCREENSHOTS = SCREENSHOTS + "//FailureScreenshots//";
	public static String FAILURESCREENSHOTS = SCREENSHOTS + "//FailureScreenshots//";
	public static String EXECUTIONSCREENSHOTS = SCREENSHOTS + "//ExecutionScreenshots//";

	public static String ORIGINLOCATION = "Delhi";
	public static String DESTINATIONLOCATION = "Chennai";
	public static String ORIGINDATE = "2018-02-19";
}