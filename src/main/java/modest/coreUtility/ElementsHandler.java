package modest.coreUtility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang.NullArgumentException;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.beust.jcommander.ParameterException;

import edu.emory.mathcs.backport.java.util.concurrent.TimeoutException;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Attachment;


@SuppressWarnings({"unused" ,"rawtypes"})
public class ElementsHandler extends ExceptionHandler	{

	private int waitTime =		40000;
	private WebDriverWait 		wait ;
	private JavascriptExecutor 	jsEx;
	private ColorUtils colorUtil	= new ColorUtils();
	
	//  elementsHandler Constructor 1. Parametrized (for retry wait-time setting) and 2. Default ( for retry-logic execution)
	public ElementsHandler (int waitTime) {	this.waitTime = waitTime;	}
	
	public ElementsHandler() {}
	

	// public setter for modifying waitTime to perform exception handling.
	public void setWaitTimer ( int WaitTime) {		this.waitTime = WaitTime;	}
	public void resetWaitTimer() { this.waitTime = 40000; }
	

	@Attachment(value="Test Step", type = "image/png")
    public byte[] saveScreenshotPNG (WebDriver driver) {
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    } 	
	   
		
	/*******************************************************************************************************************************/
										//	Waits with Expected Conditions
	/*******************************************************************************************************************************/
	
