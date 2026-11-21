package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class HomePage {
	private ActionDriver actionDriver;

	// Define locators using By class
	private By adminTab = By.xpath("//span[text()='Admin']");
	private By userIDButton = By.className("oxd-userdropdown-name");
	private By logoutButton = By.xpath("//a[text()='Logout']");
	private By orangeHRMLogo = By.xpath("//*[@id=\"app\"]/div[1]/div[1]/aside/nav/div[1]/a/div[2]/img");

//	public HomePage(WebDriver driver) {
//		this.actionDriver = new ActionDriver(driver);
//	}

	public HomePage(WebDriver driver) {
		this.actionDriver = BaseClass.getactionDriver();
	}

	// Method to verify Admin tab is visible
	public boolean isAdminTabVisible() {
		return actionDriver.isDisplayed(adminTab);
	}

	public boolean verifyOrangeHRMlogo() {
		return actionDriver.isDisplayed(orangeHRMLogo);
	}

	// Method to perform logout operations
	public void logout() {
		actionDriver.click(userIDButton);
		actionDriver.click(logoutButton);

	}

}
