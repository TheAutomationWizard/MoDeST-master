package utility;

import java.io.IOException;
import java.time.Instant;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import app_utility.Locators;
import modest.coreUtility.BaseTest;
import modest.coreUtility.Common_Utility;
import modest.coreUtility.DriverSupplier;

public class baseTest extends BaseTest implements Configuration_file{

	public static ThreadLocal<Locators> 		 	  		  obj_ = new ThreadLocal<Locators>();
	static Common_Utility CU = new Common_Utility();
	private   static Instant 		startTime ;
	private static int testMethodCounter =0;
	
	
	public void intiateAutomation() throws Exception {
		setScreenshottakeCriteria(true, false);
		Ready_Set_Go("",false, isAndroid, isiOS, isANDROID_iOS);
		obj_.set(new Locators(getDevicePlatform()));
		testMethodCounter++;
	}

	/**
	 * @function - obj => used to get the initialized object with Locators according
	 *           to nature of device (iOS or Android) based on thread calling the
	 *           method.
	 * @return - Locators object.
	 */
	public Locators obj() {
		return obj_.get();
	}

	
	
	@BeforeSuite
	public void Initial_Setup() throws IOException {
		startTime = Instant.now();
		readConfigurationProperites("",false, isAndroid, isiOS, isANDROID_iOS);
		DriverSupplier.Get_All_Connected_Devices(isAndroid, isiOS, isANDROID_iOS);
	}

	
	
	@AfterSuite
	public void Tear_Down() throws IOException, InterruptedException {
		//if (testMethodCounter > 0)
			//CU.generate_report(ServeReport);

		System.out.print("\n**********************************************************");
		System.out.print("\nTest Suite Execution Duration " + calcTimeDifference(startTime));
		System.out.println("\n**********************************************************\n");
	}	
		
}
