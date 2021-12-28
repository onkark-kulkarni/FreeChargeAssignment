package freecharge.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Properties;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Utilities {

	private String path = "";
	private String ts = "";

	// This method will give the Current time stamp.
	public void getCurrentTimeStamp() {
		DateFormat df = DateFormat.getDateTimeInstance();
		Date date = new Date();
		String dateValue = df.format(date);
		dateValue = dateValue.replaceFirst(":", "_");
		dateValue = dateValue.replaceFirst(",", "_");
		ts = dateValue;

	}

	// This method will create new folder along with time stamp for saving the
	// screen shot.
	public void create_folder() {
		path = System.getProperty("user.dir") + getConfigValue("REPORT_PATH") + "\\" + "ScreenShots";
		File outputFile = new File(path);
		outputFile.mkdir();
	}

	// This method will return the Time stamp.
	public String getTimeStamp() {
		return ts;
	}

	// This method will return the path
	public String getPath() {
		return path;
	}

	// This method will delete the folder from reports directory
	public void delete_folder() {
		File file = new File(System.getProperty("user.dir") + getConfigValue("REPORT_PATH") + "\\");
		deleteDirectory(file);
		File logFile = new File(System.getProperty("user.dir") + getConfigValue("LOG_PATH") + "\\");
		deleteDirectory(logFile);
	}

	// This method has logic for deleting the folder
	public void deleteDirectory(File file) {

		if (!file.exists()) {

		} else {
			if (file.isDirectory()) {
				if (file.list().length == 0) {
					deleteEmptyDirectory(file);
				} else {
					File[] files = file.listFiles();
					for (File fileDelete : files) {
						deleteDirectory(fileDelete);

					}
					if (file.list().length == 0) {
						deleteEmptyDirectory(file);
					}
				}
			} else {
				file.delete();
			}
		}
	}

	// This method will delete empty directory
	private void deleteEmptyDirectory(File file) {
		if (file.getName().trim().equalsIgnoreCase("TestResults")) {

		} else {
			file.delete();
		}

	}

	// This method is used to read data from Config file
	public String getConfigValue(String key) {
		String key_value = "";
		FileInputStream input;
		String propertyFilePath = System.getProperty("user.dir") + "\\config.properties";
		try {
			input = new FileInputStream(new File(propertyFilePath));
			Properties obj_config = new Properties();
			try {
				input = new FileInputStream(new File(System.getProperty("user.dir") + "\\config.properties"));
				obj_config.load(input);
				key_value = obj_config.getProperty(key);

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException f) {
			f.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		}
		return key_value;

	}

	// This method is used to read data from properties file
	public String getPropValue(String property_Name) {

		FileInputStream input;
		String propertyVal = "";
		String property_value[] = property_Name.split(":");
		String modName = property_value[0].trim();
		String value = property_value[1].trim();
		String propertyFilePath = "";
		try {
			propertyFilePath = System.getProperty("user.dir") + "\\Locators" + "\\" + modName + ".properties";
			input = new FileInputStream(new File(propertyFilePath));
			Properties obj_prop = new Properties();
			try {
				input = new FileInputStream(new File(propertyFilePath));
				obj_prop.load(input);
				propertyVal = obj_prop.getProperty(value);

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException f) {
			f.printStackTrace();
			throw new RuntimeException("Locator.properties not found at " + propertyFilePath);
		}

		return propertyVal;
	}

	public String[][] excelReader(String xlsFilePath, String sheetName, String tableName)
			throws BiffException, IOException {

		Workbook workbook = Workbook.getWorkbook(new File(xlsFilePath));
		Sheet sheet = workbook.getSheet(sheetName);
		int startRow, startCol, endRow, endCol, citr_row, citr_col;
		String[][] tableArray;
		Cell tableStart = sheet.findCell(tableName);
		startRow = tableStart.getRow();
		startCol = tableStart.getColumn();
		Cell tableEnd = sheet.findCell(tableName + "End");
		endRow = tableEnd.getRow();
		endCol = tableEnd.getColumn();
		tableArray = new String[endRow - startRow - 1][endCol - startCol - 1];
		citr_row = 0;
		for (int itr_row = startRow + 1; itr_row < endRow; itr_row++, citr_row++) {
			citr_col = 0;
			for (int itr_col = startCol + 1; itr_col < endCol; itr_col++, citr_col++) {
				tableArray[citr_row][citr_col] = sheet.getCell(itr_col, itr_row).getContents();

			}
		}

		return (tableArray);
	}

}
