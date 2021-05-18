package app_utility;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import io.qameta.allure.Step;
import modest.coreUtility.ElementsHandler;
import utility.baseTest;

public class Business_utility extends ElementsHandler {

	
	public  Business_utility (int WaitTime) {
		// This is used to set a wait time to resolve the exception dynamically for most common webdriver related exceptions.
		super(WaitTime);
	}
	
	baseTest bTest = new baseTest();
	
	/**
	 * login - to login into the appliation => demo function -> here i'll use some of the most commonly used methods.
	 * @param username
	 * @param password
	 * @throws Exception 
	 */
	
	@Step("Provide step defination. This is optional annotation, and is used to mark underlying method as a test step. Sample step description : User logged in as : {0} with password : {1}")
	public boolean login (String username, String password) throws Exception {
		
		// Almost all helper methods related to element interaction are present in elementsHandler class.
		// Most of the methods will accept either locator details as By or WebElement as parameter along with extra details.
		clickElement(By.id("sss"));
		
		// To wait for an element...
		waitForElementVisibility(bTest.obj().username, 1000); // via locator detials.  throw exception if not found.
		checkForElementToBeVisible(bTest.obj().password, 1000); // via locator details.  returns true/fase based on found or not.
		
		
		// Action on elements
		//(1) sending text..
		
		enterText(bTest.obj().username, username, true);   // via locator details, text to send, and boolean to either clear text field before sending text.
		enterText(driver().findElement(By.id("some id of element")), password, false); // via webElement , text to send, dont clear the field.
		
		// Click on the element 
		clickElement(bTest.obj().signinbtn); 	// click using locator details.
		clickElement(driver().findElement(By.id("some id of element"))); 	// click using webElement 
		
		
		
		// Swipe on screen
		swipeVertical(0.5, 0.3, 0.7, 200); 	// this can swipe vertical on screen anywhere based on co-ordinates provided. Later i'll provide detailed explanation.
		
		WebElement scrollBox = driver().findElement(By.xpath("scroll box xpath"));
		swipeInElement(scrollBox, true,0.5, 0.3, 0.7, 200); // WebElement inside which you have to scroll/swipe, true/false for horizontal/vertical swipe action, rest are co-ordinate details. more detailed explanation i'll provide later.
		
		
		
		return true;
	}
	
	
	
	
}
