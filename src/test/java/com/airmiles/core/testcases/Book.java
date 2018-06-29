package com.airmiles.core.testcases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;

import com.airmiles.core.base.BaseTest;
import com.airmiles.core.util.Xls_Reader;

public class Book extends BaseTest {

	public static void main(String[] args) throws IOException,InterruptedException {
		
	  // Read Properties files
		
		Properties prop = new Properties () ;
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")
				+ "//src//test//resources//config.properties");
		prop.load(fs);
		
		// Reading Excel File 
		
		Xls_Reader datatable = new Xls_Reader ("C:\\Users\\puneet.kashyap\\workspace\\maven_airmiles_framework\\Airmiles.xlsx");
		int r= datatable.getRowCount("Data");
		System.out.println("Total number of rows are >>>>>>" + r);
		int c = datatable .getColumnCount("Data");
		System.out.println("Total number of columns are >>>>>" + c);
		
		// Looping through all the rows in excel 
		
		for (int i=2 ; i<=2 ; i++){
			ChromeDriver driver = new ChromeDriver();
			
			driver.get(prop.getProperty("appurl"));

			
			// Opening Flight tabs
			
			String tripType = datatable.getCellData("Data", "Trip Type", i).trim();
			System.out.println("Trip type is " + tripType);
			String fromCity = datatable.getCellData("Data", "From", i).trim();
			System.out.println("Departure city is " + fromCity);
			String toCity = datatable.getCellData("Data", "To", i).trim();
			System.out.println("Arrival city is " + toCity);
			
			// Retrieving today's date 
			String today = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
			System.out.println("Today's date is " + today);
			
			// Split Today's date 
			
			String [] tdparts = today.split("/");
			String tday = tdparts[0];
			String tmon = tdparts[1];
			String tyear = tdparts[2];
			System.out.println("Current month is " + tmon);
			
			String departureDate = datatable.getCellData("Data", "Departure Date", i);
			System.out.println("Departure Date is " + departureDate);
			
			// Split the departure day,month and year 
			
			String[] dparts = departureDate.split("/");
			String depDay = dparts[0];
			System.out.println("Departure date is " + depDay);
			String depMon = dparts[1];
			System.out.println("Daparture month is " + depMon);
			String depYear = dparts[2];
			System.out.println("Departure month is " + depYear);
			
			int dClicks = Integer.parseInt(depMon)- Integer.parseInt(tmon);
			System.out.println("Number of departure clicks required is " + dClicks);
			
			String returnDate = datatable.getCellData("Data", "Returning Date", i).trim();
			System.out.println("Return date is " + returnDate);
			
		
			// Split the return day,month and year
			
			if (!returnDate.isEmpty()){
				String[]rparts = returnDate.split("/");
				String retDay = rparts[0];
				System.out.println("Return Date is  " + retDay);
				String retMon = rparts[1];
				System.out.println("Return Month is  " + retMon);
				String retYear = rparts[2];
				System.out.println("Return year is " + retYear);
				
				int rClicks = Integer.parseInt(retMon)- Integer.parseInt(depMon);
				System.out.println("Number of return clicks required is " + rClicks);

			}
			
			System.out.println(tripType);
			// Clicking to select flight type
			
			if (tripType.equalsIgnoreCase("Round Trip")){
			driver.findElementByXPath(prop.getProperty("return_xpath")).click();
			}
			else if (tripType.equalsIgnoreCase("One way")){
			driver.findElementByXPath(prop.getProperty("oneway_xpath")).click();
			}else {
				System.out.println("Invalid Trip entered");
			}
		
		// Departure and arrival city selected
			driver.findElementByXPath(prop.getProperty("From_xpath")).sendKeys(fromCity);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			Thread.sleep(2000);
			driver.findElementByXPath(prop.getProperty("airportselect_xpath")).click();
			
			driver.findElementByXPath(prop.getProperty("To_xpath")).sendKeys(toCity);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			Thread.sleep(2000);

			driver.findElementByXPath(prop.getProperty("airportselect_xpath")).click();


			
			
		Thread.sleep(5000);
		
		driver.quit();
		
		}
		
		
		
		

	}

}

