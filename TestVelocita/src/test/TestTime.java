package test;

import static org.junit.Assert.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class TestTime {
	int distance = 20; 	    // Km
	int speedMiles1 = 75;   // Miles/h
	int speedMiles2 = 55;   // Miles/h
	float speedKm1;	   		// Km/h
	float speedKm2;	   		// Km/h
	float time1 = 0;		// minutes
	float time2 = 0;
	float actual = 0;	
	float expected = 5;
	WebDriver webDriver = null;
	WebElement webElement;
	
	@Before
	public void getConversion() {
		try {
			System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--start-maximized"); //start chrome in fullscreen
			webDriver = new ChromeDriver();
			webDriver.get("https://www.rapidtables.com/convert/length/index.html");
			
			Thread.sleep(1000);
			
			webElement = webDriver.findElement(By.xpath("//*[@id=\"mi\"]"));
			webElement.clear();
			webElement.sendKeys(String.valueOf(speedMiles1));
			webDriver.findElement(By.xpath("//*[@id=\"doc\"]/form/table/tbody/tr[7]/td[4]/input")).click();
			webElement = webDriver.findElement(By.xpath("//*[@id=\"km\"]"));
			speedKm1 = Float.parseFloat(webElement.getAttribute("value"));
			
			Thread.sleep(2000);
			
			webElement = webDriver.findElement(By.xpath("//*[@id=\"mi\"]"));
			webElement.clear();
			webElement.sendKeys(String.valueOf(speedMiles2));
			webDriver.findElement(By.xpath("//*[@id=\"doc\"]/form/table/tbody/tr[7]/td[4]/input")).click();
			webElement = webDriver.findElement(By.xpath("//*[@id=\"km\"]"));
			speedKm2 = Float.parseFloat(webElement.getAttribute("value"));	
		}
		
		catch(Exception e){
			Logger.getGlobal().log(Level.SEVERE, "Selenium Driver Exception");
		}
		finally {
			if(webDriver != null) {
				Logger.getGlobal().log(Level.INFO, "Driver closed");
				webDriver.close();
			}
		}
	}

	
	@Test
	public void test() {
		time1 = (distance/speedKm1)*60; //Tempo espresso in minuti
		time2 = (distance/speedKm2)*60; //Tempo espresso in minuti
		System.out.println("MPH: "+speedMiles1 + " -> KM/H: " + speedKm1 + " -> TIME: " + time1 + " minutes");
		System.out.println("MPH: "+speedMiles2 + " -> KM/H: " + speedKm2 + " -> TIME: " + time2 + " minutes");
		actual = time2-time1;
		assertTrue(actual < expected);
	}

}
