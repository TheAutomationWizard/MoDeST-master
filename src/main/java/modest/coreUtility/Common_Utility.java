package modest.coreUtility;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.remote.MobileCapabilityType;




public class Common_Utility extends BaseTest{

	static Random random = new Random();
	
	
	
	
	
	
	// ***************************************************************************************************
 /**
  * @function - Step_Number => Used to count steps seperately for every executing thread. Helpful in parallel execution environment
  * @return - ThreadLocal <Integer> 
  * @author - https://github.com/AdityaAutomationTechniques   */
	private ThreadLocal <Integer> 	Step_Number = new ThreadLocal<Integer>() {
		@Override protected Integer initialValue() {
			return 1;
		}
	};


	
 /**
  * @function 			- 	stepPrinter => Used to pretty print the steps with descriptive message and step count. Extremely useful in parallel executing environment.
  * @param Step_Text	-	Description about the steps.
  * @param PassFail		-	PASS/FAIL nature of the step.
  * @return 			- 	{void}
  * @author - https://github.com/AdityaAutomationTechniques   */
	 public void stepPrinter(String Step_Text, String PassFail) {	 
		 System.out.println("\n\n********************************   Step Number "+Step_Number.get()+"\t"+PassFail+"    **************************************************");
		 System.out.println(driver().getCapabilities().getCapability("udid")+" : "+Step_Text);
		 System.out.println("***************************************************************************************************************\n\n");
		 Step_Number.set(Step_Number.get()+1);		 
	 }

	
//*********************************************           COMMON FUNCTIONS     **************************************************************	
	
/**
 * @function DesiredCapabilities	- 	Aggregates capabilities required for the test execution on Android Device.
 * @param Device_Udid				- 	{String} UDID of the device.
 * @return							- 	{DesiredCapabilities} an object of type DesiredCapabilities with all expected capabilities.
 * @throws InterruptedException
 * @author - https://github.com/AdityaAutomationTechniques   */
	public static synchronized DesiredCapabilities DesiredCapability_Setter( String Device_Udid  ) throws InterruptedException {

		DesiredCapabilities capabilities = new DesiredCapabilities();

		//Set the capabilities
		capabilities.setCapability("noReset", androidProperties.getProperty("noReset"));
		capabilities.setCapability("deviceName", androidProperties.getProperty("deviceName"));
		capabilities.setCapability("udid",Device_Udid);
		capabilities.setCapability("platformVersion", androidProperties.getProperty("platformVersion"));
		capabilities.setCapability("platformName", "Android");
		
		if(!androidProperties.getProperty("browserName").equalsIgnoreCase("") && androidProperties.getProperty("browserName") !=null )
			capabilities.setCapability("browserName", androidProperties.getProperty("deviceName"));

		else {
		if(!androidProperties.getProperty("appPackage").equalsIgnoreCase("") && androidProperties.getProperty("appPackage") !=null )
			capabilities.setCapability("appPackage", androidProperties.getProperty("appPackage"));
		
		if(!androidProperties.getProperty("appActivity").equalsIgnoreCase("") && androidProperties.getProperty("appActivity") !=null )
			capabilities.setCapability("appActivity", androidProperties.getProperty("appActivity"));
		}
		if( androidProperties.getProperty("DISABLE_KEYBOARD").equalsIgnoreCase("") | androidProperties.getProperty("DISABLE_KEYBOARD").equalsIgnoreCase("true")){
				capabilities.setCapability("unicodeKeyboard", true);
				capabilities.setCapability("resetKeyboard", true);
		}
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, androidProperties.getProperty("AUTOMATION_NAME"));
		capabilities.setCapability("wdaLocalPort", "8556");
		capabilities.setCapability("wdaStartupRetries", "4");
		capabilities.setCapability("iosInstallPause","8000" );
		capabilities.setCapability("wdaStartupRetryInterval", "20000");
		capabilities.setCapability("noSign", true);
		capabilities.setCapability("noReset", "false");
		
		
		System.out.println("\n\n===============================================");
		System.out.println("DESIRED CAPABILITIES SET FOR DEVICE AS: \ndeviceName       : "
				+ androidProperties.getProperty("deviceName") + "\nwith OS          : ANDROID \nPlatform version : "
				+ androidProperties.getProperty("platformVersion") + "\nwith UDID        : " + Device_Udid);
		System.out.println("===============================================");

