package com.orangehrm.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BaseClass {

	protected static Properties prop;
	protected WebDriver driver;

	@BeforeSuite
	public void loadConfig() throws IOException {
		// Load the config file
		prop = new Properties();
		// Read the config file
		FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
		// Load the config file
		prop.load(fis);
	}

	@BeforeMethod
	public void setup() {
		// Give Name of the class
		System.out.println("Setting up webDriver for:" + this.getClass().getSimpleName());
		launchBrowser();
		configureBrowser();
		staticWait(2);

	}

	private void launchBrowser() {
		// Initialize the WebDriver based on browser defined in config.properties file
		String browser = prop.getProperty("browser");

		if (browser.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		}

		else if (browser.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		} else {
			throw new IllegalArgumentException("Browser Not Supported:" + browser);
		}
	}

	//Configure browser settings such as implicit wait, maximize the browser and navigate to the url
	public void configureBrowser() {
		// Implicit Wait
		int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

		// Maximize the driver
		driver.manage().window().maximize();

		// Navigate to URL
		try {
			driver.get(prop.getProperty("url"));
		} catch (Exception e) {
			System.out.println("Failed to Navigate to the URL:" + e.getMessage());
		}
	}

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			try {
				driver.quit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Unable to Quit the Driver:" + e.getMessage());
			}
		}

	}
	
	//Getter method for prop
	public static Properties getProp() {
		return prop; 
	}

	//Driver getter method
	public WebDriver getDriver() {
		return driver;
	}

	//Driver setter method
	public void setDriver(WebDriver driver) {
		this.driver = driver;

	}

	// Static wait for pause
	public void staticWait(int seconds) {
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
	}
}
