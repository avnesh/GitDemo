package oss.quantum;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;



public class quantumcorpbase {
    protected WebDriver driver;
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest test;


	@BeforeClass
    public void setUp() throws InterruptedException, EncryptedDocumentException, IOException {


		driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://uat.quantumcorphealth.in");
        Thread.sleep(3000);
		login();
    }

	@AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        extent.flush();
    }

	private void login() throws InterruptedException, EncryptedDocumentException, IOException {

		test = extent.createTest("Login Test");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        driver.findElement(By.xpath("//span[text()='Login']")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//span[text()='Login']")).click();

        FileInputStream fileinput = new FileInputStream("D:\\Resources\\TestData.xlsx");
        Workbook book = WorkbookFactory.create(fileinput);

        // Calling Excel sheet
        Sheet sheet = book.getSheet("Sheet1");
        // get data from excel for email
        String email = sheet.getRow(1).getCell(0).getStringCellValue();

        // for otp first number
        double Otp = sheet.getRow(1).getCell(1).getNumericCellValue();
        // converting number to string
        String otp = String.format("%.0f", Otp);

        driver.findElement(By.xpath("//input[@name='email_or_mobile_no']")).sendKeys(email);

        Thread.sleep(3000);
        driver.findElement(By.xpath("//button[@class='otp-btn btn btn-secondary']")).click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        driver.findElement(By.xpath("//input[@name='otp']")).sendKeys(otp);
        driver.findElement(By.xpath("//button[@class='otp-btn btn btn-secondary']")).click();

        Thread.sleep(5000);
        test.pass("Login Test Passed");

}
}