		return capabilities;
	}	
	
	
	/**
	 * @function DesiredCapabilities	- 	Aggregates capabilities required for the test execution on iOS Device.
	 * @param Device_Udid				- 	{String} UDID of the device.
	 * @return							- 	{DesiredCapabilities} an object of type DesiredCapabilities with all expected capabilities.
	 * @throws InterruptedException
	 * @author - https://github.com/AdityaAutomationTechniques   */
	public static synchronized DesiredCapabilities DesiredCapability_Setter_iOS( String Device_Udid  ) throws InterruptedException {

		DesiredCapabilities capabilities = new DesiredCapabilities();

	    capabilities = new DesiredCapabilities();
		
	  /************           SELECTING APP PATH / BUNDLE ID OR BROWSER FOR AUTOMATION ************* @author - https://github.com/AdityaAutomationTechniques   */   
      
	  if(!iOSProperties.getProperty("browserName").equalsIgnoreCase("") || !iOSProperties.getProperty("browserName").equalsIgnoreCase(null)) {
		  capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, iOSProperties.getProperty("browserName"));
		  capabilities.setCapability("startIWDP", true);
      }
      
	  else if(!iOSProperties.getProperty("BUNDLE_ID").equalsIgnoreCase(null) || !iOSProperties.getProperty("BUNDLE_ID").equalsIgnoreCase("")) {
		  capabilities.setCapability(MobileCapabilityType.APP, iOSProperties.getProperty("BUNDLE_ID"));
      }
      else if(!iOSProperties.getProperty("ipa_PATH").equalsIgnoreCase(null) || !iOSProperties.getProperty("ipa_PATH").equalsIgnoreCase("")) {
		  capabilities.setCapability(MobileCapabilityType.APP, iOSProperties.getProperty("ipa_PATH"));
      }
      else if(!iOSProperties.getProperty("ipa_PATH").equalsIgnoreCase(null) || iOSProperties.getProperty("BUNDLE_ID").equalsIgnoreCase(null)) {
    	  System.out.println("\n\nUser must provide either AUT's \".ipa\" file path or its \"BundleID\" or BrowserName on which the automation suite will run. \n\n");
      }
      else {
    	  System.out.println("Browser Automation is being performed !");
      }

	  /**************        CONFIGURING OTHER CAPABILITIES         **************** @author - https://github.com/AdityaAutomationTechniques   */  
	  
      capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,  iOSProperties.getProperty("AUTOMATION_NAME"));
      capabilities.setCapability(MobileCapabilityType.UDID, 			Device_Udid);  
      capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, 		iOSProperties.getProperty("deviceName"));
      capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, 	"iOS");
      capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, iOSProperties.getProperty("platformVersion"));
      capabilities.setCapability("useNewWDA", true);                    //  required for series of execution on iOS devices. Has to be true.
 //   capabilities.setCapability("startIWDP", true);
      
      
	    
		
		System.out.println("\n\n===============================================");
		System.out.println("DESIRED CAPABILITIES SET FOR DEVICE AS: \ndeviceName       : "
				+ iOSProperties.getProperty("deviceName") + "\nwith OS          : iOS     \nPlatform version : "
				+ iOSProperties.getProperty("platformVersion") + "\nwith UDID        : " + Device_Udid);
		System.out.println("===============================================");

		return capabilities;
	}

		
	
	public static int random_number_generator( int min , int max ) {
		int randomNumber = random.nextInt(max + 1 - min) + min;
		return randomNumber;
	}


	 /**
	  * returnDynamicLocator - Used to form locator with dynamic payloads
	  * @param locator	- {By} the original locator structure with 'replaceText' written as variable in all places where values needs to be passed dynamically
	  * @param replaceText	- {String [] } this should contain min 1 and max = count of replaceText written in the locator, with actual values that needs to sit in the final locator.
	  * @param locatorType - {String}  the type of locator to be formed : xpath, id, name, className, cssSelector, tagName and partiallinkText.
	  * @return	- {By} the dynamic locator formed after re-writing the payloads in original locator.
	  */
	 public By returnDynamicLocator ( By locator, String[] replaceText, String locatorType) {
		 
		 String originalLocator = locator.toString().split("By.xpath: ")[1];
		 
		 for (String s : replaceText)
			 originalLocator = originalLocator.replaceFirst("replaceText", s);
		 
		 locatorType = locatorType.toLowerCase();
		 switch (locatorType) {
		  
		 case "xpath"		:  return By.xpath(originalLocator);
		 case "id"			:  return By.id(originalLocator);
		 case "name" 		:  return By.name(originalLocator);
		 case "classname" 	:  return By.className(originalLocator);
		 case "cssselector"	:  return By.cssSelector(originalLocator);
		 case "tagname"		:  return By.tagName(originalLocator);
		 case "linktext"	:  return By.linkText(originalLocator);
		 case "partiallinktext"	:  return By.partialLinkText(originalLocator);
		 
		 default : System.out.println("Invalid locator type. Only xpath, id, name, classname, tagname, linktext and partiallinktext are allowed. ");
		  return null;
		 }
		 
	 }

		
