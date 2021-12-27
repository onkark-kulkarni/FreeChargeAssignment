package freecharge.Util;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class Reports {

	private Utilities putil = null;
	static ExtentReports report;
	ExtentTest test;
	private static Reports _report = null;

	private Reports() {
		putil = new Utilities();
		report = new ExtentReports(System.getProperty("user.dir") + putil.getConfigValue("REPORT_PATH") + "\\Report"
				+ putil.getTimeStamp() + ".html");
	}

	public static Reports getReportInstance() {
		if (_report == null) {
			_report = new Reports();
		}
		return _report;
	}

	public ExtentReports html_Report() {
		return report;
	}

	public void closeReports() {
		report.endTest(test);
		report.flush();
	}

}
