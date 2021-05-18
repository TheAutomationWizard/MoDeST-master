package modest.coreUtility;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;


@SuppressWarnings("unused")
public class ExceptionHandler extends BaseTest{

	
	private int waitTime = 60000;
	

 /**
  * 	
  * @param e	-	Exception object , which invoked this method.
  * @param timeOut	-	time in milli seconds after which stop trying to resolve the exception. 
  * @param fullClassName	- full class name , i.e. packageName.className of the class where method to invoke is available, that caused the exception.
  * @param methodName		- method Name which caused the exception to occur.
  * @param args				- arguments of the method that needs to be invoked.
  * @throws Exception		
  */
	public void exceptionRecovery(Exception e, int timeOut, String fullClassName, String methodName, Object[] args) throws Exception
	{
		if (timeOut <= 0) 
			timeOut = this.waitTime;
		
		Exception e1 			= e;
		final long waitTimer	= System.currentTimeMillis()+timeOut;
		Instant start = Instant.now();
		System.out.println("\nException Occured ! Trying to recover in "+timeOut);
		
		while(System.currentTimeMillis()<=waitTimer)
		{
			
			if(e.getClass().getSimpleName().equalsIgnoreCase("NullPointerException"))
			{
				try {
					executeMyMethod(fullClassName, methodName, args);
					System.out.println("Exception Handled in "+calcTimeDifference(start));
					return;
				}catch (Exception e2)	{	e =	e2;	 System.out.println("Message : "+e.getClass().getSimpleName()+" Error is found.");}
			}
			else if(e.getClass().getSimpleName().equalsIgnoreCase("ClassNotFoundException"))
			{
				try {
					executeMyMethod(fullClassName, methodName, args);
					System.out.println("Exception Handled in "+calcTimeDifference(start));
					return;
				}catch (Exception e2)	{	e =	e2;	 System.out.println("Message : "+e.getClass().getSimpleName()+" Error is found.");}
			}
			else if(e.getClass().getSimpleName().equalsIgnoreCase("NoSuchElementException") || e.getClass().getSimpleName().equalsIgnoreCase("ClassNotFoundException"))
			{
				try {
					executeMyMethod(fullClassName, methodName, args);
					System.out.println("Exception Handled in "+calcTimeDifference(start));
					return;
				}catch (Exception e2)	{	e =	e2;	 System.out.println("Message : "+e.getCause().getClass().getSimpleName()+" Error is found.");}
			}
			else if(e.getClass().getSimpleName().equalsIgnoreCase("StaleElementReferenceException") || e.getCause().getClass().getSimpleName().equalsIgnoreCase("StaleElementReferenceException"))
			{
				try {
					executeMyMethod(fullClassName, methodName, args);
					System.out.println("Exception Handled in "+calcTimeDifference(start));
					return;
				}catch (Exception e2)	{	e =	e2;	 System.out.println("Message : "+e.getCause().getClass().getSimpleName()+" Error is found.");}
			}
			else if(e.getClass().getSimpleName().equalsIgnoreCase("WebDriverException") || e.getCause().getClass().getSimpleName().equalsIgnoreCase("WebDriverException"))
			{
				try {
					executeMyMethod(fullClassName, methodName, args);
					System.out.println("Exception Handled in "+calcTimeDifference(start));
					return;
				}catch (Exception e2)	{	e =	e2;	 System.out.println("Message : "+e.getCause().getClass().getSimpleName()+" Error is found.");}
			}
			else if(e.getClass().getSimpleName().equalsIgnoreCase("InvocationTargetException") || e.getCause().getClass().getSimpleName().equalsIgnoreCase("InvocationTargetException"))
			{
				try {
					executeMyMethod(fullClassName, methodName, args);
					System.out.println("Exception Handled in "+calcTimeDifference(start));
					return;
				}catch (Exception e2)	{	e =	e2;	 System.out.println("Message : "+e.getCause().getClass().getSimpleName()+" Error is found.");}
			}
			else 
			{
				System.out.println("Un expected exception: "+e.getClass().getSimpleName()+ "\n\ncould be caused by: "+e.getCause().getClass().getSimpleName());
					throw new Exception(e);
			}
			Thread.sleep(500);
		}
		
		System.err.println("Unable to recover in target TIME "+calcTimeDifference(start)+"in method : "+methodName+" present in Class : "+fullClassName+"  due to error : "+e.getCause().getClass().getSimpleName()+"\n\n");
		throw new Exception(e);
	}
	
	
	
 /**
  * executeMyMethod - invoke the method from a differnt class file.
  * @param fullClassName	- full specified class name where the method to invoke is present.
  * @param methodName		- method name that needs to be invoked.
  * @param args				- arguments of the method that needs to be invoked.
  * @throws Exception
  */
	@SuppressWarnings("deprecation")
	public void executeMyMethod(String fullClassName, String methodName, Object[] args) throws Exception
	{
		Class<?> params[] 	= new Class[args.length];
				 params   	= setClassTypes(args, params) ;
				 
		Class<?> cls 		= Class.forName(fullClassName);
		Object _instance 	= cls.newInstance();
        
        	Method myMethod = cls.getDeclaredMethod(methodName, params);	
        		myMethod.setAccessible(true);
        			myMethod.invoke(_instance, args);	
	}

	
 /**
  * setClassTypes - To define the type of arguments that are acceptable to invoke a funciton via this API.
  * @param args	  - actual arguments that are being passed to the invoked method.
  * @param params - Array of Class<?> object
  * @return		  - array of Class, that the arguments belong to
  */
	private Class<?>[] setClassTypes(Object[] args, Class<?> params[])
	{
		for (int i = 0; i < args.length; i++)
		{
			if (args[i] instanceof Integer) 
				params[i] = Integer.TYPE;
			else if (args[i] instanceof String) 
				params[i] = String.class;
			else if ( args[i] instanceof InternetExplorerDriver)
				params[i] = InternetExplorerDriver.class;
			else if (args[i] instanceof int[])
				params[i] = int[].class;
			else if (args[i] instanceof By )
				params[i] = By.class;
			else if (args[i] instanceof Boolean)
				params[i] = Boolean.TYPE;
			else if (args[i] instanceof List)
				params[i] = List.class;
			else if (args[i] instanceof WebElement)
				params[i] = WebElement.class;
			else 
				System.out.println("Please ask developer to include : \'"+args[i].getClass().getSimpleName()+"\' to permitted inputs for paramters");
		}
		return params;
	}

	
	



}
