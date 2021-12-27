package freecharge.Myntra.EntityPack;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

public class FunctionsShopping extends CommonFunctions {

	public FunctionsShopping(String moduleName, String browser) {
		super(moduleName, browser);
		// TODO Auto-generated constructor stub
	}

	public void verifyMyntraHomePage() throws IOException {
		verifyUserLoggedIn();
		waitForElement("shopping:NAVIGATIONELEMENTS");
		String expectedTitle = "Online Shopping for Women, Men, Kids Fashion & Lifestyle - Myntra";
		String actualtitle = getPageTitle();
		try {
			Assert.assertEquals(actualtitle, expectedTitle);
			reportEntry(LogStatus.PASS, "Verify the Myntra Home page",
					reportScreenShot(capture("HomePage")) + "User is landed on Home Page");
		} catch (AssertionError e) {
			reportEntry(LogStatus.FAIL, "Verify the Myntra Home page",
					reportScreenShot(capture("HomePage")) + "User is not landed on Home Page");
		}
	}

	public void selectSubCategory(String category, String subCategory) throws IOException {
		selectCategoryAndSubCategoryFromHomePage(category, subCategory);

	}

	public void addItemToBagAndPlaceOrder() throws IOException {
		waitForElement("shopping:FIRSTRECORD");
		jsClickOnElement("shopping:FIRSTRECORD");
		switchToWindow(getWindowHandle());
		waitForElement("shopping:JEANSSIZE");
		String title = getPageTitle();
		try {
			assertThat(title.contains("Buy"));
			reportEntry(LogStatus.PASS, "Verify the selected record is opened",
					reportScreenShot(capture("Record")) + "Selected Record is opened");
		} catch (AssertionError e) {
			reportEntry(LogStatus.FAIL, "Verify the selected record is opened",
					reportScreenShot(capture("Record")) + "Selected Record is not opened");
		}
		clickOnElement("shopping:JEANSSIZE");
		clickOnElement("shopping:ADDTOBAG");
		waitForElement("shopping:BAGCOUNT");
		jsClickOnElement("shopping:BAG");
		waitForElement("shopping:RECORDINBAG");
		String recordCount = getElementText("shopping:RECORDINBAG");
		try {
			assertThat(recordCount.equalsIgnoreCase("1/1 ITEMS SELECTED"));
			reportEntry(LogStatus.PASS, "Verify Bag count",
					reportScreenShot(capture("bagCount")) + "Selected item is added in bag");
		} catch (AssertionError e) {
			reportEntry(LogStatus.FAIL, "Verify Bag count",
					reportScreenShot(capture("bagCount")) + "Selected item is not added in bag");
		}
		addPinCode();
		waitForElement("shopping:PLACEORDER");
		pageRefresh();
		scroll("shopping:PLACEORDER");
		jsClickOnElement("shopping:PLACEORDER");
		addAddressDetails();

	}

	

}
