package modest.coreUtility;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.time.Instant;
import java.util.Properties;
import java.util.Stack;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;




public class BaseTest {
	
	static	DriverSupplier 						 DS = new DriverSupplier();
	static Common_Utility 						 CU = new Common_Utility();
	
	public static ThreadLocal<String> 					     UDID_ = new ThreadLocal<String>();
	public static ThreadLocal<AppiumDriver<WebElement>>   driver_  = new ThreadLocal<AppiumDriver<WebElement>>();
	public static ThreadLocal<AppiumDriverLocalService>  service_  = new ThreadLocal<AppiumDriverLocalService>();
	public static ThreadLocal<Long> timer							= new ThreadLocal<Long>();

	protected static Stack <String> androidUDID 	= new Stack<String>();
	protected static Stack <String> iOSUDID 		= new Stack<String>();
	
	static Properties androidProperties 	= new Properties();
	static Properties iOSProperties 		= new Properties();
	static String testDataFilePath   		= "";
	
	protected static boolean screenshotOnTestFailure =false, screenshotonTestSuccess =  false;
	
	public void readConfigurationProperites(String configuration,boolean checkTestData, boolean isAndroid, boolean isiOS, boolean any) throws IOException {
		
		String executionPath = System.getProperty("user.dir");
		String location = "";
		String OperatingSystem = System.getProperty("os.name");
		
		
		if ( OperatingSystem.contains("Windows")){
		
		if(configuration.equalsIgnoreCase("")) {
			try
			{
				if(isiOS | any)
					{
						location = executionPath+"\\src\\test\\resources\\configuration\\iOSconfiguration.properties";
							iOSProperties.load(new FileReader(location));
					}
				if(isAndroid | any )
					{
						location = executionPath+"\\src\\test\\resources\\configuration\\androidConfiguration.properties";
							androidProperties.load(new FileReader(location));
					}
				if(checkTestData)
				{
					testDataFilePath = executionPath+"\\src\\test\\resources\\dataBox\\TestData.xls";
//							executionProperties.load(new FileReader(location));
				}
			}
			catch (FileNotFoundException f)	{
				System.out.println("File not found at location : "+location);
				System.out.println("Sample path type : "+"\\src\\test\\resources\\configuration");
				throw new FileNotFoundException();
			}
			catch (IOException e) {
				System.out.println("IOException occured, while reading the properties file");
				throw new IOException();
			}
		}
		else
		{
			try
			{
				if(isiOS | any)
					{
						location = configuration+"\\iOSconfiguration.properties";
						iOSProperties.load(new FileReader(location));
					}
				if(isAndroid | any )
					{
						location = configuration+"\\androidConfiguration.properties";
								androidProperties.load(new FileReader(location));
					}
				if(checkTestData)
				{
					testDataFilePath = configuration+"\\TestData.xls";
//							executionProperties.load(new FileReader(location));
				}
			}
			catch (FileNotFoundException f)	{
				System.out.println("File not found at location : "+location);
				System.out.println("Sample realative path type : "+"\\src\\test\\resources\\configuration");
				throw new FileNotFoundException();
			}
			catch (IOException e) {
				System.out.println("IOException occured, while reading the properties file");
				throw new IOException();
			}
		}
	}
	else if (OperatingSystem.contains("Mac"))
	{
		if(configuration.equalsIgnoreCase("")) {
			try
			{
				if(isiOS | any)
					{
						location = executionPath+"//src//test//resources//configuration//iOSconfiguration.properties";
							iOSProperties.load(new FileReader(location));
					}
				if(isAndroid | any )
					{
						location = executionPath+"//src//test//resources//configuration//androidConfiguration.properties";
							androidProperties.load(new FileReader(location));
					}
				if(checkTestData)
				{
					testDataFilePath  = executionPath+"//src//test//resources//dataBox//TestData";
//							executionProperties.load(new FileReader(location));
				}
			}
			catch (FileNotFoundException f)	{
				System.out.println("File not found at location : "+location);
				System.out.println("Sample path type : "+"//src//test//resources//configuration");
				throw new FileNotFoundException();
			}
			catch (IOException e) {
				System.out.println("IOException occured, while reading the properties file");
				throw new IOException();
			}
		}
		else
		{
			try
			{
				if(isiOS | any)
					{
						location = configuration+"//iOSconfiguration.properties";
						iOSProperties.load(new FileReader(location));
					}
				if(isAndroid | any )
					{
						location = configuration+"//androidConfiguration.properties";
								androidProperties.load(new FileReader(location));
					}
				if(checkTestData)
				{
					testDataFilePath  = executionPath+"//TestData.xls";
//							executionProperties.load(new FileReader(location));
				}
			}
			catch (FileNotFoundException f)	{
				System.out.println("File not found at location : "+location);
				System.out.println("Sample realative path type : "+"//src//test//resources//configuration");
				throw new FileNotFoundException();
			}
			catch (IOException e) {
				System.out.println("IOException occured, while reading the properties file");
				throw new IOException();
			}
		}
		
	}
	}
	

	
  /**
   * @function 	-	Ready_Set_Go => Starter function that performs the following actions
   * 				1. Get one available device from the list of attached devices (based on device UDID)
   * 				2. Initializes all the object locators based on the nature of device selected for execution.
   * 				3. Start Appium server and creates a appium service to interact with chosen device.
   * 				4. Creates a new AppiumDriver and registers it to be used for calling test script.
   * 				5. Increments method counter for every TestNG test method executed.
   * @throws 	-	Exception
   * @author - https://github.com/AdityaAutomationTechniques 
   */
	
