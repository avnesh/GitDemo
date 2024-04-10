package oss.quantum;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class quantumcorp extends quantumcorpbase  {

	private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest test;

    @Test(priority = 1)
    private void bookAppointment() throws InterruptedException {

    	test = extent.createTest("Book Appointment Test");
    	
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

    	WebElement healthCheckup= wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Health Check Ups']")));
    	healthCheckup.click();
        WebElement appointmentLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@class='menu menu--layout--classic']//a[normalize-space()='Book Appointment']")));
        appointmentLink.click();
        System.out.println(driver.getCurrentUrl());
        Thread.sleep(3000);
        WebElement vitaminLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h6[.='vitamin profile']")));
        vitaminLink.click();

        Thread.sleep(5000);
		driver.findElement(By.id("pincode")).click();

		driver.findElement(By.id("pincode")).sendKeys("400066");
		Thread.sleep(2000);
		driver.findElement(By.id("pincode")).sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.DELETE);

		driver.findElement(By.id("pincode")).sendKeys("400066");
		Thread.sleep(2000);
		driver.findElement(By.id("pincode")).sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.DELETE);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));



        driver.findElement(By.xpath("//button[@class='chooseBtn false ']")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		driver.findElement(By.xpath("//button[@class='btn cartBtn']")).click();
		//driver.findElement(By.cssSelector("a[href='/site/cart']")).click();
		test.pass("Book Appointment Test");

    }

   @Test(priority = 2)
    private void addPatient() throws InterruptedException {
    	// Add patient

	   test = extent.createTest("Add Patient Test");


	driver.findElement(By.xpath("//img[@alt='Add Patients']")).click();
//	Thread.sleep(3000);
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(25));
	driver.findElement(By.xpath("(//input[@class='input-check__input sample-input-check__input'])[3]")).click();
	driver.findElement(By.xpath("//button[@class='otp-btn btnWidth btn btn-secondary']")).click();
	
	test.pass("Add Patient Test");
    }

    @Test(priority = 3)
    private void scheduleAppointment() throws InterruptedException {

    	test = extent.createTest("Schedule Appointment Test");
    	
    	//Schedule Appointment
    	// Click on the schedule button
		String scheduleButtonXPath = "//div[@class='ant-picker scheduleBtn']//input[@id='appointmentDate']";
		WebElement scheduleButton = driver.findElement(By.xpath(scheduleButtonXPath));
		scheduleButton.click();

		// select the Date "28/03/2024"

 		String dateCellXPath = "//div[contains(text(),'20')]";
		WebElement dateCell = driver.findElement(By.xpath(dateCellXPath));
		dateCell.click();

		//WebElement dateCell = driver.findElement(By.xpath("//div[@class='ant-picker-cell-inner' and text()='28']"));
		//dateCell.click();


		//checkout

		driver.findElement(By.xpath("//span[@class='checkout-btn-width']")).click();
		
		test.pass("Schedule Appointment Test");

    }

    @Test(priority = 4)
    private void payment() throws InterruptedException
    {
    	test = extent.createTest("Payment Test");

    	
    	driver.findElement(By.xpath("//button[@class='btn scheduleBtn']")).click();

		//OpensRazorpay Modal

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		// Get the handles of all open windows before the new window opens
		Set<String> windowHandlesBefore = driver.getWindowHandles();
		Thread.sleep(3000);

		// Locate the iframe element
		WebElement iframeElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("razorpay-checkout-frame")));

		// Switch to the iframe
		driver.switchTo().frame(iframeElement);

		// Now you can interact with elements inside the iframe
		// locate and click the "Netbanking" button
		WebElement netbankingButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.new-method[method='netbanking']")));
		//button.new-method[method='netbanking']
		netbankingButton.click();

		// After interacting with the "Netbanking" button, locate and click the label
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		WebElement labelICICI = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("label[for='bank-radio-ICIC']")));
		labelICICI.click();


		// Click the "Pay Now" button
		driver.findElement(By.id("redesign-v15-cta")).click();
//			WebElement payNowButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("redesign-v15-cta")));
//			payNowButton.click();
//


		// Wait for the new window to open
		wait.until(ExpectedConditions.numberOfWindowsToBe(2));

		// Get the handles of all open windows after the new window opens
		Set<String> windowHandlesAfter = driver.getWindowHandles();

		// Find the handle of the new window
		String newWindowHandle = null;
		for (String handle : windowHandlesAfter) {
		    if (!windowHandlesBefore.contains(handle)) {
		        newWindowHandle = handle;
		        break;
		    }
		}

		// Switch to the new window
		driver.switchTo().window(newWindowHandle);

		// locate and click the "Success" button
		WebElement successButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-val='S']")));
		successButton.click();

		// Switch back to the original window
		driver.switchTo().window(windowHandlesBefore.iterator().next());
		
		test.pass("Payment Test");

    }
}