/***************************                       Report Generation  *********************************************** @author - https://github.com/AdityaAutomationTechniques   */
	
	/** 
	 * @function - generate_report => Generate a clean report with/without history of runs 
	 * @throws IOException
	 * @throws InterruptedException 
	 * @author - https://github.com/AdityaAutomationTechniques   */

	public void generate_report(boolean serverReport) throws IOException, InterruptedException {

		String OperatingSystem = System.getProperty("os.name");
		if ( OperatingSystem.contains("Windows"))	{
		
			System.out.println("Generating execution reports...");
		String Project_path = System.getProperty("user.dir").toString();

		String generatingReports = "allure generate --clean";
		String servingReports = "allure serve \"" + Project_path + "\\allure-results\"";

		String allure_results = Project_path + "/allure-results/history";
		String allure_reports = Project_path + "/allure-report/history";

		// GoTo the results folder and copy history file from previous reports to results
		try {

			String source = allure_reports;
			File srcDir = new File(source);
			String destination = allure_results;
			File destDir = new File(destination);

			FileUtils.copyDirectory(srcDir, destDir);
			System.out.println("Historical reports maintained.");
		} catch (Exception e) {
			System.out.println("This is either a fresh run or the run history has been deleted/moved. ");
		}

		String[] generateReports = new String[] { "cmd.exe", "/c", generatingReports };
		String[] serveReport = new String[] { "cmd.exe", "/c", servingReports };

		// Generate new Reports based on current Run
		try {
			// Generating Reports
			ProcessBuilder builder = new ProcessBuilder(generateReports);
			builder.redirectErrorStream(true);
			Process p = builder.start();

			p.waitFor();
			if (p.isAlive())
				p.destroy();
			System.out.println("Reports generation completed.");

			// Serving Reports
			if (serverReport) {
				builder = new ProcessBuilder(serveReport);
				builder.redirectErrorStream(true);
				p = builder.start();

				p.waitFor(10, TimeUnit.SECONDS);
				if (p.isAlive())
					p.destroy();

				System.out.println("Report is being served ...");

			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		}
		else if (OperatingSystem.contains("Mac"))
		{
			
			
			
		}
	}
	
	
 /**
  * @function	-	serve_report => Used to open the generated reports after test suite execution finishes.
  * @throws IOException
  * @throws InterruptedException
  * @author - https://github.com/AdityaAutomationTechniques   */
	@SuppressWarnings("null")
	@Deprecated
	public void serve_report(boolean serverReport) throws IOException, InterruptedException {
		Process p = null;
		if (serverReport) {

			String Project_path = System.getProperty("user.dir").toString();
			try {
				Project_path.replaceAll("/", "//");
			} catch (Exception e) {
				Project_path.replaceAll("\\", "\\\\");
			}

			String command1 = "cd " + Project_path;
			String command3 = "allure serve";

			p = Runtime.getRuntime().exec(command1);
			p = Runtime.getRuntime().exec(command3);

		} else {
			System.out.println("Report generated @ location " + System.getProperty("user.dir"));

		}
		try {
			Thread.sleep(10000);
			p.destroy();
		} catch (Exception e) {
		}
	}	
	
	
	

}