	public void Ready_Set_Go(String configurationDirectoryPath, boolean checkTestData, boolean testAndroid, boolean testiOS , boolean testAnyofAndroidiOS) throws Exception {		
//			readConfigurationProperites(configurationDirectoryPath,	checkTestData, testAndroid, testiOS , testAnyofAndroidiOS);
			UDID_.set(_Ready(testAndroid, testiOS , testAnyofAndroidiOS));
			service_.set(_Set(UDID_.get()));	
			driver_.set(_Go(UDID_.get(),service_.get()));
	}
	
 /**
  * @function 	- 	driver => returns the caller with appropriate instance of AppiumDriver accroding to nature of device (iOS or Android).
  * @return		-	AppiumDriver<WebElement>
  * @author - https://github.com/AdityaAutomationTechniques   */
	public AppiumDriver<WebElement> driver(){
		if (UDID_.get().toString().split("#")[1].equalsIgnoreCase("iOS"))  {    return (IOSDriver<WebElement>) driver_.get();     } 
		else return (AndroidDriver<WebElement>) driver_.get();
	}
	
 
  /** 
   * @function	-	service => used to get the Appium service based on the thread calling the method.
   * @return	-	AppiumDriverLocalService
   * @author - https://github.com/AdityaAutomationTechniques   */
	public AppiumDriverLocalService service() {
		return service_.get();
	}
	
 /**
  * @function	-	UDID => used to get the UDID of the device which is under control of the thread calling this method.	
  * @return		-	String.
  * @author - https://github.com/AdityaAutomationTechniques   */
	public String UDID() {
		return UDID_.get().toString();
	}
	
	/**
	 * getDevicePlatform -> return the OS signarture for thread asking the details corresponding to the device under control..
	 * @return
	 * @author - https://github.com/AdityaAutomationTechniques   */
	public String getDevicePlatform() {
		return (UDID_.get().split("#")[1]);
	}
	
	
	
	
	
 /**
  * @function 	- 	_Ready => used to get the UDID of the device from list of devices along with the OS signature of the picked device.
  * @return		-	j{String} - UDID+OS_sign 
  * @author - https://github.com/AdityaAutomationTechniques   */
	public static String  _Ready(boolean isAndroid, boolean isiOS, boolean isANDROID_iOS)
	{
		String UDID = null;
		if ( isAndroid | isANDROID_iOS )
		{
			UDID = DriverSupplier.udid_provider_digital(true);
			if (!UDID.equalsIgnoreCase("")) return UDID+"#Android"; 		
		}
		if (isiOS  | isANDROID_iOS )
		{
			UDID = DriverSupplier.udid_provider_digital(false);
			if (!UDID.equalsIgnoreCase(""))
				return UDID+"#iOS"; 	
		}
	
		return null;
	}	

