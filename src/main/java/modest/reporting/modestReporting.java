package modest.reporting;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import modest.coreUtility.BaseTest;

import java.lang.reflect.Field;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

@SuppressWarnings("unchecked")
public class modestReporting extends BaseTest implements ITestListener {
	
	private static String getTestMethodName(ITestResult result)	{
		return result.getMethod().getConstructorOrMethod().getName();
	}

	@Attachment(value="Test Step", type = "image/png")
    public byte[] saveScreenshotPNG (WebDriver screenshoter) {
        return ((TakesScreenshot)screenshoter).getScreenshotAs(OutputType.BYTES);
    } 		

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("Started Test METHOD : "+getTestMethodName(result)+"\n");		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("PASSED TEST : "+getTestMethodName(result)+"\n");		
	}

	@Step("Failure : Test Step")
	@Override
	public void onTestFailure(ITestResult result)
	{
		try{
		System.err.print("FAILED TEST : "+getTestMethodName(result)+"\n");		
		ThreadLocal<AppiumDriver<WebElement>> driver1 =null;

			Class<?> clazz = result.getTestClass().getRealClass();
			Class<?> baseClass = clazz.getSuperclass();
		    Field field = null;
			try {
				field = baseClass.getSuperclass().getDeclaredField("driver_");
			} catch (NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}		        
		    field.setAccessible(true);
		    try {
				driver1 = (ThreadLocal<AppiumDriver<WebElement>>) field.get(result.getInstance());
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}		    
			saveScreenshotPNG(driver1.get());
		}
		catch ( Exception session ) {session.printStackTrace();}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.err.print("SKIPPED TEST : "+getTestMethodName(result)+"\n");		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("FAILED ; BUT WITHIN ALLOWED FAIL % , TEST : "+getTestMethodName(result)+"\n");		
	}
	
	@Override
	public void onStart(ITestContext context) {	}

	@Override
	public void onFinish(ITestContext context)
	{
		try {	Thread.sleep(2000);		} catch (InterruptedException e) {	e.printStackTrace();		}
		System.out.println("Test Cases with execution status are:-\n");  
		context.getPassedTests()
        .getAllResults()
        .forEach(result -> {
        	System.out.println("| "+result.getName() +"\t\t| Passed | "+resolveIntoTime(result.getEndMillis()-result.getStartMillis())+" |");  
        });
		System.out.println();
		context.getFailedTests()
        .getAllResults()
        .forEach(result -> {
        	System.err.println("| "+result.getName() +"\t\t| Failed | "+resolveIntoTime(result.getEndMillis()-result.getStartMillis())+" |");  
        });
		try {	Thread.sleep(500);	} catch (InterruptedException e) {	}
	   System.out.print("\n**********************************************************");
	   System.out.print("\nTest completed on: "+context.getEndDate().toString());
	 System.out.println("\n**********************************************************\n");

	}
	

}
