package app_utility;

import org.openqa.selenium.By;



public class Locators {

	
	
/**
 * 	Describe all the locators below	as "By"
 * 	Segregate all locators against pages they are located in ..for easier maintenance.
 */

	
	public By testLocator, username, password, signinbtn;
	
	
	
	
	
	
	
/**
 * 		Assign values for all locators ==  based on the platform type.
 * 		Use Common objects to define locators if test flow is dependent on some extra
 * 		execution platform like base is Android with web application inclusion as extra. 
 * 	
 */
	

// Assign locator values to all the objects for Android device...	
	private void setAndroidObjects() 
	{
		/** Common Locators 		*/
		username = By.xpath("//*[contains(@resource-id,'id/input_phone')]");
		password = By.xpath("//*[contains(@resource-id,'id/input_password')]");
		
		
		
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	Assign locator values to all the objects for iOS device...	
	private void setiOSObjects() {
	}
//	Assign locator values to all the objects for Web browsers...	
	private void setWebObjects() {
	}

//	Assign Common Objects Here...extensively used in case of dependency on Web App if any. To set extra set of webpp locators..  	
	private void setCommonObjects() {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/**     This is FIXED for all projects. ______________PLEASE DO NOT MODIFY___________     **/	
	
	//	Constructor to assign objects based on the nature of the device.	
	public Locators(String DevicePlatform) {

		if (DevicePlatform.equalsIgnoreCase("Android")) {
			setAndroidObjects();
			setCommonObjects();
		} else if (DevicePlatform.equalsIgnoreCase("iOS")) {
			setiOSObjects();
			setCommonObjects();
		} else {
			setWebObjects();
			setCommonObjects();
		}
	}
	
}
