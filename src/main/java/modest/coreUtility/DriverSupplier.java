package modest.coreUtility;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.InsufficientResourcesException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;




public class DriverSupplier extends BaseTest{

	  
    public  static  AppiumServiceBuilder appiumServiceBuilder;
	public 			AppiumServiceBuilder builder;
	public 			DesiredCapabilities cap;
	
	public static URL url;
	public static DesiredCapabilities capabilities;
	  
  

	
	
	//*************************************************************        Driver Provider     **********************************************************	
	  
  /**
   * Connected_Devices	 -> This will fetch the list of Connected UDIDs and store it in a object of ArryaList<Set<String>>.
   * @param isANDROID	 -> yes to store list of all android devices.
   * @param isiOS		 -> yes to store list of all iOS based devices.
   * @param isBOTH		 -> yes to store list of both android/iOS devices.
   * @return 			 -> ArrayList<Set<UDID>> ; UDID is the unique id of all connected devices.
   * @author - https://github.com/AdityaAutomationTechniques   */
	
	public static ArrayList<Set<String>> Connected_Devices(boolean isANDROID, boolean isiOS ,boolean isBOTH)
	{

	ArrayList<Set<String>> allDevices = new ArrayList<Set<String>>();
	
	if(isBOTH == true) {	isANDROID = true; isiOS =true;     }
	
	Set<String> 		Androids 	= new LinkedHashSet<String>();
	Set<String> 		iDevices 	= new LinkedHashSet<String>();
	List<Pattern> 		DevicePattern = new ArrayList<Pattern>();
	ArrayList<String> 	adbInput	= new ArrayList<String>();
	Matcher matcher;
	
	String OperatingSystem = System.getProperty("os.name");
	System.out.println("\n=================================================\nHost Operating System\t"+OperatingSystem+"\n=================================================\n\n");

	if ( OperatingSystem.contains("Windows"))
	{
		
		if(isANDROID==true) {
		try {
			
			
        Process process = Runtime.getRuntime().exec("adb devices");
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));  
        String line = null;  
        while ((line = in.readLine()) != null)  
        	adbInput.add(line);

        DevicePattern.add(Pattern.compile("((\\d+[.:]*){4}\\d{4,})\\s*(device)"));	// Regex is to pick devices over WiFi (via TCP )
        DevicePattern.add(Pattern.compile("([a-zA-Z0-9]{1,})\\s*(device)"));		// Regex is to pick devices mobile devices connected via USB
        DevicePattern.add(Pattern.compile("((\\w+[-]*)*)\\s*(device)"));			// Regex is to pick emulators
        
        
        for (Pattern pattern : DevicePattern) {	        
        	for(String s : adbInput) {
        		if (s.matches(pattern.pattern())) {
	                matcher = pattern.matcher(s);
	                if (matcher.find())
	                	Androids.add(matcher.group(1));
//	                System.out.println(matcher.group(1)   +"\t|\tANDROID");
	            	}
	        	}
        	}	     
     
		}catch (IOException e) {
        e.printStackTrace();
			}
		}
		if(isiOS==true && !isBOTH) {
		try {
			throw new InvalidKeyException("\n===================================================\n                       iOS device automation not supported with WINDOWS  Host Machine   ...yet\n===================================================\n\n");
			}catch (InvalidKeyException e) {
			e.printStackTrace();
			System.exit(1);
		}		
		}	
	}
	
	
	else if (OperatingSystem.contains("Mac"))
	{
		//************   ANDROID
		if(isANDROID==true) {
			try {
				
				
		        Process process = Runtime.getRuntime().exec("adb devices");
		        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));  
		        String line = null;  
		        while ((line = in.readLine()) != null)  
		        	adbInput.add(line);

		        DevicePattern.add(Pattern.compile("((\\d+[.:]*){4}\\d{4,})\\s*(device)"));
		        DevicePattern.add(Pattern.compile("([a-zA-Z0-9]{1,})\\s*(device)"));
		        DevicePattern.add(Pattern.compile("((\\w+[-]*)*)\\s*(device)"));
		        
		        
		        for (Pattern pattern : DevicePattern) {	        
		        	for(String s : adbInput) {
		        		if (s.matches(pattern.pattern())) {
			                matcher = pattern.matcher(s);
			                if (matcher.find())
			                	Androids.add(matcher.group(1));
//			                System.out.println(matcher.group(1)   +"\t|\tANDROID");
			            	}
			        	}
		        	}	     
		     
				}catch (IOException e) {
		        e.printStackTrace();
					}
				}
		//********** iDEVICES
		if(isiOS==true) {
		try {
        Process process = Runtime.getRuntime().exec("idevice_id -l");
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));  
        String line = null;  

        Pattern USB_Wifi_Device = Pattern.compile("\\w*");   // This regex will pickup udid's of physical devices connected to host machine.

        while ((line = in.readLine()) != null) {        	
        if (line.matches(USB_Wifi_Device.pattern())) {
        matcher = USB_Wifi_Device.matcher(line);
        if (matcher.find())
    	iDevices.add(matcher.group());
//        System.out.println(matcher.group()  +"      i(Phones_Pads_etc.)");
	            	}
	        	}  
		}catch (IOException e) {
		e.printStackTrace();
			}		
		}		
	}
	else
	{
		System.out.println("\nHost operating system NOT SUPPORTED! ONLY LINUX(on request), WINDOWS & MAC OS(LINUX) are currently supported.\n");
		try {
			throw new InvalidKeyException("\n===================================================\n              Pleaes use Windows/Mac machines.\n ===================================================\n\n");
			}catch (InvalidKeyException e) {			System.exit(1);		}		
	}
	

	allDevices.add(iDevices);
	allDevices.add(Androids);
	
	return allDevices;	
	   
}
  
	  
 /**
  * Get_All_Connected_Devices -> Uses the {@link DriverSupplier.Connected_Devices} method to store a local thread-safe copy of all the required devices for ongoing execution.
  * @return	-	void.
  * @author - https://github.com/AdityaAutomationTechniques   */
	public static void Get_All_Connected_Devices(boolean testAndroid, boolean testiOS , boolean testAnyofAndroidiOS) {
		

		Set<String> android 		= new LinkedHashSet<String>();
		Set<String> iOS 			= new LinkedHashSet<String>();
		ArrayList<Set<String>> allDevices = new ArrayList<Set<String>>();
		boolean No_Android =false, No_iOS = false;
		
		if (testAnyofAndroidiOS == false & testAndroid == false &  testiOS == false)		
		{		
			try {
				throw new InvalidParameterException("\n============================================================================================\n                Set the Flags properly to match the kind of Device Automation, in \"Configuration_file\" located under \'src\test\resources\'.============================================================================================\n\n");
			}catch (InvalidParameterException e) {				System.exit(1);				}		
		}

		allDevices = Connected_Devices(testAndroid, testiOS, testAnyofAndroidiOS);

		android = allDevices.get(1);
		iOS     = allDevices.get(0);

		//  Android Writing....
		if (android.size()>0)
		{
			for(String UDID : android)
				androidUDID.push(UDID);
		}else {		No_Android = true;	}
		
		
		//  iDEVICES Writing....
		if (iOS.size()>0)
		{
			for(String UDID : iOS)
				iOSUDID.push(UDID);
		}else { No_iOS = true;	}

		
		if( ((testAndroid == true & No_Android ==true) | (testiOS == true & No_iOS ==true)) | (testAnyofAndroidiOS == true  & No_iOS ==true & No_Android == true) )
		{
			String first ="", second ="";
				if(testAndroid == true & No_Android ==true)  first = "****************************\nNo Android Device(s) attached!\n********************";
				if(testiOS == true & No_iOS ==true)     second = "****************************\nNo iOS Device(s) attached!\\n********************";
					try
					{
						throw new InsufficientResourcesException("\n\n********************************************************************\n                        MoDeST >>--->  No Device Available \n********************************************************************"
								+ "\n\n1) Please attach min 1 device to start with Test execution."
								+ "\n2) For Parallel execution mode - attach devices equal to or more than\n   the execution thread count!! \n\n"+"Details below:\n"
								+ first +"\n"+ second+"\n\n\n");
					}
					catch (InsufficientResourcesException e) {	System.exit(1);		}
		}
	
		
		
	}
	  
	  
 /**
  * udid_provider_digital	-	Used to get either Android or iOS device UDID with priority to Android devices. 
  * @param Android_iOS		- 	true to get Android and false to get iOS device UDID.
  * @return	-> {String} 	-	UDID of the device.
  * @author - https://github.com/AdityaAutomationTechniques   */
	public static  String udid_provider_digital(boolean Android_iOS){
		
		if (Android_iOS)
		{
			if(androidUDID.isEmpty())
			{
				System.err.println("\n\nAll available android devices are in use now ! You are trying to build appium server without available device.\n\n");
				try {
					throw new InsufficientResourcesException("\n============================================================================================\nAll android devices are consumed in execution. Please decrease parallel-count or connect more devices.============================================================================================\n\n");
					}catch (InsufficientResourcesException e) {					System.exit(1);					}
			}
			String DeviceID = androidUDID.pop();
			System.out.println("| DEVICE USED |\t"+DeviceID+"| ANDROID |");
			return DeviceID;
		}
		else
		{
			if(iOSUDID.isEmpty())
			{
				System.err.println("\n\nAll available iOS devices are in use now ! You are trying to build appium server without available device.\n\n");
				try {
					throw new InsufficientResourcesException("\n============================================================================================\nAll iOS devices are consumed in execution. Please decrease parallel-count or connect more devices.============================================================================================\n\n");
					}catch (InsufficientResourcesException e) {					System.exit(1);					}
			}
			String DeviceID = iOSUDID.pop();
			System.out.println("| DEVICE USED |\t"+DeviceID+"| APPLE |");
			return DeviceID;
			
		}
	}
  
	  
 /**
  *	UDID_Resetter_digital	-> Used to restore the UDID into the list of available devices, once the execution over that UDID linked device is over, So that it can be reused.
  * @param Released_item	-> UDID of the device, over which execution is finished.
  * @return - void.
  * @author - https://github.com/AdityaAutomationTechniques   */
	public static void UDID_Resetter_digital(String Released_item) {
		
		if (Released_item.split("#")[1].equalsIgnoreCase("iOS")) 
			iOSUDID.push((Released_item.split("#")[0]).trim());
		else if (Released_item.split("#")[1].equalsIgnoreCase("ANDROID"))
			androidUDID.push((Released_item.split("#")[0]).trim());
		
	}
	
  
	  
	  
	  
	  
	//**************************        APPIUM  Server & Service Creation/Termination       ********************************	
 
	  
	/**
	 * startService - To start an appium server at any available port for parallel execution
	 * @Usage       - To be called from BeforeMethod or Test of TestNG to start appium server on any free port
	 * @param 	    - AppiumDriverLocalService -> null instance , to be returned for control at thread/script level. 
	 * @return      - APPIUM service instance so that we can kill it at the end of test execution.
	 * @throws      - MalformedURLException - InterruptedException
	 * @author      - Aditya Kumar 
	 * @author - https://github.com/AdityaAutomationTechniques   */
	
	public AppiumDriverLocalService startService(AppiumDriverLocalService service) throws MalformedURLException, InterruptedException {

		synchronized (this.getClass()){
		System.out.println("\n=====   APPIUM SERVICE BUILDER - Building New Service   =======");
		
		String BPN = ((Integer)Common_Utility.random_number_generator(4726,65536)).toString();
		// Build the Appium service
		builder = new AppiumServiceBuilder();
		builder.withIPAddress("127.0.0.1");
		builder.usingAnyFreePort();
		builder.withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, BPN);
		builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
		builder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");
		//Start the server with the builder
		service = AppiumDriverLocalService.buildService(builder);
		service.start();
		
		System.out.println("\n=====   APPIUM SERVICE BUILDER - Built New Service      =======");
		}
		
		
		return service;
	}


	
	public AppiumDriverLocalService startService_iOS(AppiumDriverLocalService service) throws MalformedURLException, InterruptedException {

		
		synchronized (this.getClass()){
		System.out.println("\n=====   APPIUM SERVICE BUILDER - Building New Service   =======");
		
		// Build the Appium service
		builder = new AppiumServiceBuilder();
		builder.usingAnyFreePort();
		if(!iOSProperties.getProperty("NODE").equalsIgnoreCase("") | iOSProperties.getProperty("NODE") !=null)
			builder.usingDriverExecutable(new File(iOSProperties.getProperty("NODE")));
		if(!iOSProperties.getProperty("MAIN_JS").equalsIgnoreCase("") | iOSProperties.getProperty("MAIN_JS") !=null)
			builder.withAppiumJS(new File(iOSProperties.getProperty("MAIN_JS")));
		builder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");
		
		//Start the server with the builder
		service = AppiumDriverLocalService.buildService(builder);
		service.start();
		System.out.println("\n=====   APPIUM SERVICE BUILDER - Built New Service      =======");
		}
		
		return service;
	}
	

	
	
	
	/**
	 * kill_APPIUM - kills both appium service & driver instance at the end of the test.
	 * @param 	AppiumDriverLocalService   -> any running AppiumDriverLocalService
	 * @param 	AppiumDriver 			   -> any running AppiumDriver
	 * @author 	Aditya Kumar						
	 * @author - https://github.com/AdityaAutomationTechniques   */
	public static synchronized void kill_APPIUM( AppiumDriverLocalService service, AppiumDriver<WebElement> driver)
	{
		driver.quit();
		System.out.println("Killed Appium driver");
		service.stop();
		System.out.println("Killed Appium service\n\n\n");
	}



 /**
  * createIOSdriver - creates an instance of IOSDriver on the given AppiumDriverLocalService with given capabilities.
  * @param Device_Udid	- UDID of the device.
  * @param service	- instance of up and running AppiumDriverLocalService
  * @param driver	- a driver instance on which the newly generated copy of driver will be over-ridden
  * @return			- IOSDriver<IOSElement>
  * @throws MalformedURLException
  * @throws InterruptedException
  * @author - https://github.com/AdityaAutomationTechniques   */
	public IOSDriver<IOSElement> createIOSdriver(String Device_Udid , AppiumDriverLocalService service,  IOSDriver<IOSElement> driver ) throws MalformedURLException, InterruptedException {
	synchronized (this.getClass()){
			cap = Common_Utility.DesiredCapability_Setter_iOS( Device_Udid);
			String service_url =service.getUrl().toString();
			System.out.println(service_url);
			driver = new IOSDriver<IOSElement>(new URL(service_url), cap);
	}	
	return driver;
}

 
 /**
  * createAndroidDriver - creates an instance of AndroidDriver on the given AppiumDriverLocalService with given capabilities.
  * @param Device_Udid	- UDID of the device.
  * @param service	- instance of up and running AppiumDriverLocalService
  * @param driver	- a driver instance on which the newly generated copy of driver will be over-ridden.
  * @return			- AndroidDriver<MobileElement>
  * @throws MalformedURLException
  * @throws InterruptedException
  * @author - https://github.com/AdityaAutomationTechniques   */
	public AndroidDriver<MobileElement> createAndroidDriver(String Device_Udid , AndroidDriver<MobileElement> driver , AppiumDriverLocalService service) throws MalformedURLException, InterruptedException {
		synchronized (this.getClass()){
				cap = Common_Utility.DesiredCapability_Setter( Device_Udid);
				String service_url =service.getUrl().toString();
				System.out.println(service_url);
				driver = new AndroidDriver<MobileElement>(new URL(service_url), cap);
		}	
		return driver;
	}




	public AppiumDriver<WebElement> startServer(String Device_Udid , AppiumDriver<WebElement> driver , AppiumDriverLocalService service) throws MalformedURLException, InterruptedException {
		synchronized (this.getClass()){
				//Set APPIUM CAPABILITIES
				cap = Common_Utility.DesiredCapability_Setter( Device_Udid);
				//BOOTSTRAP_PORT_NUMBER
				String service_url =service.getUrl().toString();
				System.out.println(service_url);
				driver = new AndroidDriver<WebElement>(new URL(service_url), cap);
				//IF FAILURE IS OBSERVED THEN ->> ADD A wait HERE FOR 10SEC.... ( Since it take time for appium to launch the app).
		}	
		
		return driver;
	}
	

	
	public IOSDriver<WebElement> startServer_iOS(String Device_Udid , IOSDriver<WebElement> driver , AppiumDriverLocalService service) throws MalformedURLException, InterruptedException {
	synchronized (this.getClass()){
			//Set APPIUM CAPABILITIES
			cap = Common_Utility.DesiredCapability_Setter_iOS( Device_Udid);
			//BOOTSTRAP_PORT_NUMBER
			String service_url =service.getUrl().toString();
			System.out.println(service_url);
			driver = new IOSDriver<WebElement>(new URL(service_url), cap);
			//IF FAILURE IS OBSERVED THEN ->> ADD A wait HERE FOR 10SEC.... ( Since it take time for appium to launch the app).
	}	
	
	return driver;
}
	

	
	
/************************************************    Function Debugging Zone   BELOW  ***************************************** @author - https://github.com/AdityaAutomationTechniques   */


}	
