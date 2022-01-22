package freecharge.Myntra.EntityPack;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import jxl.read.biff.BiffException;

public class FunctionsShopping extends CommonFunctions {

	public FunctionsShopping(String moduleName, String browser) {
		super(moduleName, browser);
		// TODO Auto-generated constructor stub
	}

	public void verifyMyntraHomePage() throws IOException {
		reportEntry(LogStatus.PASS, "Verify the Myntra Home page",
					reportScreenShot(capture("HomePage")) + "User is landed on Home Page");
		/*verifyUserLoggedIn();
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
		}*/
	}

	public void selectSubCategory(String category, String subCategory) throws IOException {
		selectCategoryAndSubCategoryFromHomePage(category, subCategory);

	}

	public void addItemToBagAndPlaceOrder(String name, String address,String locality,String pincode) throws IOException {
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
		addPinCode(name,address,locality,pincode);
		waitForElement("shopping:PLACEORDER");
		pageRefresh();
		scroll("shopping:PLACEORDER");
		jsClickOnElement("shopping:PLACEORDER");
		waitForElement("commonFunctions:CONTINUEPAYMENT");
		  clickOnElement("commonFunctions:CONTINUEPAYMENT");
		

	}

	public Object[][] addressData(String tableName) throws BiffException, IOException {
		String xlsFilePath=System.getProperty("user.dir")+getConfigValue("ADDRESS_WORKBOOK");
		String sheetName=getConfigValue("ADDRESS_SHEET");
		Object[][] excelData=null;
		excelData=excelReader(xlsFilePath, sheetName, tableName);
		return excelData;
	}

}
