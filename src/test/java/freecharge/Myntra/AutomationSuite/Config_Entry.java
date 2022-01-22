package freecharge.Myntra.AutomationSuite;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import freecharge.Myntra.EntityPack.CommonFunctions;
import freecharge.Util.Utilities;

public class Config_Entry {

	public static CommonFunctions commonLib;
	public static String tName="";
	public static int tCount=0;
	
	public void beforeClassConfig(CommonFunctions fnLib,String moduleName) {
		commonLib=fnLib;
		//commonLib.doLogin();
		}
	public void reportConfig(String tTitle) {
		commonLib.initReport(tTitle);
		
	}
	
	/*
	 * public static CommonFunctions getInstance() { if(commonLib==null) {
	 * 
	 * } }
	 */
	
	@BeforeSuite(alwaysRun = true)
	public void deleteExistingReport() {
		Utilities util=new Utilities();
		util.delete_folder();
	}
	
	@AfterClass(alwaysRun = true)
	public void closeAll() {
		tCount=0;
		commonLib.closeAll();
	}
	
	@BeforeMethod(alwaysRun = true)
	public void testNameDefault() {
		tCount++;
		tName="Test case did not run as expected";
	}
	
	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult result) {
		IRetryAnalyzer retry=result.getMethod().getRetryAnalyzer(result);
		if(retry==null) {
			return;
		}
		//result.getTestContext().getSkippedTests().removeResult(result.getMethod());
	}
	
}
