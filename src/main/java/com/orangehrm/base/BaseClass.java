package com.orangehrm.base;

import java.awt.SystemColor;
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

import com.orangehrm.actiondriver.ActionDriver;

public class BaseClass {

	protected static Properties prop;
//	protected static WebDriver driver;
//	private static ActionDriver actionDriver;

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private static ThreadLocal<ActionDriver> actionDriver = new ThreadLocal<>();

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
	// Synchronized method so that only one thread can use this method at a time.
	public synchronized void setup() {
		// Give Name of the class
		System.out.println("Setting up webDriver for:" + this.getClass().getSimpleName());
		launchBrowser();
		configureBrowser();
		staticWait(2);

		// Initialize the actiondriver only once
		/*
		 * if (actionDriver == null) { actionDriver = new ActionDriver(driver);
		 * System.out.println("ActionDriver instance is created."); }
		 */
//Initialize Actiondriver for the current Thread
		actionDriver.set(new ActionDriver(getDriver()));

	}

	// Synchronized method so that only one thread can use this method at a time.
	private synchronized void launchBrowser() {
		// Initialize the WebDriver based on browser defined in config.properties file
		String browser = prop.getProperty("browser");

		if (browser.equalsIgnoreCase("chrome")) {
			// driver = new ChromeDriver();
			driver.set(new ChromeDriver()); // New changes as per Thread
		} else if (browser.equalsIgnoreCase("firefox")) {
			// driver = new FirefoxDriver();
			driver.set(new FirefoxDriver()); // New changes as per Thread
		} else if (browser.equalsIgnoreCase("edge")) {
			// driver = new EdgeDriver();
			driver.set(new EdgeDriver()); // New changes as per Thread
		} else {
			throw new IllegalArgumentException("Browser Not Supported:" + browser);
		}
	}

	// Configure browser settings such as implicit wait, maximize the browser and
	// navigate to the url
	public void configureBrowser() {
		// Implicit Wait
		int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

		// Maximize the driver
		getDriver().manage().window().maximize();

		// Navigate to URL
		try {
			getDriver().get(prop.getProperty("url"));
		} catch (Exception e) {
			System.out.println("Failed to Navigate to the URL:" + e.getMessage());
		}
	}

	@AfterMethod
	//Synchronized method so that only one thread can use this method at a time. 
	public synchronized void tearDown() {
		if (getDriver() != null) {
			try {
				getDriver().quit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Unable to Quit the Driver:" + e.getMessage());
			}
		}
		System.out.println("Webdriver instance is closed.");
//		driver = null;
//		actionDriver = null;
		driver.remove();
		actionDriver.remove();

	}

//
//	//Driver getter method
//	public WebDriver getDriver() {
//		return driver;
//	}

	// Getter Method for webdriver
	public static WebDriver getDriver() {
		if (driver.get() == null) {
			System.out.println("WebDriver is not initialized");
			throw new IllegalStateException("WebDriver is not initialized");
		}
		return driver.get();

	}

	// Getter Method for Actiondriver
	public static ActionDriver getactionDriver() {
		if (actionDriver.get() == null) {
			System.out.println("ActionDriver is not initialized");
			throw new IllegalStateException("ActionDriver is not initialized");
		}
		return actionDriver.get();

	}

	// Getter method for prop
	public static Properties getProp() {
		return prop;
	}

	// Driver setter method
	public void setDriver(ThreadLocal<WebDriver> driver) {
		this.driver = driver;

	}

	// Static wait for pause
	public void staticWait(int seconds) {
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
	}
}
