package freecharge.Util;

import org.testng.ITestListener;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.LogStatus;

import freecharge.Myntra.AutomationSuite.Config_Entry;

public class TestListener  extends Config_Entry implements ITestListener  {
	static int f=0;
	@Override
	public void onTestStart(ITestResult result) {
		
	}
	@Override
	public void onTestSuccess(ITestResult result) {
		commonLib.reportEntry(LogStatus.PASS, "TEST CASE EXECUTED: "+tCount+tName);
		commonLib.closeReport();
		f=0;
	}
	@Override
	public void onTestFailure(ITestResult result) {
		commonLib.reportEntry(LogStatus.FAIL, "TEST CASE EXECUTED: "+tCount+tName);
		commonLib.closeReport();
		f=0;
	}
	@Override
	public void onTestSkipped(ITestResult result) {
		if(f==0) {
		reportConfig("SKIPPED: "+ result.getMethod().getMethodName());
		tCount++;
		tName=result.getMethod().getMethodName();
		commonLib.reportEntry(LogStatus.SKIP, "TEST CASE EXECUTED: "+tCount+tName+" is skipped because of failure of dependent test case");
		}else {
			commonLib.reportEntry(LogStatus.SKIP, "TEST CASE EXECUTED: "+tCount+tName);
		}
		commonLib.closeReport();
		f=0;
	}

}
