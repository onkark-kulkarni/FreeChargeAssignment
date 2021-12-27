package freecharge.Myntra.EntityPack;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Message;

import freecharge.Util.DriverFunctions;

public class CommonFunctions extends DriverFunctions {

	public static final String ACCOUNT_SID = "ACa7b7fcde8ae79916f0d6cba709c9686c";
	public static final String AUTH_TOKEN = "e30b6949ad40e9ff87fdf0e76ab4b5a5";

	public CommonFunctions(String moduleName, String browser) {
		super(moduleName, browser);
		// TODO Auto-generated constructor stub
	}

	public void doLogin() {
		String mobileNumber = getConfigValue("MOBILE_NUMBER");
		String password = getConfigValue("PASSWORD");
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

	public static String getMessage() {
		return getMessages().filter(m -> m.getDirection().compareTo(Message.Direction.INBOUND) == 0)
				.filter(m -> m.getTo().equals("+13343734019")).map(Message::getBody).findFirst()
				.orElseThrow(IllegalStateException::new);
	}

	private static Stream<Message> getMessages() {
		ResourceSet<Message> messages = Message.reader(ACCOUNT_SID).read();
		return StreamSupport.stream(messages.spliterator(), false);
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

	public void addPinCode() throws IOException {
		waitForElement("commonFunctions:ENTERPINCODE");
		jsClickOnElement("commonFunctions:ENTERPINCODE");
		enterText("commonFunctions:PINCODE", "411021");
		clickOnElement("commonFunctions:CHECK");
		waitForElement("commonFunctions:DELIVERYPINCODE");
		String pinCode = getElementText("commonFunctions:DELIVERYPINCODE");
		try {
			assertThat(pinCode.equals("411021"));
			reportEntry(LogStatus.PASS, "Verify the address",
					reportScreenShot(capture("address")) + "Address is added");
		} catch (AssertionError e) {
			reportEntry(LogStatus.FAIL, "Verify the address",
					reportScreenShot(capture("address")) + "Address is not added");
		}

	}

	public void addAddressDetails() {
		// TODO Auto-generated method stub
		waitForElement("commonFunctions:CONTACTDETAILS");
		enterText("commonFunctions:NAME", "Test");
		enterText("commonFunctions:MOBNUMBER", "9970385535");
		enterText("commonFunctions:ENTADDRESS", "F203,Pebbles");
		enterText("commonFunctions:LOCALITY", "Pune");
		clickOnElement("commonFunctions:ADDADDRESS");
		waitForElement("commonFunctions:CONTINUEPAYMENT");
		clickOnElement("commonFunctions:CONTINUEPAYMENT");

	}
	
	public void doPayment() throws IOException {
		waitForElement("commonFunctions:PAYMENT");
		String payment=getElementText("commonFunctions:PAYMENT");
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
