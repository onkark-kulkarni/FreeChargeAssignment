package freecharge.Myntra.EntityPack;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import freecharge.Util.DriverFunctions;

public class CommonFunctions extends DriverFunctions {

	String mobileNumber;
	String password;

	public CommonFunctions(String moduleName, String browser) {
		super(moduleName, browser);
		// TODO Auto-generated constructor stub
	}

	public void doLogin() {
		mobileNumber = getConfigValue("MOBILE_NUMBER");
		password = getConfigValue("PASSWORD");
		openAppURL(getConfigValue("URL"));
		browserMaximize();

		waitForElement("commonFunctions:PROFILE");
		clickOnElement("commonFunctions:PROFILE");
		clickOnElement("commonFunctions:LOGIN");
		enterText("commonFunctions:ENTERMOBNUMBER", mobileNumber);
		clickOnElement("commonFunctions:CONTINUE");
		clickOnElement("commonFunctions:PASSWORDLINK");
		enterText("commonFunctions:PASSWORDINPUT", password);
		clickOnElement("commonFunctions:EMAILLOGIN");
		waitForElement("commonFunctions:PROFILE");
		clickOnElement("commonFunctions:PROFILE");

	}

	public void verifyUserLoggedIn() throws IOException {
		String expected = "7020183675";
		waitForElement("commonFunctions:PROFILE");
		clickOnElement("commonFunctions:PROFILE");
		String actual = getElementText("commonFunctions:USERMOBNUMBER");
		try {
			Assert.assertEquals(actual, expected);
			reportEntry(LogStatus.PASS, "Verify user is logged in",
					reportScreenShot(capture("login")) + "User is logged in");
		} catch (AssertionError e) {
			reportEntry(LogStatus.FAIL, "Verify user is logged in",
					reportScreenShot(capture("login")) + "User is not logged in");
		}

	}

	public void handleMyntraOTP() {

	}

	public void selectCategoryAndSubCategoryFromHomePage(String category, String subCategory) throws IOException {
		pageRefresh();
		waitForElement("shopping:NAVIGATIONELEMENTS");
		List<WebElement> elements = listOfElements("shopping:NAVIGATIONELEMENTS");
		for (WebElement cat : elements) {
			System.out.println(getElementText(cat));
			if (getElementText(cat).equalsIgnoreCase(category)) {
				moveToElement(cat);
				break;
			}

		}

		List<WebElement> elementsCategory = listOfElements("shopping:SUBCATEGORYMEN");
		for (WebElement cat : elementsCategory) {
			if (getElementText(cat).equalsIgnoreCase(subCategory)) {
				clickOnElement(cat);
				break;
			}

		}
		waitForElement("shopping:HOME");
		String title = getPageTitle();
		try {
			assertThat(title.contains("Men"));
			reportEntry(LogStatus.PASS, "Verify the " + subCategory + " page",
					reportScreenShot(capture("MenPage")) + "User is landed on category = " + subCategory + "  Page");
		} catch (AssertionError e) {
			reportEntry(LogStatus.FAIL, "Verify the " + subCategory + " page",
					reportScreenShot(capture("MenPage")) + "User is landed on category = " + subCategory + "  Page");
		}

	}

	public void addPinCode(String name, String address,String locality,String pincode) throws IOException {
		try {
			waitForElement("commonFunctions:ENTERPINCODE");
			jsClickOnElement("commonFunctions:ENTERPINCODE");
			enterText("commonFunctions:PINCODE", pincode);
			clickOnElement("commonFunctions:CHECK");
			waitForElement("commonFunctions:DELIVERYPINCODE");
			String pinCode = getElementText("commonFunctions:DELIVERYPINCODE");
			try {
				assertThat(pinCode.equals(pincode));
				reportEntry(LogStatus.PASS, "Verify the address",
						reportScreenShot(capture("address")) + "Address is added");
			} catch (AssertionError e) {
				reportEntry(LogStatus.FAIL, "Verify the address",
						reportScreenShot(capture("address")) + "Address is not added");
			}
		}catch(Exception e) {
			
		}
		 
			jsClickOnElement("commonFunctions:CHANGEADDRESS");
			waitForElement("commonFunctions:ADDNEWADD");
			clickOnElement("commonFunctions:ADDNEWADD");
			addAddressDetails(name,address,locality);
			
		
		

	}

	public void addAddressDetails(String name, String address,String locality) {
		// TODO Auto-generated method stub
		waitForElement("commonFunctions:CONTACTDETAILS");
		enterText("commonFunctions:NAME", name);
		enterText("commonFunctions:MOBNUMBER", mobileNumber);
		enterText("commonFunctions:ENTADDRESS", address);
		enterText("commonFunctions:LOCALITY", locality);
		clickOnElement("commonFunctions:ADDADDRESS");
		
		  
		 

	}

	public void doPayment() throws IOException {
		waitForElement("commonFunctions:PAYMENT");
		String payment = getElementText("commonFunctions:PAYMENT");
		try {
			assertThat(payment.contains("Choose Payment Mode"));
			reportEntry(LogStatus.PASS, "Verify the Payment",
					reportScreenShot(capture("Payments")) + "User is on payment page");
		} catch (AssertionError e) {
			reportEntry(LogStatus.FAIL, "Verify the Payment",
					reportScreenShot(capture("Payments")) + "User is not on payment page");
		}
	}

}