	 /**
	  * waitForElementVisibility - waits for an element to be visible on webpage for given time
	  * @param elementLocator	 - {By} locator of element to be looked up 
	  * @param Timeout			 - time to wait for element to become visible
	  * @return					 - {WebElement, null } if visible or not respectively.
	  */
	public WebElement waitForElementVisibility(By elementLocator, int Timeout) {
		wait = new WebDriverWait(driver(), Timeout/1000);
		try {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));         
		}catch(Exception e) { return null; }
	}
	
	
	 /**
	  * waitForAllElementsVisibility - waits for all element to be visible on webpage for given time that matches the locator details provided.
	  * @param elementLocator	 - {By} locator of element(s) to be looked up 
	  * @param Timeout			 - time to wait for element(s) to become visible
	  * @return					 - {List<WebElement>}
	  */
	public List<WebElement> waitForAllElementsVisibility(By elementLocator, int Timeout) {
		wait = new WebDriverWait(driver(), Timeout/1000);
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(elementLocator));   		
	}

	
	 /**
	  * checkForElementToBeVisible - waits for an element to be visible on webpage for given time
	  * @param elementLocator	 - {By} locator of element to be looked up 
	  * @param Timeout			 - time to wait for element to become visible
	  * @return					 - {boolean} if visible or not respectively within given time.
	  */
	public boolean checkForElementToBeVisible(By elementLocator, int Timeout) {
		wait = new WebDriverWait(driver(), Timeout/1000);
		try {
		wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator)); 
		return true;
		}catch(Exception e) { return false; }
	}

	
	 /**
	  * waitForElementInVisibility - waits for an element to become invisible on webpage within given time
	  * @param elementLocator	 - {By} locator of element to become invisible 
	  * @param Timeout			 - time to wait for element to become invisible
	  * @return					 - {boolean } if invisible or not respectively, within time limit.
	  */
	public Boolean waitForElementInVisibility(By elementLocator, int Timeout) {
		wait = new WebDriverWait(driver(), Timeout/1000);
		try{
			return wait.until(ExpectedConditions.invisibilityOfElementLocated(elementLocator));       
		}catch(Exception e) { return false; }
	}

	 /**
	  * waitForAllElementInVisibility - waits for all element(s) to become invisible on webpage within given time
	  * @param elementLocator	 - {By} locator of element(s) to become invisible 
	  * @param Timeout			 - time to wait for element to become invisible
	  * @return					 - {boolean } if invisible or not respectively, within time limit.
	  */
	public Boolean waitForAllElementInVisibility(By elementLocator, int Timeout) {
		wait = new WebDriverWait(driver(), Timeout/1000);
		try {
			return wait.until(ExpectedConditions.invisibilityOfAllElements(driver().findElements(elementLocator)));    
		}catch(Exception e) { return false; }
	}

	
	/**
	  * waitForElementToContainText - waits for an element to contain provided text in it.
	  * @param elementLocator	 - {By} locator of element that contains the expected text.
	  * @param Timeout			 - time to wait for element to contain the expected text.
	  * @return					 - {boolean } if text is present in it, within time limit.
	  */
	public Boolean waitForElementToContainText(By elementLocator,String text,  int Timeout) {
		wait = new WebDriverWait(driver(), Timeout/1000);
		try {
			return wait.until(ExpectedConditions.textToBePresentInElementLocated(elementLocator, text));       
		}catch(Exception e) { return false; }
	}

	/**
	  * waitForElementToContainTextAsValue - waits for an element to contain provided text in it as value attribute.
	  * @param elementLocator	 - {By} locator of element that contains the expected text.
	  * @param Timeout			 - time to wait for element to contain the expected text.
	  * @return					 - {boolean } if text is present in it as value attribute, within time limit.
	  */
	public Boolean waitForElementToContainTextAsValue(By elementLocator,String text,  int Timeout) {
		wait = new WebDriverWait(driver(), Timeout/1000);
		try {
			return wait.until(ExpectedConditions.textToBePresentInElementValue(elementLocator, text));       
		}catch(Exception e) { return false; }
	}
	
	
	
	
	/*******************************************************************************************************************************/
													//	Action on elements
	/*******************************************************************************************************************************/

	
	 /**
	  * enterText_ - to send text into a WebElement
	  * @param elementLocator	- {By} locator of the WebElement on which text is to be sent.
	  * @param textToEnter		- {String} text to be entered.
	  * @param clearField		- {boolean} whether to clear the text box or not before typing in it.
	  */
	private void enterText_(By elementLocator, String textToEnter, boolean clearField) {
		WebElement target = driver().findElement(elementLocator);
		if(clearField)
			target.clear();
		target.sendKeys(textToEnter);
	}
	private void enterText_(By elementLocator, Keys keyboardKEYS, boolean clearField) {
		WebElement target = driver().findElement(elementLocator);
		if(clearField)
			target.clear();
		target.sendKeys(keyboardKEYS);
	}

	/**
	  * enterText_ - to send text into a WebElement
	  * @param elementLocator	- {WebElement} WebElement on which text is to be sent.
	  * @param textToEnter		- {String} text to be entered.
	  * @param clearField		- {boolean} whether to clear the text box or not before typing in it.
	  */
	private void enterText_(WebElement elementLocator, String textToEnter, boolean clearField) {
		if(clearField)
			elementLocator.clear();
		elementLocator.sendKeys(textToEnter);
	}

	private void enterText_(WebElement elementLocator, Keys keyboardKEYS, boolean clearField) {
		if(clearField)
			elementLocator.clear();
		elementLocator.sendKeys(keyboardKEYS);
	}

	 /**
	  * enterText_ - to send text into a WebElement
	  * @param elementLocator	- {By} locator of the WebElement on which text is to be sent.
	  * @param textToEnter		- {String} text to be entered.
	  * @param clearField		- {boolean} whether to clear the text box or not before typing in it.
	  */
	public void enterText(By elementLocator, String textToEnter, boolean clearField) throws Exception {
		try {
			enterText_(elementLocator, textToEnter, clearField);
		}catch (Exception e) {
			exceptionRecovery(e, waitTime,  "utilities.ElementsHandler", "enterText_", new Object[]{elementLocator, textToEnter, clearField} );
		}
	}

	public void enterText(By elementLocator, Keys keyboardKEYS, boolean clearField) throws Exception {
		try {
			enterText_(elementLocator, keyboardKEYS, clearField);
		}catch (Exception e) {
			exceptionRecovery(e, waitTime,  "utilities.ElementsHandler", "enterText_", new Object[]{elementLocator, keyboardKEYS, clearField} );
		}
	}

	 /**
	  * enterText_ - to send text into a WebElement
	  * @param elementLocator	- {WebElement} WebElement on which text is to be sent.
	  * @param textToEnter		- {String} text to be entered.
	  * @param clearField		- {boolean} whether to clear the text box or not before typing in it.
	  */
	public void enterText(WebElement elementLocator, String textToEnter, boolean clearField) throws Exception {
		try {
			enterText_(elementLocator, textToEnter, clearField);
		}
		catch (NullPointerException n) {throw new NullPointerException();}
		catch (Exception e) {
			exceptionRecovery(e, waitTime,  "utilities.ElementsHandler", "enterText_", new Object[]{elementLocator, textToEnter, clearField} );
		}
	}
	
	public void enterText(WebElement elementLocator, Keys keyboardKEYS, boolean clearField) throws Exception {
		try {
			enterText_(elementLocator, keyboardKEYS, clearField);
		}
		catch (NullPointerException n) {throw new NullPointerException();}
		catch (Exception e) {
			exceptionRecovery(e, waitTime,  "utilities.ElementsHandler", "enterText_", new Object[]{elementLocator, keyboardKEYS, clearField} );
		}
	}
	
	
	
	
	public void enterTextJSE(By elementLocator, String textToEnter) {
		jsEx = (JavascriptExecutor) driver();
		jsEx.executeScript("arguments[0].value=arguments[1]", driver().findElement(elementLocator),textToEnter);
	}
	
	
	
	
	// **********************      CLICK ON ELEMENT 	*******************************
	
	
	 /**
	  * clickElement_ - to click a WebElement
	  * @param elementLocator	- {By} locator of the WebElement, to be clicked.
	  */
	private void clickElement_(By elementLocator) {
		driver().findElement(elementLocator).click();
	}

	/**
	  * clickElement_ - to click a WebElement
	  * @param elementLocator	- {WebElement} WebElement, to be clicked.
	  */
	private void clickElement_(WebElement elementLocator) {
		elementLocator.click();
	}

	 /**
	  * clickElement_ - to click a WebElement
	  * @param elementLocator	- {By} locator of the WebElement, to be clicked.
	  */
	public void clickElement(By elementLocator) throws Exception {
		try	{	clickElement_( elementLocator);	}	
		catch (Exception e) {	exceptionRecovery(e, waitTime,  "utilities.ElementsHandler", "clickElement_", new Object[]{elementLocator} );	}
	}
	
	/**
	  * clickElement_ - to click a WebElement
	  * @param elementLocator	- {WebElement} WebElement, to be clicked.
	  */
	public void clickElement(WebElement elementLocator) throws Exception {
		try	{	clickElement_( elementLocator);	}
		catch (NullPointerException n) {throw new NullPointerException();}
		catch (Exception e) {	exceptionRecovery(e, waitTime,  "utilities.ElementsHandler", "clickElement_", new Object[]{elementLocator} );	}
	}
	
	
	
	
	
	
	public void clickElementJSE(By elementLocator) {
		jsEx = (JavascriptExecutor) driver();
		jsEx.executeScript("arguments[0].click();",driver().findElement(elementLocator));
	}
	
	public String getText(By elementLocator) {
		return waitForElementVisibility(elementLocator, 1000).getText();
	}
	
	
	private WebElement findElement_ ( By elementLocator) {
		return driver().findElement( elementLocator );
	}
	
	public WebElement elementLookup ( By elementLocator) throws Exception {
			try	{	return findElement_(elementLocator);	}	
			catch (Exception e) {	exceptionRecovery(e, waitTime,  "utilities.ElementsHandler", "elementLookup", new Object[]{elementLocator} );	return findElement_(elementLocator);}
	}
	
	
	
	private WebElement getElementFromList_ ( List<WebElement> listOFElements, int index) {
		return listOFElements.get(index);
	}

	
	public WebElement getElementFromList ( List<WebElement> listOFElements, int index	) throws Exception {
			try	{	return getElementFromList( listOFElements, index	);	}	
			catch (Exception e) {	exceptionRecovery(e, waitTime,  "utilities.ElementsHandler", "elementLookup", new Object[]{listOFElements, index} );	return getElementFromList( listOFElements, index); }
	}
	
	
	
	
	

	public String getTextJSE(By elementLocator, String textToEnter) {
		jsEx = (JavascriptExecutor) driver();
		return jsEx.executeScript("return arguments[0].value", driver().findElement(elementLocator)).toString();
	}

	public String getAttribute (By elementLocator, String attributeName) {
		return waitForElementVisibility(elementLocator, 1000).getAttribute(attributeName);
	} 
	
	public void setAttribute  (By elementLocator, String attributeName, String attributeValue) {
		jsEx = (JavascriptExecutor) driver();
		jsEx.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])",driver().findElement(elementLocator), attributeName, attributeValue);
	} 
	
	
	
	/*******************************************************************************************************************************/
								// *****************        JavaScript Actions   ***********************
	/*******************************************************************************************************************************/
	
	public void scrollElementIntoView(By elementLocator) {
		jsEx = (JavascriptExecutor) driver();
		jsEx.executeScript("arguments[0].scrollIntoView()", driver().findElement(elementLocator));
	}
	
	public void scrollToPageEnd() {
		jsEx = (JavascriptExecutor) driver();
		jsEx.executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}
	
	public void scrollToPageBegining() {
		jsEx = (JavascriptExecutor) driver();
		jsEx.executeScript("window.scrollTo(document.body.scrollHeight,0)");
	}

	
	
	
	/*******************************************************************************************************************************/
						//  ****************          Swipe/Scroll Actions on Mobile-Screen  			******************
	/*******************************************************************************************************************************/

	
	public  void swipeVertical(double anchorPercentage, double startPercentage, double finalPercentage, int waitBeforeScroll) throws Exception {
		// Keep startPercentage > finalPercentage to swipe up and other way to swipe down
		Dimension size = driver().manage().window().getSize();
	    int anchor = (int) (size.width * anchorPercentage);
	    int startPoint = (int) (size.height * startPercentage);
	    int endPoint = (int) (size.height * finalPercentage);
	    PointOption start = PointOption.point(anchor, startPoint);
	    PointOption end = PointOption.point(anchor, endPoint);
	    new TouchAction(driver()).press(start).waitAction(WaitOptions.waitOptions(Duration.ofMillis(waitBeforeScroll))).moveTo(end).release().perform();
	  }
	
	
	
	 /**
	  * swipeInElement - When you want to position your swipe actions confined within an element, like a text box or a sub-part of screen.
	  * @param containerElement - {WebElement} The screen area defined by this WebElement will be where the swipe will take place.
	  * @param swipeAxis	- {boolean) true/false for vertical/horizontal swipe action respectively
	  * @param anchorPercentage -{double, [0-1]}	This is the fixed co-ordinate for your swiping axis. Use 0.5 as default, this will points to mid of the element body. Increase/Decrease if required.
	  * @param startPercentage	-{double, [0-1]}	This is the % distance from top of the screen, from where the swipe should start. 0 implies extreme top (or left) & 1 implies extreme bottom (or right). 
	  * @param endPercentage	-{double, [0-1]}	This is the % distance from top of the screen, from where the swipe should end. 0 implies extreme top (or left) & 1 implies extreme bottom (or right).
	  * @param waitBeforeScroll -{milli seconds}   This is the wait time between clicking on start point and start of the swipe action.
	  * @swipeVertical		- startPercentage > endPercentage means swipe Up and swipe Down if other way round.
	  * @swipeHorizontal	- startPercentage > endPercentage means swipe Left and swipe Right if other way round.
	  * @throws Exception
	  */
	public  void swipeInElement(WebElement containerElement, boolean swipeAxis , double anchorPercentage, double startPercentage, double endPercentage, int waitBeforeScroll) throws Exception {
		
//		anchorPercentage = 0.5; startPercentage = 0.6; endPercentage = 0.4; 

		if(anchorPercentage > 1 | anchorPercentage < 0)
			System.out.println("Enter a valid value for anchorPercentage in the range 0 to 1. Entered value is : "+anchorPercentage);
		if(startPercentage > 1 | startPercentage < 0)
			System.out.println("Enter a valid value for startPercentage in the range 0 to 1. Entered value is : "+startPercentage);
		if(endPercentage > 1 | endPercentage < 0)
			System.out.println("Enter a valid value for startPercentage in the range 0 to 1. Entered value is : "+endPercentage);

		
		// This will get the Top-Left coordinates of the element.
		Point point 		= containerElement.getLocation();
		Dimension elemBorder= containerElement.getSize();
		int swipeAnchor; int swipeStart; int swipeEnd; PointOption startPoint; PointOption endPoint;
		
		// Set the anchor based on swipeAxis (1) Vertical if true, (2) Horizontal if false.
		if(swipeAxis)	
		{
			swipeAnchor = (int) (point.getX()+(elemBorder.getWidth()*anchorPercentage));
		    swipeStart 	= (int) (elemBorder.height * startPercentage) + point.getY();
		    swipeEnd 	= (int) (elemBorder.height * endPercentage) + point.getY();
		    startPoint	= PointOption.point(swipeAnchor, swipeStart);
		    endPoint	= PointOption.point(swipeAnchor, swipeEnd);
			new TouchAction(driver()).press(startPoint).waitAction(WaitOptions.waitOptions(Duration.ofMillis(waitBeforeScroll))).moveTo(endPoint).release()
					.perform();
		return;
		}
		else
		{	

//			swipeAnchor = (int) ( point.getY()+(elemBorder.getHeight()*anchorPercentage));	
//		    swipeStart 	= (int) (elemBorder.width * startPercentage) + point.getY();
//		    swipeEnd 	= (int) (elemBorder.width * endPercentage) + point.getY();

			swipeAnchor = (int) ( point.getY()+(elemBorder.getHeight()*anchorPercentage));	
		    swipeStart 	= (int) (elemBorder.width * startPercentage) + point.getX();
		    swipeEnd 	= (int) (elemBorder.width * endPercentage) + point.getX();
		    
		    
		    startPoint 	= PointOption.point(swipeStart, swipeAnchor);
			endPoint	= PointOption.point(swipeEnd, swipeAnchor);
			new TouchAction(driver()).press(startPoint).waitAction(WaitOptions.waitOptions(Duration.ofMillis(waitBeforeScroll))).moveTo(endPoint).release()
					.perform();
		return;
		}
	}
	
	
	
	
	
	
	/******************************************************************************************/
						//  *****    VALIDATE/WAIT(s) ELEMENT COLOR   ********
	/******************************************************************************************/
	
		
	/**
	 * elementBackgroundColorAsRGB 	- Get the RGB equivalent code for a particular pixel inside target element based on location input.
	 * @param element - {By} locator of the element whose color is to be determined.
	 * @param pixel_W - {int} integer value to move along the width of the given element, in pixels. 0 points to the top most line of the WebElment border.
	 * @param pixel_H - {int} integer value to move along the height of the given element, in pixels 0 points to extreme left of the WebElement border.
	 * @return - {String} RGB value as RRGGBB in form of string. Allegedly unique for every color. 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public String elementPixelColorAsRGB(By element, int pixel_W, int pixel_H ) throws InterruptedException, IOException {
		
		MobileElement elem = (MobileElement) driver().findElement(element);
		
//		pixel_H = 15; pixel_W = 12;
		
		// Get entire page screenshot
		Thread.sleep(200);
		File screenshot = ((TakesScreenshot) driver()).getScreenshotAs(OutputType.FILE);
		BufferedImage  fullImg = ImageIO.read(screenshot);

		// Get the location of element on the page
		Point point = elem.getLocation();

		// Get width and height of the element
		int eleWidth = elem.getSize().getWidth();
		int eleHeight = elem.getSize().getHeight();

		// Crop the entire page screenshot to get only element screenshot
		BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(),  eleWidth, eleHeight);
		Thread.sleep(300);
		  
		int  clr   =  eleScreenshot.getRGB(pixel_W, pixel_H);
		int  red   = (clr & 0x00ff0000) >> 16;
		int  green = (clr & 0x0000ff00) >> 8;
		int  blue  =  clr & 0x000000ff;
		  
		String colors = Integer.toString(red)+Integer.toString(green)+Integer.toString(blue);
		System.out.println(colors);
		
		System.out.println(colorUtil.getColorNameFromRgb(red, green, blue));
		
		return colors;
	    
	}

	

	
	/**
	 * elementBackgroundColorAsRGB 	- Get the RGB equivalent code for a particular pixel inside target element based on location input.
	 * @param element - {By} locator of the element whose color is to be determined.
	 * @param pixel_W - {int} integer value to move along the width of the given element, in pixels. 0 points to the top most line of the WebElment border.
	 * @param pixel_H - {int} integer value to move along the height of the given element, in pixels 0 points to extreme left of the WebElement border.
	 * @return - {String} RGB value as RRGGBB in form of string. Allegedly unique for every color. 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public String elementPixelColorAsCOLOR(By element, int pixel_W, int pixel_H ) throws InterruptedException, IOException {
		
		MobileElement elem = (MobileElement) driver().findElement(element);
		
		// Get entire page screenshot
		Thread.sleep(200);
		File screenshot = ((TakesScreenshot) driver()).getScreenshotAs(OutputType.FILE);
		BufferedImage  fullImg = ImageIO.read(screenshot);

		// Get the location of element on the page
		Point point = elem.getLocation();

		// Get width and height of the element
		int eleWidth = elem.getSize().getWidth();
		int eleHeight = elem.getSize().getHeight();

		// Crop the entire page screenshot to get only element screenshot
		BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(),  eleWidth, eleHeight);
		Thread.sleep(300);
		  
		int  clr   =  eleScreenshot.getRGB(pixel_W, pixel_H);
		String color = colorUtil.getColorNameFromHex(clr);
//			System.out.print("( W-"+pixel_W+", H-"+pixel_H+") | ");
//		  System.out.println("Element color at given pixel is :"+color);
		
		return color;
	    
	}
	
	
	public String pixelColorAtCordinate(int pixelX, int pixelY ) throws InterruptedException, IOException {
		  
		  BufferedImage fullImg = null;	InputStream stream = null;	String color = "";
		  
		  try {
		      stream = new FileInputStream(((TakesScreenshot) driver()).getScreenshotAs(OutputType.FILE));
		      fullImg = ImageIO.read(stream);

		  } catch (Exception ex) {
			  	System.out.println("File couldn't be read.");
		  } finally {
		      if (stream != null) {
		          try {
		      		int  clr   =  fullImg.getRGB(pixelX, pixelY);
		      		color = colorUtil.getColorNameFromHex(clr);

		              stream.close();
		          } catch (IOException ex) {
		             System.out.println("ERROR closing image input stream: "+ex.getClass().getSimpleName());
		          }
		      }
		  }  
		  
		  
			System.out.print("( X : "+pixelX+", Y-"+pixelY+") | ");
			System.out.println("Element color at given co-ordinate is :"+color);

		return color;
	}

	
	public String pixelColorAtCordinate(By element, int pixelX, int pixelY) throws InterruptedException, IOException {

		BufferedImage image = null;
		InputStream stream = null;
		String color = "";

		// Get the element to contain the color
		MobileElement elem = (MobileElement) driver().findElement(element);
		Point point = elem.getLocation();

		try {
			stream = new FileInputStream(((TakesScreenshot) driver()).getScreenshotAs(OutputType.FILE));
			image = ImageIO.read(stream);

			if (stream != null) {
				try {
					// Get cropped image.
					image = image.getSubimage(point.getX(), point.getY(), elem.getSize().getWidth(),
							elem.getSize().getHeight());

					int clr = image.getRGB(pixelX, pixelY);
					color = colorUtil.getColorNameFromHex(clr);
					stream.close();
				} catch (IOException ex) {
					System.out.println("ERROR closing image input stream: " + ex.getClass().getSimpleName());
				}
			}
		} catch (Exception ex) {
			System.out.println("File couldn't be read.");
		}

		System.out.print("( X : " + pixelX + ", Y-" + pixelY + ") | ");
		System.out.println("Element color at given co-ordinate is :" + color);

		return color;
	}

	
	public boolean waitForPixelToContainColor(int xCoordinate, int yCoordinate, String expectedColor, int timeout) {

		String color = ""; 
		timer.set(System.currentTimeMillis()+timeout);
		expectedColor = expectedColor.toLowerCase();
		
		while ((!color.contains(expectedColor) | !color.contains(expectedColor))) {
			if(System.currentTimeMillis()>=timer.get())
				{	System.out.println("Timeout occured for the element to contain color ");	break;}
			try {
				color = pixelColorAtCordinate(xCoordinate, yCoordinate).toLowerCase();
				if (color.contains(expectedColor))
						{ System.out.println("Target contains color :"+ color); return true; }
			}
			catch (Exception e) {
				System.out.println(e.getClass().getSimpleName());
			}
		}
	System.out.println("Color expected :"+expectedColor+" Color found: "+color);
	return false;


	}

	
	public boolean waitForElementToContainColor (By element, int xCoordinate, int yCoordinate, String expectedColor, int waitTime ) throws InterruptedException, IOException {
		String color = ""; 
		timer.set(System.currentTimeMillis()+waitTime);
		expectedColor = expectedColor.toLowerCase();
	
		while ((!color.contains(expectedColor) | !color.contains(expectedColor))) {
			if(System.currentTimeMillis()>=timer.get())
				{	System.out.println("Timeout occured for the element to contain color ");	break;}
			try {
				color = pixelColorAtCordinate(element, xCoordinate, yCoordinate).toLowerCase();
				if (color.contains(expectedColor))
						{ System.out.println("Target is of color :"+ color); return true; }
			}
			catch (Exception e) {
				System.out.println(e.getClass().getSimpleName());
			}
		}
	System.out.println("Color expected :"+expectedColor+" Color found: "+color);
	return false;

	}
	
	

	
	
	
	
	
}