 /**
  * @function	-	_Set => used to start the AppiumService on the picked device based on its UDID and OS.	
  * @param UDID	-	UDID of the device on which service will be created.
  * @return		-	{AppiumDriverLocalService}
  * @throws MalformedURLException
  * @throws InterruptedException
  * @author - https://github.com/AdityaAutomationTechniques   */
	public  AppiumDriverLocalService _Set(String UDID) throws MalformedURLException, InterruptedException
	{
		AppiumDriverLocalService service = null;

		if(UDID.split("#")[1].equalsIgnoreCase("Android")) {
			return DS.startService(service);
		}else if (UDID.split("#")[1].equalsIgnoreCase("iOS")) {
			return DS.startService_iOS(service);
		}

	return service;
	}		
	

 /**
  * @function	-	_Go	=> Used to start the server on the service for a given UDID.	
  * @param UDID	-	{String} UDID of the device.
  * @param service	- {AppiumDriverLocalService} service instance on which server will be started.
  * @return		- {AppiumDriver} an appopriate copy of created AppiumDriver to control the server and interact with the device.
  * @throws MalformedURLException
  * @throws InterruptedException
  * @author - https://github.com/AdityaAutomationTechniques   */
	public AppiumDriver<WebElement> _Go(String UDID,  AppiumDriverLocalService service) throws MalformedURLException, InterruptedException
	{
		synchronized (this.getClass())
		{
		AppiumDriver<WebElement>     driver = null;
		AndroidDriver<WebElement>   driver1 = null;
		IOSDriver<WebElement> 		driver2 = null;
		
		if(UDID.split("#")[1].equalsIgnoreCase("Android")) {  driver  = DS.startServer(UDID.split("#")[0], driver1, service);   return driver;   }
													  else {  driver  = DS.startServer_iOS(UDID.split("#")[0], driver2, service);  return driver;}
		}
		
	}
	
	public IOSDriver<IOSElement> _Go(String UDID,  AppiumDriverLocalService service, IOSDriver<IOSElement> driver) throws MalformedURLException, InterruptedException
	{		
		driver  = DS.createIOSdriver(UDID.split("#")[0], service, driver);  	
		return driver;
	}	
	
	public AndroidDriver<MobileElement> _Go(String UDID,  AppiumDriverLocalService service, AndroidDriver<MobileElement> driver) throws MalformedURLException, InterruptedException
	{		
		driver  = DS.createAndroidDriver(UDID.split("#")[0], driver, service );  	
		return driver;
	}	
	
	
	
 /**
  * @function - 	calcTimeDifference => to calculate time elapsed from a certain time stamp.
  * @param start	- {Instant} starting of the time stamp from when the elapsed time will be measured.
  * @return
  * @author - https://github.com/AdityaAutomationTechniques   */
	public String calcTimeDifference( Instant start)
	{
		long timeElapsed = Duration.between(start, Instant.now()).getSeconds();
		long hours = timeElapsed / 3600;
		long minutes = (timeElapsed % 3600) / 60;
		long seconds = timeElapsed % 60;

		return  String.format("%02d:%02d:%02d", hours, minutes, seconds);

	}
 
 /**
  * @function	-		resolveIntoTime => used to convert time in milliseconds into timer format HH:MM:SS
  * @param timeMilliSeconds	- {milliseconds} total time in milliseconds.
  * @return
  * @author - https://github.com/AdityaAutomationTechniques   */
	public String resolveIntoTime( long timeMilliSeconds)
	{
		long hours	= 	(timeMilliSeconds/1000)/ 3600;
		long minutes=	((timeMilliSeconds/1000)%3600)/60; 
		long seconds= 	((timeMilliSeconds/1000)%60);
		return  String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}
	
	public String calcTimeDifference (Instant start, Instant end)
	{
		long timeElapsed = Duration.between(start, end).getSeconds();
		long hours = timeElapsed / 3600;
		long minutes = (timeElapsed % 3600) / 60;
		long seconds = timeElapsed % 60;

		return  String.format("%02d:%02d:%02d", hours, minutes, seconds);
		
	}


	public void setScreenshottakeCriteria (boolean onTestFailure, boolean onTestPass) {
		screenshotOnTestFailure = onTestFailure;
		screenshotonTestSuccess = onTestPass;
				
	}
}
