package freecharge.Myntra.AutomationSuite;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import freecharge.Myntra.EntityPack.FunctionsShopping;
import jxl.read.biff.BiffException;

public class ShoppingTest extends Config_Entry {

	FunctionsShopping fnLib;

	@BeforeClass(alwaysRun = true)
	private void beforeClass() {
		fnLib = new FunctionsShopping("Shopping", "Chrome");
		System.out.println("after invocation");
		beforeClassConfig(fnLib, "Shopping");
	}
	
	  @Test (dataProvider = "dataForUserAddress")
	  public void verifyShopping(String name, String address,String locality,String pincode) throws IOException {
	  reportConfig("Verify Shopping scenario");
	  fnLib.verifyMyntraHomePage();
	 // fnLib.selectSubCategory("Men", "Jeans");
	 // fnLib.addItemToBagAndPlaceOrder(name,address,locality,pincode);
	 // fnLib.doPayment();
	  tName = "Shopping scenario is verified"; }
	 
	
	
	
	@DataProvider
	private Object[][] dataForUserAddress() throws BiffException, IOException{
		Object[][] excelData=null;
		excelData=fnLib.addressData("UserData");
		return excelData;
	}
}
