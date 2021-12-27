package freecharge.Util;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class DriverFunctions extends Utilities {

	// Declaring all Objects
	private Driver _driver;
	private WebDriver driver;
	private JavascriptExecutor js;
	private WebDriverWait wait;
	private Reports _report;
	private ExtentReports report;
	private String testModule = "";
	private ExtentTest test;
	private Actions actions;

	// Constructor initializing all Objects
	public DriverFunctions(String module, String browser) {
		getCurrentTimeStamp();
		create_folder();
		testModule = module;
		_report = Reports.getReportInstance();
		report = _report.html_Report();
		_driver = Driver.getDiverInstance(browser);
		driver = _driver.getDriver();
		js = (JavascriptExecutor) driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		actions=new Actions(driver);
	}

	/************************************************************
	 * 
	 * 1. Selenium Framework Methods
	 * 
	 ************************************************************/

	// This method will maximize the browser window
	public void browserMaximize() {
		driver.manage().window().maximize();
	}

	// This method will return locator according to user's parameters
	private By locator(String locator, String value) {
		if (locator.equalsIgnoreCase("xpath")) {
			return By.xpath(value);
		} else if (locator.equalsIgnoreCase("id")) {
			return By.id(value);
		} else if (locator.equalsIgnoreCase("name")) {
			return By.name(value);
		} else if (locator.equalsIgnoreCase("cssSelector")) {
			return By.cssSelector(value);
		} else if (locator.equalsIgnoreCase("tagName")) {
			return By.tagName(value);
		} else if (locator.equalsIgnoreCase("linkText")) {
			return By.linkText(value);
		} else if (locator.equalsIgnoreCase("partialLinkText")) {
			return By.partialLinkText(value);
		} else if (locator.equalsIgnoreCase("className")) {
			return By.className(value);
		} else
			return null;
	}

	// This method will return the element
	public WebElement getElement(String prop) {
		return driver.findElement(locator(locatorType(prop), locatorValue(prop)));
	}

	// This method will return the locator type
	private String locatorType(String property_Name) {
		String property_Type;
		String property_type[] = null;

		property_Type = getPropValue(property_Name);
		property_type = property_Type.split("]: ");
		
		return property_type[0].substring(1).trim();

	}

	// This method will return the locator type
	private String locatorValue(String property_Name) {
		String property_Value;
		String property_value[] = null;

		property_Value = getPropValue(property_Name);
		property_value = property_Value.split("]: ");
		
		return property_value[1].trim();

	}

	//This method will return list of web elements
	public List<WebElement> listOfElements(String prop){
		return driver.findElements(locator(locatorType(prop), locatorValue(prop)));
	}
	
	//This method will Open the application URL
	public void openAppURL(String url) {
		clearCookies();
		driver.get(url);
	}
	
	//This method will delete the browser cookies
	private void clearCookies() {
		driver.manage().deleteAllCookies();
	}
	
	//This method will click on element
	public void clickOnElement(String prop) {
		waitForElementToBeClickable(prop);
		getElement(prop).click();
	}
	public void clickOnElement(WebElement element) {
		element.click();
	}
	
	//This method is used to enter the Text
	public void enterText(String prop,String val) {
		waitForElement(prop);
		getElement(prop).sendKeys(val);
	}
	public void enterText(WebElement element,String val) {
		element.sendKeys(val);
	}
	
	//This method will capture the screenshot along with the timestamp
	//File name will be the name for the screen shot.
	public String capture(String fileName) throws IOException {
		TakesScreenshot screenshot=(TakesScreenshot)driver;
		File srcFile=screenshot.getScreenshotAs(OutputType.FILE);
		String filePath=getPath()+"\\"+fileName+".png";
		File destFile=new File(filePath);
		FileUtils.copyFile(srcFile, destFile);
		return filePath;
	}
	
	//This method is used for page refresh
	public void pageRefresh() {
		driver.navigate().refresh();
	}
	
	//This method will close the driver
	public void closeAll() {
		_driver.closeBrowser();
	}
	
	//This method is used to close the report
	public void closeReport() {
		_report.closeReports();
	}
	
	//This method will wait for element
	public void waitForElement(String prop) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator(locatorType(prop), locatorValue(prop))));
	}
	public void waitForElementToBeClickable(String prop) {
		wait.until(ExpectedConditions.elementToBeClickable(locator(locatorType(prop), locatorValue(prop))));
	}
	
	//This method will return the page title
	public String getPageTitle() {
		return driver.getTitle();
	}
	
	//This method will capture element Text
	public String getElementText(String prop) {
		return driver.findElement(locator(locatorType(prop), locatorValue(prop))).getText();
	}
	public String getElementText(WebElement element) {
		return element.getText();
	}
	
	/************************************************************
	 * 
	 * 2.  Methods Related to Javascript Executor
	 * 
	 ************************************************************/
	
	
	//This method will click on element using JS executor
	public void jsClickOnElement(String prop) {
		js.executeScript("arguments[0].click();", getElement(prop));
		
	}
	public void jsClickOnElement(WebElement element) {
		js.executeScript("arguments[0].click();", element);
		
	}
	
	//This methode will scroll the page
	public void scroll(String prop) {
		//js.executeScript("arguments[0].scrollIntoView(true);", getElement(prop));
		js.executeScript("window.scrollBy(0,500)", "");
		
	}
	
	//This method will switch to new window
	public void switchToWindow(String winHandle) {
		Set<String> handles=driver.getWindowHandles();
		Iterator<String> itr=handles.iterator();
		while(itr.hasNext()) {
			String newWin=itr.next();
			if(!newWin.equals(winHandle)) {
				driver.switchTo().window(newWin);
			}
		}
		
	}
	
	//This method will give current Window Handle
	public String getWindowHandle() {
		return driver.getWindowHandle();
	}
	
	
	/************************************************************
	 * 
	 * 3.  Methods Related to logging and Reporting
	 * 
	 ************************************************************/
	
	public void initReport(String testModuleString) {
		test=report.startTest(testModule + "_"+ testModuleString);
		TestListener.f=1;
	}
	
	public void reportEntry(LogStatus logStatus,String stepName,String details) {
		test.log(logStatus, stepName, details);
	}
	public void reportEntry(LogStatus logStatus,String details) {
		test.log(logStatus, details);
	}
	
	public String reportScreenShot(String imagePath) {
		return test.addScreenCapture(imagePath);
	}
	
	/************************************************************
	 * 
	 * 4.  Methods Related to Actions Class
	 * 
	 ************************************************************/
	//This method will do mouse movement
	public void moveToElement(String prop) {
		actions.moveToElement(getElement(prop));
	}
	public void moveToElement(WebElement element) {
		actions.moveToElement(element).build().perform();
	}
}
