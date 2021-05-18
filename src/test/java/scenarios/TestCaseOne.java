package scenarios;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import app_utility.Business_utility;
import modest.coreUtility.Common_Utility;
import modest.coreUtility.DriverSupplier;
import modest.reporting.modestReporting;
import utility.baseTest;




@Listeners(modestReporting.class)
public class TestCaseOne extends baseTest {
	
	
	Common_Utility util = new Common_Utility();
	Business_utility bl	= new Business_utility(60000);
	
	@BeforeMethod (alwaysRun=true)
	public void Testcaseone1() throws Exception {
		intiateAutomation();	
		
	}
	

	@Test
	public void Testcaseone() throws Exception {
		
		//intiateAutomation();	
		System.out.println("This is a dummy test script and won't work. Use this as a reference to script yours.");
		
		Assert.assertTrue(bl.login("9304663690", "Star@930"), " Failed to login");
			util.stepPrinter("Successfully logged in", "PASS");
		
	}

	
	
	@AfterMethod (alwaysRun=true)
	public void Testcaseone2() throws Exception {
		DriverSupplier.UDID_Resetter_digital(UDID());
		
		DriverSupplier.kill_APPIUM(service(), driver());
	}
	
	
	
}
