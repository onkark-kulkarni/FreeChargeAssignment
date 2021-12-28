package freecharge.Myntra.AutomationSuite;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import freecharge.Myntra.EntityPack.FunctionsShopping;

public class ShoppingTest extends Config_Entry {

	FunctionsShopping fnLib;

	@BeforeClass(alwaysRun = true)
	private void beforeClass() {
		fnLib = new FunctionsShopping("Shopping", "Chrome");
		System.out.println("after invocation");
		beforeClassConfig(fnLib, "Shopping");
	}

	@Test
	public void verifyShopping() throws IOException {
		reportConfig("Verify Shopping scenario");
		fnLib.verifyMyntraHomePage();
		fnLib.selectSubCategory("Men", "Jeans");
		fnLib.addItemToBagAndPlaceOrder();
		fnLib.doPayment();
		tName = "Shopping scenario is verified";
	}
}
