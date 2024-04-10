package oss.quantum;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.relevantcodes.extentreports.LogStatus;

	public class QuantumCorpHealthTests {
		
		public WebDriver driver;
		public ExtentReports extent = ExtentManager.getInstance();
		public ExtentTest extentTest;
		
		

	    @BeforeTest
	    public void setExtent() {
	    	extent.setSystemInfo("Host Name","Avnesh PC");
	    	
	    }
	    
	    @AfterTest
	    public void endReport() {
	    	extent.flush();
	    	//extent.close();
	    	
	    }

			@BeforeMethod
		    public void setUp() throws InterruptedException {
		        
		        
		        driver = new ChromeDriver();
		        driver.manage().window().maximize();
		        driver.get("https://uat.quantumcorphealth.in");
		        Thread.sleep(3000);
		        
		    }
			
			


//		    @Test
//		    public void login() throws InterruptedException, IOException {
//		        loginPage();
//		        bookAppointment();
//		        addPatient();
//		        scheduleAppointment();
//		        payment();
//
//		    }


			@Test(priority = 1)
		    private void loginPage() throws InterruptedException, IOException {
				
				extentTest = extent.createTest("Login Page Test");
//		        driver.get("https://uat.quantumcorphealth.in");
//		        Thread.sleep(3000);
		        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		        Thread.sleep(3000);
		        driver.findElement(By.xpath("//span[text()='Login']")).click();

		        FileInputStream fileinput=new FileInputStream("D:\\Resources\\TestData.xlsx");
				Workbook book = WorkbookFactory.create(fileinput);

				//Calling Excel sheet
				Sheet sheet = book.getSheet("Sheet1");
				// get data from excel for email
				String email = sheet.getRow(1).getCell(0).getStringCellValue();

				//for otp firts number
				double Otp = sheet.getRow(1).getCell(1).getNumericCellValue();
				//converting number to string
				//String otp = String.valueOf((long) otpDouble);
				String otp = String.format("%.0f", Otp);



			    driver.findElement(By.xpath("//input[@name='email_or_mobile_no']")).sendKeys(email);

			    Thread.sleep(3000);
				driver.findElement(By.xpath("//button[@class='otp-btn btn btn-secondary']")).click();

				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

				driver.findElement(By.xpath("//input[@name='otp']")).sendKeys(otp);
				driver.findElement(By.xpath("//button[@class='otp-btn btn btn-secondary']")).click();


				Thread.sleep(5000);

				driver.findElement(By.xpath("//span[normalize-space()='Health Check Ups']")).click();
				Thread.sleep(3000);
	//			driver.findElement(By.xpath("(//a[normalize-space()='Book Appointment'])[3]")).click();
				driver.findElement(By.xpath("//ul[@class='menu menu--layout--classic']//a[normalize-space()='Book Appointment']")).click();
				System.out.println(driver.getCurrentUrl());
				Thread.sleep(3000);
				driver.findElement(By.xpath("//h6[.='vitamin profile']")).click();
				

		    }

			@Test(priority = 2)
		    private void bookAppointment() throws InterruptedException, IOException {

				extentTest=extent.createTest("bookApppointment");
			

				//Thread.sleep(5000);
				//driver.findElement(By.xpath("//div[contains(text(),'Select Health Center')]")).click();
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
				


		    }

			@Test(priority = 3)
		    public void addPatient() throws InterruptedException, IOException{
			    extentTest=extent.createTest("addPatient");

		    	// Add patient

				driver.findElement(By.xpath("//img[@alt='Add Patients']")).click();
				Thread.sleep(3000);

				driver.findElement(By.xpath("(//input[@class='input-check__input sample-input-check__input'])[3]")).click();
				driver.findElement(By.xpath("//button[@class='otp-btn btnWidth btn btn-secondary']")).click();
				
		    }

			@Test(priority = 4)
		    public void scheduleAppointment() throws InterruptedException, IOException{

				 extentTest=extent.createTest("scheduleAppointment");
				
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
				Thread.sleep(5000);
				
		    }

			@Test(priority = 5)
		    public void payment() throws InterruptedException, IOException {
				
				 extentTest=extent.createTest("payment");

		    	driver.findElement(By.xpath("//button[@class='btn scheduleBtn']")).click();

				//OpensRazorpay Modal

				Thread.sleep(2000);

				// Explicit wait
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
				Thread.sleep(3000);
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
				
		    }
			
			
			public static String getScreenshot(WebDriver driver,String screenshotName) throws IOException {
				
				String dateName = new SimpleDateFormat("yyyymmddhhmmss").format(new Date());
			    TakesScreenshot ts = (TakesScreenshot) driver;
			    File source = ts.getScreenshotAs(OutputType.FILE);
			    String destination = System.getProperty("user.dir")+ "/FailedTest Screenshot/"+ screenshotName+dateName + ".png";
			    File finalDestination = new File(destination);
			    FileUtils.copyFile(source, finalDestination);
			    return destination;
			}
		
			@AfterMethod
		    public void afterMethod(ITestResult result) throws IOException {
				if(result.getStatus()== ITestResult.FAILURE) {
					extentTest.log(Status.FAIL,"Test Case Failed is"+result.getName());
					extentTest.log(Status.FAIL,"Test Case Failed is"+result.getThrowable());
					
					String screenshotPath = QuantumCorpHealthTests.getScreenshot(driver,result.getName());
					extentTest.log(Status.FAIL, "Test failed");
					extentTest.addScreenCaptureFromPath(screenshotPath);
					
					//extentTest.log(Status.FAIL, extentTest.addScreenCapture(screenshotPath));
				}
				else if(result.getStatus()==ITestResult.SKIP){
					extentTest.log(Status.SKIP,"Test Case Skipped is"+result.getName());
				}
				else  {
					extentTest.log(Status.PASS,"Test Case Passed is"+result.getName());
				}
				//extent.endTest(extentTest);
				if (driver != null) {
		            driver.quit();
		        
			
				}
			}
		        

			
//			@AfterClass
////			
//		    public void tearDown() {
//		        if (driver != null) {
//		            driver.quit();
//		        }
//		        extent.flush();
//			}

}


